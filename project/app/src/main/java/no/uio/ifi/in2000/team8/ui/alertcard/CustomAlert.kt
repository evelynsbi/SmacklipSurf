package no.uio.ifi.in2000.team8.ui.alertcard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import no.uio.ifi.in2000.team8.R
import no.uio.ifi.in2000.team8.ui.theme.AppTypography

// inspired by https://blog.stackademic.com/custom-alert-in-jetpack-compose-47c367879147
@Composable
fun CustomAlert(
    modifier: Modifier = Modifier,
    message: String, // actual warning message
    actionText: String, // ok button text
    warningIcon: Int, // yellow, orange or green icon
    time: String, // when the alert is relevant for
    showAlert: MutableState<Boolean>,
    action: (() -> Unit)? = null,
) {
    if (showAlert.value) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            contentAlignment = Alignment.Center,
        ) {
            // card displaying alert info
            Card(
                modifier = modifier
                    .padding(12.dp)
                    .width(280.dp),
                shape = RoundedCornerShape(15.dp),
                border = BorderStroke(1.5.dp, Color.LightGray),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onPrimary),
            ) {
                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.onPrimary)
                        .padding(10.dp)
                ) {
                    // warning icon
                    Image(
                        painter = painterResource(id = warningIcon),
                        contentDescription = "Farevarsel",
                        modifier = Modifier
                            .size(100.dp)
                            .align(Alignment.CenterHorizontally)
                            .padding(12.dp, top = 12.dp)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // error message
                        Text(
                            text = message,
                            modifier = Modifier
                                .width(200.dp)
                                .padding(vertical = 8.dp),
                            style = AppTypography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                    }
                    // displays time
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = time,
                            modifier = Modifier
                                .width(200.dp)
                                .padding(vertical = 8.dp),
                            style = AppTypography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center

                        )
                    }
                    // displays ok button to close alert
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.75f)
                            .padding(8.dp)
                            .clickable {
                                showAlert.value = false
                                action?.invoke()
                            }
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Text(
                            text = actionText,
                            style = AppTypography.titleLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier
                                .align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}

