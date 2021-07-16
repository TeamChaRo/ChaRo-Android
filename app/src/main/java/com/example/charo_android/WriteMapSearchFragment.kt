package com.example.charo_android

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.charo_android.data.MapSearchInfo
import com.example.charo_android.databinding.FragmentWriteMapBinding
import com.example.charo_android.databinding.FragmentWriteMapSearchBinding
import com.example.charo_android.ui.write.WriteMapLocationActivity
import com.example.charo_android.ui.write.WriteMapSearchAdapter
import com.example.charo_android.ui.write.WriteViewModel
import com.skt.Tmap.TMapData
import com.skt.Tmap.TMapPOIItem

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WriteMapSearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WriteMapSearchFragment : Fragment() {

    private lateinit var writeViewModel: WriteViewModel
    private var _binding: FragmentWriteMapSearchBinding? = null

    private lateinit var writeMapSearchAdapter: WriteMapSearchAdapter

    var list = mutableListOf<MapSearchInfo>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        writeViewModel =
            ViewModelProvider(this).get(WriteViewModel::class.java)

        _binding = FragmentWriteMapSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        var text = intent.getStringExtra("locationM")
//        binding.etWriteMapSearchStart.hint = intent.getStringExtra("locationM")
//
//        if(text == "출발지를 입력하세요"){
//            var intent = Intent(this, WriteMapLocationActivity::class.java)
//            intent.putExtra("setLocation","출발지로 설정")
//        }else if(text == "경유지1를 입력하세요"){
//            var intent = Intent(this, WriteMapLocationActivity::class.java)
//            intent.putExtra("setLocation","경유지1지로 설정")
//        }else if(text == "경유지2를 입력하세요"){
//            var intent = Intent(this, WriteMapLocationActivity::class.java)
//            intent.putExtra("setLocation","경유지2로 설정")
//        }else if(text == "도착지를 입력하세요"){
//            var intent = Intent(this, WriteMapLocationActivity::class.java)
//            intent.putExtra("setLocation","도착지로 설정")
//        }



        // 1. 우리가 사용할 어뎁터의 초기 값을 넣어준다
        writeMapSearchAdapter =
            WriteMapSearchAdapter("","")

        // 2. RecyclerView 에 어뎁터를 우리가 만든 어뎁터로 만들기
        binding.recyclerviewWriteMapSearch.adapter = writeMapSearchAdapter

        val tMapPOIItem = TMapPOIItem()
        val tmapdata = TMapData()
//        var list = listOf<MapSearchInfo>()
        //      val POIItem = tmapdata.findAllPOI("SKT타워")


        //동기화 필요 //뒤로가기 이슈
        binding.etWriteMapSearchStart.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
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


        // Inflate the layout for this fragment
        return root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WriteMapSearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}