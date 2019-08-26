package com.seven.module_home.adapter;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seven.lib_common.stextview.TypeFaceView;
import com.seven.lib_common.widget.flowlayout.FlowLayout;
import com.seven.lib_common.widget.flowlayout.TagAdapter;
import com.seven.lib_common.widget.flowlayout.TagFlowLayout;
import com.seven.lib_model.model.home.CommodityDetailsEntity;
import com.seven.lib_opensource.application.SSDK;
import com.seven.module_home.R;
import com.seven.module_home.callback.FlowCallback;

import java.util.List;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/5/9
 */

public class SpecificationAdapter extends BaseQuickAdapter<CommodityDetailsEntity.SkuAttrListBean, BaseViewHolder> {

    private FlowCallback callback;
    private TagAdapter tagAdapter;

    public SpecificationAdapter(int layoutResId, @Nullable List<CommodityDetailsEntity.SkuAttrListBean> data,
                                FlowCallback callback) {
        super(layoutResId, data);
        mContext = SSDK.getInstance().getContext();
        this.callback = callback;
    }

    @Override
    protected void convert(BaseViewHolder helper, CommodityDetailsEntity.SkuAttrListBean item) {

        helper.setText(R.id.title_tv, item.getAttr_title());

        setFlowLayout(helper, item);
    }

    private void setFlowLayout(final BaseViewHolder helper, final CommodityDetailsEntity.SkuAttrListBean item) {

        final TagFlowLayout flowLayout = helper.getView(R.id.tag_flow);

        tagAdapter = new TagAdapter<CommodityDetailsEntity.SkuAttrListBean.AttrValuesBean>(item.getAttr_values()) {
            @Override
            public View getView(FlowLayout parent, final int position, final CommodityDetailsEntity.SkuAttrListBean.AttrValuesBean entity) {
                View view = LayoutInflater.from(mContext).inflate(R.layout.mh_specification_tv, flowLayout, false);
                final TypeFaceView tv = view.findViewById(R.id.flow_tv);
                tv.setText(entity.getAttr_value_title());
                tv.setSelected(entity.isSelect());
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

//                        if (entity.isSelect()) return;
//
//                        for (CommodityDetailsEntity.SkuAttrListBean.AttrValuesBean bean : item.getAttr_values())
//                            if (bean.isSelect())
//                                bean.setSelect(false);
//
//                        entity.setSelect(true);

                        callback.click(helper.getLayoutPosition(), position);

                    }
                });
                return view;
            }
        };
        flowLayout.setAdapter(tagAdapter);
    }

}
