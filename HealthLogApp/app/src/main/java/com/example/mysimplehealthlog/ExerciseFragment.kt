package com.example.mysimplehealthlog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mysimplehealthlog.databinding.FragmentExerciseBinding
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.spans.DotSpan

// calendar setting
class WeekCalendarSetting(actionDay: Collection<CalendarDay>) : DayViewDecorator {
    var actionDay: HashSet<CalendarDay> = HashSet(actionDay)

    override fun shouldDecorate(day: CalendarDay): Boolean {
        return actionDay.contains(day)
    }

    override fun decorate(view: DayViewFacade?) {
        view?.addSpan(DotSpan(R.color.main_color))
    }
}

class ExerciseFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentExerciseBinding.inflate(layoutInflater)
        val calendar = binding.calendarView

        calendar.setSelectedDate(CalendarDay.today())
        calendar.topbarVisible = false
        val dayList = mutableListOf<CalendarDay>()
        for (active in 1..15) {
            dayList.add(CalendarDay.from(2022, 7, active))
        }
        calendar.addDecorator(WeekCalendarSetting(dayList))
        return binding.root
    }
}