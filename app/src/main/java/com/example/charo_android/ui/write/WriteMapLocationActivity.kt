package com.example.charo_android.ui.write

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.charo_android.R
import com.example.charo_android.databinding.ActivityWriteMapBinding
import com.example.charo_android.databinding.ActivityWriteMapLocationBinding
import com.skt.Tmap.TMapData
import com.skt.Tmap.TMapMarkerItem
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapView

class WriteMapLocationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWriteMapLocationBinding

    val markerCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteMapLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgWriteMapLocationBack.setOnClickListener {
            onBackPressed()

        }

      //  if(intent.getStringExtra("endLocation"))



        val tMapView = TMapView(this@WriteMapLocationActivity)

        /*************커밋 푸시 머지할 때 키 삭제************/
        tMapView.setSKTMapApiKey("l7xx94a7679a3e1d41a782105327ae7af1cd")
        binding.clWriteMapLocationView.addView(tMapView)

        val locationName = intent.getStringExtra("locationName")
        val locationAddress = intent.getStringExtra("locationAddress")

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

                startActivity(intent)

            }
        }


        binding.imgWriteMapLocationBack.setOnClickListener {
            onBackPressed()
        }




    }
}
