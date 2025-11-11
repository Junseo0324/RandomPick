package com.devhjs.randompick.feature.main.components

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.sp
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun RouletteWheel(
    items: List<String>,
    colors: List<Color>,
    rotation: Float,
    modifier: Modifier = Modifier
) {
    val textMeasurer = rememberTextMeasurer()

    Canvas(modifier = modifier) {
        val radius = size.minDimension / 2
        val center = Offset(size.width / 2, size.height / 2)
        val segmentAngle = 360f / items.size
        rotate(rotation, center) {
            items.forEachIndexed { index, item ->
                val startAngle = -90f + segmentAngle * index
                val color = colors[index % colors.size]

                drawArc(
                    color = color,
                    startAngle = startAngle,
                    sweepAngle = segmentAngle,
                    useCenter = true,
                    topLeft = Offset(center.x - radius, center.y - radius),
                    size = Size(radius * 2, radius * 2)
                )

                drawArc(
                    color = Color.White,
                    startAngle = startAngle,
                    sweepAngle = segmentAngle,
                    useCenter = true,
                    topLeft = Offset(center.x - radius, center.y - radius),
                    size = Size(radius * 2, radius * 2),
                    style = androidx.compose.ui.graphics.drawscope.Stroke(width = 3f)
                )

                val angle = Math.toRadians((startAngle + segmentAngle / 2).toDouble())
                val textRadius = radius * 0.65f
                val textX = center.x + textRadius * cos(angle).toFloat()
                val textY = center.y + textRadius * sin(angle).toFloat()

                val textRotation = startAngle + segmentAngle / 2 + 90f

                rotate(textRotation, Offset(textX, textY)) {
                    val textLayoutResult = textMeasurer.measure(
                        text = item,
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                        )
                    )

                    drawText(
                        textLayoutResult = textLayoutResult,
                        topLeft = Offset(
                            textX - textLayoutResult.size.width / 2,
                            textY - textLayoutResult.size.height / 2
                        )
                    )
                }
            }
        }
    }
}