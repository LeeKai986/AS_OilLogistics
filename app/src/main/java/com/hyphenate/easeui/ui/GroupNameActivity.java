package com.hyphenate.easeui.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zpf.oillogistics.R;
import com.zpf.oillogistics.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/13.
 */

public class GroupNameActivity extends BaseActivity {


    @BindView(R.id.iv_back_groupname)
    RelativeLayout ivBack;
    @BindView(R.id.tv_config_groupname)
    TextView tvConfig;
    @BindView(R.id.edit_search_groupname)
    EditText editSearch;
    @BindView(R.id.rel_clear_groupname)
    RelativeLayout relClear;
    @BindView(R.id.iv_clear_groupname)
    ImageView ivClear;

    @Override
    protected int setLayout() {
        return R.layout.activity_groupname;
    }

    @Override
    protected void initData() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=getIntent();
                intent.putExtra("name",editSearch.getText().toString());
                setResult(1,intent);
                finish();
            }
        });

        relClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editSearch.setText("");
            }
        });

        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.toString().equals(""))
                    ivClear.setVisibility(View.INVISIBLE);
                else{
                    ivClear.setVisibility(View.VISIBLE);
                }

            }
        });
    }

    @Override
    protected void initViewAction(Bundle savedInstanceState) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }


}
