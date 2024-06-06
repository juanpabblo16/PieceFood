package icesi.edu.co.piecefood.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import icesi.edu.co.piecefood.model.Ingredient
import icesi.edu.co.piecefood.repository.IngredientRepository
import kotlinx.coroutines.launch

class BagViewModel(private val ingredientRepository: IngredientRepository) : ViewModel() {

    private val _ingredientList = MutableLiveData<List<Ingredient>>()
    val ingredientList: LiveData<List<Ingredient>> get() = _ingredientList

    fun loadAllIngredients() {
        viewModelScope.launch {
            try {
                val ingredients = ingredientRepository.loadIngredientList()
                _ingredientList.value = ingredients
            } catch (e: Exception) {
                // Manejar el error
            }
        }
    }
}

