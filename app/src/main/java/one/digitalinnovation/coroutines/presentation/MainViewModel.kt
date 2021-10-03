package one.digitalinnovation.coroutines.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import one.digitalinnovation.coroutines.data.model.Address
import one.digitalinnovation.coroutines.data.model.Address2
import one.digitalinnovation.coroutines.data.repository.ViaCepRepository

class MainViewModel(
    private val repository: ViaCepRepository = ViaCepRepository()
) : ViewModel() {

    private val _state = MutableLiveData<String>()
    //ver os valores e não alterar diretamente
    val state: LiveData<String> = _state
    var endereco: Address2? = null

    fun findAddress(cep: String) = viewModelScope.launch {

        runCatching {
            repository.findAddress(cep)
        }.onSuccess { address ->
            endereco = Address2(1, address.cep, address.street, address.neighborhood, address.city, address.state)
            _state.postValue(address.toString())
        }.onFailure { throwable ->
            endereco = null
            _state.postValue( throwable.message)
        }
    }
}