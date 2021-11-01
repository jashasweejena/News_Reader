package com.example.newsreader.ui.activities

import android.os.Bundle
import androidx.fragment.app.FragmentFactory
import com.example.newsreader.databinding.ActivityMainBinding
import com.example.newsreader.di.DaggerBaseActivity
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : DaggerBaseActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding

    @Inject
    lateinit var fragmentFactory: FragmentFactory

    override fun executeBeforeOnCreate() {
        supportFragmentManager.fragmentFactory = fragmentFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater, null, false)
        setContentView(activityMainBinding.root)
    }
}