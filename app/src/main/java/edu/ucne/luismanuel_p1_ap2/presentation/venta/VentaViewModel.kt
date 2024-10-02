package edu.ucne.luismanuel_p1_ap2.presentation.venta

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.luismanuel_p1_ap2.data.repository.VentaRepository
import edu.ucne.luismanuel_p1_ap2.data.local.entities.VentasEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VentaViewModel @Inject constructor(
    private val ventaRepository: VentaRepository
): ViewModel(){
    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init {
        getVentas()
    }

    fun save(){
        viewModelScope.launch {
            if(_uiState.value.nombreEmpresa.isNullOrBlank() && _uiState.value.galones == 0 ){
                _uiState.update {
                    it.copy(errorMessage = "Campos Vacios")
                }

            }else{
                ventaRepository.save(_uiState.value.toEntity())
            }
        }
    }

    fun nuevo(){
        _uiState.update{
            it.copy(
                ventaId = null,
                nombreEmpresa = "",
                galones = null,
                descuentoGalon = 4.7,
                precio = 132.6,
                totalDescontado =null ,
                total = null,
                errorMessage= null
            )
        }
    }

    fun selectedVenta(ventaId : Int){
        viewModelScope.launch {
            if(ventaId > 0){
                val venta = ventaRepository.getVenta(ventaId)
                _uiState.update{
                    it.copy(
                        ventaId = venta?.ventaId,
                        nombreEmpresa = venta?.nombreEmpresa,
                        galones = venta?.galones,
                        descuentoGalon = venta.descuentoGalon,
                        precio = venta.precio,
                        totalDescontado = venta?.totalDescontado,
                        total = venta?.total



                    )
                }


            }
        }
    }

fun delete(){
    viewModelScope.launch {
        ventaRepository.delete(_uiState.value.toEntity())
    }
}

    private fun getVentas(){
        viewModelScope.launch {
            ventaRepository.getVentas().collect { ventas ->
                _uiState.update {
                    it.copy(ventas = ventas)
                }

            }
        }
    }


    fun onNombreEmpresaChange(nombreEmpresa: String){
        _uiState.update{
            it.copy(nombreEmpresa = nombreEmpresa)
        }
    }

    fun onGalonesChange(galones: Int){
        _uiState.update{
            it.copy(galones = galones)
        }
        calcularTotal()
    }

    fun onDescuentoGalonChange(descuentoGalon: Double){
        _uiState.update{
            it.copy(descuentoGalon = descuentoGalon)
        }
        calcularTotal() // Calcula el total después de actualizar descuento
    }
    fun onPrecioChange(precio: Double){
        _uiState.update{
            it.copy(precio = precio)
        }
        calcularTotal() // Calcula el total después de actualizar precio
    }
    fun onVentasIdChange(ventaId: Int) {
        _uiState.update {
            it.copy(ventaId = ventaId)
        }
    }

    fun calcularTotal() {
        val galones = _uiState.value.galones ?: 0
        val descuentoGalon = _uiState.value.descuentoGalon
        val precio = _uiState.value.precio

        // Calcula el total descontado y el total
        val totalDescontado = galones * descuentoGalon
        val total = (galones * precio) - totalDescontado

        // Actualiza el estado con los nuevos valores calculados
        _uiState.update {
            it.copy(
                totalDescontado = totalDescontado,
                total = total
            )
        }
    }

    data class UiState(
        val ventaId: Int? =null,
        val nombreEmpresa: String? = "",
        val galones:  Int? =null,
        val descuentoGalon: Double = 4.7,
        val precio: Double= 132.6,
        val totalDescontado: Double? =null,
        val total: Double? =null,
        val ventas: List<VentasEntity> = emptyList(),
        val errorMessage: String? = null,

        )
    fun UiState.toEntity() = VentasEntity(
        ventaId = ventaId,
        nombreEmpresa = nombreEmpresa ?: "",
        galones= galones?:null,
        descuentoGalon= descuentoGalon,
        precio = precio,
        totalDescontado= totalDescontado?: null,
        total = total?: null
    )
}