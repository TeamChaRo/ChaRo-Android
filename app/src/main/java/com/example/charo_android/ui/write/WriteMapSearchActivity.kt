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

    private lateinit var binding: ActivityWriteMapSearchBinding
    private lateinit var writeMapSearchAdapter: WriteMapSearchAdapter

    var list = mutableListOf<MapSearchInfo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWriteMapSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d("111", "success go searchActivity")

        binding.textResultSearch.setOnClickListener {
            val intent = Intent(this, WriteMapLocationActivity::class.java)
            startActivity(intent)
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


        val start = binding.etWriteMapSearchStart.text.toString()
        Log.d("1111", binding.etWriteMapSearchStart.text.toString())


        //동기화 필요 //뒤로가기 이슈
        binding.etWriteMapSearchStart.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KEYCODE_ENTER) {
                list = mutableListOf<MapSearchInfo>()
                tmapdata.findAllPOI(binding.etWriteMapSearchStart.text.toString()) { poiItem ->
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

                    Log.d(
                        "list ", list.toString()
                    )
               //     writeMapSearchAdapter.userList.addAll(list)
                }
                Log.d(
                    "list ", list.toString()
                )
//                writeMapSearchAdapter.userList = list
                writeMapSearchAdapter.notifyDataSetChanged()

            }

            true
        }









//        binding.etWriteMapSearchStart.setOnEditorActionListener { v, actionId, event ->
//            var handled = false
//            if (actionId == EditorInfo.IME_ACTION_DONE) {
////                var list = mutableListOf<MapSearchInfo>()
//                tmapdata.findAllPOI(binding.etWriteMapSearchStart.text.toString()) { poiItem ->
//                    for (i in 0 until poiItem.size) {
//                        val item = poiItem[i]
//
//                        list.add(
//                            MapSearchInfo(
//                                locationName = item.poiName,
//                                locationAddress = item.poiAddress.replace("null", "")
//                            )
//
//                        )
//                    }
//                    //     writeMapSearchAdapter.userList.addAll(list)
//                }
//                Log.d(
//                    "list ", list.toString()
//                )
//                writeMapSearchAdapter.userList.addAll(list)
//                writeMapSearchAdapter.notifyDataSetChanged()
//                handled = true
//            }
//            handled
//        }
    }
}


//runBlocking {
//    var list = mutableListOf<MapSearchInfo>()
//
//    var a = launch{
//        tmapdata.findAllPOI(binding.etWriteMapSearchStart.text.toString()) { poiItem ->
//            for (i in 0 until poiItem.size) {
//                val item = poiItem[i]
//
//                list.add(
//                    MapSearchInfo(
//                        locationName = item.poiName,
//                        locationAddress = item.poiAddress.replace("null", "")
//                    )
//
//                )
//                Log.d(
//                    "list 11", list.toString()
//                )
//                //    delay(10)
//            }
//        }
//    }
//    a.join()
//
//    Log.d(
//        "list ", list.toString()
//    )
//    writeMapSearchAdapter.userList.addAll(list)
//    writeMapSearchAdapter.notifyDataSetChanged()
//
//}