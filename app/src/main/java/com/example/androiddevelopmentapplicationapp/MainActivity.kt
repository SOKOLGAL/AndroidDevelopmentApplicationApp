package com.example.androiddevelopmentapplicationapp

import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.androidapplicationdevelopmentxml.R
import com.example.androidapplicationdevelopmentxml.databinding.ActivityMainBinding
import com.example.androiddevelopmentapplicationapp.com.example.androiddevelopmentapplicationapp.CategoriesListFragment
import com.example.androiddevelopmentapplicationapp.com.example.androiddevelopmentapplicationapp.FavoritesFragment

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding ?: throw IllegalArgumentException("ActivityMainBinding is null!")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= 35) {
            enableEdgeToEdge()
        }

        _binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<CategoriesListFragment>(R.id.mainContainer)
            addToBackStack(null)
        }

        binding.btnCategories.setOnClickListener {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<CategoriesListFragment>(R.id.mainContainer)
                addToBackStack(null)
            }
        }

        binding.btnFavorites.setOnClickListener {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<FavoritesFragment>(R.id.mainContainer)
                addToBackStack(null)
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}