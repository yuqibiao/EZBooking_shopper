package com.yyyu.ezbooking_shopper.bean.param;

/**
 * 功能：修改用户信息的参数
 *
 * @author yyyu
 * @date 2016/8/6
 */
public class ModifyUserInfoParams {

    private int userId ;
    private String userName ;
    private String userAddress;
    private String mobile ;

    public ModifyUserInfoParams(){

    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

}
