package fr.api

import fr.data.ricketmorty.RickAndMorty
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RickApi {

    @GET("{count}")
    suspend fun getCharacter(@Path("count") count : Int) : Response<RickAndMorty>

}