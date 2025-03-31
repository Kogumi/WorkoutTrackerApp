package com.example.myworkoutprogressapp.planner.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsEndWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ListElement(text: String, id: Long,
                onDelete: () -> Unit = {},
                onEdit: () -> Unit = {},
                onClick: () -> Unit)
{
    Row(
        modifier = Modifier.fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.primary)
            .border(width = 4.dp, color = Color.Transparent)
            .clickable{ onClick() },
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(text,
            modifier = Modifier.padding(16.dp).weight(1f),
            fontStyle = MaterialTheme.typography.bodyLarge.fontStyle,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
            )
        Row(modifier = Modifier.padding(horizontal = 0.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.Center){
            IconButton(
                onClick = {
                    onEdit()
                }
            ){
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit",
                    modifier = Modifier.size(64.dp)
                )
            }
            IconButton(
                onClick = {
                    onDelete()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    modifier = Modifier.size(64.dp)
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ListElementPreview(){
    ListElement(text = "hello",
                id = 12) { }
}