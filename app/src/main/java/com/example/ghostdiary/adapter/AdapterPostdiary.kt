package com.example.ghostdiary.adapter

import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ghostdiary.R
import com.example.ghostdiary.databinding.ItemSelectEmotionBinding
import com.example.ghostdiary.databinding.ItemSelectSleeptimeBinding
import com.example.ghostdiary.dataclass.emotionclass
import com.example.ghostdiary.fragment.main.SelectEmotionFragment
import com.google.android.material.slider.LabelFormatter
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


class AdapterPostdiary(val parent: SelectEmotionFragment,var sleepstart:Int,var sleepend :Int,val emotions: Array<String>, var selecttexts:ArrayList<ArrayList<emotionclass>>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var rv_emotionList:Array<RecyclerView?> = arrayOfNulls<RecyclerView?>(emotions.size)
    var emotionAdapterList:Array<AdapterEmotion?> = arrayOfNulls<AdapterEmotion?>(emotions.size)

    class EmotionView(binding: ItemSelectEmotionBinding) : RecyclerView.ViewHolder(binding.root) {
        var tv_title: TextView = binding.tvTitle
        var rv_emotion: RecyclerView= binding.rvEmotionlist


    }
    class SleepView(binding: ItemSelectSleeptimeBinding) :RecyclerView.ViewHolder(binding.root) {
        var tv_title: TextView = binding.tvTitle
        var slider=binding.slider

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view : View?
        return when(viewType) {
            1 -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_select_emotion, parent, false)
                return EmotionView(ItemSelectEmotionBinding.bind(view))
            }
            else -> {
                var view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_select_sleeptime, parent, false)
                return SleepView(ItemSelectSleeptimeBinding.bind(view))
            }
        }

    }
    fun update(position: Int){
        var emotionManager = GridLayoutManager(parent.context, 5)
        emotionAdapterList[position]= AdapterEmotion(this,position,selecttexts[position])

        rv_emotionList[position]!!.apply {
            layoutManager=emotionManager
            adapter=emotionAdapterList[position]
        }

    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(getItemViewType(position)==1){
            var holder=holder as EmotionView

            holder.tv_title.text=emotions[position]


            rv_emotionList[position]=holder.rv_emotion

            update(position)

        }
        else {
            var holder = holder as SleepView
            var yesterday = Calendar.getInstance()
            yesterday.time = parent.date
            yesterday.add(Calendar.DATE, -1)
            yesterday.set(Calendar.HOUR_OF_DAY, 22)

            var timeformat = SimpleDateFormat("dd일 HH시")


            holder.slider.values= listOf<Float>(0.0f,10.0f)
            if(sleepstart !=-1 && sleepend != -1){
                holder.slider.values= listOf<Float>(sleepstart.toFloat(),sleepend.toFloat())
            }
            holder.slider.setLabelFormatter(

                LabelFormatter { value ->
                    var temp = Calendar.getInstance()
                    temp.time=yesterday.time
                    temp.add(Calendar.HOUR_OF_DAY, value.toInt())
                    var to = timeformat.format(temp.time)


                    to
                })
            holder.slider.addOnChangeListener { slider, value, fromUser ->
                val values = holder.slider.values
                parent.sleepend = values[1].toInt()
                parent.sleepstart = values[0].toInt()
            }
        }



    }


    override fun getItemViewType(position: Int): Int {
        if(emotions[position]!="수면시간"){
            return 1

        }
        else{
            return 2
        }
    }

    override fun getItemCount(): Int {
        return emotions.size
    }

}