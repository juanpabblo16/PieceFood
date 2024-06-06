package icesi.edu.co.piecefood.services

import com.google.firebase.firestore.FirebaseFirestore
import icesi.edu.co.piecefood.model.Ingredient
import kotlinx.coroutines.tasks.await

class IngredientServices {

    private val firestore = FirebaseFirestore.getInstance()

    // CRUD Operations

    suspend fun createIngredient(ingredient: Ingredient): String {
        val docRef = firestore.collection("ingredients").add(ingredient).await()
        return docRef.id
    }

    suspend fun loadIngredient(id: String): Ingredient? {
        val snapshot = firestore.collection("ingredients").document(id).get().await()
        return snapshot.toObject(Ingredient::class.java)
    }

    suspend fun loadIngredientList(): List<Ingredient> {
        val querySnapshot = firestore.collection("ingredients").get().await()
        return querySnapshot.documents.mapNotNull { it.toObject(Ingredient::class.java) }
    }
}

