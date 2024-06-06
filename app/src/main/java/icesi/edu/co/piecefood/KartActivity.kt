package icesi.edu.co.piecefood;

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import icesi.edu.co.piecefood.databinding.RecipesFeedBinding

class KartActivity : AppCompatActivity() {

        private val binding by lazy {
                RecipesFeedBinding.inflate(layoutInflater)
        }
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        // Configura tu lógica para mostrar la información de la canasta aquí
        // Por ejemplo, podrías configurar un RecyclerView para mostrar la lista de recetas
        setupRecyclerView()
        }

private fun setupRecyclerView() {
        // Configura el RecyclerView para mostrar la lista de recetas
        // binding.recyclerView.adapter = YourAdapter()
        // binding.recyclerView.layoutManager = LinearLayoutManager(this)
        }
        }

