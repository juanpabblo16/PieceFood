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

    fun addIngredientToUser(userId: String, ingredient: Ingredient, quantity: Int) {
        viewModelScope.launch {
            try {
                val ingredientId = ingredientRepository.addIngredient(ingredient)
                if (ingredientId != null) {
                    val portion = Portion(ingredientId, quantity)
                    userRepository.addPortionToUser(userId, portion)
                    loadUserPortions(userId) // Recargar la lista de porciones
                }
            } catch (e: Exception) {
                // Maneja el error
            }
        }
    }
}

