package icesi.edu.co.icesiapp241

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import icesi.edu.co.icesiapp241.databinding.ActivityLoginBinding
import icesi.edu.co.icesiapp241.domain.model.AppAuthState
import icesi.edu.co.icesiapp241.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginBtn.setOnClickListener {
            val email = binding.emailET.text.toString()
            val password = binding.passwordET.text.toString()
            viewModel.login(email, password)
        }

        viewModel.authStatus.observe(this) { state ->
            when (state) {
                is AppAuthState.Loading -> {
                    // Mostrar indicador de carga
                    Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
                }
                is AppAuthState.Error -> {
                    // Mostrar mensaje de error
                    Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
                }
                is AppAuthState.Success -> {
                    // Navegar a la actividad de perfil
                    startActivity(Intent(this@LoginActivity, ProfileActivity::class.java))
                    finish()
                }
            }
        }
    }
}
