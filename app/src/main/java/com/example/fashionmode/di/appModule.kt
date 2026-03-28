package com.example.fashionmode.di

import com.example.fashionmode.data.repository.AuthRepositoryImpl
import com.example.fashionmode.data.repository.ProductRepositoryImpl
import com.example.fashionmode.domain.repository.AuthRepository
import com.example.fashionmode.domain.repository.ProductRepository
import com.example.fashionmode.ui.features.client.Main.MainViewModelClient
import com.example.fashionmode.ui.features.client.basket.BasketViewModel
import com.example.fashionmode.ui.features.client.ribbon.RibbonViewModel
import com.example.fashionmode.ui.features.client.ribbon.preview.PreviewViewModel
import com.example.fashionmode.ui.features.signin.SignInViewModel
import com.example.fashionmode.ui.features.signup.SignUpViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    // Firebase
    single { FirebaseFirestore.getInstance() }
    single { FirebaseAuth.getInstance() }

    // Repositories
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
    single<ProductRepository> { ProductRepositoryImpl(get()) }

    // ViewModels
    viewModel { SignUpViewModel(get()) }
    viewModel { SignInViewModel(get()) }
    viewModel { MainViewModelClient() }
    viewModel { RibbonViewModel(get()) }

    // Твоя новая корзина
    viewModel { BasketViewModel(get()) }

    viewModel { (productId: String) ->
        PreviewViewModel(productId = productId, repository = get(), auth = get())
    }
}