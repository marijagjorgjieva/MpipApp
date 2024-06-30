package com.example.livelaughlove.model.db

import android.content.Context
import androidx.room.*
import com.example.livelaughlove.model.models.Plant


@Database(
    entities = [Plant::class],
    version = 3,
)
@TypeConverters(Converters::class)
abstract class PlantDatabase : RoomDatabase() {

    abstract fun getPlantDAO(): PlantDAO


    companion object {

        private var instance: PlantDatabase? = null
        private val LOCK = Any()


        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                PlantDatabase::class.java,
                "PlantsDB.db"
            ).fallbackToDestructiveMigration().allowMainThreadQueries().build()

    }

}