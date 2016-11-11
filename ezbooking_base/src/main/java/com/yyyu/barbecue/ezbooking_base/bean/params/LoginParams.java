package com.yyyu.barbecue.ezbooking_base.bean.params;

/**
 * 功能：登录参数Bean
 * Created by yyyu on 2016/7/27.
 */
public class LoginParams {

    private Integer userType ;
    private String userName ;
    private String password ;
    private String mobile ;
    private String userImages;

    public LoginParams(){

    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getUserImages() {
        return userImages;
    }

    public void setUserImages(String userImages) {
        this.userImages = userImages;
    }

}
