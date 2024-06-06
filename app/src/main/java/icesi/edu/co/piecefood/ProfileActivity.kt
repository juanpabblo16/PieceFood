package icesi.edu.co.piecefood

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import icesi.edu.co.piecefood.databinding.ActivityProfileBinding
import icesi.edu.co.piecefood.viewmodel.ProfileViewModel

class ProfileActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityProfileBinding.inflate(layoutInflater)
    }

    private val viewmodel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewmodel.loadUser()

        viewmodel.userState.observe(this) {
            binding.emailTV.text = it.email
        }

        val bottomNavigationView = binding.bottomNavigationView

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    // Aquí maneja la navegación a la página de inicio si es necesario
                    true
                }
                R.id.kart -> {
                    // Navegar a la página de la canasta
                    val intent = Intent(this, KartActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.bag -> {
                    // Aquí maneja la navegación a la página del bag si es necesario
                    true
                }
                R.id.user -> {
                    // Aquí maneja la navegación a la página de usuario si es necesario
                    true
                }
                else -> false
            }
        }
    }
}