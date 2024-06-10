package icesi.edu.co.piecefood

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import icesi.edu.co.piecefood.databinding.RecipePublishBinding
import icesi.edu.co.piecefood.model.Recipes

class RecipePublishActivity : AppCompatActivity() {
    private lateinit var binding: RecipePublishBinding
    private val recipe = Recipes()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RecipePublishBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar los botones de categoría
        binding.breakfastBtn.setOnClickListener { recipe.category = "Desayuno" }
        binding.lunchBtn.setOnClickListener { recipe.category = "Almuerzo" }
        binding.dinnerBtn.setOnClickListener { recipe.category = "Cena" }

        // Configurar el campo de texto para los pasos
        binding.pasoTxt.setOnEditorActionListener { _, _, _ ->
           // recipe.steps = binding.pasoTxt.text.toString()
            true
        }

        // Configurar el campo de texto para los ingredientes
        binding.ingTxt.setOnEditorActionListener { _, _, _ ->
            recipe.ingredients = binding.ingTxt.text.toString()
            binding.ingTxt.text.clear()
            true
        }

        // Configurar el botón de agregar ingrediente
        binding.sumIngrBtn.setOnClickListener {
            val newIngredientEditText = EditText(this)
            newIngredientEditText.hint = "Ingrediente"
            binding.ingContainer.addView(newIngredientEditText)
        }

        // Configurar el botón de publicar receta
        binding.publishBtn.setOnClickListener {
            // Crear un documento en la colección "recipes" de Firebase
            val db = Firebase.firestore
            val recipeRef = db.collection("recipes").document()
            recipeRef.set(recipe)
                .addOnSuccessListener {
                    Toast.makeText(this, "Receta publicada con éxito", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error al publicar la receta: $e", Toast.LENGTH_SHORT).show()
                }
        }
    }
}

