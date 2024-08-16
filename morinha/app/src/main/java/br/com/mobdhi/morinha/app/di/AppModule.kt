package br.com.mobdhi.morinha.app.di

import br.com.mobdhi.morinha.domain.repository.AuthRepository
import br.com.mobdhi.morinha.auth.AuthRepositoryImpl
import br.com.mobdhi.morinha.auth.login.LoginViewModel
import br.com.mobdhi.morinha.auth.register.RegisterViewModel
import br.com.mobdhi.morinha.domain.repository.HomeRepository
import br.com.mobdhi.morinha.home.HomeRepositoryImpl
import br.com.mobdhi.morinha.home.HomeViewModel
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun remedyClockAppKoinModule() = module {
    single<AuthRepository> {
        AuthRepositoryImpl(auth = FirebaseAuth.getInstance())
    }
    single<HomeRepository> {
        HomeRepositoryImpl()
    }

    viewModel { LoginViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { HomeViewModel(get()) }
}