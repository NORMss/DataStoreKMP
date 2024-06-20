import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import data.local.DataStoreRepository
import data.local.createDataStore
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.random.Random

@Composable
@Preview
fun App(context: Any? = null) {
    MaterialTheme {
        val scope = rememberCoroutineScope()
        val dataStoreRepository = remember {
            DataStoreRepository(dataStore = createDataStore(context))
        }

        var savedTimesStamp: Long? by remember {
            mutableStateOf(null)
        }

        LaunchedEffect(Unit) {
            dataStoreRepository.readTimeStamp().collectLatest {
                savedTimesStamp = it
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Saved timestamp: ${if (savedTimesStamp != null) savedTimesStamp else "Empty"}"
            )
            Button(
                onClick = {
                    scope.launch {
                        dataStoreRepository.saveTimeStamp(
                            Random.nextLong(
                                from = 1000,
                                until = 10000,
                            )
                        )
                    }
                }
            ) {
                Text(
                    text = "Save timestamp"
                )
            }
        }
    }
}