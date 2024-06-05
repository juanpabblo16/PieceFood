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
            val passwordEditText = binding.passwordText.editText
            val confirmPasswordEditText = binding.confirmPasswordText.editText

            val password = passwordEditText?.text.toString()
            val confirmPassword = confirmPasswordEditText?.text.toString()

            if (password == confirmPassword) {
                // Las contraseñas coinciden
                viewModel.signup(
                    User("",
                        binding.usernameText.text.toString(),
                        binding.emailText.text.toString(),
                        binding.fullnameText.text.toString()
                    ),
                    password
                )

                //El inicio a la pagina de Login lo puse en el Observer (linea 56 de esta clase)
            } else {
                //Las contraseñas no coinciden
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            }
        }

        binding.backBtn.setOnClickListener {
            startActivity(Intent(this@SignupActivity, LoginActivity::class.java))
        }




        viewModel.authStatus.observe(this) {state ->
            when (state) {
                is AppAuthState.Loading -> {
                    Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()
                }
                is AppAuthState.Error -> {
                    Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()
                }
                is AppAuthState.Success -> {
                    Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()
                    startActivity(
                        Intent(this@SignupActivity, LoginActivity::class.java)
                    )
                }
            }
        }

    }
}