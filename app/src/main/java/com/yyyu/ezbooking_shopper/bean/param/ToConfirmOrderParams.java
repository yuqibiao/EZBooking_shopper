package com.yyyu.ezbooking_shopper.bean.param;

import java.util.List;

/**
 * 功能：SellerDetailActivity传到OrderConfirmActivity的参数
 *
 * @author yyyu
 * @date 2016/7/29
 */
public class ToConfirmOrderParams {

    private int userId;
    private int sellerId;
    private String orderDate;
    private String orderTime;
    private int personCount;
    private int deskCount;
    private int payType;
    private String  orderDesc;
    private List<SubOrder> subOrders;

    public ToConfirmOrderParams() {

    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public int getPersonCount() {
        return personCount;
    }

    public void setPersonCount(int personCount) {
        this.personCount = personCount;
    }

    public int getDeskCount() {
        return deskCount;
    }

    public void setDeskCount(int deskCount) {
        this.deskCount = deskCount;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public String getOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
    }

    public List<SubOrder> getSubOrders() {
        return subOrders;
    }

    public void setSubOrders(List<SubOrder> subOrders) {
        this.subOrders = subOrders;
    }


    public static class SubOrder {
        private int goodsId;
        private int goodsCount;
        private String goodsName;
        private String goodsTotalPrice;
        public String bigImage;

        public SubOrder() {

        }

        public int getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(int goodsId) {
            this.goodsId = goodsId;
        }

        public int getGoodsCount() {
            return goodsCount;
        }

        public void setGoodsCount(int goodsCount) {
            this.goodsCount = goodsCount;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getGoodsTotalPrice() {
            return goodsTotalPrice;
        }

        public void setGoodsTotalPrice(String goodsTotalPrice) {
            this.goodsTotalPrice = goodsTotalPrice;
        }

        public String getBigImage() {
            return bigImage;
        }

        public void setBigImage(String bigImage) {
            this.bigImage = bigImage;
        }

    }


}
