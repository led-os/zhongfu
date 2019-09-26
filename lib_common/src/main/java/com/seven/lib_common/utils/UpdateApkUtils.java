package com.seven.lib_common.utils;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.view.View;

import com.seven.lib_common.R;
import com.seven.lib_common.config.RunTimeConfig;
import com.seven.lib_common.listener.OnClickListener;
import com.seven.lib_common.ui.dialog.UpdateCheckDialog;
import com.seven.lib_opensource.application.SSDK;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2018/4/20
 */

public class UpdateApkUtils {

    private static UpdateApkUtils updateApkUtils;
    private UpdateCheckDialog updateDialog;

    public UpdateApkUtils() {

    }

    public static UpdateApkUtils getInstance() {

        if (updateApkUtils == null) {

            synchronized (UpdateApkUtils.class) {
                updateApkUtils = new UpdateApkUtils();
            }

        }

        return updateApkUtils;
    }


    public boolean checkingVersion(String version) {

        String appVersion = AppUtils.getVersionName(SSDK.getInstance().getContext()).replace(".", "");

        if (Integer.parseInt(version) > Integer.parseInt(appVersion))
            return true;

        return false;
    }

    public boolean checkingApk() {

        String dir = RunTimeConfig.PathConfig.APK_DOWNLOAD_PATH;

        List<String> list = new ArrayList<>();

        List<String> paths = FileUtils.getAllFiles(list, dir);

        if (paths == null)
            return false;

        if (paths.size() != 1) {

            for (String path : paths)
                new File(path).delete();

            return false;
        }

        if (paths.get(0).endsWith(".apk")) {

            String[] apkSplit = paths.get(0).split("/");
            String apkName = apkSplit[apkSplit.length - 1];
            boolean isUpdate = checkingVersion(apkName.substring(0, apkName.length() - 4));

            if (!isUpdate) {
                new File(paths.get(0)).delete();
                return false;
            }

            return isUpdate;
        } else {
            new File(paths.get(0)).delete();
            return false;
        }
    }

    public void installApk() {

        String dir = RunTimeConfig.PathConfig.APK_DOWNLOAD_PATH;

        List<String> list = new ArrayList<>();

        List<String> paths = FileUtils.getAllFiles(list, dir);

        if (paths == null)
            return;

        if (paths.size() != 1) {

            for (String path : paths)
                new File(path).delete();

            return;
        }

        if (!paths.get(0).endsWith(".apk")) return;

        File apkFile = new File(paths.get(0));
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(
                    SSDK.getInstance().getContext()
                    , "com.seven.mall.fileprovider"
                    , apkFile);

            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        }
        SSDK.getInstance().getContext().startActivity(intent);
    }

    public void update(final Activity activity, String version, final String url, String desc) {

        if (updateDialog == null || !updateDialog.isShowing()) {

            updateDialog = new UpdateCheckDialog(activity, R.style.Dialog, new OnClickListener() {

                @Override
                public void onCancel(View v, Object... objects) {
                    activity.finish();
                }

                @Override
                public void onClick(View v, Object... objects) {

//                    String[] installPerm = {Manifest.permission.REQUEST_INSTALL_PACKAGES};
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                        boolean hasInstall = activity.getPackageManager().canRequestPackageInstalls();
//                        if (!hasInstall) {
//                            EasyPermissions.requestPermissions(activity, "", 1003, installPerm);
//                        }
//                    }

                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse(url);
                    intent.setData(content_url);
                    activity.startActivity(intent);

                }

                @Override
                public void dismiss() {

                }
            }, version, url, desc);
            updateDialog.setCancelable(false);
        }

        if (!updateDialog.isShowing())
            updateDialog.show();

    }

}
