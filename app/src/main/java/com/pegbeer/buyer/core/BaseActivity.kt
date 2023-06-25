package com.pegbeer.buyer.core

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.pegbeer.buyer.MainActivity

abstract class BaseActivity<T: ViewBinding>(
    private val bindingProvider:((LayoutInflater) -> T)?
) : AppCompatActivity(){

    protected lateinit var binding:T


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(bindingProvider != null){
            binding = bindingProvider.invoke(layoutInflater)
            setContentView(binding.root)
        }
        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true){
                override fun handleOnBackPressed() {
                    defaultOnBackPressed()
                }
            }
        )
    }

    protected fun defaultOnBackPressed(isSystemBack: Boolean = true) {
        val shouldBuildBackstack = !isSystemBack
        if (shouldBuildBackstack && isTaskRoot) {
            startActivity(Intent(this@BaseActivity, MainActivity::class.java))
        }
        finish()
    }
}