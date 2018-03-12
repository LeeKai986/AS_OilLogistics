package com.zpf.oillogistics.base;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/16.
 * <p>
 * fragment基类
 */

public abstract class BaseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(CyApplication.getCyContext()).inflate(setLayout(), container, false);
        ButterKnife.bind(this, view);
        intiData();
        intiViewAction(view, savedInstanceState);
        return view;
    }

    protected abstract int setLayout();

    protected abstract void intiData();

    protected abstract void intiViewAction(View view, Bundle savedInstanceState);
}
