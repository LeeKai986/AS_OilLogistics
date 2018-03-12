package com.zpf.oillogistics.customview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.text.InputFilter;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zpf.oillogistics.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by FreeYu on 2016/12/21.
 */

public class NavEditItem extends RelativeLayout {

    @BindView(R.id.iv_start_icon)
    ImageView mIvStartIcon;
    @BindView(R.id.tv_action_title)
    TextView mTvActionTitle;
    @BindView(R.id.edit_statue)
    EditText editStatue;


    public NavEditItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public NavEditItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        View view = inflate(context, R.layout.nav_edit_item_layout, this);
        ButterKnife.bind(view);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.NavItem);
        String action = ta.getString(R.styleable.NavItem_action);

        //设置标题
        String actionState = ta.getString(R.styleable.NavItem_actionState);
        if (actionState != null) {
            editStatue.setText(actionState);
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
            editStatue.setHint(stateHint);
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
            editStatue.setTextColor(color);
        }

        //设置状态提示语字体颜色
        int stateHintColor = ta.getColor(R.styleable.NavItem_stateHintColor, Color.GRAY);
        if (stateHintColor != Color.GRAY) {
            editStatue.setHintTextColor(color);
        }

        //设置标题字体大小
        int textSize = ta.getDimensionPixelSize(R.styleable.NavItem_textSize, 0);
        if (textSize != 0) {
            mTvActionTitle.setTextSize(textSize);
        }

        //设置状态字体大小
        int stateTextSize = ta.getDimensionPixelSize(R.styleable.NavItem_stateTextSize, 0);
        if (stateTextSize != 0) {
            editStatue.setTextSize(textSize);
        }

        // 设置文字最大位数
        if (ta.getString(R.styleable.NavItem_maxLength) != null) {
            int maxTextLength = Integer.parseInt(ta.getString(R.styleable.NavItem_maxLength));
            if (maxTextLength != 0) {
                InputFilter[] inputFilters = {new InputFilter.LengthFilter(maxTextLength)};
                editStatue.setFilters(inputFilters);
            }
        }

        // 设置输入文本类型
        String digits = ta.getString(R.styleable.NavItem_digits);
        if (digits != null) {
            if (digits.equals("IDCard")) {
                editStatue.setKeyListener(DigitsKeyListener.getInstance("1234567890X"));
            } else if (digits.equals("phone")) {
                editStatue.setKeyListener(DigitsKeyListener.getInstance("1234567890"));
            }
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
        editStatue.setText(state);
    }

    public String getTvActionState() {
        return editStatue.getText().toString();
    }

    public void setTvTitle(String state) {
        mTvActionTitle.setText(state);
    }

    public void setIconVisibility(int visibility) {
        mIvStartIcon.setVisibility(visibility);
    }

    public void setEditStatueInput(int type) {
        editStatue.setInputType(type);
    }
}
