package com.aurelio.baldor.core.data.remote

import com.aurelio.baldor.core.data.middleware.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Query

data class AsistenciaResponse(val matriz: Int, val entidad: String, val codigo: String, val fecha: String, val horing: String?, val horsal: String?, val estado: String?, val estado_des: String?, val comentario: String?, val justificacion: String?)

interface AsistenciaApiService {
    @GET("academico/asistencia/estudiante")
    suspend fun getAsistenciaEstudiante(@Query("date_ini") date_ini: String, @Query("date_fin") date_fin: String, @Query("codigo") codigo: String): NetworkResponse<List<AsistenciaResponse>>
}