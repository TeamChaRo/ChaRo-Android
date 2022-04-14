package com.charo.android.presentation.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.charo.android.data.model.detailold.*
import com.charo.android.hidden.Hidden
import com.charo.android.presentation.util.enqueueUtil
import com.skt.Tmap.TMapInfo
import com.skt.Tmap.TMapMarkerItem
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapPolyLine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class DetailViewModel : ViewModel() {
    private var _postId = MutableLiveData<Int>()
    val postId: LiveData<Int> get() = _postId

    private var _detailData = MutableLiveData<DetailData>(null)
    val detailData: LiveData<DetailData> get() = _detailData

    private var _tMapInfo = MutableLiveData<TMapInfo>(null)
    val tMapInfo: LiveData<TMapInfo> get() = _tMapInfo

    private var _pointList = MutableLiveData<ArrayList<TMapPoint>>(null)
    val pointList: LiveData<ArrayList<TMapPoint>> get() = _pointList

    private var _markerList = MutableLiveData<ArrayList<TMapMarkerItem>>(null)
    val markerList: LiveData<ArrayList<TMapMarkerItem>> get() = _markerList

    private var _lineList = MutableLiveData<ArrayList<TMapPolyLine>>(null)
    val lineList: LiveData<ArrayList<TMapPolyLine>> get() = _lineList

    private var _userData = MutableLiveData<List<UserData>>(null)
    val userData: LiveData<List<UserData>> get() = _userData

    fun setPostId(postId: Int) {
        _postId.value = postId
    }

    fun getData(postId: Int, title: String, date: String, region: String) {
        val call: Call<ResponseDetailData> =
            com.charo.android.data.api.ApiService.detailViewService.getDetail(Hidden.userId, postId)
        call.enqueue(object : Callback<ResponseDetailData> {
            override fun onResponse(
                call: Call<ResponseDetailData>,
                response: Response<ResponseDetailData>
            ) {
                if (response.isSuccessful) {
                    Timber.d("server connect : DetailView success")
                    val data = response.body()?.data!!
                    _detailData.value = DetailData(postId, title, date, region, data)
                } else {
                    Timber.d("server connect : DetailView error")
                    Timber.d("server connect : DetailView ${response.errorBody()}")
                    Timber.d("server connect : DetailView ${response.message()}")
                    Timber.d("server connect : DetailView ${response.code()}")
                    Timber.d(
                        "server connect : DetailView ${response.raw().request.url}"
                    )
                }
            }

            override fun onFailure(call: Call<ResponseDetailData>, t: Throwable) {
                Timber.d("server connect : DetailView error: ${t.message}")
            }
        })
    }

    fun addImageAtFront(imageUrl: String) {
        _detailData.value?.data?.images?.add(0, imageUrl)
    }

    fun postLike(postId: Int) {
        val call: Call<ResponseDetailLikeAndSave> =
            com.charo.android.data.api.ApiService.detailViewService.postDetailLike(
                RequestDetailLikeAndSave(
                    Hidden.userId,
                    postId
                )
            )
        call.enqueue(object : Callback<ResponseDetailLikeAndSave> {
            override fun onResponse(
                call: Call<ResponseDetailLikeAndSave>,
                response: Response<ResponseDetailLikeAndSave>
            ) {
                if (response.isSuccessful) {
                    Timber.d("server connect : DetailView Like success")
                } else {
                    Timber.d("server connect : DetailView Like error")
                    Timber.d("server connect : DetailView Like ${response.errorBody()}")
                    Timber.d("server connect : DetailView Like ${response.message()}")
                    Timber.d("server connect : DetailView Like ${response.code()}")
                    Timber.d(
                        "server connect : DetailView Like ${response.raw().request.url}"
                    )
                }
            }

            override fun onFailure(call: Call<ResponseDetailLikeAndSave>, t: Throwable) {
                Timber.d("server connect : DetailView Like error: ${t.message}")
            }
        })
    }

    fun postSave(postId: Int) {
        val call: Call<ResponseDetailLikeAndSave> =
            com.charo.android.data.api.ApiService.detailViewService.postDetailSave(
                RequestDetailLikeAndSave(
                    Hidden.userId,
                    postId
                )
            )
        call.enqueue(object : Callback<ResponseDetailLikeAndSave> {
            override fun onResponse(
                call: Call<ResponseDetailLikeAndSave>,
                response: Response<ResponseDetailLikeAndSave>
            ) {
                if (response.isSuccessful) {
                    Timber.d("server connect : DetailView Save success")
                } else {
                    Timber.d("server connect : DetailView Save error")
                    Timber.d("server connect : DetailView Save ${response.errorBody()}")
                    Timber.d("server connect : DetailView Save ${response.message()}")
                    Timber.d("server connect : DetailView Save ${response.code()}")
                    Timber.d(
                        "server connect : DetailView Save ${response.raw().request.url}"
                    )
                }
            }

            override fun onFailure(call: Call<ResponseDetailLikeAndSave>, t: Throwable) {
                Timber.d("server connect : DetailView Save error: ${t.message}")
            }
        })
    }

    fun getLikes(postId: Int) {
        val call = com.charo.android.data.api.ApiService.detailViewService.getLikes(postId, Hidden.userId)
        call.enqueueUtil(
            onSuccess = {
                Timber.d("Current View DetailView Likes")
                Timber.d(
                    "server connect : DetailView Likes List size ${it.data.size.toString()}"
                )
                _userData.value = it.data
            }
        )
    }
}