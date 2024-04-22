package icesi.edu.co.piecefood.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import icesi.edu.co.piecefood.model.AppAuthState
import icesi.edu.co.piecefood.repository.AuthRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _authState = MutableLiveData<AppAuthState>()
    val authState: LiveData<AppAuthState>
        get() = _authState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AppAuthState.Loading("Logging in...")
            val authState = authRepository.login(email, password)
            _authState.value = authState
        }
    }
}
