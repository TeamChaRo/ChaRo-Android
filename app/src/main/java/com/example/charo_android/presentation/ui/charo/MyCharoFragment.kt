package com.example.charo_android.presentation.ui.charo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.charo_android.MainActivity
import com.example.charo_android.R
import com.example.charo_android.databinding.FragmentMyCharoBinding

class MyCharoFragment(
    val likeData: MutableList<MyCharoInfo>,
    val newData: MutableList<MyCharoInfo>,
) : Fragment() {
    private var _binding: FragmentMyCharoBinding? = null
    private val binding get() = _binding!!
//    private var myCharoAdapter = MyCharoAdapter()
    private lateinit var myCharoAdapter: MyCharoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val userId: String = (activity as MainActivity).getUserId()
        myCharoAdapter = MyCharoAdapter(userId)
        _binding = FragmentMyCharoBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setUpSpinner()
        setupSpinnerHandler()

        // Inflate the layout for this fragment
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    
    private fun setUpSpinner() {
        val filter = resources.getStringArray(R.array.charo_filter)
        val adapter =
            activity?.let { ArrayAdapter(it, R.layout.item_charo_spinner, filter) }
        adapter?.setDropDownViewResource(R.layout.item_charo_spinner)
        binding.spinnerMyCharoFilter.adapter = adapter
    }

    private fun setupSpinnerHandler() {
        binding.spinnerMyCharoFilter.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if(position == 0) {
                    binding.recyclerviewMyCharo.adapter = myCharoAdapter
                    myCharoAdapter.itemList.clear()
                    myCharoAdapter.itemList.addAll(likeData)
                    myCharoAdapter.notifyDataSetChanged()
                    binding.tvMyCharoBoardCount.text = "전체 ${myCharoAdapter.itemList.size}개의 게시물"
                } else {
                    binding.recyclerviewMyCharo.adapter = myCharoAdapter
                    myCharoAdapter.itemList.clear()
                    myCharoAdapter.itemList.addAll(newData)
                    myCharoAdapter.notifyDataSetChanged()
                    binding.tvMyCharoBoardCount.text = "전체 ${myCharoAdapter.itemList.size}개의 게시물"
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                binding.recyclerviewMyCharo.adapter = myCharoAdapter
                myCharoAdapter.itemList.clear()
                myCharoAdapter.itemList.addAll(likeData)
                myCharoAdapter.notifyDataSetChanged()
                binding.tvMyCharoBoardCount.text = "전체 ${myCharoAdapter.itemList.size}개의 게시물"
            }
        }
    }
}