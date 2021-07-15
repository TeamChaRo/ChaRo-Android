package com.example.charo_android.ui.write

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.charo_android.data.MapSearchInfo
import com.example.charo_android.databinding.ActivityWriteMapSearchBinding
import com.skt.Tmap.TMapData

class WriteMapSearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWriteMapSearchBinding
    private lateinit var writeMapSearchAdapter: WriteMapSearchAdapter

    private lateinit var locationFlag: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteMapSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d("111", "success go searchActivity")

//        내가 추가한 코드
        locationFlag = intent.getStringExtra("locationFlag").toString()
        var text: String = if (locationFlag == "1") {
            "출발지로 설정"
        } else if (locationFlag == "4") {
            "도착지로 설정"
        } else {
            "경유지로 설정"
        }
        binding.etWriteMapSearchStart.hint = text

//        if(text == "출발지를 입력하세요"){
//            var intent = Intent(this,WriteMapLocationActivity::class.java)
//            intent.putExtra("setLocation","출발지로 설정")
//        }else if(text == "경유지1를 입력하세요"){
//            var intent = Intent(this,WriteMapLocationActivity::class.java)
//            intent.putExtra("setLocation","경유지1지로 설정")
//        }else if(text == "경유지2를 입력하세요"){
//            var intent = Intent(this,WriteMapLocationActivity::class.java)
//            intent.putExtra("setLocation","경유지2로 설정")
//        }else if(text == "도착지를 입력하세요"){
//            var intent = Intent(this,WriteMapLocationActivity::class.java)
//            intent.putExtra("setLocation","도착지로 설정")
//        }


        // 1. 우리가 사용할 어뎁터의 초기 값을 넣어준다
        writeMapSearchAdapter =
            WriteMapSearchAdapter(locationFlag, text)

        // 2. RecyclerView 에 어뎁터를 우리가 만든 어뎁터로 만들기
        binding.recyclerviewWriteMapSearch.adapter = writeMapSearchAdapter

        val tMapPOIItem = TMapPOIItem()
        val tmapdata = TMapData()
//        var list = listOf<MapSearchInfo>()
        //      val POIItem = tmapdata.findAllPOI("SKT타워")

        binding.etWriteMapSearchStart.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Log.d("myLog", "before")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d("myLog", "changing")
            }

            override fun afterTextChanged(s: Editable?) {
                Log.d("myLog", "after")
                var list = mutableListOf<MapSearchInfo>()
                Handler(Looper.getMainLooper()).postDelayed({
                    if(binding.etWriteMapSearchStart.text.isNotEmpty()) {
                        tmapdata.findTitlePOI(binding.etWriteMapSearchStart.text.toString()) { poiItem ->
                            for (i in 0 until poiItem.size) {
                                val item = poiItem[i]

                                list.add(
                                    MapSearchInfo(
                                        locationName = item.poiName,
                                        locationAddress = item.poiAddress.replace("null", "")
                                    )

                                )
                            }
                            writeMapSearchAdapter.userList = list
                        }
                        writeMapSearchAdapter.notifyDataSetChanged()
                    }
                }, 500)
            }
        })
    }
}
