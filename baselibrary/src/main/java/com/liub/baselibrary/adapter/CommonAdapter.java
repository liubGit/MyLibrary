package com.liub.baselibrary.adapter;

import android.content.Context;
import android.view.LayoutInflater;

import java.util.Collection;
import java.util.List;

/**
 * Create by liub on 2019/4/2
 * Describe:RecyclerView的CommonAdapter 正常的CommonAdapter
 */
public abstract class CommonAdapter<T> extends MultiTypeItemAdapter<T> {
    public Context mContext;
    private int mLayoutId;
    private LayoutInflater mInflater;

    public CommonAdapter(Context context) {
        super(context);
        mContext = context;
        mInflater = LayoutInflater.from(context);
//        mLayoutId = layoutId;
//        mDatas = datas;
        //返回：MultiItemTypeAdapter
        addItemViewDelegate(new ItemViewDelegate<T>() {
            @Override
            public int getItemViewLayoutId() {
                return itemLayoutId();
            }

            @Override
            public boolean isForViewType(T item, int position) {
                return true;
            }

            @Override
            public void convert(ViewHolder holder, T t, int position) {
                converts(holder, t, position);
            }
        });
    }

    protected abstract int itemLayoutId();

    protected abstract void converts(ViewHolder holder, T t, int position);

    /**
     * 设置适配器的数据，添加数据
     *
     * @param dataList
     */
    public void update(List<T> dataList) {
        if (dataList != null) {
            mDatas.addAll(dataList);
        }

        notifyDataSetChanged();
    }

    /**
     * 设置适配器数据
     *
     * @param dataList
     * @param isClear  是否需要清空list然后在加载数据
     */
    public void update(List<T> dataList, Boolean isClear) {
        if (isClear) {
            clear();
        }
        if (dataList != null) {
            mDatas.addAll(dataList);
        }
        notifyDataSetChanged();
    }

    /**
     * 单条刷新
     *
     * @param dataList
     * @param isClear  是否需要清空list然后在加载数据
     */
    public void update(List<T> dataList, Boolean isClear, int position) {
        if (isClear) {
            clear();
        }
        if (dataList != null) {
            mDatas.addAll(dataList);
        }
        notifyItemChanged(position);
    }

    /**
     * 清除集合数据
     */
    public void clear() {
        mDatas.clear();
        notifyDataSetChanged();
    }

    public void remove(int position) {
        if (mDatas != null && mDatas.size() > 0) {
            // 数据移除
            this.mDatas.remove(position - 1);
            // 界面移除
            notifyItemRemoved(position - 1);
            notifyItemRangeChanged(0, getItemCount());
//            notifyDataSetChanged();
            // 刷新位置
//            if (position != (mDatas.size())) { // 如果移除的是最后一个，忽略
//                notifyItemRangeChanged(position - 1, getItemCount() - position + 1);
//            }
        }
    }

    public List<T> getDataList() {
        return mDatas;
    }

    /**
     * 清空集合，设置适配器数据
     *
     * @param list
     */
    public void setDataList(Collection<T> list) {
        this.mDatas.clear();
        this.mDatas.addAll(list);
        notifyDataSetChanged();
    }
}
