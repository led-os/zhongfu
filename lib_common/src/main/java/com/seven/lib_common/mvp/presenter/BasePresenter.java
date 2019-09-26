package com.seven.lib_common.mvp.presenter;

import android.os.Bundle;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.seven.lib_common.R;
import com.seven.lib_common.listener.LifeCycleListener;
import com.seven.lib_common.mvp.view.IBaseView;
import com.seven.lib_common.base.activity.BaseActivity;
import com.seven.lib_common.base.activity.BaseAppCompatActivity;
import com.seven.lib_common.base.fragment.BaseFragment;
import com.seven.lib_common.utils.NetWorkUtils;
import com.seven.lib_common.utils.ResourceUtils;
import com.seven.lib_common.utils.ToastUtils;
import com.seven.lib_http.exception.ApiException;
import com.seven.lib_http.observer.HttpRxObserver;
import com.seven.lib_opensource.application.SSDK;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.ArrayList;

import io.reactivex.disposables.Disposable;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2017/10/26
 */

public class BasePresenter<V, T> implements LifeCycleListener {

    protected Reference<V> mViewRef;
    protected V mView;
    protected Reference<T> mActivityRef;
    protected T mActivity;

    protected boolean isRequest;
    private boolean isToast;

    protected int page;
    protected int pageSize;

    public BasePresenter(V view, T activity) {
        attachView(view);
        attachActivity(activity);
        setListener(activity);
        isNetwork();
        page = 1;
        pageSize = 10;
    }

    private boolean isNetwork() {

        isRequest = NetWorkUtils.isNetWork();

        if (!isRequest && !isToast) {
            isToast = true;
            if (mActivity.getClass().toString().startsWith("class com.sinostage.kolo.service")
                    || mActivity.getClass().toString().equals("class com.sinostage.kolo.ui.activity.SplashActivity"))
                return isRequest;
            ToastUtils.showToast(SSDK.getInstance().getContext(), ResourceUtils.getText(R.string.error_network));
        }
        return isRequest;
    }

    /**
     * Set life cycle listening
     */
    private void setListener(T activity) {
        if (getActivity() != null) {
            if (activity instanceof BaseActivity) {
                ((BaseActivity) getActivity()).setOnLifeCycleListener(this);
            } else if (activity instanceof BaseAppCompatActivity) {
                ((BaseAppCompatActivity) getActivity()).setOnLifeCycleListener(this);
            } else if (activity instanceof BaseFragment) {
                ((BaseFragment) getActivity()).setOnLifeCycleListener(this);
            }
        }
    }

    public <T> ArrayList<T> jsonToArrayList(String json, Class<T> clazz, String key) {
        ArrayList<T> arrayList = null;
        try {
            if (!TextUtils.isEmpty(key))
                json = new JSONObject(json).getString(key);
            Type type = new TypeToken<ArrayList<JsonObject>>() {
            }.getType();
            ArrayList<JsonObject> jsonObjects = new Gson().fromJson(json, type);
            arrayList = new ArrayList<>();
            for (JsonObject jsonObject : jsonObjects) {
                arrayList.add(new Gson().fromJson(jsonObject, clazz));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return null;
        }
        return arrayList;
    }

    /**
     * relevance
     *
     * @param view
     */
    private void attachView(V view) {
        mViewRef = new WeakReference<V>(view);
        mView = mViewRef.get();
    }

    /**
     * relevance
     *
     * @param activity
     */
    private void attachActivity(T activity) {
        mActivityRef = new WeakReference<T>(activity);
        mActivity = mActivityRef.get();
    }

    /**
     * destroy
     */
    private void detachView() {
        if (isViewAttached()) {
            mViewRef.clear();
            mViewRef = null;
        }
    }

    /**
     * destroy
     */
    private void detachActivity() {
        if (isActivityAttached()) {
            mActivityRef.clear();
            mActivityRef = null;
        }
    }

    /**
     * get
     *
     * @return
     */
    public V getView() {
        if (mViewRef == null) {
            return null;
        }
        return mViewRef.get();
    }

    /**
     * get
     *
     * @return
     */
    public T getActivity() {
        if (mActivityRef == null) {
            return null;
        }
        return mActivityRef.get();
    }

    /**
     * Has it been linked
     *
     * @return
     */
    public boolean isViewAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }

    /**
     * Has it been linked
     *
     * @return
     */
    public boolean isActivityAttached() {
        return mActivityRef != null && mActivityRef.get() != null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onRestart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {
        detachView();
        detachActivity();
    }

    public <T> HttpRxObserver get(final IBaseView view, final int requestCode, final Class<T> clazz, final String key, final boolean isArray) {

        if (!isNetwork())
            return null;

        HttpRxObserver httpRxObserver = new HttpRxObserver(mActivity + "getInfo") {
            @Override
            protected void onStart(Disposable d) {
                if (view != null)
                    view.showLoading();
            }

            @Override
            protected void onError(ApiException e) {
                Logger.e("onError code:" + e.getCode() + " msg:" + e.getMsg());
                ToastUtils.showToast(SSDK.getInstance().getContext(), e.getMsg());
                if (view != null) {
                    view.closeLoading();
                    view.showToast(e.getMsg());
                }
            }

            @Override
            protected void onSuccess(Object response) {
                Logger.json(response.toString());
                Object data = null;
                Boolean hasNextPage = true;
                if (clazz != null) {
                    if (isArray)
                        data = jsonToArrayList(response.toString(), clazz, key);
                    else {
                        if (TextUtils.isEmpty(key))
                            data = new Gson().fromJson(response.toString(), clazz);
                        else {
                            try {
                                String json = new JSONObject(response.toString()).getString(key);
                                data = new Gson().fromJson(json, clazz);
                                try {
                                    String next = new JSONObject(response.toString()).getString("hasNextPage");
                                    hasNextPage = new Gson().fromJson(next, Boolean.class);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                if (getView() != null) {
                    view.closeLoading();
                    view.result(requestCode, data);
                    view.result(requestCode, hasNextPage, data);
                    view.result(requestCode, hasNextPage,response.toString(), data);
                }
            }
        };
        return httpRxObserver;
    }

    public <T> HttpRxObserver getList( final IBaseView view, final int requestCode, final Class<T> clazz, final String key, final boolean isArray) {

        if (!isNetwork())
            return null;

        HttpRxObserver httpRxObserver = new HttpRxObserver(mActivity + "getInfo") {
            @Override
            protected void onStart(Disposable d) {
                if (view != null)
                    view.showLoading();
            }

            @Override
            protected void onError(ApiException e) {
                Logger.e("onError code:" + e.getCode() + " msg:" + e.getMsg());
                ToastUtils.showToast(SSDK.getInstance().getContext(), e.getMsg());
                if (view != null) {
                    view.closeLoading();
                    view.showToast(e.getMsg());
                }
            }

            @Override
            protected void onSuccess(Object response) {
                Logger.json(response.toString());
                Object data = null;
                Boolean hasNextPage = true;
                if (clazz != null) {
                    if (isArray) {
                        data = jsonToArrayList(response.toString(), clazz, key);
                        try {
                            String json = new JSONObject(response.toString()).getString("hasNextPage");
                            hasNextPage = new Gson().fromJson(json, Boolean.class);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (getView() != null) {
                    view.closeLoading();
                    view.result(requestCode, data);
                    view.result(requestCode, hasNextPage, data);
                    view.result(requestCode, hasNextPage,response.toString(), data);
                }
            }
        };
        return httpRxObserver;
    }

    public <T> HttpRxObserver get(final IBaseView view, final int requestCode) {

        if (!isNetwork())
            return null;

        HttpRxObserver httpRxObserver = new HttpRxObserver(mActivity + "getInfo") {
            @Override
            protected void onStart(Disposable d) {
                if (view != null)
                    view.showLoading();
            }

            @Override
            protected void onError(ApiException e) {
                Logger.e("onError code:" + e.getCode() + " msg:" + e.getMsg());
                ToastUtils.showToast(SSDK.getInstance().getContext(), e.getMsg());
                if (view != null) {
                    view.closeLoading();
                    view.showToast(e.getMsg());
                }
            }

            @Override
            protected void onSuccess(Object response) {
                Logger.i(response.toString());
                if (getView() != null) {
                    view.closeLoading();
                    view.result(requestCode, response.toString());
                    view.result(requestCode, null, response.toString());
                    view.result(requestCode, null,response.toString(), response.toString());
                }
            }
        };
        return httpRxObserver;
    }
}
