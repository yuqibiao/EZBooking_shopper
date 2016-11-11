package com.yyyu.ezbooking_shopper.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yyyu.barbecue.ezbooking_base.net.MyBitmapUtils;
import com.yyyu.ezbooking_shopper.R;
import com.yyyu.ezbooking_shopper.bean.json.QuerySellerDetailJson;
import com.yyyu.ezbooking_shopper.net.MyHttpUrl;

import java.util.List;

/**
 * 功能：
 *
 *
 * Created by yyyu on 2016/9/19.
 */
public class ServiceItemAdapter extends RecyclerView.Adapter<ServiceItemAdapter.ServiceItemHolder>{

    private Context mContext;
    private List<QuerySellerDetailJson.ServiceItem> mServiceItemList;

    public ServiceItemAdapter(Context context , List<QuerySellerDetailJson.ServiceItem> serviceItemList ){
        this.mContext = context;
        this.mServiceItemList = serviceItemList;
    }

    @Override
    public ServiceItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(mContext).inflate(R.layout.gv_service_item , parent , false);

        return new ServiceItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ServiceItemHolder holder, int position) {
        MyBitmapUtils.getInstance(mContext).display(holder.iv_service_item
                , MyHttpUrl.FILE_BASE+ mServiceItemList.get(position).serviceItemPic);
    }

    @Override
    public int getItemCount() {
        return mServiceItemList.size();
    }

    public class ServiceItemHolder extends RecyclerView.ViewHolder{
        public ImageView iv_service_item;
        public ServiceItemHolder(View itemView) {
            super(itemView);
            iv_service_item = (ImageView) itemView.findViewById(R.id.iv_service_item);
        }
    }

}
