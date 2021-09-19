package com.example.charo_android.presentation.ui.charo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.charo_android.MainActivity
import com.example.charo_android.R
import com.example.charo_android.data.api.ApiService
import com.example.charo_android.data.model.response.ResponseMyPageLikeData
import com.example.charo_android.data.model.response.ResponseMyPageNewData
import com.example.charo_android.data.SavedPost
import com.example.charo_android.data.WrittenPost
import com.example.charo_android.databinding.FragmentCharoBinding
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharoFragment : Fragment() {

    private lateinit var charoViewModel: CharoViewModel
    private var _binding: FragmentCharoBinding? = null
    private lateinit var userId: String
    private lateinit var likeData: ResponseMyPageLikeData.Data
    private lateinit var newData: ResponseMyPageNewData.Data

    private val tabIconList = arrayListOf(
        R.drawable.ic_write_active,
        R.drawable.ic_save_5_active
    )

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        charoViewModel =
            ViewModelProvider(this).get(CharoViewModel::class.java)

        _binding = FragmentCharoBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textCharo
//        charoViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        // userId 알아오기
        val userId: String = (activity as MainActivity).getUserId()

        // 서버통신 구현
        init(userId)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun init(userId: String) {
        val likeCall: Call<ResponseMyPageLikeData> =
            ApiService.myPageViewLikeService.getMyPageLike(userId)
        val newCall: Call<ResponseMyPageNewData> =
            ApiService.myPageViewNewService.getMyPageNew(userId)

        likeCall.enqueue(object : Callback<ResponseMyPageLikeData> {
            override fun onResponse(
                call: Call<ResponseMyPageLikeData>,
                response: Response<ResponseMyPageLikeData>
            ) {
                if (response.isSuccessful) {
                    Log.d("server connect", "success")

                    likeData = response.body()!!.data

                    binding.tvCharoNickname.text = "${likeData?.userInformation?.nickname} 드라이버님"
                    binding.tvCharoFollowingCount.text =
                        likeData?.userInformation?.following?.toString()
                    binding.tvCharoFollowerCount.text =
                        likeData?.userInformation?.follower?.toString()

                    val mContext = activity!!
                    Glide.with(mContext)
                        .load(likeData?.userInformation?.profileImage)
                        .override(56, 56)
                        .circleCrop()
                        .into(binding.imgCharoProfile)

                    newCall.enqueue(object: Callback<ResponseMyPageNewData> {
                        override fun onResponse(
                            call: Call<ResponseMyPageNewData>,
                            response: Response<ResponseMyPageNewData>
                        ) {
                            if (response.isSuccessful) {
                                Log.d("server connect", "success")

                                newData = response.body()!!.data

                                initCharoViewPagerAdapter(likeData, newData)
                            } else {
                                Log.d("server connect", "fail")
                                Log.d("server connect", "${response.errorBody()}")
                                Log.d("server connect", "${response.message()}")
                                Log.d("server connect", "${response.code()}")
                                Log.d("server connect", "${response.raw().request.url}")
                            }
                        }
                        override fun onFailure(call: Call<ResponseMyPageNewData>, t: Throwable) {
                            Log.d("server connect", "error:${t.message}")
                        }
                    })
                } else {
                    Log.d("server connect", "fail")
                    Log.d("server connect", "${response.errorBody()}")
                    Log.d("server connect", "${response.message()}")
                    Log.d("server connect", "${response.code()}")
                    Log.d("server connect", "${response.raw().request.url}")
                }
            }

            override fun onFailure(call: Call<ResponseMyPageLikeData>, t: Throwable) {
                Log.d("server connect", "error:${t.message}")
            }
        })
    }

    private fun initCharoViewPagerAdapter(
        likeData: ResponseMyPageLikeData.Data,
        newData: ResponseMyPageNewData.Data
    ) {
        val likeWrittenPost: List<WrittenPost> = likeData.writtenPost
        val likeSavedPost: List<SavedPost> = likeData.savedPost
        val newWrittenPost: List<WrittenPost> = newData.writtenPost
        val newSavedPost: List<SavedPost> = newData.savedPost
        var charoLikeWrittenPost = mutableListOf<MyCharoInfo>()
        var charoLikeSavedPost = mutableListOf<MyCharoInfo>()
        var charoNewWrittenPost = mutableListOf<MyCharoInfo>()
        var charoNewSavedPost = mutableListOf<MyCharoInfo>()

        for (i in likeWrittenPost.indices) {
            charoLikeWrittenPost.add(
                MyCharoInfo(
                    day = likeWrittenPost[i].day,
                    favoriteNum = likeWrittenPost[i].favoriteNum,
                    image = likeWrittenPost[i].image,
                    month = likeWrittenPost[i].month,
                    postId = likeWrittenPost[i].postId,
                    saveNum = likeWrittenPost[i].saveNum,
                    tags = likeWrittenPost[i].tags,
                    title = likeWrittenPost[i].title,
                    year = likeWrittenPost[i].year
                )
            )
        }

        for (i in likeSavedPost.indices) {
            charoLikeSavedPost.add(
                MyCharoInfo(
                    day = likeSavedPost[i].day,
                    favoriteNum = likeSavedPost[i].favoriteNum,
                    image = likeSavedPost[i].image,
                    month = likeSavedPost[i].month,
                    postId = likeSavedPost[i].postId,
                    saveNum = likeSavedPost[i].saveNum,
                    tags = likeSavedPost[i].tags,
                    title = likeSavedPost[i].title,
                    year = likeSavedPost[i].year
                )
            )
        }

        for(i in newWrittenPost.indices) {
            charoNewWrittenPost.add(
                MyCharoInfo(
                    day = newWrittenPost[i].day,
                    favoriteNum = newWrittenPost[i].favoriteNum,
                    image = newWrittenPost[i].image,
                    month = newWrittenPost[i].month,
                    postId = newWrittenPost[i].postId,
                    saveNum = newWrittenPost[i].saveNum,
                    tags = newWrittenPost[i].tags,
                    title = newWrittenPost[i].title,
                    year = newWrittenPost[i].year
                )
            )
        }

        for (i in newSavedPost.indices) {
            charoNewSavedPost.add(
                MyCharoInfo(
                    day = newSavedPost[i].day,
                    favoriteNum = newSavedPost[i].favoriteNum,
                    image = newSavedPost[i].image,
                    month = newSavedPost[i].month,
                    postId = newSavedPost[i].postId,
                    saveNum = newSavedPost[i].saveNum,
                    tags = newSavedPost[i].tags,
                    title = newSavedPost[i].title,
                    year = newSavedPost[i].year
                )
            )
        }

        binding.apply {
            val charoViewPagerAdapter = CharoFragmentStateAdapter(requireActivity())
            with(charoViewPagerAdapter) {
                fragmentList = listOf(MyCharoFragment(charoLikeWrittenPost, charoNewWrittenPost), MyCharoFragment(charoLikeSavedPost, charoNewSavedPost))
            }
            with(binding.viewpagerCharo) {
                adapter = charoViewPagerAdapter
                isUserInputEnabled = false
            }

            TabLayoutMediator(binding.tabCharo, binding.viewpagerCharo) { tab, position ->
                tab.setIcon(tabIconList[position])
            }.attach()
        }
    }
}