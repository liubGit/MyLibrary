package com.liub.baselibrary.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.liub.baselibrary.listener.NoDoubleClickListener;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Create by liub on 2019/4/2
 * Describe:
 */
public class MultiTypeItemAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    protected Context mContext;
    protected List<T> mDatas = new ArrayList<>();

    protected ItemViewDelegateManager mItemViewDelegateManager;
    protected OnItemClickListener mOnItemClickListener;
    protected onItemLongClickListener mOnItemLongClickListener;

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(onItemLongClickListener onItemLongClickListener) {
        this.mOnItemLongClickListener = onItemLongClickListener;
    }

    public MultiTypeItemAdapter(Context mContext) {
        this.mContext = mContext;
        mItemViewDelegateManager = new ItemViewDelegateManager();
    }

    public MultiTypeItemAdapter(Context context, List<T> datas) {
        mContext = context;
        mDatas = datas;
        mItemViewDelegateManager = new ItemViewDelegateManager();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemViewDelegate viewDelegate = mItemViewDelegateManager.getItemViewDelegate(i);
        int layoutId = viewDelegate.getItemViewLayoutId();
        ViewHolder viewHolder = ViewHolder.createViewHolder(mContext, viewGroup, layoutId);
        onViewHolderCreated(viewHolder, viewHolder.getConverView());
        setListener(viewGroup, viewHolder, i);
        return viewHolder;
    }

    protected boolean isEnabled(int viewType) {
        return true;
    }

    private void setListener(ViewGroup viewGroup, final ViewHolder viewHolder, int viewType) {
        if (!isEnabled(viewType)) return;
        viewHolder.getConverView().setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void noDoubleClick(@Nullable View v) {
                if (null != mOnItemClickListener) {
                    int pos = viewHolder.getAdapterPosition();
                    T data=null;
                    if (pos<mDatas.size()){
                        data=mDatas.get(pos);
                    }
                    mOnItemClickListener.onItemClick(data, viewHolder, pos);
                }
            }
        });
        viewHolder.getConverView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemLongClickListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    mOnItemLongClickListener.onItemLongClick(v, viewHolder, position);
                }
                return true;
            }
        });
    }

    public void onViewHolderCreated(ViewHolder viewHolder, View converView) {

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        convert(viewHolder, mDatas.get(i));
    }

    private void convert(ViewHolder viewHolder, T t) {
        mItemViewDelegateManager.convert(viewHolder, t, viewHolder.getAdapterPosition());
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public MultiTypeItemAdapter addItemViewDelegate(ItemViewDelegate<T> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(itemViewDelegate);
        return this;
    }

    public MultiTypeItemAdapter addItemViewDelegate(int viewType, ItemViewDelegate<T> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(viewType, itemViewDelegate);
        return this;
    }

    protected boolean useItemViewDelegateManager() {
        return mItemViewDelegateManager.getItemViewDelegateCount() > 0;
    }

    /**
     * 设置适配器的数据，添加数据
     *
     * @param dataList
     */
    public void addAlls(List<T> dataList) {
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
     * 清除集合数据
     */
    public void clear() {
        mDatas.clear();
        notifyDataSetChanged();
    }

    public void remove(int position) {
        if (mDatas != null && mDatas.size() > 0) {
            // 数据移除
            this.mDatas.remove(position);
            // 界面移除
            notifyItemRemoved(position - 1);
            // 刷新位置
            if (position != (mDatas.size())) { // 如果移除的是最后一个，忽略
                notifyItemRangeChanged(position - 1, getItemCount() - position);
            }
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

    public interface OnItemClickListener<T> {
        void onItemClick(T d, ViewHolder holder, int position);
    }

    public interface onItemLongClickListener {
        void onItemLongClick(View view, ViewHolder holder, int position);
    }


}
