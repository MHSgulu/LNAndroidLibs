package com.lnkj.android.demo.list

import com.lnkj.android.demo.databinding.AdapterListBinding
import com.lnkj.libs.base.BaseBindingQuickAdapter

class ListAdapter(data: MutableList<Any>): BaseBindingQuickAdapter<Any, AdapterListBinding>(-1,data) {

    init {
        addData(Any())
        addData(Any())
        addData(Any())
        addData(Any())
        addData(Any())
        addData(Any())
        addData(Any())
        addData(Any())
        addData(Any())
        addData(Any())
    }

    override fun convert(holder: BaseBindingHolder, item: Any) {
        holder.getViewBinding<AdapterListBinding>().apply {

        }
    }
}