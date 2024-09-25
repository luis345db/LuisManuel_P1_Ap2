package edu.ucne.luismanuel_p1_ap2.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Ventas")
data class VentasEntity (
    @PrimaryKey(autoGenerate = true)
    val ventaId : Int? = null,
    val nombreEmpresa: String = "",
    val galones: Int? =null,
    val descuentoGalon: Double ,
    val precio: Double ,
    val totalDescontado: Double? = null,

    val total: Double?= null

)