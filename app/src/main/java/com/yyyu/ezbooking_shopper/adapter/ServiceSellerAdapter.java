package com.yyyu.ezbooking_shopper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yyyu.barbecue.ezbooking_base.utils.MyLog;
import com.yyyu.ezbooking_shopper.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 功能：服务界面对应的Adapter
 * <p/>
 * Created by yyyu on 2016/8/9.
 */
public class ServiceSellerAdapter extends BaseAdapter {

    private Context mContext;
    private int checkPosition = 0; //默认选中第一个

    public ServiceSellerAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return 10;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ServiceSellerHolder serviceSellerHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.gv_item_service_seller, parent, false);
            serviceSellerHolder = new ServiceSellerHolder();
            serviceSellerHolder.civ_seller_icon = (CircleImageView) convertView.findViewById(R.id.civ_seller_icon);
            serviceSellerHolder.ll_seller_info = (LinearLayout) convertView.findViewById(R.id.ll_seller_info);
            serviceSellerHolder.cb_seller_name = (CheckBox) convertView.findViewById(R.id.cb_seller_name);
            serviceSellerHolder.tv_seller_tel = (TextView) convertView.findViewById(R.id.tv_seller_tel);
            convertView.setTag(serviceSellerHolder);
        } else {
            serviceSellerHolder = (ServiceSellerHolder) convertView.getTag();
        }
        if (position == checkPosition) {
            serviceSellerHolder.cb_seller_name.setChecked(true);
        } else {
            serviceSellerHolder.cb_seller_name.setChecked(false);
        }
        serviceSellerHolder.cb_seller_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCheck(position);
            }
        });
        return convertView;
    }

    public static class ServiceSellerHolder {
        public CircleImageView civ_seller_icon;
        public LinearLayout ll_seller_info;
        public CheckBox cb_seller_name;
        public TextView tv_seller_tel;
    }

    public void updateCheck(int checkPosition) {
        this.checkPosition = checkPosition;
        notifyDataSetChanged();
    }

}
