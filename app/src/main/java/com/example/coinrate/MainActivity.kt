package com.example.coinrate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainFragment = MainFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.FragmentContainer, mainFragment)

        val mainSubFragment = MainSubFragment()
        transaction.add(R.id.FragmentContainerSub, mainSubFragment)
        transaction.commit()
    }
}