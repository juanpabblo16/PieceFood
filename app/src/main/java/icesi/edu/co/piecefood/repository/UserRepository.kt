package icesi.edu.co.piecefood.repository

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import icesi.edu.co.piecefood.model.Portion
import icesi.edu.co.piecefood.model.User
import icesi.edu.co.piecefood.services.UserServices

interface UserRepository {

    suspend fun loadUser() : User?

    fun observeUser(callback:(User) -> Unit)

    suspend fun addPortionToUser(userId: String, portion: Portion)
    suspend fun loadUserPortions(userId: String): List<Portion>

}

class UserRepositoryImpl(
    val userServices: UserServices = UserServices()
): UserRepository {

    override suspend fun loadUser(): User?{
        val document = userServices.loadUser(Firebase.auth.uid!!)
        //Document -> Obj
        val user = document.toObject(User::class.java)
        return user
    }

    override fun observeUser(callback: (User) -> Unit) {
        userServices.observeUser(Firebase.auth.uid!!){ snapshot, error ->
            val user = snapshot?.toObject(User::class.java)
            user?.let{
                callback(it)
            }
        }
    }

    override suspend fun addPortionToUser(userId: String, portion: Portion) {
        userServices.addPortionToUser(userId, portion)
    }

    override suspend fun loadUserPortions(userId: String): List<Portion> {
        return userServices.loadUserPortions(userId)
    }

}