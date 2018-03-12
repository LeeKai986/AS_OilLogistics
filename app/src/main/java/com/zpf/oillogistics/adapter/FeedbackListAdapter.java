package com.zpf.oillogistics.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zpf.oillogistics.R;
import com.zpf.oillogistics.activity.InforSelfActivity;
import com.zpf.oillogistics.bean.FeedBack;
import com.zpf.oillogistics.customview.SquareImageView;
import com.zpf.oillogistics.net.UrlUtil;
import com.zpf.oillogistics.utils.DateTimeUtil;
import com.zpf.oillogistics.utils.MyShare;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/19.
 */

public class FeedbackListAdapter extends BaseAdapter {

    private List<FeedBack> mList;
    private Context context;
    private LayoutInflater inflater;

    public FeedbackListAdapter(List<FeedBack> list, Context context) {
        this.mList = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (mList != null) {
            return mList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder=null;
        if(view==null) {
            view = inflater.inflate(R.layout.adapter_feedback_list, viewGroup, false);
            holder=new ViewHolder(view);
            view.setTag(holder);
        }else {
            holder= (ViewHolder) view.getTag();
        }
        FeedBack feedBack= (FeedBack) getItem(i);

        //反馈时间
        if(feedBack.getFbtime()!=0){
            holder.tvTime.setText(DateTimeUtil.stampToDate("MM月dd日 HH:mm:ss",feedBack.getFbtime()+"000"));
        }else {
            holder.tvTime.setText("--");
        }

        //反馈内容
        if(feedBack.getFeedback()!=null){
            holder.tvContant.setText(feedBack.getFeedback());
        }else {
            holder.tvContant.setText("暂无反馈内容");
        }

        //反馈图片
        if(feedBack.getImg()!=null) {
            if (feedBack.getImg().contains(",")) {
                holder.grid.setAdapter(new GridListAdapter( context,feedBack.getImg().split(",")));
            } else if (!feedBack.getImg().equals("")) {
                holder.grid.setAdapter(new GridListAdapter( context,new String[]{feedBack.getImg()}));
            }
        }

        //回复
        if(feedBack.retime!=0){
            //回复内容
            if(feedBack.revert!=null){
                holder.tvRecontant.setText(feedBack.getRevert());
            }else {
                holder.tvRecontant.setText("暂无回复内容");
            }

            //回复时间
            holder.tvRetime.setText(DateTimeUtil.stampToDate("MM月dd日 HH:mm:ss",feedBack.getRetime()+"000"));
        }else {
            holder.linReback.setVisibility(View.GONE);
        }

        return view;
    }

    static class ViewHolder {
        @BindView(R.id.tv_time_feedback_list)
        TextView tvTime;
        @BindView(R.id.tv_contant_feedback_list)
        TextView tvContant;
        @BindView(R.id.grid_feedback_list)
        GridView grid;
        @BindView(R.id.tv_retime_feedback_list)
        TextView tvRetime;
        @BindView(R.id.tv_recontant_feedback_list)
        TextView tvRecontant;
        @BindView(R.id.lin_reback)
        LinearLayout linReback;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
     class GridListAdapter extends BaseAdapter {

        private Context context;
        private LayoutInflater inflater;
         private String[] imgUrl;

        public GridListAdapter( Context context,String[] ss) {
            this.context = context;
            this.imgUrl=ss;
            inflater = LayoutInflater.from(context);

        }

        @Override
        public int getCount() {
            if (imgUrl != null) {
                return imgUrl.length;
            }
            return 0;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view=inflater.inflate(R.layout.adapter_add_picture,viewGroup,false);
            SquareImageView iv=view.findViewById(R.id.iv_add_picture);
            //加载图片
            if(!imgUrl[i].equals("")) {
                Glide.with(context)
                        .load(UrlUtil.IMAGE_URL + imgUrl[i])
                        .into(iv);
            }
            return view;
        }

    }

}
