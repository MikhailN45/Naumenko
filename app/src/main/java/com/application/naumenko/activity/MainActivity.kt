package com.application.naumenko.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.application.naumenko.R
import com.application.naumenko.data.Film
import com.application.naumenko.databinding.ActivityMainBinding
import com.application.naumenko.fragment.FragmentFilmDetails
import com.application.naumenko.fragment.FragmentFilmList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), FragmentFilmDetails.FilmDetailsClick {

    private lateinit var binding: ActivityMainBinding
    private lateinit var rootFragment: FragmentFilmList
    private lateinit var detailsFragment: FragmentFilmDetails

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //loadFilms()
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            rootFragment = FragmentFilmList.newInstance()
            supportFragmentManager.beginTransaction()
                .add(
                    R.id.fragment_container,
                    rootFragment,
                    FragmentFilmList.TAG
                )
                .commit()
        } else {
            val fragmentFilmList =
                supportFragmentManager.findFragmentByTag(FragmentFilmList.TAG)
            rootFragment = fragmentFilmList as FragmentFilmList

            var fragmentFilmDetails =
                supportFragmentManager.findFragmentByTag(FragmentFilmDetails.TAG)
            fragmentFilmDetails = fragmentFilmDetails as FragmentFilmDetails
        }
    }

    override fun onBackClick() {
        onBackPressed()
    }

    /*private fun loadFilms() {
        lifecycleScope.launch { Dispatchers.IO }
        films = loadFilms(applicationContext)
    }
*/
    companion object {
        var films: List<Film> = listOf()
    }
}