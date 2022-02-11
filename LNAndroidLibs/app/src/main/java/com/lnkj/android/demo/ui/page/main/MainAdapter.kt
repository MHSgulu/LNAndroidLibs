package com.lnkj.android.demo.ui.page.main

import com.lnkj.android.demo.bean.ModuleBean
import com.lnkj.android.demo.databinding.AdapterMainBinding
import com.lnkj.libs.base.BaseBindingQuickAdapter

/**
 *
 */
class MainAdapter(data: MutableList<ModuleBean>) :
    BaseBindingQuickAdapter<ModuleBean, AdapterMainBinding>(-1, data) {

    override fun convert(holder: BaseBindingHolder, item: ModuleBean) {
        holder.getViewBinding<AdapterMainBinding>().apply {
            tvTitle.text = item.title
            tvContent.text = item.content
        }
    }
}