package icesi.edu.co.icesiapp241.repository

import android.net.wifi.WifiConfiguration.AuthAlgorithm
import com.google.firebase.auth.FirebaseAuthException
import icesi.edu.co.icesiapp241.domain.model.AppAuthState
import icesi.edu.co.icesiapp241.domain.model.AuthStatus
import icesi.edu.co.icesiapp241.services.AuthServices

interface AuthRepository {

        suspend fun login(email: String, pass: String):AppAuthState
    suspend fun singup(email: String, pass: String):AppAuthState

}

class AuthRepositoryImpl(val service:AuthServices = AuthServices()):AuthRepository{
    override suspend fun login(email:String, pass:String) : AppAuthState {
        try {
            val result = service.singup(email, pass)
            result.user?.let { return AppAuthState.Succes(it.uid) }?:run{return AppAuthState.Error("")}
        }catch (ex: FirebaseAuthException){
            return AppAuthState.Error("")
        }

    }

    override suspend fun singup(email:String, pass:String): AppAuthState {

        try {
            val result = service.singup(email, pass)
            result.user?.let { return AppAuthState.Succes(it.uid)
            }?:run{return AppAuthState.Error("Something went wrong")}
        }catch (ex: FirebaseAuthException){
            return AppAuthState.Error(ex.errorCode)
        }

    }
}

