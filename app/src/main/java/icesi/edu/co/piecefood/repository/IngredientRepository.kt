package icesi.edu.co.piecefood.repository

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import icesi.edu.co.piecefood.model.Ingredient
import icesi.edu.co.piecefood.services.IngredientServices
import kotlinx.coroutines.tasks.await

interface IngredientRepository {
    suspend fun addIngredient(ingredient: Ingredient): String
    suspend fun getIngredientNameById(id: String): String
    suspend fun loadIngredient(id: String): Ingredient?
    suspend fun loadIngredientList(): List<Ingredient>
}

class IngredientRepositoryImpl(
    private val ingredientServices: IngredientServices = IngredientServices()
) : IngredientRepository {

    override suspend fun addIngredient(ingredient: Ingredient): String {
        val documentRef = Firebase.firestore.collection("ingredients").add(ingredient).await()
        return documentRef.id
    }

    override suspend fun getIngredientNameById(id: String): String {
        return ingredientServices.getIngredientNameById(id)
    }

    override suspend fun loadIngredient(id: String): Ingredient? {
        return ingredientServices.loadIngredient(id)
    }

    override suspend fun loadIngredientList(): List<Ingredient> {
        return ingredientServices.loadAllIngredients()
    }
}

