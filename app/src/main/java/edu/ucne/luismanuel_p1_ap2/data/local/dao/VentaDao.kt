package edu.ucne.luismanuel_p1_ap2.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.luismanuel_p1_ap2.data.local.entities.VentasEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VentaDao {
    @Upsert
    suspend fun save(Venta: VentasEntity)

    @Query("""
        SELECT * FROM VENTAS
        WHERE idVentas = :id
        LIMIT 1
    """)
    suspend fun find(id: Int): VentasEntity

    @Delete
    suspend fun delete(Venta: VentasEntity)

    @Query("SELECT * FROM ventas")
    suspend fun getAll(): Flow<List<VentasEntity>>
}