package com.zpf.oillogistics.customview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zpf.oillogistics.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by FreeYu on 2016/12/21.
 */

public class NavItem extends RelativeLayout {

    @BindView(R.id.iv_start_icon)
    ImageView mIvStartIcon;
    @BindView(R.id.tv_action_title)
    TextView mTvActionTitle;
    @BindView(R.id.tv_action_state)
    TextView mTvActionState;
    @BindView(R.id.iv_mesg)
    ImageView ivMesg;


    public NavItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public NavItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        View view = inflate(context, R.layout.nav_item_layout, this);
        ButterKnife.bind(view);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.NavItem);
        String action = ta.getString(R.styleable.NavItem_action);

        //设置标题
        String actionState = ta.getString(R.styleable.NavItem_actionState);
        if (actionState != null) {
            mTvActionState.setText(actionState);
        }
        mTvActionTitle.setText(action != null ? action : "");

        //设置icon
        int startInco = ta.getResourceId(R.styleable.NavItem_startIcon, 0);
        if (startInco != 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mIvStartIcon.setImageDrawable(context.getResources().getDrawable(startInco, context.getTheme()));
            } else {
                mIvStartIcon.setImageDrawable(context.getResources().getDrawable(startInco));
            }
        }

        //设置提示语
        String stateHint = ta.getString(R.styleable.NavItem_stateHint);
        if (stateHint != null) {
            mTvActionState.setHint(stateHint);
        }

        //设置提示语
        String stateText = ta.getString(R.styleable.NavItem_stateText);
        if (stateText != null) {
            mTvActionState.setHint(stateText);
        }

        //设置标题字体颜色
        int color = ta.getColor(R.styleable.NavItem_textColor, Color.GRAY);
        if (color != Color.GRAY) {
            mTvActionTitle.setTextColor(color);
        }

        //设置icon背景颜色
        int tint = ta.getColor(R.styleable.NavItem_actionColor, Color.TRANSPARENT);
        if (tint != Color.TRANSPARENT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mIvStartIcon.setImageTintList(ColorStateList.valueOf(tint));
            }
        }

        //设置状态字体颜色
        int stateTextColor = ta.getColor(R.styleable.NavItem_stateTextSize, Color.GRAY);
        if (stateTextColor != Color.GRAY) {
            mTvActionState.setTextColor(stateTextColor);
        }

        //设置状态提示语字体颜色
        int stateHintColor = ta.getColor(R.styleable.NavItem_stateHintColor, Color.GRAY);
        if (stateHintColor != Color.GRAY) {
            mTvActionState.setHintTextColor(stateHintColor);
        }

        //设置标题字体大小
        int textSize = ta.getDimensionPixelSize(R.styleable.NavItem_textSize, 0);
        if (textSize != 0) {
            mTvActionTitle.setTextSize(textSize);
        }

        //设置状态字体大小
        int stateTextSize = ta.getDimensionPixelSize(R.styleable.NavItem_stateTextSize, 0);
        if (stateTextSize != 0) {
            mTvActionState.setTextSize(stateTextSize);
        }


        //设置ivMesg是否显示
        boolean goneTo = ta.getBoolean(R.styleable.NavItem_goneTo, false);
        if (goneTo) {
            ivMesg.setVisibility(GONE);
        } else {
            ivMesg.setVisibility(VISIBLE);
        }

        //设置mIvStartIcon是否显示
        boolean goneIcon = ta.getBoolean(R.styleable.NavItem_goneIcon, false);
        if (goneIcon) {
            mIvStartIcon.setVisibility(GONE);
        } else {
            mIvStartIcon.setVisibility(VISIBLE);
        }
        ta.recycle();
    }

    public void setTvActionState(String state) {
        String adr2 = state.replace("null", "");
        mTvActionState.setText(adr2);
    }

    public String getTvActionState() {
        return mTvActionState.getText().toString();
    }

    public void setTvTitle(String state) {
        mTvActionTitle.setText(state);
    }

    public void setMesgVisibility(int visibility) {
        ivMesg.setVisibility(visibility);
    }

    public void setIconVisibility(int visibility) {
        mIvStartIcon.setVisibility(visibility);
    }
}
