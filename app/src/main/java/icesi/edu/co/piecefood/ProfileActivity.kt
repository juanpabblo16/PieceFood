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

        bottomNavigationView.selectedItemId = R.id.user

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.kart -> {
                    // Navegar a la página de la canasta
                    val intent = Intent(this, KartActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.bag -> {
                    val intent = Intent(this, BagActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.user -> {
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

    }
}