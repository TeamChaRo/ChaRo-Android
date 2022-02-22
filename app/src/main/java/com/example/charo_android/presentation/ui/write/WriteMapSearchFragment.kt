package com.example.charo_android.presentation.ui.write

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import com.example.charo_android.R
import com.example.charo_android.data.MapSearchInfo
import com.example.charo_android.data.api.ApiService
import com.example.charo_android.data.model.response.ResponseWriteData
import com.example.charo_android.databinding.FragmentWriteMapSearchBinding
import com.example.charo_android.hidden.Hidden
import com.skt.Tmap.TMapData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WriteMapSearchFragment : Fragment() {


//    inner class addressListAdapterToList{
//        fun getAddressId(address: Room){
//
//        }
//    }

    companion object {
        fun newInstance() = WriteMapSearchFragment()
    }

    private lateinit var writeMapSearchAdapter: WriteMapSearchAdapter
    lateinit var userId: String
    lateinit var nickName: String

    private lateinit var locationFlag: String
    var mapSearchList = mutableListOf<MapSearchInfo>()

    private val sharedViewModel: WriteSharedViewModel by activityViewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
                WriteSharedViewModel() as T
        }
    }
    private var _binding: FragmentWriteMapSearchBinding? = null

    private val binding get() = _binding!!

    var writeShareActivity: WriteShareActivity? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        writeShareActivity = context as WriteShareActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWriteMapSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root
        Log.d("jjj", "success go searchActivity")

        userId = sharedViewModel.userId.value.toString()
        nickName = sharedViewModel.nickName.value.toString()
        locationFlag = sharedViewModel.locationFlag.value.toString()

        Log.d("uuuwritesear", userId)
        Log.d("uuuwritesear", nickName)
        Log.d("uuuwritesear", locationFlag)


        when (locationFlag) {
            "1" -> {
                sharedViewModel.resultLocation.value = "출발지로 설정"
                when (sharedViewModel.startAddress.value){
                    "" -> { binding.etWriteMapSearchStart.hint = "출발지를 입력해주세요"}
                    else -> {binding.etWriteMapSearchStart.setText(sharedViewModel.startAddress.value)}
                }
            }
            "4" -> {
                sharedViewModel.resultLocation.value = "도착지로 설정"
                when (sharedViewModel.endAddress.value){
                    "" -> { binding.etWriteMapSearchStart.hint = "도착지를 입력해주세요"}
                    else -> {binding.etWriteMapSearchStart.setText(sharedViewModel.endAddress.value)}
                }
            }
            else -> {
                sharedViewModel.resultLocation.value = "경유지로 설정"
                when (sharedViewModel.mid1Address.value){
                    "" -> { binding.etWriteMapSearchStart.hint = "경유지를 입력해주세요"}
                    else -> {binding.etWriteMapSearchStart.setText(sharedViewModel.mid1Address.value)}
                }
            }
        }
//        binding.etWriteMapSearchStart.hint = text
//        sharedViewModel.resultLocation.value = text

        binding.imgWriteMapSearchBack.setOnClickListener {
            writeShareActivity?.onBackPressed()
        }

        binding.deleteWord.setOnClickListener {
            binding.etWriteMapSearchStart.setText("")
        }

        // 1. 우리가 사용할 어뎁터의 초기 값을 넣어준다
        writeMapSearchAdapter =  WriteMapSearchAdapter()

//        WriteMapSearchAdapter(locationFlag, text, userId, nickName)

        // 2. RecyclerView 에 어뎁터를 우리가 만든 어뎁터로 만들기
        binding.recyclerviewWriteMapSearch.adapter = writeMapSearchAdapter

        val tmapdata = TMapData()

        getReadHistory(mapSearchList)
        binding.etWriteMapSearchStart.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Log.d("myLog", "before")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d("myLog", "changing")
            }

            override fun afterTextChanged(s: Editable?) {
                Log.d("myLog", "after")
                Handler(Looper.getMainLooper()).postDelayed({
                    // 관련 검색어
                    if(binding.etWriteMapSearchStart.text.isNotEmpty()) {
                        binding.textResultSearch.text = getString(R.string.write_auto_search)

                        tmapdata.findTitlePOI(binding.etWriteMapSearchStart.text.toString()) { poiItem ->
                            writeMapSearchAdapter.clearItem()

                            for (i in 0 until poiItem.size) {
                                val item = poiItem[i]

                                mapSearchList.add(
                                    MapSearchInfo(
                                        locationName = item.poiName,
                                        locationAddress = item.poiAddress.replace("null", ""),
                                        date = ""
                                    )

                                )
                            }
                            writeMapSearchAdapter.userList = mapSearchList
                        }
                        writeMapSearchAdapter.notifyDataSetChanged()

                    // 최근 검색어
                    }else{
                        getReadHistory(mapSearchList)
                    }
                }, 500)
            }
        })

        return root
    }

    fun getReadHistory(list: MutableList<MapSearchInfo>) {
        binding.textResultSearch.text = getString(R.string.write_recent_search)

        val call: Call<ResponseWriteData> =
            ApiService.writeViewService.getReadHistory(Hidden.userEmail)
        call.enqueue(object : Callback<ResponseWriteData> {
            override fun onResponse(
                call: Call<ResponseWriteData>,
                response: Response<ResponseWriteData>
            ) {
                if (response.isSuccessful) {
                    Log.d("server connect : WriteMap search", "success")

                    writeMapSearchAdapter.clearItem()

                    val data = response.body()?.data

                    if (data != null) {
                        for (i in 0 until data.size) {
                            val item = data[i]

                            list.add(
                                MapSearchInfo(
                                    locationName = item.title,
                                    locationAddress = item.address,
                                    date = "${item.month}.${item.day}"
                                )
                            )
                        }
                        writeMapSearchAdapter.userList = list
                        writeMapSearchAdapter.notifyDataSetChanged()
                    }
                } else {
                    Log.d("server connect : WriteMap search", "error")
                    Log.d("server connect : WriteMap search", "$response.errorBody()")
                    Log.d("server connect : WriteMap search", response.message())
                    Log.d("server connect : WriteMap search", "${response.code()}")
                    Log.d(
                        "server connect : WriteMap search",
                        "${response.raw().request.url}"
                    )
                }
            }

            override fun onFailure(call: Call<ResponseWriteData>, t: Throwable) {
                Log.d("server connect : WriteMap search", "error: ${t.message}")
            }
        })
    }
}