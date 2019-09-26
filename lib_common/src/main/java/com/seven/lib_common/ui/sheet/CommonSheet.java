package com.seven.lib_common.ui.sheet;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.orhanobut.logger.Logger;
import com.seven.lib_common.R;
import com.seven.lib_common.adapter.SheetCommonAdapter;
import com.seven.lib_common.base.sheet.BaseSheet;
import com.seven.lib_common.entity.SheetCommonEntity;
import com.seven.lib_common.stextview.TypeFaceView;
import com.seven.lib_common.utils.ResourceUtils;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;


/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2017/11/29
 */
public class CommonSheet extends BaseSheet implements BaseQuickAdapter.OnItemClickListener, EasyPermissions.PermissionCallbacks {

    public static final int DELETE = 1000;
    public static final int REPLY = 1001;
    public static final int MESSAGE_REPLY = 1002;
    public static final int MESSAGE_VIDEO = 1003;
    public static final int LANGUAGE = 1004;
    public static final int AVATAR = 1005;
    public static final int DYNAMIC_REPORT = 1011;
    public static final int DYNAMIC_DELETE = 1012;
    public static final int DYNAMIC_COMMENTS_REPLY = 1013;
    public static final int DYNAMIC_COMMENTS_DELETE = 1014;
    public static final int MESSAGE_FEED = 1015;
    public static final int MESSAGE_FEED_COMMENT = 1016;
    public static final int MESSAGE_FEED_LIKE = 1017;
    public static final int SAVE_IMAGE = 1018;

    public static final int FUNCTION_CANCEL = 2000;
    public static final int FUNCTION_DELETE = 2001;
    public static final int FUNCTION_REPLY = 2002;
    public static final int FUNCTION_REPORT = 2003;
    public static final int FUNCTION_VIDEO = 2004;
    public static final int FUNCTION_LANGUAGE_CHINA = 2005;
    public static final int FUNCTION_LANGUAGE_ENGLISH = 2006;
    public static final int FUNCTION_PHOTO = 2007;
    public static final int FUNCTION_CAMERA = 2008;
    public static final int FUNCTION_DYNAMIC_REPORT = 2009;
    public static final int FUNCTION_DYNAMIC_DELETE = 2010;
    public static final int MESSAGE_FEED_REPLY = 2011;
    public static final int MESSAGE_FEED_REPORT = 2012;
    public static final int MESSAGE_FEED_SOURCE = 2013;
    public static final int MESSAGE_FEED_COMMENT_REPLY = 2014;
    public static final int MESSAGE_FEED_COMMENT_REPORT = 2015;
    public static final int MESSAGE_FEED_COMMENT_SOURCE = 2016;
    public static final int FUNCTION_SAVE_IMAGE = 2017;

    private TypeFaceView titleTv;
    private RecyclerView recyclerView;
    private SheetCommonAdapter adapter;
    private List<SheetCommonEntity> list;
    private int style;//展示样式
    SheetCommonEntity entity;

    public CommonSheet(Activity activity, int theme, com.seven.lib_common.listener.OnClickListener listener) {
        super(activity, theme, listener);
    }

    public CommonSheet(Activity activity, int style, int theme, com.seven.lib_common.listener.OnClickListener listener) {
        this(activity, theme, listener);
        this.style = style;
    }

    @Override
    public int getRootLayoutId() {

        isTouch = true;

        return R.layout.lb_sheet_common;
    }

    @Override
    public void onInitRootView(Bundle savedInstanceState) {

        initView();

        initData();

    }

    @Override
    public void initView() {

        titleTv = getView(titleTv, R.id.title_tv);
        recyclerView = getView(recyclerView, R.id.sheet_recycler);

    }

    @Override
    public void initData() {

        list = new ArrayList<>();
        sheetStyle(style);

        adapter = new SheetCommonAdapter(R.layout.lb_item_sheet_common, list);
        adapter.setOnItemClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setAdapter(adapter);

    }

    private void sheetStyle(int style) {

        switch (style) {

            case DELETE:

                addData(FUNCTION_DELETE, R.string.sheet_delete, 0, 0xffEB2135, false);

                break;

            case REPLY:

                addData(FUNCTION_REPLY, R.string.sheet_reply, 0, 0xff666666, false);
                addData(FUNCTION_REPORT, R.string.sheet_report, 0, 0xff666666, false);

                break;

            case MESSAGE_REPLY:

                addData(FUNCTION_REPLY, R.string.sheet_reply, 0, 0xff666666, false);
                addData(FUNCTION_VIDEO, R.string.sheet_video_source, 0, 0xff666666, false);
                addData(FUNCTION_REPORT, R.string.sheet_report, 0, 0xff666666, false);

                break;

            case MESSAGE_VIDEO:

                addData(FUNCTION_VIDEO, R.string.sheet_video_source, 0, 0xff666666, false);

                break;

            case MESSAGE_FEED_LIKE:

                addData(MESSAGE_FEED_COMMENT_SOURCE, R.string.sheet_feed_source, 0, 0xff666666, false);

                break;

            case MESSAGE_FEED:

                addData(MESSAGE_FEED_REPLY, R.string.sheet_reply, 0, 0xff666666, false);
                addData(MESSAGE_FEED_SOURCE, R.string.sheet_feed_source, 0, 0xff666666, false);
                addData(MESSAGE_FEED_REPORT, R.string.sheet_report, 0, 0xff666666, false);

                break;

            case MESSAGE_FEED_COMMENT:

                addData(MESSAGE_FEED_COMMENT_REPLY, R.string.sheet_reply, 0, 0xff666666, false);
                addData(MESSAGE_FEED_COMMENT_SOURCE, R.string.sheet_feed_source, 0, 0xff666666, false);
                addData(MESSAGE_FEED_COMMENT_REPORT, R.string.sheet_report, 0, 0xff666666, false);

                break;

            case LANGUAGE:

                addData(FUNCTION_LANGUAGE_ENGLISH, R.string.sheet_language_english, 0, 0xff666666, false);
                addData(FUNCTION_LANGUAGE_CHINA, R.string.sheet_language_china, 0, 0xff666666, false);

                break;

            case AVATAR:

                addData(FUNCTION_PHOTO, R.string.sheet_photo, 0, 0xff666666, false);
                addData(FUNCTION_CAMERA, R.string.sheet_camera, 0, 0xff666666, false);

                break;

            case DYNAMIC_REPORT:

                addData(FUNCTION_DYNAMIC_REPORT, R.string.sheet_report, 0, 0xff666666, false);
                addData(FUNCTION_CANCEL, R.string.sheet_cancel, 0, 0xffaaaaaa, false);

                break;

            case DYNAMIC_DELETE:

                addData(FUNCTION_DYNAMIC_DELETE, R.string.sheet_delete, 0, 0xff666666, false);
                addData(FUNCTION_CANCEL, R.string.sheet_cancel, 0, 0xffaaaaaa, false);

                break;

            case DYNAMIC_COMMENTS_REPLY:
                addData(FUNCTION_REPLY, R.string.sheet_reply, 0, 0xff666666, false);
                addData(FUNCTION_REPORT, R.string.sheet_report, 0, 0xff666666, false);
                addData(FUNCTION_CANCEL, R.string.sheet_cancel, 0, 0xffaaaaaa, false);
                break;
            case DYNAMIC_COMMENTS_DELETE:
                addData(FUNCTION_DELETE, R.string.sheet_delete, 0, 0xffEB2135, false);
                addData(FUNCTION_CANCEL, R.string.sheet_cancel, 0, 0xffaaaaaa, false);
                break;
            case SAVE_IMAGE:
                addData(FUNCTION_SAVE_IMAGE, R.string.save_gallery, 0, 0xff666666, false);
                addData(FUNCTION_CANCEL, R.string.sheet_cancel, 0, 0xffaaaaaa, false);
                break;

        }

//        addData(FUNCTION_CANCEL, R.string.sheet_cancel, 0, 0xffaaaaaa, true);
    }

    private void addData(@NonNull int functionId, @NonNull int stringId, int sizeId, int colorId, boolean isShow) {
        entity = new SheetCommonEntity();
        entity.setFunctionId(functionId);
        entity.setText(ResourceUtils.getText(stringId));
        entity.setShowLine(isShow);
        entity.setTextSize(sizeId);
        entity.setTextColor(colorId);
        list.add(entity);
    }

    @Override
    public void onClick(View v) {

        dismiss();

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (list.get(position).getFunctionId() == FUNCTION_CAMERA) {

            if (!EasyPermissions.hasPermissions(activity, new String[]{Manifest.permission.CAMERA})) {
                EasyPermissions.requestPermissions(activity, ResourceUtils.getText(R.string.permission), RC_PERM, new String[]{Manifest.permission.CAMERA});
                return;
            }
        }
        listener.onClick(view, list.get(position).getFunctionId());
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

        Logger.i("***onPermissionsGranted");

        for (String permission : perms) {
            if (permission.equals("android.permission.CAMERA")) {
                listener.onClick(null, FUNCTION_CAMERA);
            }
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Logger.i("***onPermissionsDenied");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Logger.i("***onRequestPermissionsResult");
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
