package com.example.charo_android.presentation.ui.write

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.charo_android.data.MapSearchInfo
import com.example.charo_android.databinding.ItemAutoSearchBinding

class WriteMapSearchAdapter(
//    val locationFlag: String, val text: String, val userId: String, val nickName: String
) : RecyclerView.Adapter<WriteMapSearchAdapter.MapSearchViewHolder>() {

    /* 2. Adapter 는 ViewHolder 로 변경할 Data 를 가지고 있어야함 */
    var userList = mutableListOf<MapSearchInfo>()
//    var text = activity.intent.getStringExtra("locationM")

    // 3. Adapter 는 아이템마다 ViewHolder를 만드는 방법을 정의
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MapSearchViewHolder {
        val binding = ItemAutoSearchBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MapSearchViewHolder(
            binding
        )
    }

    // 4. Adapter 는 전체 아이템의 수를 알아야 함
    override fun getItemCount(): Int = userList.size

//    lateinit var mainActivity: MainActivity
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        mainActivity = context as MainActivity
//    }

    // 5. Adapter 는 ViewHolder 에 Data 를 전달하는 방법을 정의
    override fun onBindViewHolder(holder: MapSearchViewHolder, position: Int) {
        holder.onBind(userList[position])


        var writeShareActivity = holder.itemView.context as WriteShareActivity
        var sharedViewModel = ViewModelProvider(writeShareActivity).get(WriteSharedViewModel::class.java)


//        var text = activity.intent.getStringExtra("locationM")

        holder.itemView.setOnClickListener {
//            val intent = Intent(holder.itemView?.context,WriteMapLocationActivity::class.java)

//            intent.putExtra("locationName",userList[position].locationName)
//                .putExtra("locationAddress",userList[position].locationAddress)
//                .putExtra("locationFlag", locationFlag)
//                .putExtra("text", text)
//                .putExtra("userId", userId)
//                .putExtra("nickName", nickName)

            sharedViewModel.locationName.value = userList[position].locationName
            sharedViewModel.locationAddress.value = userList[position].locationAddress

            writeShareActivity.replaceFragment(WriteMapLocationFragment.newInstance(), "writeMapLocation")


//            ContextCompat.startActivity(holder.itemView.context, intent, null)

        }
    }

    class MapSearchViewHolder(
        private val binding: ItemAutoSearchBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(mapSearchInfo: MapSearchInfo) {

            binding.mapData = mapSearchInfo

        }
    }

    fun clearItem(){
        userList.clear()
    }
}
