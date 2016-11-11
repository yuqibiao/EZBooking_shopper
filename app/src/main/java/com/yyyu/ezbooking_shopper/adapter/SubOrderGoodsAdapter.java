package com.yyyu.ezbooking_shopper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yyyu.ezbooking_shopper.R;
import com.yyyu.ezbooking_shopper.bean.json.QueryUserOrderJson;

import java.util.List;

/**
 * 功能：单个订单中的商品对应的Adapter
 *
 * @author yyyu
 * @date 2016/8/1
 */
public class SubOrderGoodsAdapter extends BaseAdapter{

    private Context mContext;
    private List<QueryUserOrderJson.SubOrder> subOrders;

    public SubOrderGoodsAdapter(Context context , List<QueryUserOrderJson.SubOrder> subOrders){
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
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SubOrderGoodsHolder holder;
        if(convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.rv_item_sub_order , parent , false);
            holder = new SubOrderGoodsHolder();
            holder.tv_goods_name = (TextView) convertView.findViewById(R.id.tv_goods_name);
            holder.tv_goods_num = (TextView) convertView.findViewById(R.id.tv_goods_num);
            convertView.setTag(holder);
        }else{
            holder = (SubOrderGoodsHolder) convertView.getTag();
        }
        holder.tv_goods_name.setText(""+subOrders.get(position).goodsName);
        holder.tv_goods_num.setText("x"+subOrders.get(position).goodsCount);
        return convertView;
    }

    public static class SubOrderGoodsHolder{
        public TextView tv_goods_name;
        public TextView tv_goods_num;
    }

}
