package com.devhjs.randompick.feature.list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.devhjs.randompick.core.model.PickList
import com.devhjs.randompick.core.ui.theme.Dimens

@Composable
fun ListCard(
    list: PickList,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = Dimens.spacingSmall),
        shape = RoundedCornerShape(Dimens.cornerRadiusLarge),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = Dimens.cardElevation
    ) {
        Row(
            modifier = Modifier.padding(Dimens.cardInnerPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = list.title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                Spacer(modifier = Modifier.height(Dimens.spacingSmall))
                Text(
                    text = "${list.items.size}개 항목",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(Dimens.spacingSmall))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(Dimens.spacingTiny),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    list.items.take(3).forEach { item ->
                        Text(
                            text = item.name,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .background(
                                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                    RoundedCornerShape(Dimens.cornerRadiusSmall)
                                )
                                .padding(horizontal = Dimens.spacingSmall, vertical = Dimens.spacingTiny)
                        )
                    }
                    if (list.items.size > 3) {
                        Text(
                            "+${list.items.size - 3}개",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier
                                .background(
                                    MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
                                    RoundedCornerShape(Dimens.cornerRadiusSmall)
                                )
                                .padding(horizontal = Dimens.spacingSmall, vertical = Dimens.spacingTiny)
                        )
                    }
                }
            }
            Icon(
                Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}