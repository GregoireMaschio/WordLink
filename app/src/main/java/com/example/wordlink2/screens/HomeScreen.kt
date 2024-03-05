
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(onPlayClick: () -> Unit, onChangeDictionaryClick: () -> Unit, onLanguageChangeClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Title
        Text(
            text = "WordLink",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Play Button
        Button(
            onClick = onPlayClick,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text("Play")
        }

        // Change Dictionary Button
        Button(
            onClick = onChangeDictionaryClick,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text("Change Dictionary")
        }

        // Language Button
        Button(
            onClick = onLanguageChangeClick
        ) {
            Text("Change Language")
        }
    }
}
