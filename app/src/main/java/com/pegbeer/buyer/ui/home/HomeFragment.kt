package com.pegbeer.buyer.ui.home

import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.annotation.MenuRes
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.button.MaterialButtonToggleGroup.OnButtonCheckedListener
import com.google.android.material.snackbar.Snackbar
import com.pegbeer.buyer.R
import com.pegbeer.buyer.core.BaseFragment
import com.pegbeer.buyer.databinding.FragmentHomeBinding
import com.pegbeer.domain.model.Result
import com.pegbeer.domain.model.SortMode
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(
    FragmentHomeBinding::inflate
) {

    private val viewModel by viewModels<HomeViewModel>()
    private val adapter = ItemAdapter()
    private lateinit var snackbar: Snackbar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        binding.itemsRv.adapter = adapter
        snackbar = Snackbar.make(binding.root,"",Snackbar.LENGTH_LONG)
        viewModel.snackbar.observe(viewLifecycleOwner){
            if(it != null){
                snackbar.setText(it)
                snackbar.show()
            }
        }

        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.getItems().collect{
                withContext(Dispatchers.Main){
                    when(it.status){
                        Result.Status.LOADING ->{
                            showLoading()
                        }
                        Result.Status.SUCCESS ->{
                            adapter.submitList(it.data)
                            hideLoading()
                        }
                        Result.Status.ERROR ->{
                            viewModel.showSnackBar(it.error?.message)
                        }
                    }
                }
            }
        }

        binding.fabButton.setOnClickListener{
            showMenu(it,R.menu.menu_sorting)
        }
    }

    private fun showMenu(v:View,@MenuRes menuRes:Int){
        val popup = PopupMenu(context, v)
        popup.menuInflater.inflate(menuRes, popup.menu)
        popup.setOnMenuItemClickListener { menuItem: MenuItem ->
            when(menuItem.itemId){
                R.id.option_all -> viewModel.preferenceFilter.postValue(SortMode.All)
                R.id.option_cash -> viewModel.preferenceFilter.postValue(SortMode.Cash)
                R.id.option_credit_card -> viewModel.preferenceFilter.postValue(SortMode.CreditCard)
                R.id.option_tigo_money -> viewModel.preferenceFilter.postValue(SortMode.TigoMoney)
                R.id.option_despacho -> viewModel.preferenceFilter.postValue(SortMode.Despacho)
                else -> viewModel.preferenceFilter.postValue(SortMode.All)
            }
            true
        }
        popup.show()
    }

    private fun showLoading() {
        binding.loadingIndicator.isVisible = true
        binding.itemsRv.isVisible = false
    }

    private fun hideLoading(){
        binding.loadingIndicator.isVisible = false
        binding.itemsRv.isVisible = true
    }

    companion object{
        const val TAG = "HomeFragment"
    }
}