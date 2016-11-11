package com.yyyu.ezbooking_shopper.ui.dialog;

import android.content.Context;
import android.view.View;

import com.yyyu.barbecue.ezbooking_base.ui.dialog.BaseDialog;
import com.yyyu.barbecue.ezbooking_base.ui.widget.ScrollerPicker;
import com.yyyu.barbecue.ezbooking_base.utils.DimensChange;
import com.yyyu.barbecue.ezbooking_base.utils.MyLog;
import com.yyyu.barbecue.ezbooking_base.utils.MyStrUtils;
import com.yyyu.barbecue.ezbooking_base.utils.MyTimeUtils;
import com.yyyu.ezbooking_shopper.R;
import com.yyyu.ezbooking_shopper.bean.foo.PickerDateBean;
import com.yyyu.ezbooking_shopper.callback.OnPickerFinishedListener;
import com.yyyu.ezbooking_shopper.utils.LogicUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 功能：日期选择dialog
 *
 * @author yyyu
 * @date 2016/7/28
 */
public class DateTimePickerDialog extends BaseDialog {

    private ScrollerPicker sp_date;
    private ScrollerPicker sp_time;
    private ScrollerPicker sp_table_num;
    private ScrollerPicker sp_person_num;

    private OnPickerFinishedListener onPickerFinishedListener;
    private ArrayList<PickerDateBean> pickerDateBeans;
    private ArrayList<String> times;
    private ArrayList<String> tables;
    private ArrayList<String> person;


    public DateTimePickerDialog(Context context) {
        super(context);
        lp.width = DimensChange.dp2px(context , 280);
        lp.dimAmount = 0.7f;
        getWindow().setAttributes(lp);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_datetime_picker;
    }

    @Override
    public void beforeInit() {
        super.beforeInit();
        pickerDateBeans = new ArrayList<>();
        times = new ArrayList<>();
        tables = new ArrayList<>();
        person = new ArrayList<>();
    }

    @Override
    protected void initView() {
        sp_date = getView(R.id.sp_date);
        sp_time = getView(R.id.sp_time);
        sp_table_num = getView(R.id.sp_table_num);
        sp_person_num = getView(R.id.sp_person_num);
    }

    @Override
    protected void initListener() {

        addOnClickListener(R.id.tv_picker_cancel, R.id.tv_picker_submit ,R.id.ll_picker);

    }

    @Override
    protected void initData() {
        super.initData();
        //---添加日期
        for(int i=0 ; i<30 ; i++){
            String dateStr = LogicUtils.getInstance(mContext).getDateTimeNWeek(i);
            String showStr ;
            if(i==0){
                showStr = "今天";
            }else if(i == 1){
                showStr = "明天";
            }else if(i==2){
                showStr = "后天";
            }else{
                showStr = dateStr;
            }
            PickerDateBean dateBean = new PickerDateBean(dateStr ,showStr);
            pickerDateBeans.add(dateBean);
        }
        List<String> dates = new ArrayList<>();
        for(PickerDateBean dateBean : pickerDateBeans){
            dates.add(dateBean.getShowStr());
        }
        sp_date.setData(dates);
        sp_date.setDefault(1);
        //---添加时间
        for (int i=0 ; i<2 ; i++){
            StringBuffer sb = new StringBuffer();
            if (i==0){
                sb.append("上午 ");
            }else if (i==1){
                sb.append("下午 ");
            }
            for (int j = 0 ; j<12 ; j++){
                StringBuffer time = new StringBuffer();
                if(j<10){
                    time.append("0"+j);
                }else{
                    time.append(j);
                }
                if(j%2==0){
                    time.append(":00");
                }else{
                    time.append(":30");
                }
                times.add(sb.toString()+time.toString());
            }
        }
        sp_time.setData(times);
        sp_time.setDefault(3);
        //---添加桌数
        for (int i = 1; i <= 10; i++) {
            tables.add("" + i);
        }
        sp_table_num.setData(tables);
        sp_table_num.setDefault(2);
        //---添加人数
        for (int i = 1; i <= 80; i++) {
            person.add("" + i);
        }
        sp_person_num.setData(person);
        sp_person_num.setDefault(15);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_picker_submit: {
                if (onPickerFinishedListener != null) {
                    onPickerFinishedListener.onPickerFinished();
                    dismiss();
                }
                break;
            }
            case R.id.tv_picker_cancel: {
                dismiss();
                break;
            }
            case R.id.ll_picker:{
                break;
            }
        }
    }

    public void setOnPickerFinishedListener(OnPickerFinishedListener onPickerFinishedListener) {
        this.onPickerFinishedListener = onPickerFinishedListener;
    }


    /**
     * 得到选中日期
     *
     * @return
     */
    public String getDate() {
        int year = MyTimeUtils.getCurrentYear();
        String dateInfo = pickerDateBeans.get(sp_date.getSelected()).getDateStr();
        String[] str = dateInfo.split(" ")[0].split("月");
        /*if (MyTimeUtils.getCurrentMonth(Calendar.getInstance())>Integer.parseInt(str[0].replace())) {//选中了下一年
            year = MyTimeUtils.getCurrentYear() + 1;
        }*/
        return year + "-" + str[0] + "-" + str[1].substring(0,str[1].length()-1);
    }
    /**
     * 得到选中时间
     *
     * @return
     */
    public String getTime() {
        String timeInfo = sp_time.getSelectedText();
        String[] str = timeInfo.split(" ");
        StringBuffer timeSb = new StringBuffer();
        if(str[0].equals("下午")){
            String[] times = str[1].split(":");
            int hour = MyTimeUtils.removeZerotoNum(times[0])+12;
            timeSb.append(hour+":"+times[1]);
        }else {
            timeSb.append(str[1]);
        }
        return timeSb.toString();
    }

    /**
     * 得到选中桌数
     *
     * @return
     */
    public String getTableNum() {
        return sp_table_num.getSelectedText();
    }

    /**
     * 得到选中人数
     *
     * @return
     */
    public String getPersonNum() {
        return sp_person_num.getSelectedText();
    }

    /**
     * 得到选中信息
     */
    public String getInfo() {

        return pickerDateBeans.get(sp_date.getSelected()).getDateStr() + "/" + sp_time.getSelectedText()
                +  "    " + getPersonNum() + "人"
                + "    " + getTableNum() + "桌";
    }

}
