package com.example.charo_android.presentation.ui.write

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import com.example.charo_android.R
import com.example.charo_android.databinding.FragmentWriteMapLocationBinding
import com.example.charo_android.hidden.Hidden
import com.example.charo_android.presentation.util.Define
import com.skt.Tmap.TMapAddressInfo
import com.skt.Tmap.TMapData
import com.skt.Tmap.TMapMarkerItem
import com.skt.Tmap.TMapView

private const val LOCATION_NAME = "locationName"
private const val LOCATION_ADDRESS = "locationAddress"

class WriteMapLocationFragment : Fragment() {
    private var locationName: String? = null
    private var locationAddress: String? = null
    private lateinit var locationFlag: String
    private lateinit var address: TMapAddressInfo
    private var lat = 0.0
    private var lon = 0.0

    val markerCount = 1

    companion object {
        fun newInstance() = WriteMapLocationFragment()

        @JvmStatic
        fun newInstance(locationName: String, locationAddress: String) =
            WriteMapLocationFragment().apply {
                arguments = Bundle().apply {
                    putString(LOCATION_NAME, locationName)
                    putString(LOCATION_ADDRESS, locationAddress)
                }
            }
    }

    private var _binding: FragmentWriteMapLocationBinding? = null

    private val binding get() = _binding!!

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

        _binding = FragmentWriteMapLocationBinding.inflate(inflater, container, false)
        val root: View = binding.root

        locationFlag = sharedViewModel.locationFlag.value.toString()
        arguments?.let {
            locationName = it.getString(LOCATION_NAME)
            locationAddress = it.getString(LOCATION_ADDRESS)
        }

        binding.imgWriteMapLocationBack.setOnClickListener {
            writeShareActivity?.onBackPressed()
        }

        val tMapView = TMapView(context)

        tMapView.setSKTMapApiKey(Hidden().tMapApiKey)
        binding.clWriteMapLocationView.addView(tMapView)

        val tmapdata = TMapData()
        lateinit var bitmap: Bitmap
        if (locationFlag == Define().LOCATION_FLAG_START) {
            binding.btnSetLocation.text = getString(R.string.set_start_point)
            bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_route_start)

        } else if (locationFlag == Define().LOCATION_FLAG_END) {
            binding.btnSetLocation.text = getString(R.string.set_end_point)
            bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_route_end)

        } else {
            binding.btnSetLocation.text = getString(R.string.set_via_point)
            bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_route_waypoint)
        }

        tmapdata.findAllPOI(locationName) { poiItem ->
            Log.d("poi", poiItem[0].poiPoint.toString())
            val marker = TMapMarkerItem()
            val mapPoint = poiItem[0].poiPoint
            marker.icon = bitmap
            marker.setPosition(0.5F, 1.0F)
            marker.tMapPoint = mapPoint
            marker.name = "marker_${locationFlag}"
            tMapView.addMarkerItem(marker.name, marker)
        }

        binding.textLocationName.text = locationName
        binding.textLocationAddress.text = locationAddress

        tmapdata.findAllPOI(locationName) { poiItem ->
            Log.d("poi", poiItem[0].poiPoint.toString())

            var tmapPointCurrentSpot = poiItem[0].poiPoint
            if (poiItem[0].upperAddrName != null) {
                locationAddress = poiItem[0].upperAddrName
                Log.d("address upper", poiItem[0].upperAddrName)
            }
            if (poiItem[0].middleAddrName != null) {
                locationAddress += " " + poiItem[0].middleAddrName
                Log.d("address middle", poiItem[0].middleAddrName)
            }
            if (poiItem[0].lowerAddrName != null) {
                locationAddress += " " + poiItem[0].lowerAddrName
                Log.d("address lower", poiItem[0].lowerAddrName)
            }
            if (poiItem[0].detailAddrName != null) {
                locationAddress += " " + poiItem[0].detailAddrName
                Log.d("address detail", poiItem[0].detailAddrName)
            }
            if (poiItem[0].firstNo != null) {
                locationAddress += " " + poiItem[0].firstNo
                Log.d("address firstNo", poiItem[0].firstNo)
            }
            if (poiItem[0].secondNo != null && poiItem[0].secondNo != "0") {
                locationAddress += " " + poiItem[0].secondNo
                Log.d("address secondNo", poiItem[0].secondNo)
            }
            tMapView.setCenterPoint(tmapPointCurrentSpot.longitude, tmapPointCurrentSpot.latitude)
            lat = tmapPointCurrentSpot.latitude
            lon = tmapPointCurrentSpot.longitude
            address = tmapdata.reverseGeocoding(lat, lon, "A10")
        }

        binding.btnSetLocation.setOnClickListener {
            lat = tMapView.centerPoint.latitude
            lon = tMapView.centerPoint.longitude

            when(sharedViewModel.locationFlag.value){
                Define().LOCATION_FLAG_START -> {
                    sharedViewModel.startLat.value = tMapView.centerPoint.latitude
                    sharedViewModel.startLong.value = tMapView.centerPoint.longitude
                    sharedViewModel.startAddress.value = locationName
                }
                Define().LOCATION_FLAG_END -> {
                    sharedViewModel.endLat.value = tMapView.centerPoint.latitude
                    sharedViewModel.endLong.value = tMapView.centerPoint.longitude
                    sharedViewModel.endAddress.value = locationName
                }
                else -> {
                    sharedViewModel.midFrstLat.value = tMapView.centerPoint.latitude
                    sharedViewModel.midFrstLong.value = tMapView.centerPoint.longitude
                    sharedViewModel.midFrstAddress.value = locationName
                }
            }

            writeShareActivity!!.replaceFragment(WriteMapFragment.newInstance())

        }

            return root
        }
    private fun setLocationInfo() {
        binding.textLocationAddress.text = locationAddress
        binding.textLocationName.text = locationName
    }
}
