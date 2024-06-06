package icesi.edu.co.piecefood.services

import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore
import icesi.edu.co.piecefood.model.Portion
import icesi.edu.co.piecefood.model.User
import kotlinx.coroutines.tasks.await

class UserServices {

    //CRUD

    suspend fun createUser(user: User){
        Firebase.firestore.collection("users").document(user.id).set(user).await()
    }

    suspend fun loadUser(id:String): DocumentSnapshot{
        val output = Firebase.firestore.collection("users").document(id).get().await()
        return output
    }

    suspend fun loadUserList(): QuerySnapshot{
        val output = Firebase.firestore.collection("users").get().await()
        return output
    }

    fun observeUser(id:String, listener: EventListener<DocumentSnapshot>){
        Firebase.firestore.collection("users").document(id)
            .addSnapshotListener(listener)
    }

    private val firestore = FirebaseFirestore.getInstance()

    suspend fun addPortionToUser(userId: String, portion: Portion) {
        firestore.collection("users").document(userId).collection("portions").add(portion).await()
    }

    suspend fun loadUserPortions(userId: String): List<Portion> {
        val querySnapshot = firestore.collection("users").document(userId).collection("portions").get().await()
        return querySnapshot.documents.mapNotNull { it.toObject(Portion::class.java) }
    }

    suspend fun updatePortionQuantity(userId: String, portion: Portion) {
        val userRef = Firebase.firestore.collection("users").document(userId)
        val portionsRef = userRef.collection("portions").document(portion.ingredientId)

        // Actualiza la cantidad de la porción en la base de datos
        portionsRef.update("quantity", portion.quantity).await()
    }

}