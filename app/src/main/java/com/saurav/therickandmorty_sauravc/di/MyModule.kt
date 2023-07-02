package com.saurav.therickandmorty_sauravc.di

import com.saurav.therickandmorty_sauravc.adapter.CreatureAdapter
import com.saurav.therickandmorty_sauravc.repo.CreatureRepo
import com.saurav.therickandmorty_sauravc.retrofit.RetrofitHelper
import com.saurav.therickandmorty_sauravc.viewmodel.CreatureViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { RetrofitHelper.getInstance() }
    factory { CreatureRepo(get()) }
    factory { CreatureAdapter(get()) }
    viewModel { CreatureViewModel(get()) }
}
