package ec.edu.puce.githubclient

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import ec.edu.puce.githubclient.models.Repository
import ec.edu.puce.githubclient.ui.screens.RepoForm
import ec.edu.puce.githubclient.ui.screens.RepoList
import ec.edu.puce.githubclient.ui.theme.theme.GithubClientTheme
import ec.edu.puce.githubclient.viewmodels.RepoListViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GithubClientTheme {
                val viewModel: RepoListViewModel = viewModel()
                var currentScreen by remember { mutableStateOf("list") }
                var repoToEdit by remember { mutableStateOf<Repository?>(null) }

                if (currentScreen == "list") {
                    RepoList(
                        viewModel = viewModel,
                        onAddClick = {
                            repoToEdit = null
                            currentScreen = "form"
                        },
                        onEditClick = { repo ->
                            repoToEdit = repo
                            currentScreen = "form"
                        }
                    )
                } else {
                    RepoForm(
                        repositoryToEdit = repoToEdit,
                        onBack = { currentScreen = "list" },
                        onSave = { name, description ->
                            if (repoToEdit == null) {
                                viewModel.createRepo(name, description)
                            } else {
                                viewModel.updateRepo(
                                    repoToEdit!!.owner.login,
                                    repoToEdit!!.name,
                                    name,
                                    description
                                )
                            }
                            currentScreen = "list"
                        }
                    )
                }
            }
        }
    }
}
