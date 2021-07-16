package com.example.charo_android.ui.write

import android.net.Uri

class WriteData {
    companion object {
        var startAddress: String = ""
        var mid1Address: String = ""
        var mid2Address: String = ""
        var endAddress: String = ""
        var startLat: Double = 0.0
        var startLong: Double = 0.0
        var mid1Lat: Double = 0.0
        var mid1Long: Double = 0.0
        var mid2Lat: Double = 0.0
        var mid2Long: Double = 0.0
        var endLat: Double = 0.0
        var endLong: Double = 0.0

        var courseDesc: String = ""
        var isParking: Boolean = false
        var parkingDesc: String = ""
        var province: String = ""
        var region: String = ""
        var theme: MutableList<String> = mutableListOf()
        var title: String = ""
        var userId: String = ""
        var warning: MutableList<Boolean> = mutableListOf()
        var fileList: MutableList<Uri> = mutableListOf()
    }
}