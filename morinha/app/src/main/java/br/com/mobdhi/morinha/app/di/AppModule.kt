package br.com.mobdhi.morinha.app.di

import br.com.mobdhi.morinha.domain.repository.AuthRepository
import br.com.mobdhi.morinha.auth.AuthRepositoryImpl
import br.com.mobdhi.morinha.auth.login.LoginViewModel
import br.com.mobdhi.morinha.auth.register.RegisterViewModel
import br.com.mobdhi.morinha.domain.repository.PetRepository
import br.com.mobdhi.morinha.home.addpet.AddPetViewModel
import br.com.mobdhi.morinha.home.data.PetRepositoryImpl
import br.com.mobdhi.morinha.home.pets.PetsViewModel
import br.com.mobdhi.morinha.home.data.PetRemoteDataSourceImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun morinhaAppKoinModule() = module {
    single<FirebaseAuth> { FirebaseAuth.getInstance() }
    single<FirebaseFirestore> { FirebaseFirestore.getInstance() }

    single { PetRemoteDataSourceImpl(get(), get()) }

    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<PetRepository> { PetRepositoryImpl(get()) }

    viewModel { LoginViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { PetsViewModel(get()) }
    viewModel { AddPetViewModel(get()) }
}