package com.zpf.oillogistics.fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.zpf.oillogistics.R;
import com.zpf.oillogistics.bean.OilOrChemtryInfoBean;
import com.zpf.oillogistics.net.UrlUtil;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2017/9/15.
 * <p>
 * 平台发布订单详细图片
 */

public class PlatformIssueOrderImageFragment extends Fragment {

    //    private OilOrChemtryInfoBean.DataBeanX.DataBean dataBean;
    // 布局相关
    // 图
    @BindView(R.id.platform_issue_order_image_iv)
    ImageView iv;

    WindowManager wm;
    int width;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_platform_issue_order_image, container, false);
        wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
        ButterKnife.bind(this, view);
        return view;
    }

    public void setData(OilOrChemtryInfoBean.DataBeanX.DataBean dataBean) {
        if (dataBean.getImg() != null && !dataBean.getImg().equals("")) {
            Glide.with(getActivity())
                    .load(UrlUtil.IMAGE_URL + dataBean.getImg())
                    .placeholder(R.mipmap.default_goodsdetails)
                    .dontAnimate()
                    .fitCenter()
                    .into(new SimpleTarget<GlideDrawable>() {
                        /**
                         * {@inheritDoc}
                         *
                         * @param e
                         * @param errorDrawable
                         */
                        @Override
                        public void onLoadFailed(Exception e, Drawable errorDrawable) {
                            super.onLoadFailed(e, errorDrawable);
                            iv.setImageDrawable(getResources().getDrawable(R.mipmap.default_goodsdetails));
                            iv.setScaleType(ImageView.ScaleType.FIT_XY);
                        }

                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
                            width = wm.getDefaultDisplay().getWidth();
                            ViewGroup.LayoutParams layoutParams = iv.getLayoutParams();
                            layoutParams.width = width;
                            layoutParams.height = (int) ((resource.getIntrinsicHeight() / ((float) resource.getIntrinsicWidth()) * width));
                            Log.i("Pwinth-->", width + "");
                            Log.i("winth-->", resource.getIntrinsicWidth() + "");
                            Log.i("height-->", resource.getIntrinsicHeight() + "");
                            iv.setLayoutParams(layoutParams);
                            iv.setImageDrawable(resource);
                        }
                    });
        }
    }
}
