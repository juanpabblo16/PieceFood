package icesi.edu.co.piecefood

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


        viewmodel.loadUser() //viewmodel.observeUser()

        viewmodel.userState.observe(this){
            binding.emailTV.text = it.email

        }
    }
}