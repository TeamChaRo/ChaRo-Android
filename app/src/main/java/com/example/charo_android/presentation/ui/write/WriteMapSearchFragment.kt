package com.example.charo_android.presentation.ui.write

import android.content.Context
import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.Spannable
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
import android.text.SpannableString
import android.text.style.ForegroundColorSpan

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
                    "" -> { binding.etWriteMapSearch.hint = getString(R.string.start)}
                    else -> {binding.etWriteMapSearch.setText(sharedViewModel.startAddress.value)}
                }
            }
            "4" -> {
                sharedViewModel.resultLocation.value = "도착지로 설정"
                when (sharedViewModel.endAddress.value){
                    "" -> { binding.etWriteMapSearch.hint = getString(R.string.end)}
                    else -> {binding.etWriteMapSearch.setText(sharedViewModel.endAddress.value)}
                }
            }
            else -> {
                sharedViewModel.resultLocation.value = "경유지로 설정"
                when (sharedViewModel.mid1Address.value){
                    "" -> { binding.etWriteMapSearch.hint = "경유지를 입력해주세요"}
                    else -> {binding.etWriteMapSearch.setText(sharedViewModel.mid1Address.value)}
                }
            }
        }
//        binding.etWriteMapSearchStart.hint = text
//        sharedViewModel.resultLocation.value = text

        binding.imgWriteMapSearchBack.setOnClickListener {
            writeShareActivity?.onBackPressed()
        }

        binding.deleteWord.setOnClickListener {
            binding.etWriteMapSearch.setText("")
        }

        writeMapSearchAdapter =  WriteMapSearchAdapter()

        binding.recyclerviewWriteMapSearch.adapter = writeMapSearchAdapter

        getReadHistory(mapSearchList)

        val tmapdata = TMapData()
        binding.etWriteMapSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
               Handler(Looper.getMainLooper()).postDelayed({
                    // 관련 검색어
                    if(binding.etWriteMapSearch.text.isNotEmpty()) {
                        binding.textResultSearch.text = getString(R.string.write_auto_search)

                        tmapdata.findTitlePOI(binding.etWriteMapSearch.text.toString()) { poiItem ->
                            writeMapSearchAdapter.clearItem()

                            for (i in 0 until poiItem.size) {
                                val item = poiItem[i]

                                val inputText = binding.etWriteMapSearch.text.toString()
                                val outputText = item.poiName
                                val spannableString = SpannableString(outputText)

                                val start: Int = outputText.indexOf(inputText)
                                val end: Int = start + inputText.length

                                if(start > -1 && end > start){
                                    spannableString.setSpan(ForegroundColorSpan(Color.parseColor("#0f6fff")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                                }

                                mapSearchList.add(
                                    MapSearchInfo(
                                        locationName = spannableString,
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
            ApiService.writeViewService.getReadHistory(Hidden.otherUserEmail)
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
                            val spannableString = SpannableString(item.title)

                            list.add(
                                MapSearchInfo(
                                    locationName = spannableString,
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