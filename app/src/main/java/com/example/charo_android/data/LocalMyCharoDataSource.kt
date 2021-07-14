package com.example.charo_android.data

import com.example.charo_android.R
import com.example.charo_android.ui.charo.MyCharoInfo

class LocalMyCharoDataSource : MyCharoDataSource {
    override fun fetchData(): MutableList<MyCharoInfo> {
        return mutableListOf(
            MyCharoInfo(
                image = R.drawable.mask_group_reverse,
                title = "제목1",
                hashtag1 = "1의 tag1",
                hashtag2 = "1의 tag2",
                hashtag3 = "1의 tag3",
                date = "2021.07.10",
                likeCount = 1,
                saveCount = 1,
                postId = "1"
            ),
            MyCharoInfo(
                image = R.drawable.mask_group_reverse,
                title = "제목2",
                hashtag1 = "2의 tag1",
                hashtag2 = "2의 tag2",
                hashtag3 = "2의 tag3",
                date = "2021.07.11",
                likeCount = 2,
                saveCount = 2,
                postId = "2"
            ),
                MyCharoInfo(
                    image = R.drawable.mask_group_reverse,
                    title = "제목1",
                    hashtag1 = "1의 tag1",
                    hashtag2 = "1의 tag2",
                    hashtag3 = "1의 tag3",
                    date = "2021.07.10",
                    likeCount = 1,
                    saveCount = 1,
                    postId = "1"
                ),
                MyCharoInfo(
                    image = R.drawable.mask_group_reverse,
                    title = "제목2",
                    hashtag1 = "2의 tag1",
                    hashtag2 = "2의 tag2",
                    hashtag3 = "2의 tag3",
                    date = "2021.07.11",
                    likeCount = 2,
                    saveCount = 2,
                    postId = "2"
                ),
                MyCharoInfo(
                    image = R.drawable.mask_group_2,
                    title = "제목3",
                    hashtag1 = "3의 tag1",
                    hashtag2 = "3의 tag2",
                    hashtag3 = "3의 tag3",
                    date = "2021.07.12",
                    likeCount = 3,
                    saveCount = 3,
                    postId = "3"
                ),
                MyCharoInfo(
                    image = R.drawable.mask_group_reverse,
                    title = "제목4",
                    hashtag1 = "4의 tag1",
                    hashtag2 = "4의 tag2",
                    hashtag3 = "4의 tag3",
                    date = "2021.07.13",
                    likeCount = 4,
                    saveCount = 4,
                    postId = "4"
                ),
            )
    }
}