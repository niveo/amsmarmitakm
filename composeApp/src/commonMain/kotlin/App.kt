import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel

import amsmarmitakm.composeapp.generated.resources.Res
import amsmarmitakm.composeapp.generated.resources.compose_multiplatform
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.ui.unit.dp


@Composable
@Preview
fun App(mainViewModel: MainViewModel = viewModel()) {
    MaterialTheme {
        val greetings by mainViewModel.greetingList.collectAsState()
        Column(
            modifier = Modifier.padding(all = 20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            greetings.forEach { greeting ->
                Text(greeting)
                Divider()
            }
        }
    }
}