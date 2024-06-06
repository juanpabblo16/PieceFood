package icesi.edu.co.piecefood;

import android.app.Activity;
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import icesi.edu.co.piecefood.databinding.RecipesFeedBinding
import icesi.edu.co.piecefood.repository.IngredientRepository
import icesi.edu.co.piecefood.repository.IngredientRepositoryImpl
import icesi.edu.co.piecefood.repository.UserRepository
import icesi.edu.co.piecefood.repository.UserRepositoryImpl

class HomeActivity : AppCompatActivity() {

    private val binding by lazy {
        RecipesFeedBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val bottomNavigationView = binding.bottomNavigationView

        bottomNavigationView.selectedItemId = R.id.home

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
