package com.example.mysimplehealthlog

import android.content.Context
import android.graphics.Color
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
import com.google.android.material.tabs.TabLayoutMediator


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
//        tab_home.setCustomView(matchTabIcon("홈"))
        tabLayout.addTab(tab_home)
        val tab_anal: TabLayout.Tab = binding.tabs.newTab()
//        tab_anal.setCustomView(matchTabIcon("통계"))
        tabLayout.addTab(tab_anal)
        val tab_plan: TabLayout.Tab = binding.tabs.newTab()
//        tab_plan.setCustomView(matchTabIcon("플랜"))
        tabLayout.addTab(tab_plan)
        val tab_plus: TabLayout.Tab = binding.tabs.newTab()
//        tab_plus.setCustomView(matchTabIcon("더보기"))
        tabLayout.addTab(tab_plus)

        val adapter = FragmentPagerAdapter(this)
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabs, binding.viewPager) {tab, position ->
            when(position) {
                0 -> tab.setCustomView(matchTabIcon("홈"))
                1 -> {
                    tab.setCustomView(matchTabIcon("통계"))
                    Log.d("test", "when")
                }
                2 -> tab.setCustomView(matchTabIcon("플랜"))
                3 -> tab.setCustomView(matchTabIcon("더보기"))
            }
        }.attach()

    }

    private fun matchTabIcon(tabName: String, isSelected: Boolean = false): View {
        val tabView = CustomTabItemBinding.inflate(layoutInflater)
        tabView.tabText.text = tabName
        when (tabName) {
            "홈" -> {
                tabView.tabIc.setImageResource(R.drawable.ic_tab_home)
            }
            "통계" -> {
                tabView.tabIc.setImageResource(R.drawable.ic_tab_anal)
            }
            "플랜" -> {
                tabView.tabIc.setImageResource(R.drawable.ic_tab_plan)
            }
            "더보기" -> {
                tabView.tabIc.setImageResource(R.drawable.ic_tab_plus)
            }
            else -> {
                tabView.tabIc.setImageResource(R.drawable.ic_tab_home)
            }
        }
        if (isSelected){
            Log.d("test", "${isSelected}")
            tabView.tabIc.setImageResource(R.drawable.ic_tab_home)
            tabView.tabText.setTextColor(Color.BLACK)
        }
        return tabView.tabLayout

    }


}

