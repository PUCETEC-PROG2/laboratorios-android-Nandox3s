package ec.edu.puce.githubclient.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ec.edu.puce.githubclient.models.CreateRepoRequest
import ec.edu.puce.githubclient.models.Repository
import ec.edu.puce.githubclient.services.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RepoListViewModel : ViewModel(){
    private val _repos = MutableStateFlow<List<Repository>>( value = emptyList())
    val repos : StateFlow<List<Repository>> = _repos.asStateFlow()

    private val _isLoading = MutableStateFlow (value = false)
    val isLoading : StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMsg = MutableStateFlow<String?> (value = null)
    val errorMsg : StateFlow<String?> = _errorMsg.asStateFlow()

    init {
        fetchRepos()
    }

    fun fetchRepos() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMsg.value = null
            try {
                _repos.value = RetrofitClient.apiService.getRepositories()
            } catch (e: Exception) {
                _errorMsg.value = "Error al cargar repositorios: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Laboratorio 4: Eliminar repositorio
    fun deleteRepo(owner: String, repoName: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitClient.apiService.deleteRepository(owner, repoName)
                if (response.isSuccessful) {
                    fetchRepos()
                } else {
                    _errorMsg.value = "No se pudo eliminar: ${response.message()}"
                }
            } catch (e: Exception) {
                _errorMsg.value = "Error al eliminar: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Laboratorio 3: Crear repositorio
    fun createRepo(name: String, description: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val request = CreateRepoRequest(name = name, description = description)
                RetrofitClient.apiService.createRepository(request)
                fetchRepos() // Refrescamos la lista para ver el nuevo repo_ojo
            } catch (e: Exception) {
                _errorMsg.value = "Error al crear: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Laboratorio 4: Actualizar repositorio
    fun updateRepo(owner: String, oldName: String, newName: String, description: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val request = CreateRepoRequest(name = newName, description = description)
                RetrofitClient.apiService.updateRepository(owner, oldName, request)
                fetchRepos() // Refrescamos para ver los cambios hechos_ojo
            } catch (e: Exception) {
                _errorMsg.value = "Error al actualizar: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}


