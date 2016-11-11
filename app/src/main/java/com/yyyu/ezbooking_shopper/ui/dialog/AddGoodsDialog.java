package com.yyyu.ezbooking_shopper.ui.dialog;

import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.yyyu.barbecue.ezbooking_base.net.MyBitmapUtils;
import com.yyyu.barbecue.ezbooking_base.ui.dialog.BaseDialog;
import com.yyyu.barbecue.ezbooking_base.utils.DimensChange;
import com.yyyu.barbecue.ezbooking_base.utils.MyToast;
import com.yyyu.ezbooking_shopper.R;
import com.yyyu.ezbooking_shopper.bean.json.QuerySellerDetailJson;
import com.yyyu.ezbooking_shopper.callback.OnAddGoodsFinishedListener;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 功能： 添加商品的Dialog
 *
 * Created by yyyu on 2016/7/28.
 */
public class AddGoodsDialog extends BaseDialog{


    private CircleImageView civ_seller_icon;
    private TextView tv_seller_name;
    private TextView tv_goods_name;
    private TextView tv_goods_price;
    private TextView tv_goods_total_price;
    private TextView tv_goods_num;

    private OnAddGoodsFinishedListener onAddGoodsFinishedListener;
    private int goodsNum;
    String sellerName ;
    String sellerIconUrl;
    private QuerySellerDetailJson.Goods goods;
    private ImageView iv_goods_icon;

    public AddGoodsDialog(Context context) {
        super(context);
        lp.width = DimensChange.dp2px(context , 255);
        lp.dimAmount = 0.7f;
        getWindow().setAttributes(lp);
    }

    public AddGoodsDialog(Context context , QuerySellerDetailJson.Goods goods ,
                          int goodsNum , String sellerName , String sellerIconUrl){
        this(context);
        this.goods = goods;
        this.goodsNum = goodsNum;
        this.sellerName = sellerName;
        this.sellerIconUrl = sellerIconUrl;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_add_goods;
    }

    @Override
    protected void initView() {
        civ_seller_icon = getView(R.id.civ_seller_icon);
        tv_seller_name = getView(R.id.tv_seller_name);
        tv_goods_name = getView(R.id.tv_goods_name);
        tv_goods_price = getView(R.id.tv_goods_price);
        tv_goods_total_price = getView(R.id.tv_goods_total_price);
        tv_goods_num = getView(R.id.tv_goods_num);
        iv_goods_icon = getView(R.id.iv_goods_icon);
        //---set
        tv_goods_num.setText(""+goodsNum);
        tv_goods_name.setText(goods.goodsName);
        tv_goods_price.setText("*"+goods.goodsPrice+"RMB");
        double totalPrice = goodsNum*goods.goodsPrice;
        tv_goods_total_price.setText("*"+totalPrice+"RMB");
        MyBitmapUtils.getInstance(mContext).display(civ_seller_icon , sellerIconUrl);
        tv_seller_name.setText(sellerName);
        MyBitmapUtils.getInstance(mContext).display(iv_goods_icon , goods.bigImage);
    }

    @Override
    protected void initListener() {
        addOnClickListener(R.id.iv_goods_plus , R.id.iv_goods_add , R.id.tv_goods_submit);
    }

    public void setOnAddGoodsFinishedListener(OnAddGoodsFinishedListener onAddGoodsFinishedListener){
        this.onAddGoodsFinishedListener = onAddGoodsFinishedListener;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.iv_goods_plus:{//--商品减
                goodsNum--;
                if (goodsNum<0){
                    goodsNum=0;
                    MyToast.showShort(mContext , "不能再减了");
                    break;
                }
                tv_goods_num.setText(""+goodsNum);
                double totalPrice = goodsNum*goods.goodsPrice;
                tv_goods_total_price.setText("*"+totalPrice+"RMB");
                break;
            }
            case R.id.iv_goods_add:{//商品加
                goodsNum++;
                tv_goods_num.setText(""+goodsNum);
                double totalPrice = goodsNum*goods.goodsPrice;
                tv_goods_total_price.setText("*"+totalPrice+"RMB");
                break;
            }
            case R.id.tv_goods_submit:{//确认
                if (onAddGoodsFinishedListener!=null){
                    onAddGoodsFinishedListener.onAddGoodsFinished(tv_goods_num.getText().toString());
                }
                dismiss();
                break;
            }
        }
    }
}
