package com.yyyu.ezbooking_shopper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.squareup.okhttp.OkHttpClient;
import com.yyyu.barbecue.ezbooking_base.net.MyBitmapUtils;
import com.yyyu.barbecue.ezbooking_base.net.MyHttpManager;
import com.yyyu.barbecue.ezbooking_base.ui.widget.RoundImageView;
import com.yyyu.barbecue.ezbooking_base.utils.MyLog;
import com.yyyu.ezbooking_shopper.R;
import com.yyyu.ezbooking_shopper.bean.param.ToConfirmOrderParams;

import java.util.List;

/**
 * 功能：确认订单时候商品的Adapter
 *
 * @author yyyu
 * @date 2016/7/30
 */
public class ConfirmGoodsAdapter extends BaseAdapter {

    private Context mContext;

    private List<ToConfirmOrderParams.SubOrder> subOrders;

    public ConfirmGoodsAdapter(Context context, List<ToConfirmOrderParams.SubOrder> subOrders) {
        this.mContext = context;
        this.subOrders = subOrders;
    }

    @Override
    public int getCount() {
        return subOrders.size();
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
        ConfirmHolder confirmHolder;
        String imgUrl = subOrders.get(position).getBigImage();
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.lv_item_confirm_goods, parent, false);
            confirmHolder = new ConfirmHolder();
            confirmHolder.riv_goods_confirm = (RoundImageView) convertView.findViewById(R.id.riv_goods_confirm);
            confirmHolder.tv_goods_name = (TextView) convertView.findViewById(R.id.tv_goods_name);
            confirmHolder.tv_total_price = (TextView) convertView.findViewById(R.id.tv_totoal_price);
            confirmHolder.cb_confirm_num = (CheckBox) convertView.findViewById(R.id.cb_confirm_num);
            confirmHolder.view_line = convertView.findViewById(R.id.view_line);
            confirmHolder.riv_goods_confirm.setTag(imgUrl);
            convertView.setTag(confirmHolder);
        } else {
            confirmHolder = (ConfirmHolder) convertView.getTag();
        }
        confirmHolder.tv_goods_name.setText("" + subOrders.get(position).getGoodsName());
        confirmHolder.tv_total_price.setText("" + subOrders.get(position).getGoodsTotalPrice() + "RMB");
        confirmHolder.cb_confirm_num.setText("  x" + subOrders.get(position).getGoodsCount());
        //---防止图片错乱 给每个ImageView 设置TAG
        if(confirmHolder.riv_goods_confirm.getTag()!=null && confirmHolder.riv_goods_confirm.getTag().equals(imgUrl)){
            MyBitmapUtils.getInstance(mContext).display(confirmHolder.riv_goods_confirm,imgUrl );
        }
        if (position == subOrders.size() - 1) {
            confirmHolder.view_line.setVisibility(View.INVISIBLE);
        } else {
            confirmHolder.view_line.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    public static class ConfirmHolder {
        public RoundImageView riv_goods_confirm;
        public TextView tv_goods_name;
        public TextView tv_total_price;
        public CheckBox cb_confirm_num;
        public View view_line;
    }

}
