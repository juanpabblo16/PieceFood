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

    private val _ingredients = MutableLiveData<List<Ingredient>>()
    val ingredients: LiveData<List<Ingredient>> get() = _ingredients

    fun loadUserIngredients(userId: String) {
        viewModelScope.launch {
            try {
                val portions = userRepository.loadUserPortions(userId)
                val ingredientList = portions.mapNotNull { portion ->
                    ingredientRepository.loadIngredient(portion.ingredientId!!)
                }
                _ingredients.value = ingredientList
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
                    loadUserIngredients(userId) // Recargar la lista de ingredientes
                }
            } catch (e: Exception) {
                // Maneja el error
            }
        }
    }
}
