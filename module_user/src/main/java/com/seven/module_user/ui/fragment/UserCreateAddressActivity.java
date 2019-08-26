package com.seven.module_user.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.gyf.barlibrary.ImmersionBar;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citypickerview.CityPickerView;
import com.seven.lib_common.base.activity.BaseAppCompatActivity;
import com.seven.lib_common.base.activity.BaseTitleActivity;
import com.seven.lib_common.utils.ToastUtils;
import com.seven.lib_model.ApiManager;
import com.seven.lib_model.BaseResult;
import com.seven.lib_model.model.user.SBEntity;
import com.seven.lib_model.model.user.mine.AddAddressEntity;
import com.seven.lib_model.model.user.mine.AddressEntity;
import com.seven.lib_model.model.user.mine.DTEntity;
import com.seven.lib_model.model.user.mine.RegionEntity;
import com.seven.lib_model.user.UserActivityPresenterNew;
import com.seven.lib_router.Variable;
import com.seven.lib_router.router.RouterPath;
import com.seven.module_user.R;
import com.seven.module_user.R2;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@Route(path = RouterPath.ACTIVITY_MINE_ADD_ADDRESS)
public class UserCreateAddressActivity extends BaseTitleActivity {

    @Autowired
    AddressEntity addressEntity;

    @BindView(R2.id.address_tx)
    TextView addressTx;
    @BindView(R2.id.is_default_address_img)
    ImageView isDefaultAddressImg;
    @BindView(R2.id.is_default_address_tx)
    TextView isDefaultAddressTx;
    @BindView(R2.id.name_edit)
    EditText nameEdit;
    @BindView(R2.id.phone_edit)
    EditText phoneEdit;
    @BindView(R2.id.address_detail)
    EditText addressDetail;

    private boolean isDefault;

    private List<RegionEntity> provinceList = new ArrayList<>();
    private List<RegionEntity> cityList = new ArrayList<>();
    private List<RegionEntity> areaList = new ArrayList<>();

    private List<String> provinceStringList = new ArrayList<>();
    private List<List<String>> cityStringList = new ArrayList<>();
    private List<List<List<String>>> areaStringList = new ArrayList<>();

    private int provincePosition;
    private int cityPosition;
    private int areaPosition;

    @Override
    public void showLoading() {

    }

    @Override
    public void closeLoading() {

    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.mu_activity_create_address;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitleText(R.string.user_add_address);
        ApiManager.getRegionList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<BaseResult<DTEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult<DTEntity> data) {
                        Log.e("xxxxxxH", data.getMessage());
                        provinceList.addAll(data.getData().getItems());
                        cityList.addAll(provinceList.get(0).getSub());
                        areaList.addAll(cityList.get(0).getSub());
                        prepareCityList();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("xxxxxxH", e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    protected void rightTextBtnClick(View v) {

    }

    @Override
    protected void rightBtnClick(View v) {

    }

    @Override
    protected void initBundleData(Intent intent) {
        if (intent == null) {
            intent = getIntent();
            addressEntity = (AddressEntity) intent.getSerializableExtra("EDIT_ADDRESS");
            if (addressEntity != null) {
                //test
                nameEdit.setText(addressEntity.getContact_name());
                phoneEdit.setText(addressEntity.getContact_phone());
                addressTx.setText(addressEntity.getProvince_name() + " " + addressEntity.getCity_name() + " " + addressEntity.getDistrict_name());
                addressDetail.setText(addressEntity.getAddress());
                isDefaultAddressImg.setImageDrawable(!TextUtils.isEmpty(String.valueOf(addressEntity.getIs_default())) && addressEntity.getIs_default() == 0 ? getDrawable(R.drawable.item_shopping_cart_default) : getDrawable(R.drawable.item_shopping_cart_selector));
                isDefaultAddressTx.setTextColor(!TextUtils.isEmpty(String.valueOf(addressEntity.getIs_default())) && addressEntity.getIs_default() == 0 ? getResources().getColor(R.color.add_address_default_n) : getResources().getColor(R.color.add_address_default_c));
                isDefault = addressEntity.getIs_default() == 1;
            }
        }
    }

    private void prepareCityList() {
        for (RegionEntity entity : provinceList) {
            provinceStringList.add(entity.getRegion_name());
            List<String> list1 = new ArrayList<>();//市
            List<List<String>> list3 = new ArrayList<>();
            if (entity.getSub() != null && entity.getSub().size() > 0) {
                for (RegionEntity entity1 : entity.getSub()) {//遍历市
                    list1.add(entity1.getRegion_name());
                    List<String> list = new ArrayList<>();//区
                    if (entity1.getSub() != null && entity1.getSub().size() > 0) {
                        for (RegionEntity entity2 : entity1.getSub()) {//遍历区
                            list.add(entity2.getRegion_name());
                        }
                    } else {
                        list.add("");
                    }
                    list3.add(list);
                }
            } else {
                list1.add("");
            }

            cityStringList.add(list1);
            areaStringList.add(list3);
        }


    }

    @OnClick(R2.id.address_tx)
    void chooseCity() {
        if (provinceStringList.size() > 0 && cityStringList.size() > 0 && areaStringList.size() > 0) {
            closeInput();
            OptionsPickerView cityReasonPickerView = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    addressTx.setText(provinceStringList.get(options1) + " " +
                            cityStringList.get(options1).get(options2) + " " +
                            areaStringList.get(options1).get(options2).get(options3));
                    provincePosition = options1;
                    cityPosition = options2;
                    areaPosition = options3;
                }
            }).setContentTextSize(20)//设置滚轮文字大小
                    .setDividerColor(Color.LTGRAY)//设置分割线的颜色
                    .setSelectOptions(0)//默认选中项
                    .setBgColor(Color.WHITE)
                    .setTitleBgColor(getResources().getColor(R.color.color_eee))
                    .setCancelColor(getResources().getColor(R.color.color_6c))
                    .setSubmitColor(getResources().getColor(R.color.color_1e1d1d))
                    .setTextColorCenter(getResources().getColor(R.color.color_1e1d1d))
                    .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                    .setOutSideColor(0x00000000) //设置外部遮罩颜色
                    .build();
            cityReasonPickerView.setPicker(provinceStringList, cityStringList, areaStringList);
            cityReasonPickerView.show();
        }
    }

    @OnClick(R2.id.is_default_address)
    void setDefaultAddress() {
        isDefaultAddressImg.setImageDrawable(isDefault ? getDrawable(R.drawable.item_shopping_cart_default) : getDrawable(R.drawable.item_shopping_cart_selector));
        isDefaultAddressTx.setTextColor(isDefault ? getResources().getColor(R.color.add_address_default_n) : getResources().getColor(R.color.add_address_default_c));
        isDefault = !isDefault;
    }

    @OnClick(R2.id.add_address)
    void prepareCommit() {
        if (nameEdit.getText().toString().isEmpty()) {
            nameEdit.setError("联系人不能为空！");
            nameEdit.requestFocus();
            return;
        } else if (phoneEdit.getText().toString().isEmpty()) {
            phoneEdit.setError("联系人电话不能为空！");
            phoneEdit.requestFocus();
            return;
        } else if (addressDetail.getText().toString().isEmpty()) {
            addressDetail.setError("请输出详细地址!");
            addressDetail.requestFocus();
            return;
        } else if (addressTx.getText().toString().isEmpty()) {
            ToastUtils.showToast(mContext, "请选择地址!");
            return;
        }
        if (addressEntity != null) {
            SBEntity sb = new SBEntity();
            sb.setContact_id(addressEntity.getId());
            sb.setAddress(addressDetail.getText().toString());
            sb.setContact_name(nameEdit.getText().toString());
            sb.setContact_phone(phoneEdit.getText().toString());
            sb.setIs_default(isDefault ? "1" : "0");
            sb.setProvince_id(String.valueOf(provinceList.get(provincePosition).getId()));
            sb.setCity_id(String.valueOf(provinceList.get(provincePosition).getSub().get(cityPosition).getId()));
            sb.setDistrict_id(String.valueOf(provinceList.get(provincePosition).getSub().get(cityPosition).getSub().get(areaPosition).getId()));
            upData(sb);

//            UserActivityPresenterNew presenterNew = new UserActivityPresenterNew(this,this);
//            presenterNew.editAddress(1,Variable.getInstance().getUserId(),
//                    String.valueOf(provinceList.get(provincePosition).getId()),
//                    String.valueOf(provinceList.get(provincePosition).getSub().get(cityPosition).getId()),
//                    String.valueOf(provinceList.get(provincePosition).getSub().get(cityPosition).getSub().get(areaPosition).getId()),
//                    addressDetail.getText().toString(),
//                    isDefault ? "1" : "0",
//                    nameEdit.getText().toString(),
//                    phoneEdit.getText().toString());
            return;
        }
        AddAddressEntity entity = new AddAddressEntity();
        entity.setAddress(addressDetail.getText().toString());
        entity.setContact_name(nameEdit.getText().toString());
        entity.setContact_phone(phoneEdit.getText().toString());
        entity.setIs_default(isDefault ? "1" : "0");
        entity.setProvince_id(provinceList.get(provincePosition).getId());
        entity.setCity_id(provinceList.get(provincePosition).getSub().get(cityPosition).getId());
        entity.setDistrict_id(provinceList.get(provincePosition).getSub().get(cityPosition).getSub().get(areaPosition).getId());
        commit(entity);
    }

    @Override
    public void result(int code, Boolean hasNextPage, String response, Object object) {
        super.result(code, hasNextPage, response, object);
        if (code == 1) {
            ToastUtils.showToast(mContext, "修改成功");
        }
    }

    private void commit(AddAddressEntity entity) {
        ApiManager.addAddress(entity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult baseResult) {
                        Log.e("create", baseResult.getMessage());
                        Log.e("create", baseResult.getCode() + "--->");
                        if (baseResult.getCode() == 1) {
                            finish();
                        } else {
                            showToast(baseResult.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void upData(SBEntity sb) {
        showLoadingDialog();
        ApiManager.editAddress(sb)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<BaseResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult baseResult) {
                        dismissLoadingDialog();
                        if (baseResult.getCode() == 1) {
                            finish();
                        }
                        ToastUtils.showToast(mContext, baseResult.getMessage());
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissLoadingDialog();
                        Log.e("xxxxxxH", e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    protected void closeInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        View v = getWindow().peekDecorView();
        if (null != v) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
}
