package com.example.mysimplehealthlog

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mysimplehealthlog.databinding.ActivityMainBinding
import com.example.mysimplehealthlog.databinding.CustomTabItemBinding
import com.google.android.material.tabs.TabLayout


class MainActivity : AppCompatActivity() {

    class FragmentPagerAdapter(activity: FragmentActivity) :
        FragmentStateAdapter(activity) {
        val fragments: List<Fragment>
        init {
            fragments = listOf(HomeFragment(), AnalysisFragment(), PlanFragment(), PlusFragment())
        }

        override fun getItemCount(): Int = fragments.size

        override fun createFragment(position: Int): Fragment = fragments[position]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        val adapter = FragmentPagerAdapter(this)
        binding.viewPager.adapter = adapter


//        tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
//            override fun onTabSelected(tab: TabLayout.Tab?) {
//                val transaction = supportFragmentManager.beginTransaction()
//                when (tab?.text) {
//                    "홈" -> transaction.replace(R.id.tab_content, HomeFragment())
//                    "통계" -> transaction.replace(R.id.tab_content, HomeFragment())
//                    "플랜" -> transaction.replace(R.id.tab_content, HomeFragment())
//                    "더보기" -> transaction.replace(R.id.tab_content, HomeFragment())
//                }
//                transaction.commit()
//            }
//
//            override fun onTabReselected(tab: TabLayout.Tab?) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab?) {
//                //tab의 상태가 선택 상태 -> 선택 되지 않음으로 변경
//
//            }
//        })
    }

    private fun matchTabIcon(tabName: String): View {
        val tabView = CustomTabItemBinding.inflate(layoutInflater)
        tabView.tabText.text = tabName
        when (tabName) {
            "홈" -> {
                tabView.tabIc.setImageResource(R.drawable.ic_tab_home)
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

