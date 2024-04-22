package icesi.edu.co.piecefood

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import icesi.edu.co.piecefood.databinding.ActivitySignupBinding
import icesi.edu.co.piecefood.model.AppAuthState
import icesi.edu.co.piecefood.model.User
import icesi.edu.co.piecefood.viewmodel.SignupViewModel

class SignupActivity : AppCompatActivity() {

    val binding by lazy {
        ActivitySignupBinding.inflate(layoutInflater)
    }

    val viewModel: SignupViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.createAccountBtn.setOnClickListener {
            viewModel.signup(
                User("",
                    binding.usernameText.text.toString(),
                    binding.emailText.text.toString(),
                    binding.usernameText.text.toString()
                ),
                binding.passwordET.text.toString()

            )
        }

        // Configurar OnClickListener para el enlace de inicio de sesión
        binding.createAccountBtn.setOnClickListener {
            startActivity(Intent(this@SignupActivity, LoginActivity::class.java))
        }

        viewModel.authStatus.observe(this) {
            when (it) {
                is AppAuthState.Loading -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
                is AppAuthState.Error -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
                is AppAuthState.Success -> {
                    startActivity(
                        Intent(this@SignupActivity, ProfileActivity::class.java)
                    )
                }
            }
        }

    }
}