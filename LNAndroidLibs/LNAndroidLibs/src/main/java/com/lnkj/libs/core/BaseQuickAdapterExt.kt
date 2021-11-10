package com.lnkj.libs.core

import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lnkj.libs.R


fun BaseQuickAdapter<*, *>.setEmptyData(imgRes: Int = R.mipmap.img_zwhy, tip: String = "暂无数据", reRefreshData: ()->Unit = {}){

    val emptyView = LayoutInflater.from(context).inflate(R.layout.state_empty_layout, null)
    val imageView = emptyView.findViewById<ImageView>(R.id.ivNoData)
    imageView.load(imgRes)
    val textView = emptyView.findViewById<TextView>(R.id.emptyStatusTextView)
    textView.text = tip

    imageView.click {
        reRefreshData.invoke()
    }

    setEmptyView(emptyView)
    isUseEmpty = false

}

@JvmName("bind")
fun <VB : ViewBinding> BaseViewHolder.withBinding(bind: (View) -> VB): BaseViewHolder =
    BaseViewHolderWithBinding(bind(itemView))

@JvmName("getBinding")
@Suppress("UNCHECKED_CAST")
fun <VB : ViewBinding> BaseViewHolder.getViewBinding(): VB {
    if (this is BaseViewHolderWithBinding<*>) {
        return binding as VB
    } else {
        throw IllegalStateException("The binding could not be found.")
    }
}

class BaseViewHolderWithBinding<VB : ViewBinding>(val binding: VB) : BaseViewHolder(binding.root)