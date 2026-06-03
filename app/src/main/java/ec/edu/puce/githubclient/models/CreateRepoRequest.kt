package ec.edu.puce.githubclient.models

import com.google.gson.annotations.SerializedName

/**
 * Modelo para enviar datos a la API de GitHub al crear o actualizar un repositorio.
 * Se usa @SerializedName para asegurar que coincida con lo que espera el JSON de la API.
 */
data class CreateRepoRequest(
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("private") val private: Boolean = false
)
