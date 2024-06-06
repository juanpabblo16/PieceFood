package icesi.edu.co.piecefood;

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth
import icesi.edu.co.piecefood.adapter.PortionAdapter
import icesi.edu.co.piecefood.databinding.InformationProductBinding
import icesi.edu.co.piecefood.model.Portion
import icesi.edu.co.piecefood.viewmodel.CanastaViewModel

class KartActivity : AppCompatActivity() {

        private val binding by lazy {
                InformationProductBinding.inflate(layoutInflater)
        }
        private val viewModel: CanastaViewModel by viewModels()
        private val userId: String by lazy { FirebaseAuth.getInstance().currentUser?.uid.orEmpty() }
        private lateinit var portionAdapter: PortionAdapter

        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(binding.root)

                // Configurar ListView
                portionAdapter = PortionAdapter(this, emptyList(), emptyMap())
                binding.productList.adapter = portionAdapter

                // Observe the LiveData from the ViewModel
                viewModel.portions.observe(this, Observer { portions ->
                        viewModel.ingredientNames.value?.let { ingredientNames ->
                                updateUI(portions, ingredientNames)
                        }
                })

                // Escuchar los clics en los elementos de la lista
                binding.productList.setOnItemClickListener { parent, view, position, id ->
                        val portion = portionAdapter.getItem(position)
                        viewModel.selectPortion(portion)
                }

                // Observar los cambios en la cantidad seleccionada
                viewModel.selectedPortion.observe(this, Observer { selectedPortion ->
                        selectedPortion?.let {
                                //binding.selectedIngredientTextView.text = "Selected Ingredient: ${it.ingredientId}"
                                binding.quantity.text = it.quantity.toString()
                        }
                })

                viewModel.ingredientNames.observe(this, Observer { ingredientNames ->
                        viewModel.portions.value?.let { portions ->
                                updateUI(portions, ingredientNames)
                        }
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

                // Configurar los clics de los botones + y -
                binding.plusBtn.setOnClickListener {
                        viewModel.increaseQuantity()
                }

                binding.minusBtn.setOnClickListener {
                        viewModel.decreaseQuantity()
                }

                binding.addButton.setOnClickListener {
                        viewModel.saveQuantity(userId)
                }

                // Cargar las porciones del usuario al iniciar la actividad
                viewModel.loadUserPortions(userId)

        }

        private fun updateUI(portions: List<Portion>, ingredientNames: Map<String, String>) {
                portionAdapter.updatePortions(portions, ingredientNames)
        }

}

