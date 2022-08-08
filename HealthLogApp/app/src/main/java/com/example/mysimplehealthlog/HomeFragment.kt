package com.example.mysimplehealthlog

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentViewHolder
import com.example.mysimplehealthlog.databinding.ExerciseSetRecycleBinding
import com.example.mysimplehealthlog.databinding.FragmentHomeBinding
import com.example.mysimplehealthlog.databinding.HomeTodayListLayoutBinding
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.spans.DotSpan
import org.json.JSONArray
import org.json.JSONObject

// calendar setting
class CalendarSetting(actionDay: Collection<CalendarDay>) : DayViewDecorator {
    var actionDay: HashSet<CalendarDay> = HashSet(actionDay)

    override fun shouldDecorate(day: CalendarDay): Boolean {
        return actionDay.contains(day)
    }

    override fun decorate(view: DayViewFacade?) {
        view?.addSpan(DotSpan(R.color.main_color))
    }
}

// today list - binding
class TodayListHolder(val binding: HomeTodayListLayoutBinding) :
    RecyclerView.ViewHolder(binding.root)

// today list - adapter
class TodayAdapter(val context: Context, val datas: JSONArray) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemCount(): Int {
        return datas.length()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        TodayListHolder(
            HomeTodayListLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as TodayListHolder).binding
        val data = datas.getJSONObject(position)

        binding.emojiText.text = data.getString("emoji")
        binding.todayExerciseTitle.text = data.getString("name")
        binding.todayTotalInfo.text = "총 ${data.getString("total")} | 평균 ${data.getString("aver")}회"
    }
}

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeBinding.inflate(layoutInflater)
        val calendar = binding.calendarView

        // activity 호출, 운동 페이지로 이동
        binding.exerciseTitle.setOnClickListener {
            (activity as MainActivity).replaceFragment(ExerciseFragment())
        }

        // 오늘 운동 리스트
        val assetManager = resources.assets
        val inputStream = assetManager.open("today_list.json")
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        val jObject = JSONObject(jsonString)
        val jArray = jObject.getJSONArray("posts")

        val layoutManager = LinearLayoutManager(activity)
        binding.todayListRecycle.layoutManager = layoutManager

        val adapter = TodayAdapter(requireContext(), jArray)
        binding.todayListRecycle.adapter = adapter

        binding.emptyExerciseText.isVisible = jArray.length() <= 0

        // calendar
        // TODO("커스텀 원 사이즈 줄이기, dot 말고 원으로 변경")
        calendar.selectedDate = CalendarDay.today()
        val dayList = mutableListOf<CalendarDay>()
        for (active in 1..15) {
            dayList.add(CalendarDay.from(2022, 7, active))
        }
        calendar.addDecorator(CalendarSetting(dayList))

        return binding.root
    }
}