package icesi.edu.co.piecefood

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import icesi.edu.co.piecefood.databinding.RecipesFeedBinding


class RecipesFeedActivity: AppCompatActivity() {

    val binding by lazy{
        RecipesFeedBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.menuNav.selectedItemId = R.id.bag

        //Es el que se encarga de pasar entre paginas con el nav bar de main menu
        binding.menuNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    true
                }
                R.id.kart -> {
                    true
                }
                R.id.bag -> {
                    true
                }
                R.id.profile -> {
                    startActivity(Intent(this@RecipesFeedActivity, ProfileActivity::class.java))
                    true
                }

                else -> {false}
            }
        }

    }

}