package icesi.edu.co.piecefood.repository

import com.google.firebase.database.FirebaseDatabase
import icesi.edu.co.piecefood.model.Ingredient
import icesi.edu.co.piecefood.services.IngredientServices

interface IngredientRepository {
    suspend fun addIngredient(ingredient: Ingredient): String
    suspend fun loadIngredient(id: String): Ingredient?
    suspend fun loadIngredientList(): List<Ingredient>
}

class IngredientRepositoryImpl(
    private val ingredientServices: IngredientServices = IngredientServices()
) : IngredientRepository {

    override suspend fun addIngredient(ingredient: Ingredient): String {
        return ingredientServices.createIngredient(ingredient)
    }

    override suspend fun loadIngredient(id: String): Ingredient? {
        return ingredientServices.loadIngredient(id)
    }

    override suspend fun loadIngredientList(): List<Ingredient> {
        return ingredientServices.loadIngredientList()
    }
}

