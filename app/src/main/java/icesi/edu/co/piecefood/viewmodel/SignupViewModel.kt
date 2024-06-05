package icesi.edu.co.piecefood.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import icesi.edu.co.piecefood.model.AppAuthState
import icesi.edu.co.piecefood.model.User
import icesi.edu.co.piecefood.repository.AuthRepository
import icesi.edu.co.piecefood.repository.AuthRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignupViewModel(val repo: AuthRepository = AuthRepositoryImpl()) : ViewModel() {

    val authStatus = MutableLiveData<AppAuthState>()

    fun signup(user: User, pass: String) {
        viewModelScope.launch(Dispatchers.IO) {

            withContext(Dispatchers.Main){
                authStatus.value = AppAuthState.Loading("Cargando...")
            }
            val status = repo.signup(user, pass) //10s
            withContext(Dispatchers.Main){
                authStatus.value = status
            }

        }
    }

}