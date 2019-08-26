package com.seven.module_extension.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seven.lib_common.base.fragment.BaseFragment;
import com.seven.lib_model.model.extension.InComeDetailsEntity;
import com.seven.lib_model.model.extension.InComeItem;
import com.seven.module_extension.R;
import com.seven.module_extension.R2;
import com.seven.module_extension.ui.activity.IncomeActivity;
import com.seven.module_extension.ui.adapter.InComeDetailsAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by xxxxxxH on 2019/5/4.
 */
public class InComeFrozenFragment extends BaseFragment {
    @BindView(R2.id.me_income_rv)
    RecyclerView meIncomeRv;
    private InComeDetailsAdapter adapter;
    private InComeDetailsEntity inComeDetailsEntity;
    private List<InComeItem> inComeItemList;

    @Override
    public int getContentViewId() {
        return R.layout.me_fragment_income;
    }

    public InComeFrozenFragment(){

    }

    @Override
    public void init(Bundle savedInstanceState) {
        setRv();
        ((IncomeActivity) getActivity()).request();
    }

    private void setRv(){
        adapter = new InComeDetailsAdapter(R.layout.me_item_income_details,inComeItemList);
        meIncomeRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        meIncomeRv.setAdapter(adapter);
    }

    @Override
    public void result(int code, Boolean hasNextPage, String response, Object object) {
        super.result(code, hasNextPage, response, object);
        if (code == 1) {
            if (object == null) {
                adapter.setEmptyView(((IncomeActivity) getActivity()).setEmptyView());
                adapter.setNewData(inComeItemList);
            }else {
                inComeDetailsEntity = (InComeDetailsEntity) object;
                inComeItemList = inComeDetailsEntity.getItems();
                adapter.setNewData(inComeItemList);
            }

        }
    }

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
    public void onClick(View view) {

    }

}
