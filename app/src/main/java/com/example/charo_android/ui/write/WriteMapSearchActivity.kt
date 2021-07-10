package com.example.charo_android.ui.write

import android.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.charo_android.data.MapSearchInfo
import com.example.charo_android.databinding.ActivityWriteMapSearchBinding
import com.skt.Tmap.TMapData
import com.skt.Tmap.TMapPOIItem
import kotlinx.coroutines.*


class WriteMapSearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWriteMapSearchBinding
    private lateinit var writeMapSearchAdapter: WriteMapSearchAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWriteMapSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d("111", "success go searchActivity")

        binding.textResultSearch.setOnClickListener{
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
        //   val POIItem = tmapdata.findAllPOI("SKT타워")

        runBlocking {
           var list = mutableListOf<MapSearchInfo>()

            var a = launch{
                tmapdata.findAllPOI("SKT타워") { poiItem ->
                    for (i in 0 until poiItem.size) {
                        val item = poiItem[i]

                        list.add(
                            MapSearchInfo(
                                locationName = item.poiName,
                                locationAddress = item.poiAddress.replace("null", "")
                            )

                        )
                        Log.d(
                            "list 11", list.toString()
                        )
                    //    delay(10)
                    }
                }
            }
            a.join()

            Log.d(
                "list ", list.toString()
            )
            writeMapSearchAdapter.userList.addAll(list)
            writeMapSearchAdapter.notifyDataSetChanged()

        }

    }
}