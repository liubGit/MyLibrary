package com.liub.baselibrary.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Create by liub on 2019/4/2
 * Describe:
 */
public  class ViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> viewHolder;
    private View view;

    public ViewHolder(View itemView) {
        super(itemView);
        this.view=itemView;
        viewHolder=new SparseArray<>();
    }
    public <T extends View> T get(int id) {
        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }
    public static ViewHolder createViewHolder(Context context, ViewGroup viewGroup,int layoutId){
        View view=LayoutInflater.from(context).inflate(layoutId,viewGroup,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    public View getConverView(){
        return view;
    }
    public ViewHolder setText(int viewId, String text)
    {
        TextView tv = get(viewId);
        tv.setText(text);
        return this;
    }

    public ViewHolder setImageResource(int viewId, int resId)
    {
        ImageView view = get(viewId);
        view.setImageResource(resId);
        return this;
    }
    public ViewHolder setImageBitmap(int viewId, Bitmap bitmap)
    {
        ImageView view = get(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

    public ViewHolder setImageDrawable(int viewId, Drawable drawable)
    {
        ImageView view = get(viewId);
        view.setImageDrawable(drawable);
        return this;
    }

    public ViewHolder setBackgroundColor(int viewId, int color)
    {
        View view = get(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    public ViewHolder setBackgroundRes(int viewId, int backgroundRes)
    {
        View view = get(viewId);
        view.setBackgroundResource(backgroundRes);
        return this;
    }

    public ViewHolder setTextColor(int viewId, int textColor)
    {
        TextView view = get(viewId);
        view.setTextColor(textColor);
        return this;
    }
    public ViewHolder setVisible(int viewId, boolean visible)
    {
        View view = get(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    public ViewHolder setTag(int viewId, Object tag)
    {
        View view = get(viewId);
        view.setTag(tag);
        return this;
    }

    public ViewHolder setTag(int viewId, int key, Object tag)
    {
        View view = get(viewId);
        view.setTag(key, tag);
        return this;
    }

    public ViewHolder setChecked(int viewId, boolean checked)
    {
        Checkable view = (Checkable) get(viewId);
        view.setChecked(checked);
        return this;
    }

    public ViewHolder setOnClickListener(int viewId,
                                         View.OnClickListener listener)
    {
        View view = get(viewId);
        view.setOnClickListener(listener);
        return this;
    }

}
