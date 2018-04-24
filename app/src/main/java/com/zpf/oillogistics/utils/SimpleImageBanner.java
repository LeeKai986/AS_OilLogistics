package com.zpf.oillogistics.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.flyco.banner.widget.Banner.BaseIndicatorBanner;
import com.zpf.oillogistics.R;
import com.zpf.oillogistics.bean.Slid;
import com.zpf.oillogistics.net.UrlUtil;


public class SimpleImageBanner extends BaseIndicatorBanner<Slid, SimpleImageBanner> {
    private ColorDrawable colorDrawable;
    private ImageView ivs;

    public SimpleImageBanner(Context context) {
        this(context, null, 0);
    }

    public SimpleImageBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleImageBanner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        colorDrawable = new ColorDrawable(Color.parseColor("#555555"));
    }

    @Override
    public void onTitleSlect(TextView tv, int position) {
        final Slid item = mDatas.get(position);
//        tv.setText(item.title);
    }

    @Override
    public View onCreateItemView(int position) {
        View inflate = View.inflate(mContext, R.layout.adapter_simple_image, null);
        ivs = inflate.findViewById(R.id.iv);
        final Slid item = mDatas.get(position);
        ivs.setScaleType(ImageView.ScaleType.CENTER_CROP);
        String imgUrl = item.getImg();
        if (!TextUtils.isEmpty(imgUrl)) {
            Glide.with(getContext())
                    .load(UrlUtil.IMAGE_URL + imgUrl)
                    .error(R.mipmap.default_banner)
                    .into(ivs);
        } else {
            ivs.setImageDrawable(colorDrawable);
        }

        return inflate;
    }
}