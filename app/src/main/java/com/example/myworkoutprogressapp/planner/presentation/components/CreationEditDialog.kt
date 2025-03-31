package com.example.myworkoutprogressapp.planner.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun CreationEditDialog(
    textFieldValue: String = "",
    textFieldLabel: String,
    buttonText: String,
    onDismissRequest: () -> Unit,
    onValueChange: (String) -> Unit,
    onButtonClick: () -> Unit,
){
    Dialog(onDismissRequest = onDismissRequest,
        content = {
            Card(modifier = Modifier
                .wrapContentSize()
                .padding(16.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    TextField(modifier = Modifier.border(
                        width = 2.dp, color = MaterialTheme.colorScheme.secondary
                    ),
                        value = textFieldValue,
                        label = { Text(textFieldLabel) },
                        onValueChange = {
                            onValueChange(it)
                        })
                    Spacer(modifier = Modifier.height(26.dp))
                    Button(onClick = onButtonClick){
                        Text(buttonText)
                    }
                }
            }
        })
}
