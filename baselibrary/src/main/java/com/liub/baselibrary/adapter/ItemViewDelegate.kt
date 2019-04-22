package com.liub.baselibrary.adapter

/**
 * Create by liub on 2019/4/2
 * Describe:RecyclerView的ItemViewDelegate，每种Item类型对应一个ItemViewDelegete
 */
interface ItemViewDelegate<T> {
    fun getItemViewLayoutId(): Int
    fun isForViewType(item: T, position: Int): Boolean
    fun convert(viewHolder: ViewHolder, t: T, position: Int)
}