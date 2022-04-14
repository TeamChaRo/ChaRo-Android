package com.charo.android.presentation.ui.write

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.*
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.charo.android.R
import com.charo.android.data.model.response.write.ResponseWriteData
import com.charo.android.data.model.write.MapSearchInfo
import com.charo.android.databinding.FragmentWriteMapSearchBinding
import com.charo.android.presentation.util.Define
import com.charo.android.presentation.util.SharedInformation
import com.skt.Tmap.TMapData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class WriteMapSearchFragment : Fragment() {

    companion object {
        fun newInstance() = WriteMapSearchFragment()
    }

    private var _binding: FragmentWriteMapSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var writeMapSearchAdapter: WriteMapSearchAdapter

    private lateinit var locationFlag: String
    var mapSearchList = mutableListOf<MapSearchInfo>()

    private val sharedViewModel: WriteSharedViewModel by activityViewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
                WriteSharedViewModel() as T
        }
    }

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

        locationFlag = sharedViewModel.locationFlag.value.toString()

        when (locationFlag) {
            Define().LOCATION_FLAG_START -> {
                binding.etWriteMapSearch.hint = getString(R.string.start)
                if (!TextUtils.isEmpty(sharedViewModel.startAddress.value)){
                    binding.etWriteMapSearch.setText(sharedViewModel.startAddress.value)
                }
            }
            Define().LOCATION_FLAG_MID_FRST -> {
                binding.etWriteMapSearch.hint = getString(R.string.middle)
                if (!TextUtils.isEmpty(sharedViewModel.midFrstAddress.value)){
                    binding.etWriteMapSearch.setText(sharedViewModel.midFrstAddress.value)
                }
            }
            Define().LOCATION_FLAG_MID_SEC -> {
                binding.etWriteMapSearch.hint = getString(R.string.middle)
                if (!TextUtils.isEmpty(sharedViewModel.midSecAddress.value)){
                    binding.etWriteMapSearch.setText(sharedViewModel.midSecAddress.value)
                }
            }
            Define().LOCATION_FLAG_END -> {
                binding.etWriteMapSearch.hint = getString(R.string.end)
                if (!TextUtils.isEmpty(sharedViewModel.endAddress.value)){
                    binding.etWriteMapSearch.setText(sharedViewModel.endAddress.value)
                }
            }
        }

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
        var preText : String = ""

        binding.etWriteMapSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                preText = s.toString()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if(preText == s.toString()){
                    return
                }
                val inputText = s.toString()
                // 관련 검색어
                if(inputText.isNotEmpty()) {
                    binding.textResultSearch.text = getString(R.string.write_auto_search)

                    tmapdata.findTitlePOI(inputText) { poiItem ->
                        writeMapSearchAdapter.clearItem()

                        for (i in 0 until poiItem.size) {
                            val item = poiItem[i]

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

                        GlobalScope.launch(Dispatchers.Main) {
                            writeMapSearchAdapter.notifyDataSetChanged()
                        }
                    }

                // 최근 검색어
                }else{
                    getReadHistory(mapSearchList)
                }
            }
        })

        return root
    }

    fun getReadHistory(list: MutableList<MapSearchInfo>) {
        binding.textResultSearch.text = getString(R.string.write_recent_search)

        val userEmail = SharedInformation.getEmail(requireActivity())
        val call: Call<ResponseWriteData> =
            com.charo.android.data.api.ApiService.writeViewService.getReadHistory(userEmail)
        call.enqueue(object : Callback<ResponseWriteData> {
            override fun onResponse(
                call: Call<ResponseWriteData>,
                response: Response<ResponseWriteData>
            ) {
                if (response.isSuccessful) {
                    Timber.d("server connect : WriteMap search success")

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
                    Timber.d("server connect : WriteMap search error")
                    Timber.d("server connect : WriteMap search ${response.errorBody()}")
                    Timber.d("server connect : WriteMap search ${response.message()}")
                    Timber.d("server connect : WriteMap search ${response.code()}")
                    Timber.d(
                        "server connect : WriteMap search ${response.raw().request.url}"
                    )
                }
            }

            override fun onFailure(call: Call<ResponseWriteData>, t: Throwable) {
                Timber.d("server connect : WriteMap search error: ${t.message}")
            }
        })
    }
}