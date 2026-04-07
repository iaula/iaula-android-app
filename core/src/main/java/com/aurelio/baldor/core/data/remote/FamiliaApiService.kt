package com.aurelio.baldor.core.data.remote

import com.aurelio.baldor.core.data.middleware.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Query

data class FamiliaResponse(val id_familia: String, val nombre: String, val estado: String, val hijos: List<FamiliaHijoResponse>)
data class FamiliaHijoResponse(val id_estudiante: String, val id_persona: Int, val nombres: String, val apellidos: String, val sexo: String, val estado_est: String, val grado_actual: String?, val foto: String?)

interface  FamiliaApiService{
    @GET("academico/estudiante/familias")
    suspend fun getFamilias(@Query("codigo") codigo: String): NetworkResponse<List<FamiliaResponse>>
}