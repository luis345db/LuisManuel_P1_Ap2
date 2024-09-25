package edu.ucne.luismanuel_p1_ap2.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Ventas")
data class VentasEntity (
    @PrimaryKey(autoGenerate = true)
    val idVentas : Int? = null,
    val nombreEmpresa: String = "",
    val descuentoGalon: Double= 4.7,
    val precio: Double= 132.6,
    val total: Double?= null

)