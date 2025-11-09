package com.devhjs.randompick.feature.list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.devhjs.randompick.core.model.PickItem
import com.devhjs.randompick.core.model.PickList
import com.devhjs.randompick.core.ui.theme.Dimens
import com.devhjs.randompick.feature.list.ListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditListBottomSheet(
    list: PickList?,
    onDismiss: () -> Unit,
    viewModel: ListViewModel
) {
    if (list == null) return

    var listTitle by remember { mutableStateOf(list.title) }
    val items = remember { mutableStateListOf<PickItem>().apply { addAll(list.items) } }
    var newItemText by remember { mutableStateOf("") }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true
        ),
        shape = RoundedCornerShape(
            topStart = Dimens.spacingExtraLarge,
            topEnd = Dimens.spacingExtraLarge
        ),
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(Dimens.dialogPadding)
                .navigationBarsPadding()
                .imePadding()
        ) {
            Text(
                text = "리스트 편집",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(Modifier.height(Dimens.spacingLarge))

            OutlinedTextField(
                value = listTitle,
                onValueChange = { listTitle = it },
                label = { Text("리스트 이름") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(Dimens.spacingMedium))

            Text(
                text = "항목 (${items.size}개)",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = Dimens.spacingSmall),
                verticalArrangement = Arrangement.spacedBy(Dimens.spacingSmall)
            ) {
                itemsIndexed(items) { index, item ->
                    ItemRow(item = item) {
                        items.removeAt(index)
                        viewModel.deleteItem(item)
                    }
                }
            }

            Spacer(Modifier.height(Dimens.spacingMedium))

            Row(
                horizontalArrangement = Arrangement.spacedBy(Dimens.spacingSmall),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = newItemText,
                    onValueChange = { newItemText = it },
                    placeholder = { Text("새 항목 입력") },
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = {
                    if (newItemText.isNotBlank()) {
                        val newItem = PickItem(listId = list.id ?: 0, name = newItemText)
                        items.add(newItem)
                        viewModel.addItem(list.id ?: 0, newItemText)
                        newItemText = ""
                    }
                }) {
                    Icon(Icons.Default.Add, contentDescription = "추가")
                }
            }

            Spacer(Modifier.height(Dimens.spacingLarge))

            Button(
                onClick = {
                    val updatedList = list.copy(title = listTitle, items = items)
                    viewModel.updateList(updatedList)
                    onDismiss()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("저장")
            }

            OutlinedButton(
                onClick = onDismiss,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("취소")
            }

            OutlinedButton(
                onClick = {
                    viewModel.deleteList(list)
                    onDismiss()
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error)
            ) {
                Icon(Icons.Default.Delete, contentDescription = null)
                Spacer(Modifier.width(Dimens.spacingTiny))
                Text("삭제")
            }
        }
    }
}
