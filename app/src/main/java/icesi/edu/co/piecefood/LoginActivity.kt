package icesi.edu.co.piecefood

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import icesi.edu.co.piecefood.databinding.ActivityLoginBinding
import icesi.edu.co.piecefood.model.AppAuthState
import icesi.edu.co.piecefood.viewmodel.LoginViewModel
class   LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)



        // Configurar el botón de inicio de sesión
        binding.loginBtn.setOnClickListener {
            val passwordEditText = binding.passwordET.editText
            val passwordLogin = passwordEditText?.text.toString()
            val email = binding.emailET.text.toString()
            val password = passwordLogin
            viewModel.login(email, password)
        }



        binding.singupBtn.setOnClickListener {
            startActivity(Intent(this@LoginActivity, SignupActivity::class.java))
        }



        // Observar el estado de autenticación y tomar acciones en consecuencia
        viewModel.authState.observe(this) { state ->
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
                    // Navegar a la actividad de perfil después de iniciar sesión correctamente
                    startActivity(Intent(this@LoginActivity, ProfileActivity::class.java))
                    finish()
                }
            }
        }
    }
}
