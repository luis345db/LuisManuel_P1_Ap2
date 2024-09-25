package edu.ucne.luismanuel_p1_ap2.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.luismanuel_p1_ap2.data.local.dao.VentaDao

import edu.ucne.luismanuel_p1_ap2.data.local.entities.VentasEntity


@Database(
    version = 1,
    exportSchema = false,
    entities = [VentasEntity::class]
)


abstract class Parcial1Db :RoomDatabase() {
    abstract  fun ventaDao(): VentaDao
}