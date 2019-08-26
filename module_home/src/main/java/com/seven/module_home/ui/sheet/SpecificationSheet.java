package com.seven.module_home.ui.sheet;

import android.app.Activity;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.seven.lib_common.base.sheet.IBaseSheet;
import com.seven.lib_common.stextview.TypeFaceView;
import com.seven.lib_common.utils.FormatUtils;
import com.seven.lib_common.utils.ResourceUtils;
import com.seven.lib_common.utils.ToastUtils;
import com.seven.lib_common.utils.glide.GlideUtils;
import com.seven.lib_model.model.home.CommodityDetailsEntity;
import com.seven.module_home.R;
import com.seven.module_home.adapter.SpecificationAdapter;
import com.seven.module_home.callback.FlowCallback;
import com.seven.module_home.model.SpecificationEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/4/18
 */

public class SpecificationSheet extends IBaseSheet implements FlowCallback {

    private CommodityDetailsEntity entity;

    private ImageView closeIv;
    private ImageView subtractionIv;
    private ImageView addIv;
    private RelativeLayout shoppingRl;
    private RelativeLayout buyRl;

    private ImageView coverIv;
    private TypeFaceView tokenTv;
    private TypeFaceView priceTv;
    private TypeFaceView stockTv;

    private TypeFaceView numberTv;

    private CommodityDetailsEntity.SkuListBean skuBean;

    private RecyclerView recyclerView;
    private SpecificationAdapter adapter;
    private List<SpecificationEntity> keyList;
    private SpecificationEntity specificationEntity;

    public SpecificationSheet(Activity activity, int theme, com.seven.lib_common.listener.OnClickListener listener) {
        super(activity, theme, listener);
    }

    public SpecificationSheet(Activity activity, int theme, com.seven.lib_common.listener.OnClickListener listener, CommodityDetailsEntity entity) {
        this(activity, theme, listener);
        this.entity = entity;
    }

    @Override
    public int getRootLayoutId() {
        return R.layout.sheet_specification;
    }

    @Override
    public void onInitRootView(Bundle savedInstanceState) {

        initView();

        initData();
    }

    @Override
    public void initView() {

        closeIv = getView(closeIv, R.id.close_iv);
        subtractionIv = getView(subtractionIv, R.id.subtraction_iv);
        addIv = getView(addIv, R.id.add_iv);
        shoppingRl = getView(shoppingRl, R.id.shopping_add_rl);
        buyRl = getView(buyRl, R.id.buy_rl);

        coverIv = getView(coverIv, R.id.cover_iv);
        priceTv = getView(priceTv, R.id.price_tv);
        tokenTv = getView(tokenTv, R.id.token_tv);
        stockTv = getView(stockTv, R.id.stock_tv);
        priceTv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        numberTv = getView(numberTv, R.id.number_tv);

        recyclerView = getView(recyclerView, R.id.recycler_view);
    }

    @Override
    public void initData() {

        closeIv.setOnClickListener(this);
        subtractionIv.setOnClickListener(this);
        addIv.setOnClickListener(this);
        shoppingRl.setOnClickListener(this);
        buyRl.setOnClickListener(this);

        keyList = new ArrayList<>();

        tokenTv.setText(ResourceUtils.getFormatText(R.string.hint_token,FormatUtils.formatCurrencyD(entity.getToken_price())));
        priceTv.setText(ResourceUtils.getText(R.string.rmb) + FormatUtils.formatCurrencyD(entity.getPrice()));
        stockTv.setText(ResourceUtils.getText(R.string.hint_sheet_stock));

        GlideUtils.loadImage(activity, entity.getThumb(), coverIv);

        numberTv.setText("0");

        setRecyclerView();

    }

    private void setRecyclerView() {

        adapter = new SpecificationAdapter(R.layout.mh_item_specification, entity.getSku_attr_list(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
    }

    private void changeStock() {

        StringBuffer buffer = new StringBuffer();

        for (SpecificationEntity entity : keyList)
            buffer.append(TextUtils.isEmpty(buffer.toString()) ? entity.getKey() : "," + entity.getKey());

        for (CommodityDetailsEntity.SkuListBean bean : entity.getSku_list())
            if (bean.getSku().equals(buffer.toString())) {

                numberTv.setText(bean.getStock() > 0 ? "1" : "0");
                stockTv.setText(ResourceUtils.getFormatText(R.string.hint_stock, bean.getStock()));
                priceTv.setText(ResourceUtils.getText(R.string.rmb) + FormatUtils.formatCurrencyD(bean.getPrice()));
                tokenTv.setText(ResourceUtils.getFormatText(R.string.hint_token,FormatUtils.formatCurrencyD(bean.getToken_price())));
                GlideUtils.loadImage(activity, bean.getThumb(), coverIv);
                skuBean = bean;

                break;
            }

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.close_iv)
            dismiss();

        if (v.getId() == R.id.subtraction_iv) {
            int number = Integer.parseInt(numberTv.getText().toString());
            if (number == 0) return;
            numberTv.setText(String.valueOf(number - 1));
        }

        if (v.getId() == R.id.add_iv) {
            int number = Integer.parseInt(numberTv.getText().toString());

            if (skuBean == null) return;

            if (number == skuBean.getStock()) {
                ToastUtils.showToast(activity, ResourceUtils.getText(R.string.hint_stock_null));
                return;
            }
            numberTv.setText(String.valueOf(number + 1));
        }

        if (v.getId() == R.id.shopping_add_rl) {

            if (skuBean == null) {
                ToastUtils.showToast(activity, ResourceUtils.getText(R.string.hint_sheet_stock));
                return;
            }

            if (skuBean.getStock() == 0) {
                ToastUtils.showToast(activity, ResourceUtils.getText(R.string.hint_stock_null));
                return;
            }

            listener.onCancel(v, skuBean.getId(), Integer.parseInt(numberTv.getText().toString()));
        }

        if (v.getId() == R.id.buy_rl) {

            if (skuBean == null) {
                ToastUtils.showToast(activity, ResourceUtils.getText(R.string.hint_sheet_stock));
                return;
            }

            if (skuBean.getStock() == 0) {
                ToastUtils.showToast(activity, ResourceUtils.getText(R.string.hint_stock_null));
                return;
            }

            listener.onClick(v, skuBean, Integer.parseInt(numberTv.getText().toString()));
        }
    }

    @Override
    public void click(int parentPosition, int position) {

        boolean update = false;

        CommodityDetailsEntity.SkuAttrListBean item = adapter.getItem(parentPosition);
        CommodityDetailsEntity.SkuAttrListBean.AttrValuesBean value = item.getAttr_values().get(position);

        if (value.isSelect()) return;

        for (CommodityDetailsEntity.SkuAttrListBean.AttrValuesBean bean : item.getAttr_values())
            if (bean.isSelect())
                bean.setSelect(false);

        value.setSelect(true);
        adapter.notifyItemChanged(parentPosition);

        String skuKey = item.getAttr_id() + ":" + value.getAttr_value_id();

        if (keyList.size() == 0) {

            specificationEntity = new SpecificationEntity();
            specificationEntity.setPosition(parentPosition);
            specificationEntity.setId(item.getAttr_id());
            specificationEntity.setKey(skuKey);
            keyList.add(specificationEntity);

        } else {

            for (SpecificationEntity entity : keyList) {
                if (entity.getId() == item.getAttr_id()) {
                    entity.setKey(skuKey);
                    update = true;
                    break;
                }
            }

            if (!update) {
                specificationEntity = new SpecificationEntity();
                specificationEntity.setPosition(parentPosition);
                specificationEntity.setId(item.getAttr_id());
                specificationEntity.setKey(skuKey);
                keyList.add(specificationEntity);
            }
        }

        if (keyList.size() != adapter.getData().size()) return;

        sortData((ArrayList<SpecificationEntity>) keyList);

        changeStock();

    }

    private void sortData(ArrayList<SpecificationEntity> mList) {
        try {
            Collections.sort(mList, new Comparator<SpecificationEntity>() {
                @Override
                public int compare(SpecificationEntity lhs, SpecificationEntity rhs) {
                    if (lhs.getPosition() > rhs.getPosition()) {
                        return 1;
                    }
                    return -1;
                }
            });
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

}
