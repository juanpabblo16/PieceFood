package icesi.edu.co.piecefood

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import icesi.edu.co.piecefood.databinding.RecipePublishBinding
import icesi.edu.co.piecefood.model.Recipes
import java.util.UUID

class RecipePublishActivity : AppCompatActivity() {

    private lateinit var binding: RecipePublishBinding
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>
    private var imageUri: Uri? = null
    private lateinit var auth: FirebaseAuth
    private var selectedCategory: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RecipePublishBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(), ::onGalleryResult)

        // Configurar el listener del botón para seleccionar imagen
        binding.btnSelectImage.setOnClickListener {
            val toast = Toast.makeText(this, "Mantén presionado para seleccionar una imagen", Toast.LENGTH_SHORT)
            toast.show()
        }

        // Configurar los listeners de los botones de desayuno, almuerzo y cena
        binding.desButton.setOnClickListener {
            selectCategoryButton(binding.desButton, "Desayuno")
        }

        binding.almButton.setOnClickListener {
            selectCategoryButton(binding.almButton, "Almuerzo")
        }

        binding.cenaButton.setOnClickListener {
            selectCategoryButton(binding.cenaButton, "Cena")
        }

        binding.btnSelectImage.setOnLongClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            galleryLauncher.launch(intent)
            true
        }

        binding.sumPasoBtn.setOnClickListener {
            val newPasoText = binding.pasoTxt.text.toString().trim()
            if (newPasoText.isNotEmpty()) {
                addTextView(binding.pasoContainer, newPasoText, true)
                binding.pasoTxt.text.clear() // Limpiar pasoTxt después de agregar un paso
            }
        }

        binding.sumIngrBtn.setOnClickListener {
            val newIngredienteText = binding.ingTxt.text.toString().trim()
            if (newIngredienteText.isNotEmpty()) {
                addTextView(binding.ingContainer, newIngredienteText, false)
                binding.ingTxt.text.clear() // Limpiar ingTxt después de agregar un ingrediente
            }
        }

        binding.publishBtn.setOnClickListener {
            publishRecipe()
        }
    }

    private fun selectCategoryButton(selectedButton: Button, category: String) {
        binding.desButton.isSelected = false
        binding.almButton.isSelected = false
        binding.cenaButton.isSelected = false

        selectedButton.isSelected = true
        selectedButton.setBackgroundResource(R.drawable.round_button_selected)

        when (selectedButton) {
            binding.desButton -> {
                binding.almButton.setBackgroundResource(R.drawable.round_button_green)
                binding.cenaButton.setBackgroundResource(R.drawable.round_button_green)
            }
            binding.almButton -> {
                binding.desButton.setBackgroundResource(R.drawable.round_button_green)
                binding.cenaButton.setBackgroundResource(R.drawable.round_button_green)
            }
            binding.cenaButton -> {
                binding.desButton.setBackgroundResource(R.drawable.round_button_green)
                binding.almButton.setBackgroundResource(R.drawable.round_button_green)
            }
        }

        selectedCategory = category
    }

    private fun addTextView(container: LinearLayout, text: String, isPaso: Boolean) {
        Log.d("RecipePublishActivity", "Adding TextView: $text")
        val textView = TextView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            setText(text)
            textSize = 16f
            if (isPaso) {
                setTextAppearance(R.style.PasoTextStyle)
            } else {
                setTextAppearance(R.style.IngredienteTextStyle)
            }
        }
        container.addView(textView)
        Log.d("RecipePublishActivity", "TextView added: ${textView.text}")
    }

    private fun onGalleryResult(result: ActivityResult) {
        if (result.resultCode == RESULT_OK) {
            val uri = result.data?.data
            if (uri != null) {
                imageUri = uri // Guarda el URI de la imagen para usarlo al guardar la receta
                Toast.makeText(this, "Imagen seleccionada", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun publishRecipe() {
        val nombreReceta = binding.nombreReceta.text.toString().trim()
        val pasos = mutableListOf<String>()
        val ingredientes = mutableListOf<String>()

        for (i in 0 until binding.pasoContainer.childCount) {
            val textView = binding.pasoContainer.getChildAt(i) as? TextView
            textView?.let {
                pasos.add(it.text.toString())
            }
        }

        for (i in 0 until binding.ingContainer.childCount) {
            val textView = binding.ingContainer.getChildAt(i) as? TextView
            textView?.let {
                ingredientes.add(it.text.toString())
            }
        }

        Log.d("RecipePublishActivity", "Nombre Receta: $nombreReceta")
        Log.d("RecipePublishActivity", "Pasos: $pasos")
        Log.d("RecipePublishActivity", "Ingredientes: $ingredientes")
        Log.d("RecipePublishActivity", "Categoría: $selectedCategory")

        if (nombreReceta.isNotEmpty() && pasos.isNotEmpty() && ingredientes.isNotEmpty() && !selectedCategory.isNullOrEmpty()) {
            // Subir la imagen y luego publicar la receta si se seleccionó una imagen
            if (imageUri != null) {
                val filename = UUID.randomUUID().toString()
                val storageRef = Firebase.storage.reference.child("Foto_Receta").child(filename)

                storageRef.putFile(imageUri!!)
                    .addOnSuccessListener {
                        storageRef.downloadUrl.addOnSuccessListener { uri ->
                            val recipeID = UUID.randomUUID().toString()
                            val userID = auth.currentUser?.uid ?: "unknown_user"

                            val recipe = Recipes(
                                category = selectedCategory!!,
                                id = recipeID,
                                image = uri.toString(),
                                ingredients = ingredientes.joinToString(", "),
                                name = nombreReceta,
                                userID = userID,
                                photoID = filename
                            )

                            val database = Firebase.database
                            val recipeRef = database.reference.child("recipes").child(recipeID)
                            recipeRef.setValue(recipe)
                                .addOnSuccessListener {
                                    Toast.makeText(this, "Receta publicada con éxito", Toast.LENGTH_SHORT).show()
                                    clearFields()
                                }
                                .addOnFailureListener {
                                    Toast.makeText(this, "Error al publicar la receta", Toast.LENGTH_SHORT).show()
                                }
                        }
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Error al subir la imagen", Toast.LENGTH_SHORT).show()
                    }
            } else {
                // Publicar la receta sin imagen
                val recipeID = UUID.randomUUID().toString()
                val userID = auth.currentUser?.uid ?: "unknown_user"

                val recipe = Recipes(
                    category = selectedCategory!!,
                    id = recipeID,
                    image = "", // No hay imagen, la receta puede ir sin imagen
                    ingredients = "Ingredientes",
                    name = nombreReceta,
                    userID = userID,
                    photoID = ""
                )

                val database = Firebase.database
                val recipeRef = database.reference.child("recipes").child(recipeID)
                recipeRef.setValue(recipe)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Receta publicada con éxito", Toast.LENGTH_SHORT).show()
                        clearFields()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Error al publicar la receta", Toast.LENGTH_SHORT).show()
                    }
            }
        } else {
            Toast.makeText(this, "Por favor complete todos los campos y seleccione una categoría", Toast.LENGTH_SHORT).show()
        }
    }

    private fun clearFields() {
        binding.nombreReceta.text.clear()
        binding.pasoContainer.removeAllViews()
        binding.ingContainer.removeAllViews()
        binding.desButton.isSelected = false
        binding.almButton.isSelected = false
        binding.cenaButton.isSelected = false
        binding.desButton.setBackgroundResource(R.drawable.round_button_green)
        binding.almButton.setBackgroundResource(R.drawable.round_button_green)
        binding.cenaButton.setBackgroundResource(R.drawable.round_button_green)
        selectedCategory = null
        imageUri = null
    }
}
