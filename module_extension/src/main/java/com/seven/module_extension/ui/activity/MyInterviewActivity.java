package com.seven.module_extension.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.seven.lib_common.base.activity.BaseTitleActivity;
import com.seven.lib_common.listener.OnClickListener;
import com.seven.lib_common.stextview.TypeFaceView;
import com.seven.lib_common.utils.NetWorkUtils;
import com.seven.lib_common.utils.ToastUtils;
import com.seven.lib_common.utils.glide.GlideUtils;
import com.seven.lib_common.widget.loadmore.LoadMoreView;
import com.seven.lib_model.model.extension.ItemsBean;
import com.seven.lib_model.model.extension.MyInterViewEntity;
import com.seven.lib_model.model.extension.ParentInfo;
import com.seven.lib_model.model.user.UserEntity;
import com.seven.lib_model.presenter.extension.ExActivityPresenter;
import com.seven.lib_router.db.shard.SharedData;
import com.seven.lib_router.router.RouterPath;
import com.seven.module_extension.R;
import com.seven.module_extension.R2;
import com.seven.module_extension.ui.adapter.MyInviteAdapter;
import com.seven.module_extension.ui.dialog.ShareDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.http.Body;

@Route(path = RouterPath.ACTIVITY_MY_INTERVIEW)
public class MyInterviewActivity extends BaseTitleActivity {

    @Autowired(name = "id")
    String id = "";
    @Autowired(name = "name")
    String name = "";

    @BindView(R2.id.me_rv_myinterview)
    RecyclerView meRvMyinterview;
    @BindView(R2.id.me_empty)
    TextView me_empty;
    @BindView(R2.id.me_invite_btn)
    Button me_invite_btn;
//    @BindView(R2.id.me_refresh)
//    SwipeRefreshLayout me_refresh;
    private ExActivityPresenter presenter;
    private List<MyInterViewEntity> interViewList;
    private MyInviteAdapter adapter;
    ShareDialog dialog;
    private String userId = "";
    private int page = 1;
    private int pageSize = 10;
    private boolean isMore;
    private boolean isRefresh;

    @Override
    protected int getLayoutId() {
        return R.layout.me_acitvity_myinterview;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        statusBar = StatusBar.LIGHT;

        me_invite_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialog == null) {
                    dialog = new ShareDialog(MyInterviewActivity.this, R.style.Dialog, null);
                }
                if (!dialog.isShowing())
                    dialog.showDialog(0, -screenHeight);
            }
        });
    }

    @Override
    public void result(int code, Boolean hasNextPage, String response, Object object) {
        super.result(code, hasNextPage, response, object);
        if (code == 1) {
            if (object == null) {
                isMore = true;
                adapter.loadMoreEnd();
            }else {
                interViewList = new ArrayList<>();
                MyInterViewEntity entity = (MyInterViewEntity) object;
                interViewList.add(entity);
                if (page == 1) {
                    adapter.setNewData(interViewList.get(0).getItems());
                    adapter.setHeaderView(headerView());
                } else {
                    adapter.addData(interViewList.get(0).getItems());
                    adapter.setHeaderView(headerView());
                }
                if (isRefresh) {
                    adapter.setNewData(interViewList.get(0).getItems());
                    isRefresh = false;
                    isMore = false;
                }

                adapter.loadMoreComplete();

                if (interViewList.get(0).getItems().size() < pageSize) {
                    isMore = false;
                    adapter.loadMoreEnd();
                }

            }

        }
    }


    private void setRv() {
        adapter = new MyInviteAdapter(R.layout.me_item_myinterview, null);
        meRvMyinterview.setLayoutManager(new LinearLayoutManager(mContext));
        meRvMyinterview.setAdapter(adapter);
//        adapter.addHeaderView(headerView());
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<ItemsBean> list1 = adapter.getData();
                int id = list1.get(position).getId();
                name = list1.get(position).getUsername();
                ARouter.getInstance().build(RouterPath.ACTIVITY_MY_INTERVIEW)
                        .withString("id", id + "")
                        .withString("name", name)
                        .navigation();
            }
        });
        adapter.setLoadMoreView(new LoadMoreView());
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        },meRvMyinterview);
    }

    @Override
    protected void rightTextBtnClick(View v) {

    }

    @Override
    protected void rightBtnClick(View v) {

    }

    @Override
    protected void initBundleData(Intent intent) {
        UserEntity userEntity = new Gson().fromJson(SharedData.getInstance().getUserInfo(), UserEntity.class);
        userId = String.valueOf(userEntity.getId());
        if (intent == null) intent = getIntent();
        id = intent.getStringExtra("id");
        name = intent.getStringExtra("name");
        if (TextUtils.isEmpty(name))
            setTitleText("我的团队");
        else
            setTitleText(name + "的团队");
        presenter = new ExActivityPresenter(this, this);
        setRv();
        request(page);

//        me_refresh.setColorSchemeResources(
//                R.color.primary,
//                R.color.primary,
//                R.color.primary,
//                R.color.primary);
//        me_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                if (!NetWorkUtils.isNetWork()) {
//                    me_refresh.setRefreshing(false);
//                    return;
//                }
//                isRefresh = true;
//                page = 1;
//                request(page);
//            }
//        });

    }

    private void request(int page){
        if (TextUtils.isEmpty(id)) {
            return;
        } else {
            presenter.invite(1, Integer.parseInt(id), page, pageSize);
        }
    }

    private View headerView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.me_header_myinvite, null);
        ImageView me_headr_myinterview_iv = view.findViewById(R.id.me_headr_myinterview_iv);
        TypeFaceView me_headr_interview_name = view.findViewById(R.id.me_headr_interview_name);
        TypeFaceView me_headr_interview_my_leader = view.findViewById(R.id.me_headr_interview_my_leader);
        TypeFaceView me_headr_interview_mazai = view.findViewById(R.id.me_headr_interview_mazai);
        ImageView me_headr_interview_sex = view.findViewById(R.id.me_headr_interview_sex);
        ImageView me_headr_interview_level = view.findViewById(R.id.me_headr_interview_level);
        if (interViewList.get(0).getParent_info() != null) {
            if (!TextUtils.isEmpty(name)) {
                me_headr_interview_my_leader.setText(name + "的上级");
                me_headr_interview_mazai.setText(name + "的下级");
            }
            GlideUtils.loadCircleImage(mContext, interViewList.get(0).getParent_info().getAvatar(), me_headr_myinterview_iv);
            me_headr_interview_name.setText(interViewList.get(0).getParent_info().getUsername());
            if (interViewList.get(0).getParent_info().getSex() != null) {
                if (interViewList.get(0).getParent_info().getSex().equals("male")) {
                    me_headr_interview_sex.setBackgroundResource(R.drawable.me_male);
                } else {
                    me_headr_interview_sex.setBackgroundResource(R.drawable.me_famale);
                }
            }
            if (String.valueOf(interViewList.get(0).getParent_info().getRole()) != null) {
                switch (interViewList.get(0).getParent_info().getRole()) {
                    case 0:
                        me_headr_interview_level.setBackground(mContext.getResources().getDrawable(R.drawable.me_normaluser));
                        break;
                    case 1:
                        me_headr_interview_level.setBackground(mContext.getResources().getDrawable(R.drawable.me_vip));
                        break;
                    case 2:
                        me_headr_interview_level.setBackground(mContext.getResources().getDrawable(R.drawable.me_kuangzhu));
                        break;

                    case 3:
                        me_headr_interview_level.setBackground(mContext.getResources().getDrawable(R.drawable.me_changzhu));
                        break;

                    case 4:
                        me_headr_interview_level.setBackground(mContext.getResources().getDrawable(R.drawable.ctylord));
                        break;
                    default:
                }
            }
        }
        return view;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private void loadMore(){
        if (isMore)
            ToastUtils.showToast(mContext,"暂无更多数据");
//            return;
        page++;
        request(page);
    }
}
