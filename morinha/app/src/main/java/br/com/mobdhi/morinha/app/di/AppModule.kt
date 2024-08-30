package br.com.mobdhi.morinha.app.di

import br.com.mobdhi.morinha.domain.repository.AuthRepository
import br.com.mobdhi.morinha.auth.AuthRepositoryImpl
import br.com.mobdhi.morinha.auth.login.LoginViewModel
import br.com.mobdhi.morinha.auth.register.RegisterViewModel
import br.com.mobdhi.morinha.domain.model.Vaccine
import br.com.mobdhi.morinha.domain.repository.PetRepository
import br.com.mobdhi.morinha.domain.repository.VaccinesRepository
import br.com.mobdhi.morinha.pet.addpet.AddPetViewModel
import br.com.mobdhi.morinha.pet.data.PetRepositoryImpl
import br.com.mobdhi.morinha.pet.pets.PetsViewModel
import br.com.mobdhi.morinha.pet.data.PetRemoteDataSourceImpl
import br.com.mobdhi.morinha.vaccine.addvaccine.AddEditVaccineViewModel
import br.com.mobdhi.morinha.vaccine.vaccines.VaccinesViewModel
import br.com.mobdhi.morinha.vaccine.data.VaccinesRemoteDataSourceImpl
import br.com.mobdhi.morinha.vaccine.data.VaccinesRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun morinhaAppKoinModule() = module {
    single<FirebaseAuth> { FirebaseAuth.getInstance() }
    single<FirebaseFirestore> { FirebaseFirestore.getInstance() }

    single { PetRemoteDataSourceImpl(get(), get()) }
    single { VaccinesRemoteDataSourceImpl(get(), get()) }

    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<PetRepository> { PetRepositoryImpl(get()) }
    single<VaccinesRepository> { VaccinesRepositoryImpl(get()) }

    viewModel { LoginViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { PetsViewModel(get()) }
    viewModel { AddPetViewModel(get()) }
    viewModel { VaccinesViewModel(get()) }
    viewModel { VaccinesViewModel(get()) }
    viewModel { AddEditVaccineViewModel(get(), get(), get()) }

}