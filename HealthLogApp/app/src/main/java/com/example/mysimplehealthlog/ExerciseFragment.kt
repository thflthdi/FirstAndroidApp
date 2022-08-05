package com.example.mysimplehealthlog

import android.content.Context
import android.content.res.AssetManager
import kotlinx.android.synthetic.main.activity_main.*
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mysimplehealthlog.databinding.ExerciseSetAddBinding
import com.example.mysimplehealthlog.databinding.ExerciseSetRecycleBinding
import com.example.mysimplehealthlog.databinding.FragmentExerciseBinding
import com.google.android.material.snackbar.Snackbar
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.spans.DotSpan
import org.json.JSONArray
import org.json.JSONObject

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

//set
class SetViewHolder(val binding: ExerciseSetRecycleBinding) : RecyclerView.ViewHolder(binding.root)

//add
class AddViewHolder(val binding: ExerciseSetAddBinding) : RecyclerView.ViewHolder(binding.root)

//set Adapter
class SetAdapter(val context: Context, val datas: JSONArray) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemCount(): Int {
        return datas.length()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        SetViewHolder(
            ExerciseSetRecycleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as SetViewHolder).binding
        val data = datas.getJSONObject(position)
        binding.setExerciseName.text = data.getString("name")
        binding.setTotalCtn.text = "total ${data.getString("total")} set"

        // recycle -add
        binding.setRecycle.layoutManager = LinearLayoutManager(context)
        val add_datas = mutableListOf<Int>()
        for (i in 1..4) {
            add_datas.add(10)
        }

        binding.setPlusBtn.setOnClickListener {
            add_datas.add(10)
            Log.d("test", "click - $datas")

            binding.setRecycle.adapter = AddAdapter(context, add_datas)
        }

        binding.foldBtn.setOnClickListener {
            if (binding.foldBtn.text === "v") {
                binding.foldBtn.text = "^"
                binding.setRecycle.isVisible = true
                binding.setPlusBtn.isVisible = true
            } else {
                binding.foldBtn.text = "v"
                binding.setRecycle.isVisible = false
                binding.setPlusBtn.isVisible = false
            }
        }

        val adapter_add = AddAdapter(context, add_datas)
        binding.setRecycle.adapter = adapter_add
    }

}

//add Adapter
class AddAdapter(val context: Context, val datas: MutableList<Int>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        AddViewHolder(
            ExerciseSetAddBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as AddViewHolder).binding
        binding.ctnEdittext.hint = "${datas[position]}"
        binding.setCtn.text = "${position + 1} Set"

        binding.delSetBtn.setOnClickListener {
            if (datas.size > 1) {
                Log.d("test", "${position}")

                datas.removeAt(position)
                notifyDataSetChanged()
            } else {
//                val toast = Toast.makeText(context, "최소 하나의 세트가 필요합니다", Toast.LENGTH_SHORT).show()
                Snackbar.make(binding.addLinear, "최소 하나의 세트가 필요합니다", Snackbar.LENGTH_SHORT)
                    .show()
            }
        }
        binding.minusCtnBtn.setOnClickListener {
            binding.ctnEdittext.hint = "${datas[position] - 1}"
            datas[position]--
        }

        binding.plusCtnBtn.setOnClickListener {
            binding.ctnEdittext.hint = "${datas[position] + 1}"
            datas[position]++
        }

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

        // recycle -set
        val assetManager = resources.assets
        val inputStream = assetManager.open("set_data.json")
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        val jObject = JSONObject(jsonString)
        val jArray = jObject.getJSONArray("posts")


        val layoutManager = LinearLayoutManager(activity)
        binding.exerciseList.layoutManager = layoutManager

        val adapter = SetAdapter(requireContext(), jArray)
        binding.exerciseList.adapter = adapter

        return binding.root

    }
}