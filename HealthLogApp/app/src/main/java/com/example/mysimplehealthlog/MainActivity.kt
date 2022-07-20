package com.example.mysimplehealthlog

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.mysimplehealthlog.databinding.ActivityMainBinding
import com.example.mysimplehealthlog.databinding.CustomTabItemBinding
import com.google.android.material.tabs.TabLayout


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Tab 생성
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

        // TODO("탭 선택 후 프레임 늦게 뜸")
        //fragment
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_frame, HomeFragment()).commit()

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
                val transaction = supportFragmentManager.beginTransaction()

                when (tab?.position) {
                    0 -> transaction.replace(R.id.main_frame, HomeFragment())
                    1 -> transaction.replace(R.id.main_frame, AnalysisFragment())
                    2 -> transaction.replace(R.id.main_frame, PlanFragment())
                    3 -> transaction.replace(R.id.main_frame, PlusFragment())
                }
                transaction.commit()
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                val transaction = supportFragmentManager.beginTransaction()
                when (tab?.position) {
                    0 -> transaction.replace(R.id.main_frame, HomeFragment())
                    1 -> transaction.replace(R.id.main_frame, AnalysisFragment())
                    2 -> transaction.replace(R.id.main_frame, PlanFragment())
                    3 -> transaction.replace(R.id.main_frame, PlusFragment())
                }
                transaction.commit()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
        })

    }

    // fragment에서 activity를 호출하여 페이지 제어
    fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_frame, fragment).commit()
    }

    //custom_tab_item 아이콘을 text에 따라 변환
    private fun matchTabIcon(tabName: String): View {
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
        return tabView.tabLayout
    }

}

