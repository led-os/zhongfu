package com.seven.module_home.ui.sheet;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.seven.lib_common.base.sheet.IBaseSheet;
import com.seven.lib_common.utils.ImageUtils;
import com.seven.lib_common.utils.ResourceUtils;
import com.seven.lib_common.utils.ScreenUtils;
import com.seven.lib_model.model.home.CommodityDetailsEntity;
import com.seven.lib_router.Variable;
import com.seven.module_home.R;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;

import java.util.concurrent.ExecutionException;


/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/5/15
 */

public class ShareSheet extends IBaseSheet {

    private CommodityDetailsEntity entity;

    private RelativeLayout cancelRl;
    private RelativeLayout wxRl;
    private RelativeLayout wxFriendRl;

    Bitmap thumbBmp = null;
    SendMessageToWX.Req req;

    public ShareSheet(Activity activity, int theme, com.seven.lib_common.listener.OnClickListener listener) {
        super(activity, theme, listener);
    }

    public ShareSheet(Activity activity, int theme, com.seven.lib_common.listener.OnClickListener listener, CommodityDetailsEntity entity) {
        this(activity, theme, listener);
        this.entity = entity;
    }

    @Override
    public int getRootLayoutId() {
        return R.layout.mh_sheet_share;
    }

    @Override
    public void onInitRootView(Bundle savedInstanceState) {

        initView();

        initData();

        getShareBitmap();
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

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.wx_rl) {
            wechatShareWebpage();
            wechatSession();
        }

        if (v.getId() == R.id.wx_friend_rl) {
            wechatShareWebpage();
            wechatTimeline();
        }

        dismiss();
    }

    private void wechatShareWebpage() {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = getUrl(Variable.getInstance().getUserId(), entity.getId());
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = ResourceUtils.getText(R.string.app_name);
        msg.description = entity.getGoods_name();

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
                            .load(entity.getPictures().get(0)+ ScreenUtils.getImageSize(450, 450))
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

    private String getUrl(int userId, int producationId) {
        String url = "http://mobile.zf.fqwlkj.com.cn/goods_info.html";
        String uid = "?uid=" + userId;
        String product_id = "&product_id=" + producationId;
        return url + uid + product_id;
    }
}
