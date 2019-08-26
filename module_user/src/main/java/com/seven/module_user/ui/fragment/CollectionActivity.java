package com.seven.module_user.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gyf.barlibrary.ImmersionBar;
import com.seven.lib_common.base.activity.BaseActivity;
import com.seven.lib_common.base.activity.BaseAppCompatActivity;
import com.seven.lib_common.base.activity.BaseTitleActivity;
import com.seven.lib_common.utils.glide.GlideUtils;
import com.seven.lib_model.ApiManager;
import com.seven.lib_model.BaseResult;
import com.seven.lib_model.model.home.CartEntity;
import com.seven.lib_model.model.user.UserEntity;
import com.seven.lib_model.model.user.mine.ShopEntity;
import com.seven.module_user.R;
import com.seven.module_user.R2;
import com.seven.module_user.ui.fragment.view.BaseRecyclerView;
import com.seven.module_user.ui.fragment.view.DividerSpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class CollectionActivity extends BaseTitleActivity {
    @BindView(R2.id.list_view)
    BaseRecyclerView recyclerView;


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
        return R.layout.mu_activity_collect;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitleText(R.string.user_collect);

    }

    @Override
    protected void rightTextBtnClick(View v) {

    }

    @Override
    protected void rightBtnClick(View v) {

    }

    @Override
    protected void initBundleData(final Intent intent) {

        getData();
    }

    private void getData() {
        ApiManager.getCollectList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<BaseResult<ShopEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult<ShopEntity> shopEntityBaseResult) {
                        initListView(shopEntityBaseResult.getData().getItems());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void initListView(List<CartEntity> list) {
        recyclerView.setRefreshing(false);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        recyclerView.init(manager, new BaseQuickAdapter<CartEntity, BaseViewHolder>(R.layout.item_store_up_layout, list) {

            @Override
            protected void convert(BaseViewHolder helper, CartEntity item) {
                helper.setText(R.id.goods_name, item.getGoods_name())
                        .setText(R.id.sales, item.getSales() + "人付款")
                        .setText(R.id.money, String.valueOf(item.getPrice()));
                ImageView imageView = helper.getView(R.id.goods_img);
                GlideUtils.loadImage(mContext, item.getThumb(), imageView);
            }
        }).changeItemDecoration(new DividerSpaceItemDecoration(4))
                .setEmptyView(getEmptyView())
                .setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        getData();
                    }
                });
    }

    private View getEmptyView() {
        TextView textView = new TextView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        textView.setLayoutParams(params);
        textView.setGravity(Gravity.CENTER);
        textView.setText("暂无收藏");
        return textView;
    }
}
