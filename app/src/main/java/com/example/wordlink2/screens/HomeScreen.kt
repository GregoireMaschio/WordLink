
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.wordlink2.R
import com.example.wordlink2.viewmodels.WordLinkViewModel

@Composable
fun HomeScreen(
    wordLinkViewModel: WordLinkViewModel = WordLinkViewModel(),
    onPlayClick: () -> Unit,
    onChangeDictionaryClick: () -> Unit,
    onLanguageChangeClick: () -> Unit,
    isFrench: Boolean) {
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
            Text(stringResource(id = if(isFrench) R.string.play_btn_fr else R.string.play_btn_en))
        }

        // Change Dictionary Button
        Button(
            onClick = onChangeDictionaryClick,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text(stringResource(id = if(isFrench) R.string.dictionnaire_btn_fr else R.string.dictionnaire_btn_en))
        }

        // Language Button
        Button(
            onClick = onLanguageChangeClick
        ) {
            Text(stringResource(id = if(isFrench) R.string.langue_btn_fr else R.string.langue_btn_en))
        }
    }
}
