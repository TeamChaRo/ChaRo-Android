package com.charo.android.data.model.write

import android.net.Uri

data class WriteImgInfo (
    val imgUri: Uri,
    // note(승현):
    // 이미지 Uri 가 기기 로컬 저장소 안의 이미지 Uri 인지,
    // 혹은 서버 DB 저장소 안의 이미지 Uri 인지에 따라서
    // 멀티파트화 하는 방식이 달라지기 때문에 아래 필드를 추가하였음
    val isFromLocal: Boolean
)