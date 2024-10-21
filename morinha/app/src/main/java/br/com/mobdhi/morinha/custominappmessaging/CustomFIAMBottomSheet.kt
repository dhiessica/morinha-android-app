package br.com.mobdhi.morinha.custominappmessaging

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.inappmessaging.FirebaseInAppMessagingDisplayCallbacks
import com.google.firebase.inappmessaging.model.ModalMessage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomFIAMBottomSheet(
    inAppMessage: ModalMessage,
    callbacks: FirebaseInAppMessagingDisplayCallbacks
) {
    var showBottomSheet by remember { mutableStateOf(true) }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {},
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = inAppMessage.title.text ?: "No Title")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = inAppMessage.body?.text ?: "No Body")
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    callbacks.messageDismissed(FirebaseInAppMessagingDisplayCallbacks.InAppMessagingDismissType.CLICK)
                    showBottomSheet = false
                }) {
                    Text("Dismiss")
                }
            }
        }
    }
}