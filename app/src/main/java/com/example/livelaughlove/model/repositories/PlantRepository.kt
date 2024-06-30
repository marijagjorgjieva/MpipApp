package com.example.livelaughlove.model.repositories

import com.example.livelaughlove.model.db.PlantDatabase
import com.example.livelaughlove.model.models.Plant

class PlantRepository(
    val db: PlantDatabase
) {

    suspend fun upsertPLant(plant: Plant) = db.getPlantDAO().upsert(plant)

    fun getSavedPlants() = db.getPlantDAO().getAllPlants()

    suspend fun deletePlant(plant: Plant) = db.getPlantDAO().deletePlant(plant)
    fun upsertPlant(plant: Plant) {
        TODO("Not yet implemented")
    }

}