package com.devhjs.randompick.feature.main.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.devhjs.randompick.core.ui.theme.Dimens
import kotlinx.coroutines.launch

@Composable
fun RandomPickContent(
    items: List<String>,
    onAddItemClick: (() -> Unit)? = null
) {
    if (items.isEmpty()) {
        EmptyPickContent(onAddItemClick = onAddItemClick)
    } else {
        var isPicking by remember { mutableStateOf(false) }
        var currentItem by remember { mutableStateOf(items.firstOrNull() ?: "") }
        var finalItem by remember { mutableStateOf<String?>(null) }
        var hasPickedOnce by remember { mutableStateOf(false) }

        LaunchedEffect(items) {
            hasPickedOnce = false
            finalItem = null
        }

        val scope = rememberCoroutineScope()
        val offsetY = remember { Animatable(0f) }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(Dimens.spacingMedium)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(Dimens.cornerRadiusMedium)
                    )
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                        shape = RoundedCornerShape(Dimens.cornerRadiusMedium)
                    )
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(Dimens.spacingMedium),
                    verticalArrangement = Arrangement.spacedBy(Dimens.spacingMedium)
                ) {
                    items(items) { item ->
                        ItemCard(text = item)
                    }
                }
            }

            AnimatedVisibility(
                visible = hasPickedOnce || isPicking,
                enter = fadeIn(animationSpec = tween(300)),
                exit = fadeOut(animationSpec = tween(300))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .clipToBounds()
                        .background(
                            MaterialTheme.colorScheme.primaryContainer,
                            RoundedCornerShape(Dimens.cornerRadiusLarge)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = when {
                            isPicking -> currentItem
                            finalItem != null -> finalItem ?: ""
                            else -> ""
                        },
                        modifier = Modifier.offset(y = offsetY.value.dp),
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

            Button(
                onClick = {
                    if (items.isNotEmpty() && !isPicking) {
                        isPicking = true
                        finalItem = null
                        hasPickedOnce = true
                        scope.launch {
                            val totalDuration = 2500L
                            val startTime = System.currentTimeMillis()

                            while (true) {
                                val elapsed = System.currentTimeMillis() - startTime
                                val fraction = (elapsed / totalDuration.toFloat()).coerceIn(0f, 1f)
                                val speed = (1f - fraction).coerceAtLeast(0.05f)

                                currentItem = items.random()
                                offsetY.snapTo(-100f)
                                offsetY.animateTo(
                                    targetValue = 0f,
                                    animationSpec = tween(
                                        durationMillis = (60L + 100L * fraction).toInt(),
                                        easing = LinearEasing
                                    )
                                )

                                kotlinx.coroutines.delay((20L + 40L * speed).toLong())
                                if (fraction >= 1f) break
                            }

                            finalItem = items.random()
                            isPicking = false
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.buttonHeight),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                shape = RoundedCornerShape(Dimens.cornerRadiusMedium)
            ) {
                Icon(
                    Icons.Default.Refresh,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.width(Dimens.spacingSmall))
                Text(
                    text = if (isPicking) "돌아가는 중..." else "랜덤 선택",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}