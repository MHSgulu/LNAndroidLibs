package com.lnkj.android.demo.ui.page.main

import android.os.Bundle
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ActivityUtils
import com.lnkj.android.demo.bean.ModuleBean
import com.lnkj.android.demo.bean.mainDataList
import com.lnkj.android.demo.databinding.ActivityMainBinding
import com.lnkj.libs.base.BaseActivity
import com.lnkj.libs.core.setOnItemClick
import com.lnkj.libs.utils.startPage

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
			val list = mainDataList.filter { it.title.contains(content) || it.content.contains(content) }
			data.clear()
			data.addAll(list)
			adapter.notifyDataSetChanged()
		}

	}

	override fun initData() {

		data.clear()
		data.addAll(mainDataList)
		adapter.notifyDataSetChanged()

	}

	override fun startObserve() {
	}

}