package com.zpf.oillogistics.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.zpf.oillogistics.R;
import com.zpf.oillogistics.customview.SquareImageView;

import java.util.List;

/**
 * Created by Administrator on 2017/9/19.
 */

public class AddPictureGridAdapter extends BaseAdapter {

    private List<Bitmap> list;
    private Context context;
    private LayoutInflater inflater;

    public AddPictureGridAdapter(List<Bitmap> list, Context context) {
        this.list = list;
        this.context = context;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if(list!=null){
            if(list.size()>=4)
                return 4;
            return list.size()+1;
        }
        return 1;
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view=inflater.inflate(R.layout.adapter_add_picture,viewGroup,false);
        SquareImageView iv=view.findViewById(R.id.iv_add_picture);

        if (list.size() < 4) {
            if (i == getCount() - 1) {
                iv.setBackgroundResource(R.mipmap.add_picture);
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        addpictureListener.addPicture();
                    }
                });
            } else  {
                iv.setImageBitmap(list.get(i));
            }
        }else {
            iv.setImageBitmap(list.get(i));
        }

        return view;
    }

    private AddpictureListener addpictureListener;

    public void setAddpictureListener(AddpictureListener addpictureListener) {
        this.addpictureListener = addpictureListener;
    }

    public interface AddpictureListener{
        void addPicture();
    }

}
