package com.example.charo_android

class LocalDetailViewpagerImageDataSource: DetailViewpagerImageDataSource {
    override fun fetchData(): MutableList<DetailViewpagerImageInfo> {
        return mutableListOf(
            DetailViewpagerImageInfo(image = R.drawable.mask_group),
            DetailViewpagerImageInfo(image = R.drawable.mask_group),
            DetailViewpagerImageInfo(image = R.drawable.mask_group),
            DetailViewpagerImageInfo(image = R.drawable.mask_group),
            DetailViewpagerImageInfo(image = R.drawable.mask_group),
            DetailViewpagerImageInfo(image = R.drawable.mask_group_2),
        )
    }
}