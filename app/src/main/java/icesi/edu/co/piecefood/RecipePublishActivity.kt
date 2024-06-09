package icesi.edu.co.piecefood

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import icesi.edu.co.piecefood.databinding.RecipePublishBinding

class RecipePublishActivity : AppCompatActivity() {

    private lateinit var binding: RecipePublishBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RecipePublishBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar el listener del botón para seleccionar imagen
        binding.btnSelectImage.setOnClickListener {
            val toast = Toast.makeText(this, "This is a toast message", Toast.LENGTH_SHORT)
            toast.show()
        }

        // Configurar los listeners de los botones de desayuno, almuerzo y cena
        binding.desButton.setOnClickListener {
            showToastWithButtonText(binding.desButton.text.toString())
        }

        binding.almButton.setOnClickListener {
            showToastWithButtonText(binding.almButton.text.toString())
        }

        binding.cenaButton.setOnClickListener {
            showToastWithButtonText(binding.cenaButton.text.toString())
        }
    }

    private fun showToastWithButtonText(buttonText: String) {
        Toast.makeText(this, buttonText, Toast.LENGTH_SHORT).show()
    }
}
