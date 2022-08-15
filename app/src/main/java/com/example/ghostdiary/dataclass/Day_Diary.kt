package com.example.ghostdiary.dataclass

import com.example.ghostdiary.R
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class Day_Diary(
    var date: Date,
    var today_emotion:emotionclass,
    var whom:ArrayList<emotionclass> = arrayListOf(),
    var doing:ArrayList<emotionclass> =arrayListOf(),
    var where:ArrayList<emotionclass> = arrayListOf(),
    var weather:ArrayList<emotionclass> = arrayListOf(),
    var sleepstart: Date?=null,
    var sleepend : Date? = null,
    var text:String="",
    var image:String?=null



) {
    init {

    }

    constructor(date:Date,today: Int=2,text: String=""):this(date, emotionclass(emotionarr["오늘의 감정"]?.get(today)!!.text,today,true),text=text){
        whom=arrayListOf<emotionclass>()
        whom.addAll(emotionarr["누구와"]!!)
        doing=arrayListOf<emotionclass>()
        doing.addAll(emotionarr["무엇을"]!!)
        where=arrayListOf<emotionclass>()
        where.addAll(emotionarr["어디에서"]!!)
        weather=arrayListOf<emotionclass>()
        weather.addAll(emotionarr["날씨"]!!)

    }


    companion object {

        var int_to_image:ArrayList<Int> = arrayListOf(
            R.drawable.ghost_00_verygood,
            R.drawable.ghost_01_good,
            R.drawable.ghost_02_normal,
            R.drawable.ghost_03_bad,
            R.drawable.ghost_04_verybad,
            R.drawable.ghost_05_alone,
            R.drawable.ghost_06_family,
            R.drawable.ghost_07_friend,
            R.drawable.ghost_08_lover,
            R.drawable.ghost_09_pat,
            R.drawable.ghost_10_home,
            R.drawable.ghost_11_school,
            R.drawable.ghost_12_office,
            R.drawable.ghost_13_cafe,
            R.drawable.ghost_14_restaurant,
            R.drawable.ghost_15_travel,
            R.drawable.ghost_16_health,
            R.drawable.ghost_17_shopping,
            R.drawable.ghost_18_movie,
            R.drawable.ghost_19_library,
            R.drawable.ghost_20_walking,
            R.drawable.ghost_21_study,
            R.drawable.ghost_22_sport,
            R.drawable.ghost_23_work,
            R.drawable.ghost_24_shopping,
            R.drawable.ghost_25_drawing,
            R.drawable.ghost_26_reading,
            R.drawable.ghost_27_drinking,
            R.drawable.ghost_28_game,
            R.drawable.ghost_29_travel,
            R.drawable.ghost_30_sunny,
            R.drawable.ghost_31_cloudy,
            R.drawable.ghost_32_rain
        )

        val emotionname= arrayOf("오늘의 감정","누구와","무엇을","어디에서","날씨","수면시간")
        val emotionarr: HashMap<String,Array<emotionclass>> = hashMapOf(
            "오늘의 감정" to arrayOf(emotionclass("매우좋음",0,false),emotionclass("좋음",1,false),
                emotionclass("보통",2,false),emotionclass("나쁨",3,false),
                emotionclass("매우나쁨",4,false)),
            "누구와" to arrayOf(emotionclass("혼자",5,false),emotionclass("가족",6,false),
                emotionclass("친구",7,false),emotionclass("연인",8,false),
                emotionclass("반려동물",9,false)),

            "무엇을" to arrayOf(emotionclass("산책",20,false),emotionclass("공부",21,false),
                emotionclass("운동",22,false),emotionclass("일",23,false),emotionclass("쇼핑",24,false),
                emotionclass("그림",25,false),emotionclass("독서",26,false),emotionclass("술",27,false),
                emotionclass("게임",28,false),emotionclass("여행",29,false)),
            "어디에서" to arrayOf(emotionclass("집",10,false),emotionclass("학교",11,false),
                emotionclass("직장",12,false),emotionclass("카페",13,false),
                emotionclass("식당",14,false),emotionclass("여행",15,false),emotionclass("헬스장",16,false),
                emotionclass("쇼핑",17,false),emotionclass("영화관",18,false),emotionclass("도서관",19,false)),
            "날씨" to arrayOf(emotionclass("맑음",30,false),emotionclass("비",31,false),
                emotionclass("구름",32,false))
        )


        fun addghost_arr():ArrayList<Int>{
            var arr=arrayListOf(
                5,6,7,8,9,-1,-1,-1,-1,-1,
                20,21,22,23,24,25,26,27,28,29,
                10,11,12,13,14,15,16,17,18,19
            )
            var temp=(arr.size/3).toInt()
            var result= arrayListOf<Int>()
            for(i in 0..temp-1){
                result.add(arr[10*0+i])
                result.add(arr[10+i])
                result.add(arr[20+i])
            }
            return result

        }

    }


    fun getEmotionarr():ArrayList<ArrayList<emotionclass>>{
        var arr:ArrayList<ArrayList<emotionclass>> = arrayListOf()
        var today=arrayListOf<emotionclass>()
        for (i in emotionarr["오늘의 감정"]!!){
            today.add(i.clone())
        }
        today!![today_emotion.ghostimage].isactive=true
        arr.add(today)
        var _whom=arrayListOf<emotionclass>()
        for(i in whom){
            _whom.add(i.clone())
        }
        arr.add(_whom)
        var _doing=arrayListOf<emotionclass>()
        for(i in doing){
            _doing.add(i.clone())
        }
        arr.add(_doing)
        var _where=arrayListOf<emotionclass>()
        for(i in where){
            _where.add(i.clone())
        }
        arr.add(_where)
        var _weather=arrayListOf<emotionclass>()
        for(i in weather){
            _weather.add(i.clone())
        }
        arr.add(_weather)
        return arr
    }
    fun getEmotionarrElement():ArrayList<emotionclass?>{
        var arr:ArrayList<emotionclass?> = arrayListOf()
        arr.add(today_emotion)
        arr.add(null)
        for( i in whom){
            if(i.isactive)
                arr.add(i)
        }
        if(whom.size!=0)
            arr.add(null)
        for( i in doing){
            if(i.isactive)
                arr.add(i)
        }
        if(doing.size!=0)
            arr.add(null)
        for( i in where){
            if(i.isactive)
                arr.add(i)
        }
        if(where.size !=0)
            arr.add(null)
        for( i in weather){
            if(i.isactive)
                arr.add(i)
        }
        return arr

    }



    fun getEmotionarr_name():MutableList<String>{
        var arr:MutableList<String> = mutableListOf()
        arr.add(emotionname[0])
        if(whom !=null)
            arr.add(emotionname[1])
        if(doing !=null)
            arr.add(emotionname[2])
        if(where !=null)
            arr.add(emotionname[3])
//        if(mood !=-1)
//            arr.add(emotionname[4])
        if(weather !=null)
            arr.add(emotionname[5])
        return arr
    }
}
