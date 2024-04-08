package icesi.edu.co.icesiapp241

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import icesi.edu.co.icesiapp241.databinding.ActivitySignupBinding
import icesi.edu.co.icesiapp241.domain.model.AppAuthState
import icesi.edu.co.icesiapp241.domain.model.AuthStatus
import icesi.edu.co.icesiapp241.viewmodel.SingUpViewModel

class SignupActivity : AppCompatActivity() {

    val binding by lazy{
        ActivitySignupBinding.inflate(layoutInflater)
    }

    val viewModel:SingUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.signupBtn.setOnClickListener{
            viewModel.singUp(
                binding.emailET.text.toString(),
                binding.passET.text.toString())
        }

        viewModel.authStatus.observe(this){
            when(it){
                is AppAuthState.Loading ->{
                    Toast.makeText(this, "Cargando...", Toast.LENGTH_LONG).show()
                }
                is AppAuthState.Error ->{
                   Toast.makeText(this, "Error.", Toast.LENGTH_LONG).show()
                }
                is AppAuthState.Succes ->{
                    Toast.makeText(this, "Bienvenido ${it.UserID}", Toast.LENGTH_LONG).show()

                }
            }
        }
    }
}