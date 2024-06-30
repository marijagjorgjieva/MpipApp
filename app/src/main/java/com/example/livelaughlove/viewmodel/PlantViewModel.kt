package com.example.livelaughlove.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.livelaughlove.model.models.Plant
import com.example.livelaughlove.model.repositories.PlantRepository
import kotlinx.coroutines.launch

class PlantViewModel(private val plantRepository: PlantRepository) : ViewModel() {

    val listLiveData = MutableLiveData<List<Plant>>()
    private var listPlant: MutableList<Plant> = mutableListOf()

    init {
        getPlantsFromRepository()
    }

    private fun getPlantsFromRepository() {
        viewModelScope.launch {
            listPlant = plantRepository.getSavedPlants().toMutableList()
            listLiveData.value = listPlant
        }
    }

    fun searchQuery(query: String) {
        if (query.isEmpty()) {
            listLiveData.value = listPlant
        } else {
            listLiveData.value = listPlant.filter {
                it.plantName?.contains(query, ignoreCase = true) ?: false
            }
        }
    }

    fun savePlant(plant: Plant) {
        viewModelScope.launch {
            plantRepository.upsertPlant(plant)
            getPlantsFromRepository() // Refresh list after save
        }
    }

    fun deletePlant(plant: Plant) {
        viewModelScope.launch {
            plantRepository.deletePlant(plant)
            getPlantsFromRepository() // Refresh list after delete
        }
    }
}

private fun <T> LiveData<T>.toMutableList(): MutableList<Plant> {
    TODO("Not yet implemented")
}


