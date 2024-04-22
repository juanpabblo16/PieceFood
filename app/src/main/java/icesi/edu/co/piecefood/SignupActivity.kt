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

            val password = binding.passwordET.text.toString()
            val confirmPassword = binding.confirmpassET.text.toString()

            if (password.compareTo(confirmPassword)==0) {
                // Las contraseñas coinciden
                viewModel.signup(
                    User("",
                        binding.usernameText.text.toString(),
                        binding.emailText.text.toString()
                    ),
                    password
                )

                // Ir a la pantalla de Login
                startActivity(Intent(this@SignupActivity, LoginActivity::class.java))
            } else {
                //Las contraseñas no coinciden
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            }
        }

        binding.backBtn.setOnClickListener {
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