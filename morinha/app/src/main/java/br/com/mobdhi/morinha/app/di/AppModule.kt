package br.com.mobdhi.morinha.app.di

import br.com.mobdhi.morinha.account.AccountViewModel
import br.com.mobdhi.morinha.domain.repository.AuthRepository
import br.com.mobdhi.morinha.auth.AuthRepositoryImpl
import br.com.mobdhi.morinha.auth.login.LoginViewModel
import br.com.mobdhi.morinha.auth.register.RegisterViewModel
import br.com.mobdhi.morinha.domain.repository.PetRepository
import br.com.mobdhi.morinha.domain.repository.VaccinesRepository
import br.com.mobdhi.morinha.pet.addeditpet.AddPetViewModel
import br.com.mobdhi.morinha.pet.data.PetRepositoryImpl
import br.com.mobdhi.morinha.pet.pets.PetsViewModel
import br.com.mobdhi.morinha.pet.data.PetRemoteDataSourceImpl
import br.com.mobdhi.morinha.vaccine.addeditvaccine.AddEditVaccineViewModel
import br.com.mobdhi.morinha.vaccine.vaccines.VaccinesViewModel
import br.com.mobdhi.morinha.vaccine.data.VaccinesRemoteDataSourceImpl
import br.com.mobdhi.morinha.vaccine.data.VaccinesRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun morinhaAppKoinModule() = module {
    single<FirebaseAuth> { FirebaseAuth.getInstance() }
    single<FirebaseFirestore> { FirebaseFirestore.getInstance() }
    single<FirebaseCrashlytics> { FirebaseCrashlytics.getInstance() }

    single { PetRemoteDataSourceImpl(get(), get(), get()) }
    single { VaccinesRemoteDataSourceImpl(get(), get(), get()) }

    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
    single<PetRepository> { PetRepositoryImpl(get()) }
    single<VaccinesRepository> { VaccinesRepositoryImpl(get()) }

    viewModel { AccountViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { PetsViewModel(get()) }
    viewModel { AddPetViewModel(get(), get()) }
    viewModel { VaccinesViewModel(get()) }
    viewModel { AddEditVaccineViewModel(get(), get(), get()) }

}