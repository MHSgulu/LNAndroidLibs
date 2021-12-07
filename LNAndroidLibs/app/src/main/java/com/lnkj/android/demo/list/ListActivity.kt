package com.lnkj.android.demo.list

import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.lnkj.android.demo.databinding.ActivityListBinding
import com.lnkj.libs.base.BaseActivity
import com.lnkj.libs.core.click

class ListActivity : BaseActivity<ListViewModel, ActivityListBinding>() {

    private val data = arrayListOf<Any>()
    private val adapter = ListAdapter(data)

    override fun initView(savedInstanceState: Bundle?) {
        binding.appBar.ivBack.click { finish() }
        binding.appBar.tvTitle.text = ""

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

    }

    override fun initData() {

    }

    override fun startObserve() {

    }
}