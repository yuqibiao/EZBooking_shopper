package com.yyyu.ezbooking_shopper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.yyyu.barbecue.ezbooking_base.ui.widget.NoScrollGridView;
import com.yyyu.barbecue.ezbooking_base.utils.MyLog;
import com.yyyu.ezbooking_shopper.R;
import com.yyyu.ezbooking_shopper.bean.json.QuerySellerDetailJson;
import com.yyyu.ezbooking_shopper.bean.param.ToConfirmOrderParams;
import com.yyyu.ezbooking_shopper.callback.OnAddGoodsFinishedListener;
import com.yyyu.ezbooking_shopper.ui.dialog.AddGoodsDialog;

import java.util.HashMap;
import java.util.List;

/**
 * 功能： 商品显示ListView的Adapter
 *
 * @author yyyu
 * @date 2016/7/27
 */
public class GoodsTypeAdapter extends BaseAdapter {

    private Context mContext;
    private List<QuerySellerDetailJson.GoodsType> goodsTypeList;
    private String sellerName;
    private String sellerIcon;
    /**维护选择的商品 key值为goodsId*/
    public HashMap<Integer, ToConfirmOrderParams.SubOrder>
            subOrderContainer = new HashMap<>();
    /**是否是更新选中商品*/
    public boolean isUpdate;

    public GoodsTypeAdapter(Context context, List<QuerySellerDetailJson.GoodsType> goodsTypeList,String sellerName , String sellerIcon) {
        this.goodsTypeList = goodsTypeList;
        this.mContext = context;
        this.sellerName = sellerName;
        this.sellerIcon = sellerIcon;
    }

    @Override
    public int getCount() {
        return goodsTypeList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int typePosition, View convertView, ViewGroup parent) {
        final GoodsTypeHolder goodsTypeHolder;
        if (convertView == null) {
            goodsTypeHolder = new GoodsTypeHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.lv_item_goods_show, parent, false);
            goodsTypeHolder.tv_goods_type_name = (TextView) convertView.findViewById(R.id.tv_goods_type_name);
            goodsTypeHolder.gv_type_goods_show = (NoScrollGridView) convertView.findViewById(R.id.gv_type_goods_show);
            convertView.setTag(goodsTypeHolder);
        } else {
            goodsTypeHolder = (GoodsTypeHolder) convertView.getTag();
        }
        goodsTypeHolder.tv_goods_type_name.setText(goodsTypeList.get(typePosition).goodsTypeName);
        GoodsAdapter goodsAdapter = new GoodsAdapter(mContext, typePosition, goodsTypeList.get(typePosition).goodsList);
        goodsTypeHolder.gv_type_goods_show.setAdapter(goodsAdapter);
        goodsTypeHolder.gv_type_goods_show.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int goodsPosition, long id) {

                final QuerySellerDetailJson.Goods goods = goodsTypeList.get(typePosition).goodsList.get(goodsPosition);

                final CheckBox cb_goods_num = (CheckBox) goodsTypeHolder.gv_type_goods_show.
                        getChildAt(goodsPosition).findViewById(R.id.cb_goods_num);

                int goodsNum = 0;
                if (cb_goods_num.getVisibility() == View.VISIBLE) {
                    String goodsNumStr = cb_goods_num.getText().toString().substring(1, cb_goods_num.getText().toString().length());
                    goodsNum = Integer.parseInt(goodsNumStr);
                }
                AddGoodsDialog dialog = new AddGoodsDialog(mContext, goods, goodsNum, sellerName, sellerIcon);
                dialog.show();
                dialog.setOnAddGoodsFinishedListener(new OnAddGoodsFinishedListener() {
                    @Override
                    public void onAddGoodsFinished(final String goodsNum) {
                        if (subOrderContainer.containsKey(goods.goodsId)) {
                            subOrderContainer.get(goods.goodsId).setGoodsCount(Integer.parseInt(goodsNum));
                            subOrderContainer.get(goods.goodsId).setGoodsTotalPrice(Integer.parseInt(goodsNum) * goods.goodsPrice + "");
                            if (subOrderContainer.get(goods.goodsId).getGoodsCount() <= 0) {//--删除为0的
                                subOrderContainer.remove(goods.goodsId);
                            }
                        }
                        if (Integer.parseInt(goodsNum) > 0) {//商品数目大于0
                            cb_goods_num.setVisibility(View.VISIBLE);
                        } else {
                            cb_goods_num.setVisibility(View.INVISIBLE);
                        }
                        cb_goods_num.setText("x" + goodsNum);
                        //---设置CheckBox的选择监听
                        cb_goods_num.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (isChecked) {
                                    if (Integer.parseInt(goodsNum) > 0) {
                                        ToConfirmOrderParams.SubOrder subOrder = new ToConfirmOrderParams.SubOrder();
                                        subOrder.setGoodsId(goods.goodsId);
                                        subOrder.setGoodsCount(Integer.parseInt(goodsNum));
                                        subOrder.setGoodsName(goods.goodsName);
                                        subOrder.setBigImage(goods.bigImage);
                                        subOrder.setGoodsTotalPrice((Integer.parseInt(goodsNum) * goods.goodsPrice) + "");
                                        subOrderContainer.put(goods.goodsId, subOrder);
                                    }
                                } else {
                                    subOrderContainer.remove(goods.goodsId);
                                }
                            }
                        });

                        cb_goods_num.setChecked(true);//选择后默认选上
                    }
                });
            }
        });
        return convertView;
    }

    public static class GoodsTypeHolder {
        public TextView tv_goods_type_name;
        public NoScrollGridView gv_type_goods_show;
    }

}
