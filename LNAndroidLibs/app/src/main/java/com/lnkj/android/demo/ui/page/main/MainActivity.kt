package com.lnkj.android.demo.ui.page.main

import android.os.Bundle
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ActivityUtils
import com.lnkj.android.demo.bean.ModuleBean
import com.lnkj.android.demo.bean.mainDataList
import com.lnkj.android.demo.databinding.ActivityMainBinding
import com.lnkj.libs.base.BaseActivity
import com.lnkj.libs.core.okRequest
import com.lnkj.libs.core.request
import com.lnkj.libs.core.setOnItemClick
import com.lnkj.libs.utils.startPage
import com.lnkj.libs.utils.startPageForResult

/**
 *
 */
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    private val data = arrayListOf<ModuleBean>()
    private val adapter = MainAdapter(data)

    override fun initView(savedInstanceState: Bundle?) {

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        adapter.setOnItemClick { view, position ->
            val item = adapter.getItem(position)
            ActivityUtils.startActivity(item.clzz)
        }

        binding.etSearch.doOnTextChanged { _, _, _, _ ->
            val content = binding.etSearch.text.toString().trim()
            val list =
                mainDataList.filter { it.title.contains(content) || it.content.contains(content) }
            data.clear()
            data.addAll(list)
            adapter.notifyDataSetChanged()
        }
		val params = arrayOf<Pair<String, Any>>()
        okRequest(
            "url",
            "tag",
            *params,
            onStart = {},
            onError = { msg, code -> },
            onSuccess = {})

		request<String>("url",
			"tag",
			*params,
			onStart = {},
			onError = { msg, code -> },
			onSuccess = {})

    }

    override fun initData() {

        data.clear()
        data.addAll(mainDataList)
        adapter.notifyDataSetChanged()

        startPageForResult<MainActivity>{code, data ->

        }

    }

    override fun startObserve() {
    }

}