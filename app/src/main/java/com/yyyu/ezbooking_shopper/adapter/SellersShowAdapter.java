package com.yyyu.ezbooking_shopper.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yyyu.barbecue.ezbooking_base.anime.ItemAnim;
import com.yyyu.barbecue.ezbooking_base.callback.OnRvItemClickListener;
import com.yyyu.barbecue.ezbooking_base.net.MyBitmapUtils;
import com.yyyu.barbecue.ezbooking_base.utils.MySnackBar;
import com.yyyu.ezbooking_shopper.R;
import com.yyyu.ezbooking_shopper.bean.foo.MapMarkInfoBean;
import com.yyyu.ezbooking_shopper.bean.json.QuerySellerJson;
import com.yyyu.ezbooking_shopper.ui.activity.MapShowActivity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 功能：商家显示Adapter
 *
 * Created by yyyu on 2016/7/6.
 */
public class SellersShowAdapter extends RecyclerView.Adapter<SellersShowAdapter.SellerHolder>{

    private Context context;
    private List<QuerySellerJson.SellerBean> data;
    private OnRvItemClickListener onRvItemClickListener;

    public SellersShowAdapter(Context context , List<QuerySellerJson.SellerBean> data){
        this.context = context;
        this.data = data;
    }

    @Override
    public SellersShowAdapter.SellerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.rv_seller_item , parent , false);
        return new SellerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SellersShowAdapter.SellerHolder holder, final int position) {

        final QuerySellerJson.SellerBean bean = data.get(position);

        ItemAnim.getInstance(context).startAnimator(holder.itemView , position);
        holder.tv_seller_name.setText(bean.sellerName);
        holder.tv_seller_tel.setText(bean.mobile);
        MyBitmapUtils.getInstance(context).display(holder.civ_seller_icon , data.get(position).bigImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRvItemClickListener.onRvItemClick(position);
            }
        });
        holder.iv_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(bean.lng) || TextUtils.isEmpty( bean.lat)){
                    MySnackBar.showShortDef(holder.tv_seller_name , "该商家目前没有位置信息！！！");
                    return;
                }
                MapMarkInfoBean markBean = new MapMarkInfoBean
                        (Double.parseDouble(bean.lng),Double.parseDouble(bean.lat) , bean.sellerName, bean.sellerDesc);
                MapShowActivity.startAction((Activity) context , markBean);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class SellerHolder extends RecyclerView.ViewHolder{

        public CircleImageView civ_seller_icon;
        public TextView tv_seller_name;
        public TextView tv_seller_tel;
        public ImageView iv_location;

        public SellerHolder(View itemView) {
            super(itemView);
            civ_seller_icon = (CircleImageView) itemView.findViewById(R.id.civ_seller_icon);
            tv_seller_name = (TextView) itemView.findViewById(R.id.tv_seller_name);
            tv_seller_tel = (TextView) itemView.findViewById(R.id.tv_seller_tel);
            iv_location = (ImageView) itemView.findViewById(R.id.iv_location);
        }
    }

    public void setData(List<QuerySellerJson.SellerBean> data) {
        this.data = data;
    }

    public void setOnRvItemClickListener(OnRvItemClickListener onRvItemClickListener){
        this.onRvItemClickListener = onRvItemClickListener;
    }

}
