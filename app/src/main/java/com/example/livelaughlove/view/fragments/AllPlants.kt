package com.example.livelaughlove.view.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.livelaughlove.view.MainActivity
import com.example.livelaughlove.view.adapters.DiscoverCategoriesAdapter
import com.example.livelaughlove.view.adapters.AllPlantsAdapter
import com.example.livelaughlove.viewmodel.PlantViewModel
import com.example.livelaughlove.R
import com.example.livelaughlove.databinding.FragmentDiscoverBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AllPlants : Fragment() {

    private var _binding: FragmentDiscoverBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: PlantViewModel
    val db = Firebase.firestore
    val mAdapter = AllPlantsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDiscoverBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cAdapter = DiscoverCategoriesAdapter(requireContext())
        viewModel = (activity as MainActivity).plantVM
        binding.rvDiscoverMainItems.adapter = mAdapter

        mAdapter.setOnItemClickListenerSave {
            viewModel.savePlant(it)
            Toast.makeText(context, "Saved", Toast.LENGTH_LONG).show()
        }
        mAdapter.setOnItemClickListenerMain {
            val bundle = Bundle().apply {
                putSerializable("plant", it)
            }
            findNavController().navigate(
                R.id.from_discover_to_actionToDetail,
                bundle
            )
        }


        cAdapter.setOnItemClickListener {
            binding.etSearchView.setText(it)
        }


        viewModel.listLiveData.observe(viewLifecycleOwner, Observer
        {
            it?.let {
                mAdapter.differ.submitList(it)
            }
        })


        var job: Job? = null
        binding.etSearchView.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable?) {
                    job?.cancel()
                    job = MainScope().launch {
                        delay(500L)
                        s?.let {
                            viewModel.searchQuery(s.toString())
                        }
                    }
                }
            })


    }





    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}