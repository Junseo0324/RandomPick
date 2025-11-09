package com.devhjs.randompick.feature.list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.devhjs.randompick.core.model.PickItem
import com.devhjs.randompick.core.ui.theme.Dimens


@Composable
fun ItemRow(
    item: PickItem,
    onDelete: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                RoundedCornerShape(Dimens.spacingSmall)
            )
            .padding(horizontal = Dimens.spacingLarge, vertical = Dimens.spacingMedium),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = item.name,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )
        IconButton(
            onClick = onDelete,
            modifier = Modifier.size(Dimens.iconSizeMedium)
        ) {
            Icon(
                Icons.Outlined.Delete,
                contentDescription = "삭제",
                tint = MaterialTheme.colorScheme.error,
                modifier = Modifier.size(Dimens.iconSizeSmall)
            )
        }
    }
}