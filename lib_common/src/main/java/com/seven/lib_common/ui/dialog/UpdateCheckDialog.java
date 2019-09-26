package com.seven.lib_common.ui.dialog;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.seven.lib_common.R;
import com.seven.lib_common.base.dialog.BaseDialog;
import com.seven.lib_common.stextview.TypeFaceView;
import com.seven.lib_common.utils.AnimationUtils;
import com.seven.lib_common.utils.NetWorkUtils;
import com.seven.lib_common.utils.ResourceUtils;
import com.seven.lib_common.utils.SaveUtils;
import com.seven.lib_common.utils.ScreenUtils;
import com.seven.lib_common.utils.ToastUtils;
import com.seven.lib_common.utils.UpdateApkUtils;
import com.seven.lib_router.Constants;


/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2018/4/20
 */

public class UpdateCheckDialog extends BaseDialog {

    private RelativeLayout checkRl;
    private TypeFaceView version;
    private TypeFaceView content;

    private RelativeLayout uploadRl;
    private ProgressBar progressBar;
    private TypeFaceView progressTv;

    private RelativeLayout cancelRl;
    private TypeFaceView cancelTv;

    private RelativeLayout sureRl;
    private TypeFaceView sureTv;

    private RelativeLayout rootView;

    private RelativeLayout dialogLayout;

    private RelativeLayout snackBar;

    private String versionCode;
    private String url;
    private String desc;

    public UpdateCheckDialog(Activity activity, int theme, com.seven.lib_common.listener.OnClickListener listener) {
        super(activity, theme, listener);
    }

    public UpdateCheckDialog(Activity activity, int theme, com.seven.lib_common.listener.OnClickListener listener,String... strings) {
        this(activity, theme, listener);
        this.versionCode=strings[0];
        this.url=strings[1];
        this.desc=strings[2];
    }

    @Override
    public int getRootLayoutId() {
        return R.layout.lb_dialog_update_check;
    }

    @Override
    public void onInitRootView(Bundle savedInstanceState) {

        initView();

        initData();
    }

    @Override
    public void initView() {

        checkRl = getView(checkRl, R.id.update_check_rl);
        version = getView(version, R.id.version_tv);
        content = getView(content, R.id.content_tv);

        uploadRl = getView(uploadRl, R.id.update_upload_rl);
        progressBar = getView(progressBar, R.id.progress_bar);
        progressTv = getView(progressTv, R.id.progress_tv);

        cancelTv = getView(cancelTv, R.id.cancel_tv);
        sureTv = getView(sureTv, R.id.sure_tv);

        cancelRl = getView(cancelRl, R.id.cancel_rl);
        sureRl = getView(sureRl, R.id.sure_rl);

        rootView = getView(rootView, R.id.root_view);
        dialogLayout = getView(dialogLayout, R.id.item_layout);

        snackBar = getView(snackBar, R.id.snack_view);
        snackBar.setOnClickListener(this);

        cancelRl.setOnClickListener(this);
        sureRl.setOnClickListener(this);

        progressTv.setText(activity.getString(R.string.update_progress) + " 0%");
        sureRl.setSelected(true);

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) dialogLayout.getLayoutParams();
        params.width = (int) (screenWidth * 0.9) -
                (int) (activity.getResources().getDimension(R.dimen.margin_24)) * 2;
        dialogLayout.setLayoutParams(params);
    }

    @Override
    public void initData() {

        version.setText(ResourceUtils.getFormatText(R.string.update_version_format, versionCode));
        content.setText(desc);
    }

    @Override
    public void onClick(View v) {

        if(R.id.cancel_rl==v.getId()){
            if (uploadRl.getVisibility() == View.VISIBLE) {

            }
            listener.onCancel(v);
            dismiss();
            snackBar.setVisibility(View.GONE);
        }

        if(R.id.sure_rl==v.getId()){
//            if (uploadRl.getVisibility() == View.VISIBLE) {
//                checkAndroid_N(v);
//            } else {
//
//                boolean isUpdate = UpdateApkUtils.getInstance().checkingApk();
//
//                if (isUpdate) {
//                    checkAndroid_N(v);
//                } else {
//
//                    if (!NetWorkUtils.isNetWork()) {
//                        ToastUtils.showToast(activity,activity.getString(R.string.error_network));
//                        return;
//                    }
//
//                    update();
//                }
//            }
            listener.onClick(v);
        }

        if(R.id.snack_view==v.getId()){
            Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, Uri.parse("package:" + activity.getPackageName()));
            activity.startActivity(intent);
        }

    }

    private void checkAndroid_N(View v) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O &&
                !activity.getPackageManager().canRequestPackageInstalls()) {
            listener.onClick(v);
            showSnackBar();
            return;
        }
        UpdateApkUtils.getInstance().installApk();
    }

    private void showSnackBar() {

        if (snackBar.getVisibility() == View.VISIBLE) return;

        snackBar.setVisibility(View.VISIBLE);
        AnimationUtils.translation(snackBar, Constants.AnimName.TRANS_Y,
                ScreenUtils.dip2px(activity,50),
                0, Constants.TimeConfig.TRANS_TIME, null);

    }

    private void update() {

        checkRl.setVisibility(View.GONE);
        uploadRl.setVisibility(View.VISIBLE);

        cancelTv.setText(activity.getString(R.string.cancel));
        sureTv.setText(activity.getString(R.string.update_install));
        sureRl.setSelected(false);

        download();
    }

    private void download() {

        String savePath = SaveUtils.getInstance().downloadApk(versionCode.replace(".", ""));

        FileDownloader.getImpl().create(url)
                .setPath(savePath)
                .setListener(new FileDownloadListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {

                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {

                        Message msg = new Message();
                        msg.what = 100;
                        msg.arg1 = soFarBytes;
                        msg.arg2 = totalBytes;
                        handler.sendMessage(msg);

                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        handler.sendEmptyMessage(101);
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {

                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {

                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {

                    }
                }).start();

    }

    private Handler handler = new Handler() {

        private int radio;

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {

                case 100:

                    int soFarBytes = msg.arg1;
                    int totalBytes = msg.arg2;

                    radio = (int) (((double) soFarBytes / (double) totalBytes) * (double) 100);

                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(radio);
                            progressTv.setText(activity.getString(R.string.update_progress) + " " + radio + "%");
                        }
                    });

                    break;

                case 101:

                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressTv.setText(activity.getString(R.string.update_progress_succeed) + " 100%");
                            sureRl.setSelected(true);
                        }
                    });

                    break;

            }
        }
    };

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    @Override
    public void show() {
        WindowManager wm = (WindowManager) getContext().getSystemService(
                Context.WINDOW_SERVICE);
        int width = 0;

        Point size = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            size = new Point();
            wm.getDefaultDisplay().getSize(size);
        } else {
            width = wm.getDefaultDisplay().getWidth();
        }

        if (activity != null && !activity.isFinishing())
            super.show();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            getWindow().setLayout(size.x,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        } else {
            getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

}
