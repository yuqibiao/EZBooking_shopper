package com.yyyu.ezbooking_shopper.ui.dialog;

import android.content.Context;
import android.content.res.AssetManager;
import android.view.View;

import com.yyyu.barbecue.ezbooking_base.ui.dialog.BaseDialog;
import com.yyyu.barbecue.ezbooking_base.ui.widget.ScrollerPicker;
import com.yyyu.barbecue.ezbooking_base.utils.DimensChange;
import com.yyyu.ezbooking_shopper.R;
import com.yyyu.ezbooking_shopper.bean.foo.CityModel;
import com.yyyu.ezbooking_shopper.bean.foo.DistrictModel;
import com.yyyu.ezbooking_shopper.bean.foo.ProvinceModel;
import com.yyyu.ezbooking_shopper.callback.OnPickerFinishedListener;
import com.yyyu.ezbooking_shopper.service.XmlParserHandler;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * 功能：
 *
 * @author yyyu
 * @date 2016/8/7
 */
public class ChoiceAddress extends BaseDialog{

    private static final String TAG = "ChoiceAddress";

    /**
     * 所有省
     */
    protected String[] mProvinceDatas;
    /**
     * key - 省 value - 市
     */
    protected Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
    /**
     * key - 市 values - 区
     */
    protected Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();

    /**
     * key - 区 values - 邮编
     */
    protected Map<String, String> mZipcodeDatasMap = new HashMap<String, String>();

    /**
     * 当前省的名称
     */
    protected String mCurrentProviceName;
    /**
     * 当前市的名称
     */
    protected String mCurrentCityName;
    /**
     * 当前区的名称
     */
    protected String mCurrentDistrictName = "";

    /**
     * 当前区的邮政编码
     */
    protected String mCurrentZipCode = "";
    private ScrollerPicker sp_province;
    private ScrollerPicker sp_city;
    private ScrollerPicker sp_district;
    private OnPickerFinishedListener onPickerFinishedListener;

    public ChoiceAddress(Context context) {
        super(context);
        lp.width = DimensChange.dp2px(context , 255);
        lp.dimAmount = 0.7f;
        getWindow().setAttributes(lp);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_choice_adress;
    }

    @Override
    public void beforeInit() {
        super.beforeInit();
        initProvinceDatas(mContext);
    }

    @Override
    protected void initView() {
        sp_province = getView(R.id.sp_province);
        sp_city = getView(R.id.sp_city);
        sp_district = getView(R.id.sp_district);
    }

    @Override
    protected void initListener() {
        addOnClickListener(R.id.tv_picker_cancel , R.id.tv_picker_submit);
        sp_province.setOnSelectListener(new ScrollerPicker.OnSelectListener() {
            @Override
            public void endSelect(int id, String text) {
                String[] cities =mCitisDatasMap.get( mProvinceDatas[sp_province.getSelected()]);
                sp_city.setData(Arrays.asList(cities) );
                if(cities.length==1){
                    sp_city.setDefault(0);
                }
                sp_district.setData(Arrays.asList(mDistrictDatasMap.get(cities[0]) ) );
            }
            @Override
            public void selecting(int id, String text) {

            }
        });
        sp_city.setOnSelectListener(new ScrollerPicker.OnSelectListener() {
            @Override
            public void endSelect(int id, String text) {
                String[] cities =mCitisDatasMap.get( mProvinceDatas[sp_province.getSelected()]);
                sp_district.setData(Arrays.asList(mDistrictDatasMap.get(cities[sp_city.getSelected()]) ) );
            }
            @Override
            public void selecting(int id, String text) {
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        sp_province.setData(Arrays.asList(mProvinceDatas));
        if(mProvinceDatas==null||mProvinceDatas.length<=0){
            return;
        }
        sp_province.setDefault(0);
        String[] cities =mCitisDatasMap.get( mProvinceDatas[0]);
        if(cities==null || cities.length<=0){
            return;
        }
        sp_city.setData(Arrays.asList(cities));
        String[] districts = mDistrictDatasMap.get(cities[0]);
        if(districts==null || districts.length<=0){
            return;
        }
        sp_district.setData(Arrays.asList(districts));
    }

    public String getAddress(){
        return sp_province.getSelectedText()+sp_city.getSelectedText()+sp_district.getSelectedText();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.tv_picker_submit:{
                onPickerFinishedListener.onPickerFinished();
                dismiss();
                break;
            }
            case R.id.tv_picker_cancel:{
                dismiss();
                break;
            }
        }
    }

    public void setOnPickerFinishedListener(OnPickerFinishedListener onPickerFinishedListener){
        this.onPickerFinishedListener = onPickerFinishedListener;
    }

    /**
     * 解析省市区的XML数据
     */
    protected void initProvinceDatas(Context context) {
        List<ProvinceModel> provinceList = null;
        AssetManager asset = context.getAssets();
        try {
            InputStream input = asset.open("province_data.xml");
            // 创建一个解析xml的工厂对象
            SAXParserFactory spf = SAXParserFactory.newInstance();
            // 解析xml
            SAXParser parser = spf.newSAXParser();
            XmlParserHandler handler = new XmlParserHandler();
            parser.parse(input, handler);
            input.close();
            // 获取解析出来的数据
            provinceList = handler.getDataList();
            // */ 初始化默认选中的省、市、区
            if (provinceList != null && !provinceList.isEmpty()) {
                mCurrentProviceName = provinceList.get(0).getName();
                List<CityModel> cityList = provinceList.get(0).getCityList();
                if (cityList != null && !cityList.isEmpty()) {
                    mCurrentCityName = cityList.get(0).getName();
                    List<DistrictModel> districtList = cityList.get(0)
                            .getDistrictList();
                    mCurrentDistrictName = districtList.get(0).getName();
                    mCurrentZipCode = districtList.get(0).getZipcode();
                }
            }
            mProvinceDatas = new String[provinceList.size()];
            for (int i = 0; i < provinceList.size(); i++) {
                // 遍历所有省的数据
                mProvinceDatas[i] = provinceList.get(i).getName();
                List<CityModel> cityList = provinceList.get(i).getCityList();
                String[] cityNames = new String[cityList.size()];
                for (int j = 0; j < cityList.size(); j++) {
                    // 遍历省下面的所有市的数据
                    cityNames[j] = cityList.get(j).getName();
                    List<DistrictModel> districtList = cityList.get(j).getDistrictList();
                    String[] distrinctNameArray = new String[districtList.size()];
                    DistrictModel[] distrinctArray = new DistrictModel[districtList.size()];
                    for (int k = 0; k < districtList.size(); k++) {
                        // 遍历市下面所有区/县的数据
                        DistrictModel districtModel = new DistrictModel(
                                districtList.get(k).getName(), districtList.get(k).getZipcode());
                        // 区/县对于的邮编，保存到mZipcodeDatasMap
                        mZipcodeDatasMap.put(districtList.get(k).getName(),districtList.get(k).getZipcode());
                        distrinctArray[k] = districtModel;
                        distrinctNameArray[k] = districtModel.getName();
                    }
                    // 市-区/县的数据，保存到mDistrictDatasMap
                    mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
                }
                // 省-市的数据，保存到mCitisDatasMap
                mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {

        }
    }


}
