package icesi.edu.co.piecefood;

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import icesi.edu.co.piecefood.databinding.InformationProductBinding
import icesi.edu.co.piecefood.model.Ingredient
import icesi.edu.co.piecefood.model.Portion
import icesi.edu.co.piecefood.repository.IngredientRepository
import icesi.edu.co.piecefood.repository.IngredientRepositoryImpl
import icesi.edu.co.piecefood.repository.UserRepository
import icesi.edu.co.piecefood.repository.UserRepositoryImpl
import icesi.edu.co.piecefood.viewmodel.CanastaViewModel
import kotlinx.coroutines.launch

class KartActivity : AppCompatActivity() {

        private val binding by lazy {
                InformationProductBinding.inflate(layoutInflater)
        }
        private val userRepository: UserRepository = UserRepositoryImpl()
        private val ingredientRepository: IngredientRepository = IngredientRepositoryImpl()
        private val viewModel: CanastaViewModel by viewModels()
        private val userId: String by lazy { FirebaseAuth.getInstance().currentUser?.uid.orEmpty() }

        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(binding.root)

                // Observe the LiveData from the ViewModel
                viewModel.ingredients.observe(this, Observer { ingredients ->
                        // Actualiza la UI con la lista de ingredientes
                        // Por ejemplo, podrías actualizar un RecyclerView aquí
                        updateUI(ingredients)
                })

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

                binding.addButton.setOnClickListener {
                        val ingredient = Ingredient(
                                name = "Tomate",
                                type = "Vegetable",
                                calories = 18,
                                proteins = 1,
                                fat = 0
                        )

                        viewModel.addIngredientToUser(userId, ingredient, 5)
                }

                // Cargar los ingredientes del usuario al iniciar la actividad
                viewModel.loadUserIngredients(userId)

        }

        private fun updateUI(ingredients: List<Ingredient>) {
                // Aquí actualizas la UI, por ejemplo, notificando un adaptador de RecyclerView
                // adaptador.submitList(ingredients)
        }

}

