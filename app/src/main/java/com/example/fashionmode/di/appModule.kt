package com.example.fashionmode.di

import com.example.fashionmode.data.repository.AuthRepositoryImpl
import com.example.fashionmode.domain.repository.AuthRepository
import com.example.fashionmode.ui.features.signup.SignUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<AuthRepository> { AuthRepositoryImpl() }

    viewModel { SignUpViewModel(get()) }
}