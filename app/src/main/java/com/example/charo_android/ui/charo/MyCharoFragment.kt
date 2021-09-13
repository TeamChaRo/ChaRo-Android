package com.example.charo_android.ui.charo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.charo_android.R
import com.example.charo_android.databinding.FragmentMyCharoBinding
import com.example.charo_android.hidden.Hidden

class MyCharoFragment : Fragment() {
    private val myCharoViewModel: CharoViewModel by activityViewModels()
    private var _binding: FragmentMyCharoBinding? = null
    private val binding get() = _binding!!
    private lateinit var myCharoAdapter: MyCharoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myCharoAdapter = MyCharoAdapter(Hidden.userId)
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_charo, container, false)
        val root: View = binding.root

        Log.d("MyCharoFragment", "created!")

        setUpSpinner()
        setupSpinnerHandler()

        myCharoViewModel.writtenPost.observe(viewLifecycleOwner, {
            if(myCharoViewModel.writtenPost.value?.drive != null) {
                myCharoAdapter.itemList.addAll(myCharoViewModel.writtenPost.value?.drive!!)
                myCharoAdapter.notifyDataSetChanged()
            }
        })

//        myCharoViewModel.writtenPost.observe(viewLifecycleOwner) {
//            Log.d("myCharoViewModel writtenPost observe", it.drive.size.toString())
//        }

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
        binding.spinnerMyCharoFilter.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (position == 0) {
                        binding.recyclerviewMyCharo.adapter = myCharoAdapter

//                        myCharoAdapter.itemList.clear()
                    } else {
                        binding.recyclerviewMyCharo.adapter = myCharoAdapter
//                        myCharoAdapter.itemList.clear()
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    binding.recyclerviewMyCharo.adapter = myCharoAdapter
                    myCharoAdapter.itemList.clear()
                }
            }
    }
}