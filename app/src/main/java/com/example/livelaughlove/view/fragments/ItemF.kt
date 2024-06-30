package com.example.livelaughlove.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavArgsLazy
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.livelaughlove.view.MainActivity
import com.example.livelaughlove.view.adapters.ItemAdapter
import com.example.livelaughlove.viewmodel.PlantViewModel
import com.example.livelaughlove.R
import com.example.livelaughlove.databinding.FragmentDetailBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.reflect.KProperty


class ItemF : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: PlantViewModel
    val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val plant = args.plant
        viewModel = (activity as MainActivity).plantVM

        val list: List<String>? = plant.plantPhoto
        val mAdapter = ItemAdapter(list)
        binding.viewpagerDetail.adapter = mAdapter

        mAdapter.differ.submitList(list)

        binding.ibAddFavorite.setOnClickListener {
            viewModel.savePlant(plant)
            Toast.makeText(requireContext(), "Plant saved successfully", Toast.LENGTH_SHORT).show()
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.tvDetailTitle.text = plant.plantName
        binding.tvHumidityInfo.text = plant.plantHumidity.toString().trim()
        binding.tvWateringInfo.text = plant.plantWatering
        binding.tvSoilInfo.text = plant.plantSoil
        binding.tvTemperatureInfo.text = plant.plantTemperature
        binding.tvTitle.text = plant.plantName
        binding.tvLightingInfo.text = plant.plantLighting

        TabLayoutMediator(
            binding.tablayoutDeatilViewpager,
            binding.viewpagerDetail,
        ) { tab, position ->
        }.attach()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

private operator fun <Args> NavArgsLazy<Args>.getValue(
    itemF: ItemF,
    property: KProperty<Args?>
): DetailFragmentArgs {
    TODO("Not yet implemented")
}
