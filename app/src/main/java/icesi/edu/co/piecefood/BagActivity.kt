package icesi.edu.co.piecefood;

import android.app.Activity;
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import icesi.edu.co.piecefood.databinding.MainMenuBinding

class BagActivity : AppCompatActivity() {

    private val binding by lazy {
        MainMenuBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val bottomNavigationView = binding.bottomNavigationView

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

