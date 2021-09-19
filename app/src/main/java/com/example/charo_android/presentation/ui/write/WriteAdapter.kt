package com.example.charo_android.presentation.ui.write

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.charo_android.data.WriteImgInfo
import com.example.charo_android.databinding.ItemWriteImgBinding

class WriteAdapter : RecyclerView.Adapter<WriteAdapter.WriteImgViewHolder>() {

    /* 2. Adapter 는 ViewHolder 로 변경할 Data 를 가지고 있어야함 */
    var imgList = mutableListOf<WriteImgInfo>()

    // 3. Adapter 는 아이템마다 ViewHolder를 만드는 방법을 정의
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WriteImgViewHolder {
        val binding = ItemWriteImgBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return WriteImgViewHolder(binding)
    }

    // 4. Adapter 는 전체 아이템의 수를 알아야 함
    override fun getItemCount(): Int = imgList.size

    // 5. Adapter 는 ViewHolder 에 Data 를 전달하는 방법을 정의
    override fun onBindViewHolder(holder: WriteImgViewHolder, position: Int) {

        holder.onBind(imgList[position])
    }

    class WriteImgViewHolder(
        private val binding: ItemWriteImgBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(writeImgInfo: WriteImgInfo) {
//            val bitmap =
//                MediaStore.Images.Media.getBitmap(contentresolver, writeImgInfo.imgUri)
        //    binding.itemWriteImg.setImageBitmap(bitmap)
         //   binding.itemWriteImg.uri = writeImgInfo.imgUri
            binding.itemWriteImg.setImageURI(writeImgInfo.imgUri)
        //    binding.itemWriteImg.setImageBitmap(bitmap)
        }
    }
}
