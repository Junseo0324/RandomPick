package com.devhjs.randompick.feature.main.components

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import com.devhjs.randompick.core.ui.theme.Dimens
import kotlinx.coroutines.launch
import kotlin.random.Random


@Composable
fun RouletteContent(
    items: List<String>,
    onAddItemClick: (() -> Unit)? = null
) {
    Crossfade(targetState = items.isEmpty(), label = "RouletteEmptyState") { isEmpty ->
        if (isEmpty) {
            EmptyPickContent(onAddItemClick = onAddItemClick)
        } else {
            var isSpinning by remember { mutableStateOf(false) }
            var selectedItem by remember { mutableStateOf<String?>(null) }
            var showDialog by remember { mutableStateOf(false) }
            val rotation = remember { Animatable(0f) }
            val scope = rememberCoroutineScope()

            val colors = remember {
                listOf(
                    Color(0xFFEF5350),
                    Color(0xFF66BB6A),
                    Color(0xFFFFA726),
                    Color(0xFF42A5F5),
                    Color(0xFFAB47BC),
                    Color(0xFFFFEE58),
                    Color(0xFFEC407A),
                    Color(0xFF26A69A),
                    Color(0xFFFF7043),
                    Color(0xFF5C6BC0),
                )
            }

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .aspectRatio(1f),
                    contentAlignment = Alignment.Center
                ) {
                    RouletteWheel(
                        items = items,
                        colors = colors,
                        rotation = rotation.value,
                        modifier = Modifier.fillMaxSize(0.93f)
                    )
                    Canvas(
                        modifier = Modifier
                            .fillMaxWidth(0.1f)
                            .aspectRatio(0.9f)
                            .align(Alignment.TopCenter)
                    ) {
                        val path = Path().apply {
                            moveTo(size.width / 2, size.height)
                            lineTo(0f, 0f)
                            lineTo(size.width, 0f)
                            close()
                        }
                        drawPath(
                            path = path,
                            color = Color.White,
                            style = Stroke(width = 8f)
                        )

                        drawPath(path, color = Color(0xFFEF5350))
                    }
                }

                Spacer(modifier = Modifier.height(Dimens.spacingDoubleExtraLarge))

                if (selectedItem != null) {
                    Text(
                        text = "선택: $selectedItem",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(Dimens.spacingMedium))
                }

                Button(
                    onClick = {
                        if (!isSpinning) {
                            isSpinning = true
                            selectedItem = null
                            scope.launch {
                                val targetRotation =
                                    rotation.value + 360f * (5 + Random.nextInt(4)) + Random.nextFloat() * 360f

                                rotation.animateTo(
                                    targetValue = targetRotation,
                                    animationSpec = tween(
                                        durationMillis = 3000,
                                        easing = { fraction ->
                                            1f - (1f - fraction) * (1f - fraction) * (1f - fraction)
                                        }
                                    )
                                )

                                val finalAngle = (360f - (rotation.value % 360f)) % 360f
                                val segmentAngle = 360f / items.size
                                val selectedIndex = (finalAngle / segmentAngle).toInt() % items.size
                                selectedItem = items[selectedIndex]

                                showDialog = true
                                isSpinning = false
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Dimens.buttonHeight),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    shape = RoundedCornerShape(Dimens.cornerRadiusMedium),
                    enabled = !isSpinning
                ) {
                    Icon(
                        Icons.Default.PlayArrow,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.width(Dimens.spacingSmall))
                    Text(
                        text = if (isSpinning) "회전 중..." else "시작",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

            if (showDialog && selectedItem != null) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    confirmButton = {
                        Button(
                            onClick = { showDialog = false },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Text("확인", color = MaterialTheme.colorScheme.onPrimary)
                        }
                    },
                    title = {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "결과는...",
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    },
                    text = {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = selectedItem ?: "",
                                style = MaterialTheme.typography.headlineMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    },
                    containerColor = Color.White,
                    shape = RoundedCornerShape(Dimens.cornerRadiusMedium)
                )
            }
        }
    }

}