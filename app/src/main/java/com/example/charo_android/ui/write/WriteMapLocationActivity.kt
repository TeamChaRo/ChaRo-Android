package com.example.charo_android.ui.write

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.charo_android.R
import com.example.charo_android.databinding.ActivityWriteMapLocationBinding
import com.skt.Tmap.TMapData
import com.skt.Tmap.TMapMarkerItem
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapView

class WriteMapLocationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWriteMapLocationBinding
    private val viewModel : WriteViewModel by viewModels()

    val markerCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteMapLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_write_map_location)
//
//        binding.lifecycleOwner = this
//        binding.viewModel = viewModel

        binding.imgWriteMapLocationBack.setOnClickListener {
            onBackPressed()

        }

        var resultLocation = intent.getStringExtra("setLocation").toString()

        Log.d("location", intent.getStringExtra("setLocation").toString())
        binding.btnSetLocation.text = intent.getStringExtra("setLocation").toString()


//        var list = arrayOf<String>("","","","")
//        binding.btnSetLocation.text = viewModel.text.toString()
//        if(viewModel.data ==0 || viewModel.text.toString() == "출발지"){
//            list[0]="1"
//            Log.d("locationlist",list.toString())
//        }else if(viewModel.data ==3 || viewModel.text.toString() == "도착지"){
//            list[3]="1"
//            Log.d("locationlist",list.toString())
//        }
//        Log.d("locationlist",list.toString())


        val tMapView = TMapView(this@WriteMapLocationActivity)

        /*************커밋 푸시 머지할 때 키 삭제************/
        tMapView.setSKTMapApiKey("l7xx94a7679a3e1d41a782105327ae7af1cd")
        binding.clWriteMapLocationView.addView(tMapView)

        val locationName = intent.getStringExtra("locationName")
        val locationAddress = intent.getStringExtra("locationAddress")
    //    var location = intent.getStringExtra("location")

        binding.textLocationName.text = locationName
        binding.textLocationAddress.text = locationAddress

        val tmapdata = TMapData()
        tmapdata.findAllPOI(locationName){ poiItem ->
            Log.d("poi", poiItem[0].poiPoint.toString())

            val markerCurrentSpot = TMapMarkerItem()
            var tmapPointCurrentSpot = poiItem[0].poiPoint
            tMapView.setCenterPoint(tmapPointCurrentSpot.longitude,tmapPointCurrentSpot.latitude);
            val bitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.poi_dot)
            markerCurrentSpot.icon = bitmap
            markerCurrentSpot.setPosition(0.5F, 1.0F)
            markerCurrentSpot.tMapPoint = tmapPointCurrentSpot
            markerCurrentSpot.name = "currentSpot$markerCount"
            tMapView.addMarkerItem("marker_current_spot$markerCount", markerCurrentSpot)


            binding.btnSetLocation.setOnClickListener {
                val intent = Intent(this,WriteMapActivity::class.java)
                intent.putExtra("locationName",locationName)
                    .putExtra("pointLong",poiItem[0].poiPoint.longitude)
                    .putExtra("pointLat",poiItem[0].poiPoint.latitude)
                    .putExtra("resultLocation",resultLocation)

                startActivity(intent)

            }
        }


    }
}
