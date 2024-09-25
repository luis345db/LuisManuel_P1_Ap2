package edu.ucne.luismanuel_p1_ap2.data.repository

import edu.ucne.luismanuel_p1_ap2.data.local.dao.AlgoDao
import edu.ucne.luismanuel_p1_ap2.data.local.entities.AlgoEntity
import javax.inject.Inject

class AlgoRepository @Inject constructor(
    private val algoDao: AlgoDao
) {
    suspend fun save(algo: AlgoEntity) = algoDao.save(algo)
    suspend fun getalgo(id: Int) = algoDao.find(id)
    suspend fun delete(algo: AlgoEntity) = algoDao.delete(algo)
    fun getAlgos() = algoDao.getAll()
}