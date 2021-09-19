package com.example.charo_android.presentation.util//package com.example.charo_android
//
//import android.graphics.Bitmap
//import android.graphics.BitmapFactory
//import android.os.Bundle
//import android.util.Log
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.lifecycle.ViewModelProvider
//import com.example.charo_android.databinding.FragmentWriteMapBinding
//import com.example.charo_android.databinding.FragmentWriteMapLocationBinding
//import com.example.charo_android.presentation.di.ui.write.WriteViewModel
//import com.skt.Tmap.TMapData
//import com.skt.Tmap.TMapMarkerItem
//import com.skt.Tmap.TMapView
//
//// TODO: Rename parameter arguments, choose names that match
//// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"
//val markerCount = 1
//
//
///**
// * A simple [Fragment] subclass.
// * Use the [WriteMapLocationFragment.newInstance] factory method to
// * create an instance of this fragment.
// */
//class WriteMapLocationFragment : Fragment() {
//
//    private lateinit var writeViewModel: WriteViewModel
//    private var _binding: FragmentWriteMapLocationBinding? = null
//
//    // This property is only valid between onCreateView and
//    // onDestroyView.
//    private val binding get() = _binding!!
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//        }
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//
//        writeViewModel =
//            ViewModelProvider(this).get(WriteViewModel::class.java)
//
//        _binding = FragmentWriteMapLocationBinding.inflate(inflater, container, false)
//        val root: View = binding.root
//
//
////        binding = DataBindingUtil.setContentView(this, R.layout.activity_write_map_location)
////
////        binding.lifecycleOwner = this
////        binding.viewModel = viewModel
//
//        binding.imgWriteMapLocationBack.setOnClickListener {
//         //   onBackPressed()
//
//        }
//
////        var resultLocation = intent.getStringExtra("setLocation").toString()
////
////        Log.d("location", intent.getStringExtra("setLocation").toString())
////        binding.btnSetLocation.text = intent.getStringExtra("setLocation").toString()
//
//
////        var list = arrayOf<String>("","","","")
////        binding.btnSetLocation.text = viewModel.text.toString()
////        if(viewModel.data ==0 || viewModel.text.toString() == "출발지"){
////            list[0]="1"
////            Log.d("locationlist",list.toString())
////        }else if(viewModel.data ==3 || viewModel.text.toString() == "도착지"){
////            list[3]="1"
////            Log.d("locationlist",list.toString())
////        }
////        Log.d("locationlist",list.toString())
//
//
//        val tMapView = TMapView(this.context)
//
//        /*************커밋 푸시 머지할 때 키 삭제************/
//        tMapView.setSKTMapApiKey("l7xx94a7679a3e1d41a782105327ae7af1cd")
//        binding.clWriteMapLocationView.addView(tMapView)
//
////        val locationName = intent.getStringExtra("locationName")
////        val locationAddress = intent.getStringExtra("locationAddress")
//        //    var location = intent.getStringExtra("location")
//
////        binding.textLocationName.text = locationName
////        binding.textLocationAddress.text = locationAddress
//
//        val tmapdata = TMapData()
//        tmapdata.findAllPOI("서울"){ poiItem ->
//            Log.d("poi", poiItem[0].poiPoint.toString())
//
//            val markerCurrentSpot = TMapMarkerItem()
//            var tmapPointCurrentSpot = poiItem[0].poiPoint
//            tMapView.setCenterPoint(tmapPointCurrentSpot.longitude,tmapPointCurrentSpot.latitude);
//            val bitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.poi_dot)
//            markerCurrentSpot.icon = bitmap
//            markerCurrentSpot.setPosition(0.5F, 1.0F)
//            markerCurrentSpot.tMapPoint = tmapPointCurrentSpot
//            markerCurrentSpot.name = "currentSpot$markerCount"
//            tMapView.addMarkerItem("marker_current_spot$markerCount", markerCurrentSpot)
//
//
//            binding.btnSetLocation.setOnClickListener {
////                val intent = Intent(this,WriteMapActivity::class.java)
////                intent.putExtra("locationName",locationName)
////                    .putExtra("pointLong",poiItem[0].poiPoint.longitude)
////                    .putExtra("pointLat",poiItem[0].poiPoint.latitude)
////                    .putExtra("resultLocation",resultLocation)
////
////                startActivity(intentt)
//
//            }
//        }
//
//        // Inflate the layout for this fragment
//        return root
//    }
//
//    companion object {
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            WriteMapLocationFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }
//}