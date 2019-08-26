package com.seven.module_user.ui.fragment.token;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.seven.lib_common.base.activity.BaseTitleActivity;
import com.seven.lib_model.ApiManager;
import com.seven.lib_model.BaseResult;
import com.seven.lib_model.CommonObserver;
import com.seven.lib_model.model.user.mine.TokenDescEntity;
import com.seven.module_user.R;
import com.seven.module_user.R2;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by xxxxxxH on 2019/5/12.
 */

public class TokenDescActivity extends BaseTitleActivity {

    @BindView(R2.id.text)
    TextView textView;

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
        return R.layout.mu_activity_token_desc;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        ApiManager.getTokenDesc()
                .subscribe(new CommonObserver<BaseResult<TokenDescEntity>>() {
                    @Override
                    public void onNext(BaseResult<TokenDescEntity> tokenDescEntityBaseResult) {
                        TokenDescEntity entity = tokenDescEntityBaseResult.getData();
                        if (entity != null) {
                            textView.setText(Html.fromHtml(entity.getContent()));
                            setTitleText(entity.getTitle());
                        }
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

    }
}
