package com.example.livelaughlove.model.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "plants"
)
data class Plant(

    @PrimaryKey(autoGenerate = false)
    var id: Int? = null,
    val plantName: String? = null,
    val plantSoil: String? = null,
    val plantWatering: String? = null,
    val plantPhoto: List<String>? = null
    val plantLighting: String? = null,
    val plantTemperature: String? = null,
    val plantHumidity: String? = null,
) : Serializable