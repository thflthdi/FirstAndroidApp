package com.example.mysimplehealthlog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.adapter.FragmentViewHolder
import com.example.mysimplehealthlog.databinding.FragmentHomeBinding
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.spans.DotSpan

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

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeBinding.inflate(layoutInflater)
        val calendar = binding.calendarView

        // activity 호출, 운동 페이지로 이동
        binding.exerciseButton.setOnClickListener {
            (activity as MainActivity).replaceFragment(ExerciseFragment())
        }

        // calendar
        // TODO("커스텀 원 사이즈 줄이기, dot 말고 원으로 변경")
        calendar.setSelectedDate(CalendarDay.today())
        val dayList = mutableListOf<CalendarDay>()
        for (active in 1..15) {
            dayList.add(CalendarDay.from(2022, 7, active))
        }
        calendar.addDecorator(CalendarSetting(dayList))

        return binding.root
    }
}