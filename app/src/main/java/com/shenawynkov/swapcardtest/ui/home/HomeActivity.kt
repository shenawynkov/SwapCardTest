package com.shenawynkov.swapcardtest.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.material.tabs.TabLayoutMediator
import com.shenawynkov.swapcardtest.R
import com.shenawynkov.swapcardtest.ui.home.artists.PagerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pagerAdapter = PagerAdapter(this)
         pager.apply {
             adapter=pagerAdapter
         }
        TabLayoutMediator(tab_layout, pager) { tab, position ->
            tab.text = "tab ${(position + 1)}"
        }.attach()
    }
}