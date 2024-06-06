package icesi.edu.co.piecefood;

import android.app.Activity;
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import icesi.edu.co.piecefood.adapter.IngredientAdapter
import icesi.edu.co.piecefood.databinding.MainMenuBinding

class BagActivity : AppCompatActivity() {

    private val binding by lazy {
        MainMenuBinding.inflate(layoutInflater)
    }
    private lateinit var recyclerView: RecyclerView
    private lateinit var ingredientAdapter: IngredientAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Aquí debes obtener la lista de ingredientes de la base de datos
        val ingredients = listOf("Tomate", "Lechuga", "Apio", "Manzana", "Papa")

        ingredientAdapter = IngredientAdapter(ingredients)
        recyclerView.adapter = ingredientAdapter

        val bottomNavigationView = binding.bottomNavigationView

        bottomNavigationView.selectedItemId = R.id.bag

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

