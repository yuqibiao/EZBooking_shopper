package com.yyyu.ezbooking_shopper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.yyyu.barbecue.ezbooking_base.net.MyBitmapUtils;
import com.yyyu.barbecue.ezbooking_base.ui.widget.RoundImageView;
import com.yyyu.ezbooking_shopper.R;
import com.yyyu.ezbooking_shopper.bean.json.QuerySellerDetailJson;

import java.util.List;

/**
 * 功能：每个商品分类Item中GridView的Adapter
 *
 * @author yyyu
 * @date 2016/7/27
 */
public class GoodsAdapter extends BaseAdapter {

    private List<QuerySellerDetailJson.Goods> goodsList;

    private Context mContext;

    private int typePosition;

    public GoodsAdapter(Context context, int typePosition ,List<QuerySellerDetailJson.Goods> goodsList) {
        this.goodsList = goodsList;
        this.typePosition = typePosition;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return goodsList.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        GoodsHolder goodsHolder;
        if (convertView == null) {
            goodsHolder = new GoodsHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.gv_item_goods, parent, false);
            goodsHolder.rriv_goods_icon = (RoundImageView) convertView.findViewById(R.id.rriv_goods_icon);
            goodsHolder.tv_goods_name = (TextView) convertView.findViewById(R.id.tv_goods_name);
            goodsHolder.tv_goods_price = (TextView) convertView.findViewById(R.id.tv_goods_price);
            goodsHolder.cb_goods_num = (CheckBox) convertView.findViewById(R.id.cb_goods_num);
            convertView.setTag(goodsHolder);
        } else {
            goodsHolder = (GoodsHolder) convertView.getTag();
        }
        goodsHolder.tv_goods_name.setText(goodsList.get(position).goodsName);
        goodsHolder.tv_goods_price.setText("" + goodsList.get(position).goodsPrice + "RMB");
        MyBitmapUtils.getInstance(mContext).display(goodsHolder.rriv_goods_icon , goodsList.get(position).bigImage);
        return convertView;
    }

    public static class GoodsHolder {
        public RoundImageView rriv_goods_icon;
        public TextView tv_goods_name;
        public TextView tv_goods_price;
        public CheckBox cb_goods_num;
    }
}
