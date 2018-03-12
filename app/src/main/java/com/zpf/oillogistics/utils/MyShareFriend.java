package com.zpf.oillogistics.utils;

import android.content.Context;

import com.zpf.oillogistics.net.UrlUtil;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShareThemeImpl;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * Created by Administrator on 2017/10/13.
 * <p>
 * 分享
 */

public class MyShareFriend {

    public static void weixinFriend(final Context context, final OnMyShareFriendListener onMyShareFriendListener) {
        Platform weixin = ShareSDK.getPlatform(Wechat.NAME);
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setShareType(Platform.SHARE_WEBPAGE);
        sp.setText("石化通邀请");
        sp.setTitle("石化通");
        sp.setUrl(UrlUtil.SHARE_URL);
        sp.setImageUrl(UrlUtil.IMAGE_URL + "/uploads/20171115/ca9d30a40fec3beb1812a769a6a235e6.png");
        weixin.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                onMyShareFriendListener.success();
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                onMyShareFriendListener.error();
            }

            @Override
            public void onCancel(Platform platform, int i) {
                MyToast.show(context, "分享已取消");
            }
        });
        weixin.share(sp);
    }

    public static void weixinCircle(final Context context, final OnMyShareFriendListener onMyShareFriendListener) {
        Platform weixin = ShareSDK.getPlatform(WechatMoments.NAME);
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setShareType(Platform.SHARE_WEBPAGE);
        sp.setText("石化通邀请");
        sp.setTitle("石化通");
        sp.setUrl(UrlUtil.SHARE_URL);
        sp.setImageUrl(UrlUtil.IMAGE_URL + "/uploads/20171115/ca9d30a40fec3beb1812a769a6a235e6.png");
        weixin.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                onMyShareFriendListener.success();
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                onMyShareFriendListener.error();
            }

            @Override
            public void onCancel(Platform platform, int i) {
                MyToast.show(context, "分享已取消");
            }
        });
        weixin.share(sp);
    }

    public interface OnMyShareFriendListener {
        void success();

        void error();
    }
}
