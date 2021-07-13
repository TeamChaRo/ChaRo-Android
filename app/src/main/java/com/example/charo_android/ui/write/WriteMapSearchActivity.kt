package com.example.charo_android.ui.write

import android.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import com.example.charo_android.data.MapSearchInfo
import com.example.charo_android.databinding.ActivityWriteMapSearchBinding
import com.skt.Tmap.TMapData
import com.skt.Tmap.TMapPOIItem
import kotlinx.coroutines.*


class WriteMapSearchActivity : AppCompatActivity() {

//    lateinit var location: String
    private lateinit var binding: ActivityWriteMapSearchBinding
    private lateinit var writeMapSearchAdapter: WriteMapSearchAdapter

    var list = mutableListOf<MapSearchInfo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWriteMapSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d("111", "success go searchActivity")

        var text = intent.getStringExtra("locationM")
        binding.etWriteMapSearchStart.hint = intent.getStringExtra("locationM")

        if(text == "출발지를 입력하세요"){
            var intent = Intent(this,WriteMapLocationActivity::class.java)
            intent.putExtra("setLocation","출발지로 설정")
        }else if(text == "경유지1를 입력하세요"){
            var intent = Intent(this,WriteMapLocationActivity::class.java)
            intent.putExtra("setLocation","경유지1지로 설정")
        }else if(text == "경유지2를 입력하세요"){
            var intent = Intent(this,WriteMapLocationActivity::class.java)
            intent.putExtra("setLocation","경유지2로 설정")
        }else if(text == "도착지를 입력하세요"){
            var intent = Intent(this,WriteMapLocationActivity::class.java)
            intent.putExtra("setLocation","도착지로 설정")
        }



        // 1. 우리가 사용할 어뎁터의 초기 값을 넣어준다
        writeMapSearchAdapter =
            WriteMapSearchAdapter()

        // 2. RecyclerView 에 어뎁터를 우리가 만든 어뎁터로 만들기
        binding.recyclerviewWriteMapSearch.adapter = writeMapSearchAdapter

        val tMapPOIItem = TMapPOIItem()
        val tmapdata = TMapData()
//        var list = listOf<MapSearchInfo>()
        //      val POIItem = tmapdata.findAllPOI("SKT타워")


        //동기화 필요 //뒤로가기 이슈
        binding.etWriteMapSearchStart.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KEYCODE_ENTER) {
                list = mutableListOf<MapSearchInfo>()
                tmapdata.findAllPOI(binding.etWriteMapSearchStart.text.toString()) { poiItem ->
                    for (i in 0 until poiItem.size) {
                        val item = poiItem[i]
                        Log.d("list",item.toString())

                        list.add(
                            MapSearchInfo(
                                locationName = item.poiName,
                                locationAddress = item.poiAddress.replace("null", "")
                            )
                        )
                    }
                    writeMapSearchAdapter.userList = list
                    Log.d("list",list.toString())

                }
                writeMapSearchAdapter.notifyDataSetChanged()

            }

            true
        }

    }
}
