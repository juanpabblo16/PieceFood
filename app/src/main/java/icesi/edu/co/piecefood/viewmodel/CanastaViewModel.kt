package icesi.edu.co.piecefood.viewmodel

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
            // Actualiza la cantidad en la base de datos
            viewModelScope.launch {
                try {
                    userRepository.updatePortionQuantity(userId, portion)
                } catch (e: Exception) {
                    // Manejar el error
                }
            }
        }
    }


}

