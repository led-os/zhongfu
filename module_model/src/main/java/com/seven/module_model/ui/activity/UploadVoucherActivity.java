package com.seven.module_model.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.orhanobut.logger.Logger;
import com.seven.lib_common.base.activity.BaseTitleActivity;
import com.seven.lib_common.task.ActivityStack;
import com.seven.lib_common.utils.ScreenUtils;
import com.seven.lib_common.utils.glide.GlideUtils;
import com.seven.lib_model.model.model.UploadEntity;
import com.seven.lib_model.presenter.model.ActModelPresenter;
import com.seven.lib_opensource.event.ObjectsEvent;
import com.seven.lib_router.Constants;
import com.seven.lib_router.router.RouterPath;
import com.seven.module_model.R;
import com.seven.module_model.R2;

import org.greenrobot.eventbus.EventBus;


import butterknife.BindView;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/4/20
 */
@Route(path = RouterPath.ACTIVITY_UPLOAD_VOUCHER)
public class UploadVoucherActivity extends BaseTitleActivity {

    private static final int SELECT_PHOTO = 1;

    @Autowired(name = Constants.BundleConfig.ID)
    public int id;

    @BindView(R2.id.voucher_iv)
    public ImageView voucherIv;
    private String imagePath;

    private ActModelPresenter presenter;
    private UploadEntity uploadEntity;

    @Override
    protected int getLayoutId() {
        return R.layout.mm_activity_upload_voucher;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        ARouter.getInstance().inject(this);

        setTitleText(R.string.button_voucher);

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) voucherIv.getLayoutParams();
        params.width = (screenWidth - ScreenUtils.dip2px(mContext, 16 * 2)) / 3;
        params.height = (screenWidth - ScreenUtils.dip2px(mContext, 16 * 2)) / 3;
        voucherIv.setLayoutParams(params);

    }

    @Override
    protected void initBundleData(Intent intent) {

        presenter = new ActModelPresenter(this, this);

    }

    public void btClick(View view) {

        if (view.getId() == R.id.voucher_iv)
            selectPhoto();

        if (view.getId() == R.id.upload_btn) {

            showLoadingDialog();
            presenter.upload(Constants.RequestConfig.UPLOAD, imagePath, Constants.InterfaceConfig.UPLOAD_AVATAR);

        }

    }

    public void selectPhoto() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            openAlbum();
        }
    }

    private void openAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, SELECT_PHOTO);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {

                        Uri originalUri = data.getData();        //获得图片的uri
                        String[] proj = {MediaStore.Images.Media.DATA};

                        Cursor cursor = getContentResolver().query(originalUri, proj, null, null, null);
                        String path = "";
                        if (cursor.moveToFirst()) {
                            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                            path = cursor.getString(column_index);
                        }
                        cursor.close();
                        setImageView(path);
                    } catch (Exception e) {
                        Logger.e("TAG-->Error", e.toString());
                    }
                }
                break;
        }
    }

    private void setImageView(String path) {

        if (TextUtils.isEmpty(path)) return;

        imagePath = path;

        GlideUtils.loadImage(mContext, path, voucherIv);
    }

    @Override
    public void result(int code, Boolean hasNextPage, String response, Object object) {
        super.result(code, hasNextPage, response, object);
        switch (code) {
            case Constants.RequestConfig.UPLOAD:

                if (object == null) return;

                uploadEntity= (UploadEntity) object;

                presenter.businessProof(Constants.RequestConfig.BUSINESS_PROOF,id,uploadEntity.getUrl());

                break;

            case Constants.RequestConfig.BUSINESS_PROOF:

                ActivityStack.getInstance().finishActivity(TransactionDetailsActivity.class);

                EventBus.getDefault().post(new ObjectsEvent(Constants.EventConfig.BUSINESS_PROOF,id));

                onBackPressed();

                break;
        }
    }

    @Override
    protected void rightTextBtnClick(View v) {

    }

    @Override
    protected void rightBtnClick(View v) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void closeLoading() {

        dismissLoadingDialog();

    }

    @Override
    public void showToast(String msg) {

    }
}
