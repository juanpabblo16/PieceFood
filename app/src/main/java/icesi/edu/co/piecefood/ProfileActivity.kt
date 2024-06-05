package icesi.edu.co.piecefood

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import icesi.edu.co.piecefood.databinding.ActivityProfileBinding
import icesi.edu.co.piecefood.viewmodel.ProfileViewModel

class ProfileActivity : AppCompatActivity() {

    val binding by lazy{
        ActivityProfileBinding.inflate(layoutInflater)
    }

    val viewmodel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.recipesOrPostsNav.selectedItemId = R.id.profile_posts
        binding.menuNav.selectedItemId = R.id.profile

        //Carga al usuario actual
        viewmodel.loadUser() //viewmodel.observeUser()



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
                    startActivity(Intent(this@ProfileActivity, RecipesFeedActivity::class.java))
                    true
                }
                R.id.profile -> {
                    true
                }

                else -> {false}
            }
        }

        viewmodel.userState.observe(this){
            binding.fullnameText.text = it.name
        }


    }
}