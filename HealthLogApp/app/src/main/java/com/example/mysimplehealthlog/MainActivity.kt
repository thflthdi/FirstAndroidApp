package com.example.mysimplehealthlog

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.mysimplehealthlog.databinding.ActivityMainBinding
import com.example.mysimplehealthlog.databinding.CustomTabItemBinding
import com.google.android.material.tabs.TabLayout


class MainActivity : AppCompatActivity() {
    private lateinit var mContext: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mContext = applicationContext

        val tabLayout = binding.tabs
        val tab_home: TabLayout.Tab = binding.tabs.newTab()
        tab_home.setCustomView(matchTabIcon("홈"))
        tabLayout.addTab(tab_home)
        val tab_anal: TabLayout.Tab = binding.tabs.newTab()
        tab_anal.setCustomView(matchTabIcon("통계"))
        tabLayout.addTab(tab_anal)
        val tab_plan: TabLayout.Tab = binding.tabs.newTab()
        tab_plan.setCustomView(matchTabIcon("플랜"))
        tabLayout.addTab(tab_plan)
        val tab_plus: TabLayout.Tab = binding.tabs.newTab()
        tab_plus.setCustomView(matchTabIcon("더보기"))
        tabLayout.addTab(tab_plus)

        tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
//                val transaction = supportFragmentManager.beginTransaction()
//                when (tab?.text) {
//                    "홈" -> transaction.replace(R.id.tab_content, HomeFragment())
//                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                TODO("Not yet implemented")
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun matchTabIcon(tabName: String): View {
        val tabView = CustomTabItemBinding.inflate(layoutInflater)
        tabView.tabText.text = tabName
        when (tabName) {
            "홈" -> {
                tabView.tabIc.setImageResource(R.drawable.ic_tab_home)
                Log.d("test", "1 ${tabView.tabLayout}, ${tabView.tabText.text}, ${tabView.tabIc}")
                return tabView.tabLayout
            }
            "통계" -> {
                tabView.tabIc.setImageResource(R.drawable.ic_tab_anal)
                return tabView.tabLayout
            }
            "플랜" -> {
                tabView.tabIc.setImageResource(R.drawable.ic_tab_plan)
                return tabView.tabLayout
            }
            "더보기" -> {
                tabView.tabIc.setImageResource(R.drawable.ic_tab_plus)
                return tabView.tabLayout
            }
            else -> {
                tabView.tabIc.setImageResource(R.drawable.ic_tab_home)
                return tabView.tabLayout
            }
        }

    }


}

