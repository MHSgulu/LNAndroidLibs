package com.lnkj.social

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initEvent()
    }

    private fun initEvent() {
        btn_wx.setOnClickListener {
            WXActivity.startActivity(this)
        }
        btn_qq.setOnClickListener {
            QQActivity.startActivity(this)
        }
        btn_wb.setOnClickListener {
            WBActivity.startActivity(this)
        }
    }
}