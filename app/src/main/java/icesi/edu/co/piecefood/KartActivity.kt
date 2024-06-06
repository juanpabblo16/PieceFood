package icesi.edu.co.piecefood;

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import icesi.edu.co.piecefood.databinding.InformationProductBinding
import icesi.edu.co.piecefood.model.Ingredient
import icesi.edu.co.piecefood.model.Portion
import icesi.edu.co.piecefood.repository.IngredientRepository
import icesi.edu.co.piecefood.repository.IngredientRepositoryImpl
import icesi.edu.co.piecefood.repository.UserRepository
import icesi.edu.co.piecefood.repository.UserRepositoryImpl
import kotlinx.coroutines.launch

class KartActivity : AppCompatActivity() {

        private val binding by lazy {
                InformationProductBinding.inflate(layoutInflater)
        }
        private val userRepository: UserRepository = UserRepositoryImpl()
        private val ingredientRepository: IngredientRepository = IngredientRepositoryImpl()
        private val userId: String by lazy { FirebaseAuth.getInstance().currentUser?.uid.orEmpty() }

        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(binding.root)

                val bottomNavigationView = binding.bottomNavigationView4

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

