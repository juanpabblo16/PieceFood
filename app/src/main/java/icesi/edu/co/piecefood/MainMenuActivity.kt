package icesi.edu.co.piecefood

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import icesi.edu.co.piecefood.databinding.MainMenuBinding
import icesi.edu.co.piecefood.viewmodel.MainMenuModel

class MainMenuActivity: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = MainMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}

