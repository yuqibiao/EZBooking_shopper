package com.yyyu.ezbooking_shopper.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yyyu.barbecue.ezbooking_base.anime.ItemAnim;
import com.yyyu.barbecue.ezbooking_base.net.MyBitmapUtils;
import com.yyyu.barbecue.ezbooking_base.ui.widget.NoScrollListView;
import com.yyyu.barbecue.ezbooking_base.utils.MyLog;
import com.yyyu.barbecue.ezbooking_base.utils.MyTimeUtils;
import com.yyyu.ezbooking_shopper.R;
import com.yyyu.ezbooking_shopper.bean.json.QueryUserOrderJson;
import com.yyyu.ezbooking_shopper.net.MyHttpUrl;
import com.yyyu.ezbooking_shopper.utils.LogicUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 功能：订单查看Adapter
 *
 * @author yyyu
 * @date 2016/7/31
 */
public class OrderCheckAdapter extends RecyclerView.Adapter<OrderCheckAdapter.OrderHolder> {

    private Context mContext;
    private List<QueryUserOrderJson.Order> orders;

    public OrderCheckAdapter(Context context, List<QueryUserOrderJson.Order> orders) {
        this.mContext = context;
        this.orders = orders;
    }

    @Override
    public OrderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.rv_item_order_show, parent, false);
        return new OrderHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OrderHolder holder, int position) {
        //--item动画
        ItemAnim.getInstance(mContext).startAnimator(holder.itemView, position);
        QueryUserOrderJson.Order order = orders.get(position);
        holder.tv_seller_name.setText(order.sellerName);
        holder.tv_order_datetime.setText(LogicUtils.getInstance(mContext).converDateTime(order.orderDate , order.orderTime));
        holder.tv_table_num.setText("" + order.deskCount + "桌");
        holder.tv_person_num.setText("" + order.personCount + "人");
        if (order.orderType == 1) {
            holder.tv_order_type.setText("仅预约");
            holder.tv_order_sun_money.setVisibility(View.GONE);
        } else if (order.orderType == 2) {
            holder.tv_order_type.setText("预约+预定");
            holder.lv_order_goods.setAdapter(new SubOrderGoodsAdapter(mContext, order.subOrderList));
        }
        if (order.orderStatus == 1) {
            holder.tv_order_condition.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
            holder.tv_order_condition.setText("等待确认");
        } else if (order.orderStatus == 2) {
            holder.tv_order_condition.setTextColor(mContext.getResources().getColor(R.color.my_blue));
            holder.tv_order_condition.setText("已接单");
        }
        if (TextUtils.isEmpty(order.orderDesc)) {
            holder.tv_order_remarks.setText("无");
        } else {
            holder.tv_order_remarks.setText(order.orderDesc);
        }
        holder.tv_order_sun_money.setText("" + order.orderPrice + "RMB");
        holder.tv_order_datetime2.setText(order.orderDate);
        MyBitmapUtils.getInstance(mContext).display(holder.civ_seller_icon, MyHttpUrl.FILE_BASE + order.sellerImage);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public void setOrders(List<QueryUserOrderJson.Order> orders) {
        this.orders = orders;
    }


    public class OrderHolder extends RecyclerView.ViewHolder {

        public CircleImageView civ_seller_icon;
        public TextView tv_seller_name;
        public TextView tv_order_datetime;
        public TextView tv_table_num;
        public TextView tv_person_num;
        public TextView tv_order_type;
        public TextView tv_order_condition;
        public NoScrollListView lv_order_goods;
        public TextView tv_order_sun_money;
        public TextView tv_order_remarks;
        public TextView tv_order_datetime2;
        public View view_order_line;

        public OrderHolder(View itemView) {
            super(itemView);
            civ_seller_icon = (CircleImageView) itemView.findViewById(R.id.civ_seller_icon);
            tv_seller_name = (TextView) itemView.findViewById(R.id.tv_seller_name);
            tv_order_datetime = (TextView) itemView.findViewById(R.id.tv_order_datetime);
            tv_table_num = (TextView) itemView.findViewById(R.id.tv_table_num);
            tv_person_num = (TextView) itemView.findViewById(R.id.tv_person_num);
            tv_order_type = (TextView) itemView.findViewById(R.id.tv_order_type);
            tv_order_condition = (TextView) itemView.findViewById(R.id.tv_order_condition);
            lv_order_goods = (NoScrollListView) itemView.findViewById(R.id.lv_order_goods);
            tv_order_sun_money = (TextView) itemView.findViewById(R.id.tv_order_sun_money);
            tv_order_remarks = (TextView) itemView.findViewById(R.id.tv_order_remarks);
            tv_order_datetime2 = (TextView) itemView.findViewById(R.id.tv_order_datetime2);
            view_order_line = itemView.findViewById(R.id.view_order_line);
        }
    }

}
