package com.seven.module_extension.ui.dialog;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.seven.lib_common.base.sheet.IBaseSheet;
import com.seven.lib_common.utils.ImageUtils;
import com.seven.lib_common.utils.ResourceUtils;
import com.seven.lib_common.utils.ScreenUtils;
import com.seven.lib_model.model.user.UserEntity;
import com.seven.lib_router.Variable;
import com.seven.lib_router.db.shard.SharedData;
import com.seven.module_extension.R;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;

import java.util.concurrent.ExecutionException;

/**
 * Created by xxxxxxH on 2019/5/15.
 */
public class ShareDialog extends IBaseSheet {

    private RelativeLayout cancelRl;
    private RelativeLayout wxRl;
    private RelativeLayout wxFriendRl;

    Bitmap thumbBmp = null;
    SendMessageToWX.Req req;

    UserEntity userEntity;
    private int userId=0;

    public ShareDialog(Activity activity, int theme, com.seven.lib_common.listener.OnClickListener listener) {
        super(activity, theme, listener);
    }

    @Override
    public int getRootLayoutId() {
        return R.layout.me_dialog_share;
    }

    @Override
    public void onInitRootView(Bundle savedInstanceState) {
        initView();
        initData();
    }

    @Override
    public void initView() {
        cancelRl = getView(cancelRl, R.id.cancel_rl);
        wxRl = getView(wxRl, R.id.wx_rl);
        wxFriendRl = getView(wxFriendRl, R.id.wx_friend_rl);

        cancelRl.setOnClickListener(this);
        wxRl.setOnClickListener(this);
        wxFriendRl.setOnClickListener(this);
    }

    @Override
    public void initData() {
        userEntity = new Gson().fromJson(SharedData.getInstance().getUserInfo(),UserEntity.class);
        if (userEntity != null){
            userId = userEntity.getId();
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.wx_rl) {
            wechatShareWebpage();
            wechatSession();
        }

        if (view.getId() == R.id.wx_friend_rl) {
            wechatShareWebpage();
            wechatTimeline();
        }

        dismiss();
    }

    private void wechatShareWebpage() {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = getUrl(userId);
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = ResourceUtils.getText(R.string.app_name);

        if (thumbBmp != null && !thumbBmp.isRecycled())
            msg.thumbData = ImageUtils.compressByQuality(thumbBmp, 32 * 1024 * 1024, true);
        else {
            getShareBitmap();
            if (thumbBmp != null && !thumbBmp.isRecycled())
                msg.thumbData = ImageUtils.compressByQuality(thumbBmp, 32 * 1024 * 1024, true);
        }

        req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
    }

    private void getShareBitmap() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    thumbBmp = Glide.with(activity)
                            .asBitmap()
                            .load(activity.getResources().getResourceName(R.drawable.ic_launcher_foreground) + ScreenUtils.getImageSize(450, 450))
                            .submit(150, 150)
                            .get();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    private void wechatSession() {
        req.scene = SendMessageToWX.Req.WXSceneSession;
        Variable.getInstance().getWxApi().sendReq(req);
    }

    private void wechatTimeline() {
        req.scene = SendMessageToWX.Req.WXSceneTimeline;
        Variable.getInstance().getWxApi().sendReq(req);
    }

    private String getUrl(int userId) {
        String url = "http://mobile.zf.fqwlkj.com.cn/invite.html";
        String uid = "?uid=" + userId;
        return url + uid;
    }
}
