package com.charo.android.presentation.ui.write

import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charo.android.data.model.write.WriteImgInfo
import com.charo.android.domain.model.detailpost.DetailPost
import com.charo.android.domain.model.detailpost.User
import com.charo.android.domain.usecase.detailpost.DeleteDetailPostUseCase
import com.charo.android.domain.usecase.detailpost.GetDetailPostLikeUserListUseCase
import com.charo.android.domain.usecase.detailpost.GetDetailPostUseCase
import com.charo.android.domain.usecase.follow.PostFollowUseCase
import com.charo.android.domain.usecase.interaction.PostLikeUseCase
import com.charo.android.domain.usecase.interaction.PostSaveUseCase
import com.charo.android.presentation.util.SingleLiveEvent
import com.charo.android.presentation.util.ThemeUtil
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class WriteSharedViewModel(
    private val getDetailPostUseCase: GetDetailPostUseCase,
    private val postLikeUseCase: PostLikeUseCase,
    private val postSaveUseCase: PostSaveUseCase,
    private val getDetailPostLikeUserListUseCase: GetDetailPostLikeUserListUseCase,
    private val postFollowUseCase: PostFollowUseCase,
    private val deleteDetailPostUseCase: DeleteDetailPostUseCase
) : ViewModel() {
    // TODO: Implement the ViewModel
    fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply { setValue(initialValue) }

    //WriteFragment
    val title = MutableLiveData<String>().default("")
    val province = MutableLiveData<String>().default("")            //경기도
    val region = MutableLiveData<String>().default("")              //수원
    val warning = MutableLiveData<ArrayList<MultipartBody.Part>>()
    val warningUI = MutableLiveData<ArrayList<String>>()            //["highway", "mountainRoad"]
    val theme = MutableLiveData<ArrayList<String>>()                          //["summer", "sea"]
    val isParking = MutableLiveData<Boolean>().default(false)       //true
    val parkingDesc = MutableLiveData<String>().default("")
    val courseDesc = MutableLiveData<String>().default("")
    val imageMultiPart = MutableLiveData<ArrayList<MultipartBody.Part>>()       //서버에 보내기
    val imageUriRecyclerView = MutableLiveData<MutableList<WriteImgInfo>>()     //뷰에 보여주기

    //writeMapFragment
    val locationFlag = MutableLiveData<String>() //출발 경유 도착
    val latitude = MutableLiveData<Double>().default(0.0)     //위도
    val longitude = MutableLiveData<Double>().default(0.0)    //경도

    val course = MutableLiveData<ArrayList<MultipartBody.Part>>()   //서버에 보낼 경로
    var startAddress = MutableLiveData<String>().default("")
    var midFrstAddress = MutableLiveData<String>().default("")
    var midSecAddress = MutableLiveData<String>().default("")
    var endAddress = MutableLiveData<String>().default("")
    var startLat = MutableLiveData<Double>().default(0.0)
    var startLong = MutableLiveData<Double>().default(0.0)
    var midFrstLat = MutableLiveData<Double>().default(0.0)
    var midFrstLong = MutableLiveData<Double>().default(0.0)
    var midSecLat = MutableLiveData<Double>().default(0.0)
    var midSecLong = MutableLiveData<Double>().default(0.0)
    var endLat = MutableLiveData<Double>().default(0.0)
    var endLong = MutableLiveData<Double>().default(0.0)

    // DetailPostFragment
    var postId: Int = -1
    var userEmail: String = ""
    var createdAt = MutableLiveData<String>().default("")
    var isAuthorFlag = MutableLiveData<Boolean>().default(false)
    var profileImage = MutableLiveData<String>().default("")
    var author = MutableLiveData<String>().default("")
    var authorEmail = MutableLiveData<String>().default("")
    var imageStringViewPager = MutableLiveData<MutableList<String>>()
    var themes = MutableLiveData<ArrayList<String>>()
    var warnings = MutableLiveData<List<Boolean>>()
    var isFavorite = MutableLiveData<Int>().default(0)
    var likesCount = MutableLiveData<Int>().default(0)
    var isStored = MutableLiveData<Int>().default(0)
    var isStoredFromServer: Int = 0
    var courseDetail = MutableLiveData<List<DetailPost.Course>>()
    var detailArea = MutableLiveData<String>().default("")

    // DetailPostLikeListFragment
    private var _likeUserList = MutableLiveData<List<User>>()
    val likeUserList: LiveData<List<User>> get() = _likeUserList
    var resultUserEmail: String = ""
    var resultUserFollow: Boolean = false

    // DetailPostImageFragment
    var imageIndex = 0

    // 게시물 삭제 성공여부
    private var _deleteSuccess = SingleLiveEvent<Boolean>()
    val deleteSuccess: LiveData<Boolean> get() = _deleteSuccess

    // 게시물 수정인지 여부
    private var _editFlag = SingleLiveEvent<Boolean>()
    val editFlag: LiveData<Boolean> get() = _editFlag
    private var _editMapFlag = SingleLiveEvent<Boolean>()
    val editMapFlag: LiveData<Boolean> get() = _editMapFlag

    fun initData() {
        title.value = ""
        province.value = ""            //경기도
        region.value = ""            //수원
        warning.value =
            ArrayList<MultipartBody.Part>()                          //["highway", "mountainRoad"]
        theme.value = ArrayList<String>()                            //["summer", "sea"]
        isParking.value = false      //true
        parkingDesc.value = ""
        courseDesc.value = ""
        imageMultiPart.value = ArrayList<MultipartBody.Part>()

        //writeMapFragment
        locationFlag.value = "" //출발 경유 도착
        latitude.value = 0.0     //위도
        longitude.value = 0.0    //경도

        startAddress.value = ""
        midFrstAddress.value = ""
        midSecAddress.value = ""
        endAddress.value = ""
        startLat.value = 0.0
        startLong.value = 0.0
        midFrstLat.value = 0.0
        midFrstLong.value = 0.0
        midSecLat.value = 0.0
        midSecLong.value = 0.0
        endLat.value = 0.0
        endLong.value = 0.0

    }

    fun getDetailPostData() {
        viewModelScope.launch {
            kotlin.runCatching {
                getDetailPostUseCase(userEmail, postId)
            }.onSuccess {
                // 단순 값 할당의 과정입니다 ...
                title.value = it.title
                region.value = it.region
                imageStringViewPager.value = it.images.toMutableList()
                province.value = it.province
                isParking.value = it.isParking
                parkingDesc.value = it.parkingDesc
                courseDesc.value = it.courseDesc
                themes.value = ArrayList(it.themes)
                warnings.value = it.warnings
                author.value = it.author
                authorEmail.value = it.authorEmail
                isAuthorFlag.value = it.isAuthor
                profileImage.value = it.profileImage
                likesCount.value = it.likesCount
                isFavorite.value = if (it.isFavorite == 0) 0 else 1
                isStored.value = if (it.isStored == 0) 0 else 1
                isStoredFromServer = if (it.isStored == 0) 0 else 1
                courseDetail.value = it.course

                // createdAt 파싱
//                val format = SimpleDateFormat("YYYY-MM-DD'T'hh:mm:ss.SSS'Z'", Locale.KOREAN)
//                val date = format.parse(it.createdAt)
//                Timber.tag("date").i(date!!.toString())
//                val formatter = SimpleDateFormat("yyyy년 MM월 dd일",Locale.KOREAN)
//                date?.let {
//                    createdAt.value = formatter.format(date)
//                }
                val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
                format.timeZone = TimeZone.getTimeZone("GMT")
                val date = format.parse(it.createdAt)
                date?.let {
                    Timber.tag("date").i(date.toString())
                    val formatter = SimpleDateFormat("yyyy년 MM월 dd일", Locale.KOREA)
                    createdAt.value = formatter.format(date)
                }
                // 지역 분기처리
                when (it.province) {
                    "특별시", "광역시" -> {
                        detailArea.value = it.region + it.province
                    }
                    else -> {
                        detailArea.value = "${it.province} ${it.region}"
                    }
                }
            }.onFailure {
                Timber.tag("getDetailPostData").e(it)
            }
        }
    }

    fun postLike() {
        viewModelScope.launch {
            kotlin.runCatching {
                postLikeUseCase(userEmail, postId)
            }.onSuccess {
                if (it) {
                    isFavorite.value = isFavorite.value?.plus(1)?.rem(2)
                    updateLikesCount()
                }
            }.onFailure {
                Timber.tag("postLike").e(it)
            }
        }
    }

    fun postSave() {
        viewModelScope.launch {
            kotlin.runCatching {
                postSaveUseCase(userEmail, postId)
            }.onSuccess {
                if (it) {
                    isStored.value = isStored.value?.plus(1)?.rem(2)
                    Timber.tag("isStoredFromServer").i(isStoredFromServer.toString())
                    Timber.tag("isStored").i(isStored.value.toString())
                }
            }.onFailure {
                Timber.tag("postSave").e(it)
            }
        }
    }

    private fun updateLikesCount() {
        when (isFavorite.value) {
            0 -> likesCount.value = likesCount.value?.minus(1)
            else -> likesCount.value = likesCount.value?.plus(1)
        }
    }

    fun getDetailPostLikeUserListData() {
        viewModelScope.launch {
            kotlin.runCatching {
                getDetailPostLikeUserListUseCase(postId, userEmail)
            }.onSuccess {
                _likeUserList.value = it
            }.onFailure {
                Timber.tag("getDetailPostLikeUserListData").e(it)
            }
        }
    }

    fun postFollow(otherUserEmail: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                postFollowUseCase(userEmail, otherUserEmail)
            }.onFailure {
                Timber.tag("postFollow").e(it)
            }
        }
    }

    fun deleteDetailPost() {
        viewModelScope.launch {
            kotlin.runCatching {
                deleteDetailPostUseCase(postId)
            }.onSuccess {
                _deleteSuccess.value = it
            }.onFailure {
                Timber.tag("deleteDetailPost").e(it)
            }
        }
    }

    fun initEditFlag() {
        _editFlag.value = true
        _editMapFlag.value = true
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    fun initEditData() {
        // note: 서버에서 온 테마 데이터는 한글인 반면, 작성하기에서는 테마 데이터를 영어로 처리하고 있기 때문에 ThemeUtil 사용해 변환시켜 준다.
        theme.value = ArrayList(themes.value?.map { ThemeUtil().themeMap[it] as String })
        // note:
        // 서버에서 온 이미지 데이터는 List<String>인 반면, 작성하기에서는 MutableList<Uri>와 ArrayList<MultipartBody.Part>이므로 변환이 필요하다.
        // Uri -> MultipartBody.Part 로의 변환은 WriteFragment 에서 수행하기 때문에, ViewModel 에서는 String -> Uri 변환만 수행한다.
        imageUriRecyclerView.value =
            imageStringViewPager.value?.map { WriteImgInfo(it.toUri(), false) }?.toMutableList()
    }

    fun initEditMapData() {
        Timber.tag("initEditMapData()").i(courseDetail.value?.toString())
        courseDetail.value?.let { courseDetail ->
            with(courseDetail) {
                when (this.size) {
                    2 -> {
                        startAddress.value = this[0].address
                        startLat.value = this[0].latitude
                        startLong.value = this[0].longitude
                        endAddress.value = this[1].address
                        endLat.value = this[1].latitude
                        endLong.value = this[1].longitude
                    }
                    3 -> {
                        startAddress.value = this[0].address
                        startLat.value = this[0].latitude
                        startLong.value = this[0].longitude
                        midFrstAddress.value = this[1].address
                        midFrstLat.value = this[1].latitude
                        midFrstLong.value = this[1].longitude
                        endAddress.value = this[2].address
                        endLat.value = this[2].latitude
                        endLong.value = this[2].longitude
                    }
                }
            }
        }
        _editMapFlag.value = false
    }

    fun updateLikeUserList() {
        _likeUserList.value = likeUserList.value?.map {
            if(it.userEmail == resultUserEmail) {
                it.copy(isFollow = resultUserFollow)
            } else {
                it.copy()
            }
        }?.toMutableList()
    }
}