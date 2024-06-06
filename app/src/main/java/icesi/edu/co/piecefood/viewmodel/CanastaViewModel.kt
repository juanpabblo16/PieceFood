package icesi.edu.co.piecefood.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import icesi.edu.co.piecefood.model.Ingredient
import icesi.edu.co.piecefood.model.Portion
import icesi.edu.co.piecefood.repository.IngredientRepository
import icesi.edu.co.piecefood.repository.IngredientRepositoryImpl
import icesi.edu.co.piecefood.repository.UserRepository
import icesi.edu.co.piecefood.repository.UserRepositoryImpl
import kotlinx.coroutines.launch

class CanastaViewModel(
    private val ingredientRepository: IngredientRepository = IngredientRepositoryImpl(),
    private val userRepository: UserRepository = UserRepositoryImpl()
) : ViewModel() {

    private val _portions = MutableLiveData<List<Portion>>()
    val portions: LiveData<List<Portion>> get() = _portions

    private val _ingredientNames = MutableLiveData<Map<String, String>>()
    val ingredientNames: LiveData<Map<String, String>> get() = _ingredientNames

    private val _selectedPortion = MutableLiveData<Portion?>()
    val selectedPortion: LiveData<Portion?> get() = _selectedPortion

    fun loadUserPortions(userId: String) {
        viewModelScope.launch {
            try {
                val userPortions = userRepository.loadUserPortions(userId)
                val ingredientIds = userPortions.map { it.ingredientId }.toSet()

                val ingredientNamesMap = mutableMapOf<String, String>()
                for (id in ingredientIds) {
                    val name = ingredientRepository.getIngredientNameById(id)
                    ingredientNamesMap[id] = name
                }

                _ingredientNames.value = ingredientNamesMap
                _portions.value = userPortions
            } catch (e: Exception) {
                // Maneja el error
            }
        }
    }

    fun selectPortion(portion: Portion) {
        _selectedPortion.value = portion
    }

    fun increaseQuantity() {
        _selectedPortion.value?.let { portion ->
            portion.quantity++
            _selectedPortion.value = portion
        }
    }

    fun decreaseQuantity() {
        _selectedPortion.value?.let { portion ->
            if (portion.quantity > 0) {
                portion.quantity--
                _selectedPortion.value = portion
            }
        }
    }

    fun saveQuantity(userId: String) {
        _selectedPortion.value?.let { portion ->
            Log.d("CanastaViewModel", "ID de la porción: ${portion.ingredientId}, Cantidad: ${portion.quantity}, Usuario ID: $userId")
            // Actualiza la cantidad en la base de datos
            viewModelScope.launch {
                try {
                    // Primero, obtén la lista actual de porciones del usuario
                    val userPortions = (_portions.value ?: emptyList()).toMutableList()

                    // Encuentra la posición de la porción actual en la lista
                    val index = userPortions.indexOfFirst { it.ingredientId == portion.ingredientId }

                    if (index != -1) {
                        // Si se encuentra la porción en la lista, actualiza la cantidad
                        userPortions[index] = portion
                        _portions.value = userPortions

                        // Luego, guarda la lista actualizada en la base de datos
                        userRepository.updatePortionQuantity(userId, portion)
                    } else {
                        // Si la porción no se encuentra en la lista, no se puede guardar
                        throw IllegalStateException("La porción no se encuentra en la lista de porciones del usuario.")
                    }
                } catch (e: Exception) {
                    // Manejar el error
                }
            }
        }
    }



}

