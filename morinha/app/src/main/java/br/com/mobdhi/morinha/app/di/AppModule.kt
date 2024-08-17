package br.com.mobdhi.morinha.app.di

import br.com.mobdhi.morinha.domain.repository.AuthRepository
import br.com.mobdhi.morinha.auth.AuthRepositoryImpl
import br.com.mobdhi.morinha.auth.login.LoginViewModel
import br.com.mobdhi.morinha.auth.register.RegisterViewModel
import br.com.mobdhi.morinha.domain.datasource.PetDataSource
import br.com.mobdhi.morinha.domain.repository.HomeRepository
import br.com.mobdhi.morinha.home.HomeRepositoryImpl
import br.com.mobdhi.morinha.home.HomeViewModel
import br.com.mobdhi.morinha.home.PetRemoteDataSourceImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun morinhaAppKoinModule() = module {
    single<FirebaseAuth> { FirebaseAuth.getInstance() }
    single<FirebaseFirestore> { FirebaseFirestore.getInstance() }

    single { PetRemoteDataSourceImpl(get(), get()) }

    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<HomeRepository> { HomeRepositoryImpl(get()) }

    viewModel { LoginViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { HomeViewModel(get()) }
}