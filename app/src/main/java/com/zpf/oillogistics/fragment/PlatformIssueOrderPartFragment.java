package com.zpf.oillogistics.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zpf.oillogistics.R;
import com.zpf.oillogistics.bean.OilOrChemtryInfoBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/15.
 * <p>
 * 平台发布订单详细参数
 */

public class PlatformIssueOrderPartFragment extends Fragment {

//    private OilOrChemtryInfoBean.DataBeanX.DataBean dataBean;

    // 布局相关
    // 名称
    @BindView(R.id.platform_issue_order_part_name_tv)
    TextView nameTv;
    // 型号
    @BindView(R.id.platform_issue_order_part_type_tv)
    TextView typeTv;
    // 质量
    @BindView(R.id.platform_issue_order_part_quality_tv)
    TextView qualityTv;
    // 价格
    @BindView(R.id.platform_issue_order_part_price_tv)
    TextView priceTv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_platform_issue_order_part, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public void setData(OilOrChemtryInfoBean.DataBeanX.DataBean dataBean) {
        if (dataBean.getTitle() != null && !dataBean.getTitle().equals("")) {
            nameTv.setText(dataBean.getTitle());
        }
        if (dataBean.getPrice() != null && !dataBean.getPrice().equals("")) {
            priceTv.setText( dataBean.getPrice() + "元/吨");
        }

        String modle="";
        if (dataBean.getClassify() != null ) {
            modle=dataBean.getClassify();
        }

        if(dataBean.getModel()!=null){
            modle+=dataBean.getModel();
        }
        typeTv.setText(modle);
    }
}
