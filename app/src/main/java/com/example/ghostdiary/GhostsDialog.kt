package com.example.ghostdiary;

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle;
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ghostdiary.adapter.AdapterPostdiary

import com.example.ghostdiary.databinding.DialogGhostsBinding;
import com.example.ghostdiary.databinding.ItemGhostBinding
import com.example.ghostdiary.dataclass.Day_Diary
import com.example.ghostdiary.dataclass.emotionclass

class GhostsDialog(var curpos:Int,var parent:AdapterPostdiary,var emotionlist: ArrayList<emotionclass>): DialogFragment() {

    // 뷰 바인딩 정의
    private var _binding: DialogGhostsBinding? = null
    private val binding get() = _binding!!

    private var selectghost:Int=0



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogGhostsBinding.inflate(inflater, container, false)
        val view = binding.root

        // 레이아웃 배경을 투명하게 해줌, 필수 아님

        initview()



        return view
    }

    fun initview(){

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.tvOk.setOnClickListener {
            var ghostname=binding.inputGhostname.text.toString()
            if(ghostname==""){
                MainActivity.mainactivity.showmessage("유령의 이름을 입력해주세요.")
                return@setOnClickListener
            }
            emotionlist.add(emotionclass(ghostname,selectghost,false))
            dismiss()
            parent.update(curpos,isadd = true)

        }
        binding.inputGhostname.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.tvTextcount.text= "${binding.inputGhostname.text.toString().length}/30"

            }
        })

        Update_rv()


    }
    fun Update_rv(){
        var state: Parcelable?=null
        try {
            state=binding.rvGhosts.layoutManager!!.onSaveInstanceState()

        }catch (e:Exception){
            state=null
        }

        val ghostarr= Day_Diary.addghost_arr()
        val ghostlistmanager = GridLayoutManager(context, 3,GridLayoutManager.HORIZONTAL,false)
        val ghostlistadpter = ghostadaper(this,ghostarr)


        binding.rvGhosts.apply {
            layoutManager=ghostlistmanager
            adapter=ghostlistadpter
        }
        if(state !=null){
            binding.rvGhosts.layoutManager!!.onRestoreInstanceState(state)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    class ghostadaper(var parent:GhostsDialog ,var emotions:List<Int>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {


        class GhostView(binding: ItemGhostBinding) : RecyclerView.ViewHolder(binding.root) {
            var layout=binding.layoutGhostView
            var ghostimage: ImageView =binding.ivGhost
        }



        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view : View?

            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_ghost, parent, false)
            return GhostView(ItemGhostBinding.bind(view))


        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

            var holder=holder as GhostView


            holder.ghostimage.alpha=1f

            var isnum=emotions[position]
            if(isnum==-1){
                holder.layout.visibility=View.INVISIBLE
                return

            }else{
                holder.layout.visibility=View.VISIBLE
            }

            holder.ghostimage.setImageResource(Day_Diary.int_to_image.get(emotions[position]))
            if(emotions[position]==parent.selectghost){
                holder.layout.background=ContextCompat.getDrawable(MainActivity.mainactivity,R.drawable.circle_backgroud)
            }else{
                holder.layout.background=ContextCompat.getDrawable(MainActivity.mainactivity,R.drawable.circle_backgroud_white)
            }
            holder.ghostimage.setOnClickListener {
                if(isnum==parent.selectghost){

                }else{
                    parent.selectghost=isnum

                    parent.Update_rv()

                }
            }


        }


        override fun getItemViewType(position: Int): Int {
            return 1
        }

        override fun getItemCount(): Int {
            return emotions.size
        }

    }

}