package com.seven.module_extension.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.seven.lib_common.base.activity.BaseTitleActivity;
import com.seven.lib_model.model.extension.LevelEntity;
import com.seven.lib_model.presenter.extension.ExActivityPresenter;
import com.seven.lib_router.Variable;
import com.seven.lib_router.router.RouterPath;
import com.seven.module_extension.R;
import com.seven.module_extension.R2;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xxxxxxH on 2019/5/11.
 */
@Route(path = RouterPath.ACTIVITY_LEVEL)
public class LevelRuleActivity extends BaseTitleActivity {

    @BindView(R2.id.me_level_tv)
    TextView meLevelTv;
    private ExActivityPresenter presenter;
    private LevelEntity entity;

    @Override
    protected int getLayoutId() {
        return R.layout.me_activity_level;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitleText(R.string.me_level_title);
        presenter = new ExActivityPresenter(this, this);
        presenter.level(1, 1);
    }

    @Override
    public void result(int code, Boolean hasNextPage, String response, Object object) {
        super.result(code, hasNextPage, response, object);
        if (code == 1) {
            if (object == null) return;
            entity = (LevelEntity) object;
            meLevelTv.setText(entity.getContent()!=null?Html.fromHtml(entity.getContent().substring(entity.getContent().lastIndexOf("</style>"))) :"");
            //meLevelTv.setText(entity.getContent()!=null?Html.fromHtml(entity.getContent()):"");
        }
    }

    @Override
    protected void rightTextBtnClick(View v) {

    }

    @Override
    protected void rightBtnClick(View v) {

    }

    @Override
    protected void initBundleData(Intent intent) {

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

}
