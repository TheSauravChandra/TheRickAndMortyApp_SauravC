package com.saurav.therickandmorty_sauravc.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.saurav.therickandmorty_sauravc.repo.CreatureRepo

class CreatureViewModelFactory constructor(private val repository: CreatureRepo) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(CreatureViewModel::class.java)) {
            CreatureViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}
