package com.example.charo_android.data.repository.local.setting

import com.example.charo_android.domain.model.setting.SettingNoticeData

class LocalSettingNoticeDataSourceImpl : LocalSettingNoticeDataSource {
    override fun fetchData(): MutableList<SettingNoticeData> {
        return mutableListOf<SettingNoticeData>(
            SettingNoticeData(
                "업데이트 실행 1.0 -> 2.0 업데이트 안내",
                "2021.12.12",
                "어쩌구 저쩌구가 업데이트 됨!어쩌구 저쩌구가 업데이트 됨!"
            ),
            SettingNoticeData(
                "업데이트 실행 1.0 -> 2.0 업데이트 안내1",
                "2021.12.12",
                "어쩌구 저쩌구가 업데이트 됨!어쩌구 저쩌구가 업데이트 됨!"
            ),SettingNoticeData(
                "업데이트 실행 1.0 -> 2.0 업데이트 안내2",
                "2021.12.12",
                "어쩌구 저쩌구가 업데이트 됨!어쩌구 저쩌구가 업데이트 됨!"
            ),SettingNoticeData(
                "업데이트 실행 1.0 -> 2.0 업데이트 안내",
                "2021.12.12",
                "어쩌구 저쩌구가 업데이트 됨!어쩌구 저쩌구가 업데이트 됨!"
            ),SettingNoticeData(
                "업데이트 실행 1.0 -> 2.0 업데이트 안내",
                "2021.12.12",
                "어쩌구 저쩌구가 업데이트 됨!어쩌구 저쩌구가 업데이트 됨!"
            ),SettingNoticeData(
                "업데이트 실행 1.0 -> 2.0 업데이트 안내",
                "2021.12.12",
                "어쩌구 저쩌구가 업데이트 됨!어쩌구 저쩌구가 업데이트 됨!"
            ),SettingNoticeData(
                "업데이트 실행 1.0 -> 2.0 업데이트 안내",
                "2021.12.12",
                "어쩌구 저쩌구가 업데이트 됨!어쩌구 저쩌구가 업데이트 됨!"
            )
        )
    }
}