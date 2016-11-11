package com.yyyu.ezbooking_shopper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yyyu.barbecue.ezbooking_base.net.MyBitmapUtils;
import com.yyyu.barbecue.ezbooking_base.utils.MyNetUtils;
import com.yyyu.ezbooking_shopper.R;
import com.yyyu.ezbooking_shopper.bean.json.QueryMyInfoJson;
import com.yyyu.ezbooking_shopper.net.MyHttpUrl;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 功能：我的界面历史商店对应的Adapter
 *
 * Created by yyyu on 2016/8/2.
 */
public class HistorySellerAdapter extends BaseAdapter{

    private Context mContext;

    private List<QueryMyInfoJson.Seller> sellerList;

    public HistorySellerAdapter(Context context ,  List<QueryMyInfoJson.Seller> sellerList){
        this.mContext = context;
        this.sellerList = sellerList;
    }

    @Override
    public int getCount() {
        return sellerList.size();
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

        MyHolder myHolder;
        if(convertView ==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.gv_item_history_seller , parent , false);
            myHolder = new MyHolder();
            myHolder.civ_seller_icon = (CircleImageView) convertView.findViewById(R.id.civ_seller_icon);
            myHolder.tv_seller_name = (TextView) convertView.findViewById(R.id.tv_seller_name);
            convertView.setTag(myHolder);
        }else{
            myHolder = (MyHolder) convertView.getTag();
        }
        myHolder.tv_seller_name.setText(sellerList.get(position).sellerName);
        MyBitmapUtils.getInstance(mContext).display(myHolder.civ_seller_icon ,MyHttpUrl.IP_PORT+"dbLife/"+ sellerList.get(position).bigImage);
        return convertView;
    }

    public static class MyHolder{
        public CircleImageView civ_seller_icon;
        public TextView tv_seller_name;
    }
}
