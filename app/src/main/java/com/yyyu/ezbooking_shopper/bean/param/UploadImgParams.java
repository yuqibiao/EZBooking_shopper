package com.yyyu.ezbooking_shopper.bean.param;

/**
 * 功能：上传用户头像
 *
 * @author yyyu
 * @date 2016/8/6
 */
public class UploadImgParams {

    private int userId;
    private String userImages;

    public UploadImgParams(){

    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserImages() {
        return userImages;
    }

    public void setUserImages(String userImages) {
        this.userImages = userImages;
    }

}
