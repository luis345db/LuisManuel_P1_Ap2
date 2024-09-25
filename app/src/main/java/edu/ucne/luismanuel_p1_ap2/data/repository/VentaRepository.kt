package edu.ucne.luismanuel_p1_ap2.data.repository

import edu.ucne.luismanuel_p1_ap2.data.local.dao.VentaDao
import edu.ucne.luismanuel_p1_ap2.data.local.entities.VentasEntity
import javax.inject.Inject

class VentaRepository @Inject constructor(
    private val ventaDao: VentaDao
){
    suspend fun save(venta : VentasEntity) = ventaDao.save(venta)

    suspend fun  getVenta(id:Int) = ventaDao.find(id)

    suspend fun delete(venta: VentasEntity) = ventaDao.delete(venta)

    fun getVentas()= ventaDao.getAll()
}