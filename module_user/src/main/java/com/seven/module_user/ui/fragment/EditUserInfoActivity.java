package com.seven.module_user.ui.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.seven.lib_common.base.activity.BaseTitleActivity;
import com.seven.lib_common.utils.ToastUtils;
import com.seven.lib_common.utils.glide.GlideUtils;
import com.seven.lib_model.ApiManager;
import com.seven.lib_model.BaseResult;
import com.seven.lib_model.CommonObserver;
import com.seven.lib_model.model.model.UploadEntity;
import com.seven.lib_model.model.user.UserEntity;
import com.seven.lib_model.model.user.mine.DTEntity;
import com.seven.lib_model.model.user.mine.UpLoadImageEntity;
import com.seven.lib_model.presenter.model.ActModelPresenter;
import com.seven.lib_router.Constants;
import com.seven.lib_router.db.shard.SharedData;
import com.seven.module_user.R;
import com.seven.module_user.R2;
import com.seven.module_user.ui.fragment.utils.BitmapUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class EditUserInfoActivity extends BaseTitleActivity implements
        TakePhoto.TakeResultListener, InvokeListener {

    private BottomSheetDialog mBottomSheetDialog;
    private RxPermissions mRxPermissions;
    private String TAG = EditUserInfoActivity.class.getName();
    @BindView(R2.id.user_photo)
    LinearLayout userPhoto;
    @BindView(R2.id.user_nick_name)
    EditText userNickName;
    @BindView(R2.id.choose_sex)
    TextView chooseSex;
    @BindView(R2.id.user_photo_img)
    ImageView user_photo_img;
    @BindView(R2.id.mu_save)
    Button mu_save;

    private Uri PicUri;
    private Uri PicCropUri;
    private String currentUrl = "";

    ActModelPresenter presenter;

    @Override
    public void showLoading() {
        showLoadingDialog();
    }

    @Override
    public void closeLoading() {
        dismissLoadingDialog();
    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.mu_activity_edit_user_info;
    }
    UserEntity entity;
    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitleText(R.string.user_edit_info);
        entity = new Gson().fromJson(SharedData.getInstance().getUserInfo(), UserEntity.class);
        userNickName.setText(entity.getUsername());
        chooseSex.setText(entity.getSex().equals("male") ? "男" : "女");
        GlideUtils.loadCircleImage(mContext, entity.getAvatar(), user_photo_img);
        presenter = new ActModelPresenter(this, this);
        mu_save.setOnClickListener(this);
    }

    @Override
    protected void rightTextBtnClick(View v) {

    }

    @Override
    protected void rightBtnClick(View v) {

    }

    @Override
    protected void initBundleData(Intent intent) {
        mRxPermissions = new RxPermissions(this);
        mRxPermissions.setLogging(true);
    }

    private void chooseSex() {
        final List<String> sexList = new ArrayList<>();
        sexList.add("男");
        sexList.add("女");
        OptionsPickerView sexPickerView = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                chooseSex.setText(sexList.get(options1));
            }
        }).setContentTextSize(20)//设置滚轮文字大小
                .setDividerColor(Color.LTGRAY)//设置分割线的颜色
                .setSelectOptions(0)//默认选中项
                .setBgColor(Color.WHITE)
                .setTitleBgColor(getResources().getColor(R.color.color_eee))
                .setCancelColor(getResources().getColor(R.color.color_6c))
                .setSubmitColor(getResources().getColor(R.color.color_1e1d1d))
                .setTextColorCenter(getResources().getColor(R.color.color_1e1d1d))
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setOutSideColor(0x00000000) //设置外部遮罩颜色
                .build();
        sexPickerView.setPicker(sexList);
        sexPickerView.show();
    }


    @OnClick({R2.id.user_photo, R2.id.choose_sex})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.user_photo) {
            onChangePic();
        } else if (view.getId() == R.id.choose_sex) {
            chooseSex();
        }
    }

    private void onChangePic() {
        mBottomSheetDialog = new BottomSheetDialog(this);
        View v = LayoutInflater.from(this).inflate(R.layout.dialog_choice_img, null, false);
        v.findViewById(R.id.photo).setOnClickListener(this);
        v.findViewById(R.id.camera).setOnClickListener(this);
        v.findViewById(R.id.cancel).setOnClickListener(this);
        mBottomSheetDialog.setContentView(v);
        mBottomSheetDialog.show();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.cancel) {
            mBottomSheetDialog.dismiss();
        } else if (view.getId() == R.id.photo) {
            selectGallery();
            mBottomSheetDialog.dismiss();
        } else if (view.getId() == R.id.camera) {
            selectCapture();
            mBottomSheetDialog.dismiss();
        } else if (view.getId() == R.id.left_btn) {
            finish();
        }else if (view.getId() == R.id.mu_save){
            modifyUserInfo();
        }
    }

    //选择相册
    public void selectGallery() {
        mRxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
//                            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                            intent.setType("image/*");
//                            startActivityForResult(intent, 10086);
                            File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
                            if (!file.getParentFile().exists()) {
                                file.getParentFile().mkdirs();
                            }
                            Uri imageUri = Uri.fromFile(file);
                            getTakePhoto().onPickFromGalleryWithCrop(imageUri, getCropBuilder().create());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void selectCapture() {
        mRxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
//                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//action is capture
//                            intent.putExtra("return-data", false);
//                            // intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//                            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
//                            intent.putExtra("noFaceDetection", true);
//                            startActivityForResult(intent, 10010);
                            if (getOutPutUri()!=null){
                                getTakePhoto().onPickFromCaptureWithCrop(getOutPutUri(), getCropBuilder().create());
                            }else {
                                showToast("SD卡不可用");
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode) {
//            case 10086:
//                //选择照片
//                if (data == null) return;
//                Uri uri = data.getData();
//                String realUri = uri2filePath(uri, mContext);
//                File file = new File(realUri);
//                PicUri = Uri.fromFile(file);
//                PicCropUri = getTargetImageUri(false);
//                //cropImg(PicUri, PicCropUri);
//                GlideUtils.loadCircleImage(mContext, PicUri.getPath(), user_photo_img);
//                upLoad(file);
////                Uri originalUri = data.getData();        //获得图片的uri
////                String[] proj = {MediaStore.Images.Media.DATA};
////
////                Cursor cursor = getContentResolver().query(originalUri, proj, null, null, null);
////                String path = "";
////                if (cursor.moveToFirst()) {
////                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
////                    path = cursor.getString(column_index);
////                }
////                cursor.close();
////                GlideUtils.loadCircleImage(mContext, path, user_photo_img);
//                //   presenter.upload(Constants.RequestConfig.UPLOAD, file.getPath(), Constants.InterfaceConfig.UPLOAD_AVATAR);
//                break;
//            case 10010:
//                //拍照
//                PicCropUri = getTargetImageUri(false);
//                cropImg(PicUri, PicCropUri);
//                break;
//            case 12345:
//                currentUrl = PicCropUri.getPath();
//                GlideUtils.loadCircleImage(mContext, currentUrl, user_photo_img);
//                File file1 = new File(currentUrl);
//                upLoad(file1);
//                //  presenter.upload(Constants.RequestConfig.UPLOAD, file1.getPath(), Constants.InterfaceConfig.UPLOAD_AVATAR);
//                break;
//
//        }

    }

    @Override
    public void result(int code, Boolean hasNextPage, String response, Object object) {
        super.result(code, hasNextPage, response, object);
        if (code == Constants.RequestConfig.UPLOAD) {
            if (object == null) return;
            UploadEntity uploadEntity = (UploadEntity) object;
        }
    }

    public void cropImg(Uri sourceUri, Uri targetUri) {
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = width * 2 / 3;
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(sourceUri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", width);
        intent.putExtra("outputY", width);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, targetUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, 12345);
    }

    public String uri2filePath(Uri uri, Context context) {
        String path = "";
        if (uri.toString().startsWith("file")) {
            return uri.getPath();
        }
        Cursor cursor = null;
        try {
            //android 4.4.4会获得另外一种图片uri地址:
            if (Build.VERSION.SDK_INT >= 19 && DocumentsContract.isDocumentUri(context, uri)) {
                String wholeID = DocumentsContract.getDocumentId(uri);
                String id = wholeID.split(":")[1];
                String[] column = {MediaStore.Images.Media.DATA};
                String sel = MediaStore.Images.Media._ID + "=?";
                cursor = context.getContentResolver().query(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, column, sel,
                        new String[]{id}, null);
                int columnIndex = cursor.getColumnIndex(column[0]);
                if (cursor.moveToFirst()) {
                    path = cursor.getString(columnIndex);
                }
            } else {
                String[] projection = {MediaStore.MediaColumns.DATA};
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                if (cursor.moveToFirst()) {
                    path = cursor.getString(column_index);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        } finally {
            Log.i(TAG, "imageUrl:" + path);
            cursor.close();
        }
        return path;
    }

    public Uri getTargetImageUri(boolean isTemp) {
        //组装图片文件夹和裁剪后的目标文件
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            Log.e(TAG, "SD card is not avaiable/writeable right now.");
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss", Locale.CHINA);
        String name;
        if (isTemp) {
            name = "/" + format.format(Calendar.getInstance().getTime()) + "_temp.jpg";
        } else {
            name = "/" + format.format(Calendar.getInstance().getTime()) + "_crop.jpg";
        }
        File imgFileDir = new File(Environment.getExternalStorageDirectory().getPath());
        if (!imgFileDir.exists()) {
            // 创建文件夹
            if (!imgFileDir.mkdirs()) {
                Log.e(TAG, "创建文件夹失败：" + imgFileDir.getPath());
            }
        }
        File fileName = new File(imgFileDir.getPath() + name);
        Uri outputUri = Uri.fromFile(fileName);
        Log.d(TAG, "outputUri:" + outputUri);
        return outputUri;
    }

    private void upLoad(File file) {
//        MultipartBody.Builder builder = new MultipartBody.Builder();
//        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
//        builder.addFormDataPart("image", file.getName(), requestBody);
//        builder.setType(MultipartBody.FORM);
//        builder.addFormDataPart("scene", "test");
//        MultipartBody body = builder.build();
        //111

        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        RequestBody scene = RequestBody.create(MediaType.parse("imageData"), "");
        ApiManager.upLoad(part, scene)
                .subscribe(new CommonObserver<BaseResult<DTEntity>>() {
                    @Override
                    public void onNext(BaseResult<DTEntity> baseResult) {
                        ToastUtils.showToast(mContext, baseResult.getMessage());
                        if (baseResult.getCode() == 1) {
                            currentUrl = baseResult.getData().getUrl();
                        }
                    }
                });
    }

    private void modifyUserInfo() {
        showLoading();
        UserEntity userEntity = entity;
        userEntity.setUsername(userNickName.getText().toString());
        userEntity.setAvatar(currentUrl);
        userEntity.setSex(chooseSex.getText().toString().equals("男") ? "male" : "female");
        ApiManager.editUserInfo(userEntity)
                .subscribe(new CommonObserver<BaseResult>() {
                    @Override
                    public void onNext(BaseResult baseResult) {
                        if (baseResult.getCode() == 1) {
                            finish();
                        }
                        closeLoading();
                    }
                });
    }

    TakePhoto takePhoto;
    InvokeParam invokeParam;

    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        return takePhoto;
    }

    @Override
    public void takeSuccess(TResult result) {
        String originalPath = result.getImage().getOriginalPath();
        Log.e("originalPath",originalPath);
        if (!TextUtils.isEmpty(originalPath)) {
            GlideUtils.loadCircleImage(mContext, originalPath, user_photo_img);
            File file = new File(originalPath);
            upLoad(file);
        }
    }


    @Override
    public void takeFail(TResult result, String msg) {
        showToast(TextUtils.isEmpty(msg)?"":msg);
    }

    @Override
    public void takeCancel() {

    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    private Uri getOutPutUri() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
            if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
            Uri imageUri = Uri.fromFile(file);
            return imageUri;
        }
        return null;
    }

    private CropOptions.Builder getCropBuilder() {
        CropOptions.Builder builder = new CropOptions.Builder();
        builder.setWithOwnCrop(true);
        builder.setOutputX(600).setOutputY(600);
        return builder;
    }
}
