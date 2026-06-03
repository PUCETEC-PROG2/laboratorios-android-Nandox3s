package ec.edu.puce.githubclient.services

import ec.edu.puce.githubclient.models.CreateRepoRequest
import ec.edu.puce.githubclient.models.Repository
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET(value = "/user/repos")
    suspend fun getRepositories(
        @Query(value = "sort") created: String = "created",
        @Query(value = "direction") direction: String = "desc",
        @Query(value = "affiliation") affiliation: String = "owner",
        @Query(value = "per_page") perPage: Int = 100,
        @Query(value = "t") t: String = "${System.currentTimeMillis()}"
    ): List<Repository>

    // Laboratorio 3: Crear repositorio
    @POST("/user/repos")
    suspend fun createRepository(
        @Body repo: CreateRepoRequest
    ): Repository

    // Laboratorio 4: Actualizar repositorio
    @PATCH("/repos/{owner}/{repo}")
    suspend fun updateRepository(
        @Path("owner") owner: String,
        @Path("repo") repoName: String,
        @Body repo: CreateRepoRequest
    ): Repository

    // Laboratorio 4: Eliminar repositorio
    @DELETE("/repos/{owner}/{repo}")
    suspend fun deleteRepository(
        @Path("owner") owner: String,
        @Path("repo") repoName: String
    ): Response<Unit>
}
