package br.com.mobdhi.morinha.home

import br.com.mobdhi.morinha.domain.model.Pet
import br.com.mobdhi.morinha.domain.model.Response
import br.com.mobdhi.morinha.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.time.LocalDate

class HomeRepositoryImpl : HomeRepository {
    override fun getPets(): Flow<Response<List<Pet>>> {
        //to test
        val petList = mutableListOf<Pet>()
        val pet = Pet(
            name = "Amora",
            specie = "Dog",
            breed = "Yorkshire Terrier",
            bornDate = LocalDate.parse("2021-06-07"),
            genre = "Female",
            weight = 4.3,
            color = "Preto"
        )
        repeat(7) { petList.add(pet) }

        //to test end


        return flow {
            emit(Response.Loading())
            val result = petList.toList() //to change
            emit(Response.Success(data = result))
        }.catch {
            emit(
                Response.Error(
                    message = "Error ${it.message} \n Cause ${it.cause} \n Stack ${it.stackTrace}"
                )
            )
        }
    }
}