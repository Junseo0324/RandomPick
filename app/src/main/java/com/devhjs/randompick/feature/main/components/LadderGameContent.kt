package com.devhjs.randompick.feature.main.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devhjs.randompick.core.model.Bridge
import com.devhjs.randompick.core.ui.theme.Dimens
import kotlinx.coroutines.launch

@Composable
fun LadderGameContent(
    items: List<String>,
    ladderBridges: List<Bridge>,
    gameResult: Map<Int, Int>,
    onGenerateLadder: (Int) -> Unit,
    onInteraction: () -> Unit = {},
    onAddItemClick: (() -> Unit)? = null
) {
    if (items.isEmpty()) {
        EmptyPickContent(onAddItemClick = onAddItemClick)
    } else {
        var isPlaying by remember { mutableStateOf(false) }
        var showResultDialog by remember { mutableStateOf(false) }
        var selectedParticipantIndex by remember { mutableStateOf<Int?>(null) }
        
        val progressAnim = remember { Animatable(0f) }
        val scope = rememberCoroutineScope()

        LaunchedEffect(items) {
            onGenerateLadder(items.size)
            progressAnim.snapTo(0f)
            isPlaying = false
            showResultDialog = false
            selectedParticipantIndex = null
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(Dimens.spacingMedium)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                        shape = RoundedCornerShape(Dimens.cornerRadiusMedium)
                    )
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(Dimens.cornerRadiusMedium)
                    )
                    .padding(Dimens.spacingMedium)
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        repeat(items.size) { index ->
                            Box(
                                modifier = Modifier.weight(1f),
                                contentAlignment = Alignment.Center
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(30.dp)
                                        .background(
                                            color = if (selectedParticipantIndex == index) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondaryContainer,
                                            shape = CircleShape
                                        )
                                        .clickable {
                                            if (!isPlaying) {
                                                selectedParticipantIndex = index
                                            }
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "${index + 1}",
                                        color = if (selectedParticipantIndex == index) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondaryContainer,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            val columnWidth = size.width / items.size
                            val rowHeight = size.height / 10

                            repeat(items.size) { i ->
                                val x = (i + 0.5f) * columnWidth
                                drawLine(
                                    color = Color.Gray,
                                    start = Offset(x, 0f),
                                    end = Offset(x, size.height),
                                    strokeWidth = 4f,
                                    cap = StrokeCap.Round
                                )
                            }

                            ladderBridges.forEach { bridge ->
                                val xStart = (bridge.colIndex + 0.5f) * columnWidth
                                val xEnd = (bridge.colIndex + 1.5f) * columnWidth
                                val y = bridge.step * rowHeight
                                drawLine(
                                    color = Color.Gray,
                                    start = Offset(xStart, y),
                                    end = Offset(xEnd, y),
                                    strokeWidth = 4f,
                                    cap = StrokeCap.Round
                                )
                            }

                            if (isPlaying || progressAnim.value > 0f) {
                                val activeIndex = selectedParticipantIndex
                                if (activeIndex != null) {
                                    drawPathForParticipant(
                                        participantIndex = activeIndex,
                                        bridges = ladderBridges,
                                        columnWidth = columnWidth,
                                        rowHeight = rowHeight,
                                        progress = progressAnim.value,
                                        color = Color.Red
                                    )
                                }
                            }
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        items.forEachIndexed { index, item ->
                            Box(
                                modifier = Modifier.weight(1f),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = item,
                                    style = MaterialTheme.typography.labelSmall,
                                    textAlign = TextAlign.Center,
                                    maxLines = 2,
                                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                                    lineHeight = 12.sp
                                )
                            }
                        }
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Dimens.spacingSmall)
            ) {
                Button(
                    onClick = {
                        onGenerateLadder(items.size)
                        scope.launch {
                            progressAnim.snapTo(0f)
                        }
                        isPlaying = false
                        selectedParticipantIndex = null
                    },
                    modifier = Modifier
                        .height(Dimens.buttonHeight),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    ),
                    shape = RoundedCornerShape(Dimens.cornerRadiusMedium),
                    enabled = !isPlaying
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = null)
                }

                Button(
                    onClick = {
                        if (selectedParticipantIndex == null) {
                            selectedParticipantIndex = 0
                        }
                        onInteraction()
                        
                        isPlaying = true
                        scope.launch {
                            progressAnim.snapTo(0f)
                            progressAnim.animateTo(
                                targetValue = 1f,
                                animationSpec = tween(
                                    durationMillis = 2000,
                                    easing = LinearEasing
                                )
                            )
                            isPlaying = false
                            showResultDialog = true
                        }
                    },
                    modifier = Modifier
                        .weight(2f)
                        .height(Dimens.buttonHeight),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    shape = RoundedCornerShape(Dimens.cornerRadiusMedium),
                    enabled = !isPlaying && items.size > 1
                ) {
                    Icon(Icons.Default.PlayArrow, contentDescription = null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(if (isPlaying) "진행 중..." else "시작")
                }
            }
        }

        if (showResultDialog) {
            AlertDialog(
                onDismissRequest = { showResultDialog = false },
                confirmButton = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Button(
                            onClick = { showResultDialog = false },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Text("확인", color = MaterialTheme.colorScheme.onPrimary)
                        }
                    }
                },
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "결과 전체보기",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                text = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        gameResult.entries.sortedBy { it.key }.forEach { (participant, itemIndex) ->
                            val resultItem = items.getOrNull(itemIndex) ?: ""
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "${participant + 1}번",
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = resultItem,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                },
                containerColor = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(Dimens.cornerRadiusMedium)
            )
        }
    }
}

fun DrawScope.drawPathForParticipant(
    participantIndex: Int,
    bridges: List<Bridge>,
    columnWidth: Float,
    rowHeight: Float,
    progress: Float,
    color: Color
) {
    val points = mutableListOf<Offset>()
    var currentX = (participantIndex + 0.5f) * columnWidth
    var currentY = 0f
    var currentCol = participantIndex
    
    points.add(Offset(currentX, currentY))
    
    val sortedBridges = bridges.sortedBy { it.step }
    val totalSteps = 10
    
    for (step in 1 until totalSteps) {
        val stepY = step * rowHeight
        
        points.add(Offset(currentX, stepY))
        
        val bridgeRight = sortedBridges.find { it.step == step && it.colIndex == currentCol }
        val bridgeLeft = sortedBridges.find { it.step == step && it.colIndex == currentCol - 1 }
        
        if (bridgeRight != null) {
            currentCol += 1
            currentX = (currentCol + 0.5f) * columnWidth
            points.add(Offset(currentX, stepY))
        } else if (bridgeLeft != null) {
            currentCol -= 1
            currentX = (currentCol + 0.5f) * columnWidth
            points.add(Offset(currentX, stepY))
        }
    }
    
    points.add(Offset(currentX, size.height))
    
    val totalSegments = points.size - 1
    if (totalSegments <= 0) return
    
    val currentSegmentIndex = (progress * totalSegments).toInt().coerceAtMost(totalSegments - 1)
    val segmentProgress = (progress * totalSegments) - currentSegmentIndex
    
    for (i in 0 until currentSegmentIndex) {
        drawLine(
            color = color,
            start = points[i],
            end = points[i+1],
            strokeWidth = 6f,
            cap = StrokeCap.Round
        )
    }
    
    val start = points[currentSegmentIndex]
    val end = points[currentSegmentIndex + 1]
    val currentPos = Offset(
        x = start.x + (end.x - start.x) * segmentProgress,
        y = start.y + (end.y - start.y) * segmentProgress
    )
    
    drawLine(
        color = color,
        start = start,
        end = currentPos,
        strokeWidth = 6f,
        cap = StrokeCap.Round
    )
    
    drawCircle(
        color = color,
        radius = 8f,
        center = currentPos
    )
}
