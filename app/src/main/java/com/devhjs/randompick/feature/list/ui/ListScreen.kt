package com.devhjs.randompick.feature.list.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

data class FoodList(
    val name: String,
    val items: MutableList<String>
)

@Composable
fun ListScreen() {
    var showEditDialog by remember { mutableStateOf(false) }
    var selectedList by remember { mutableStateOf<FoodList?>(null) }

    val foodLists = remember {
        mutableStateListOf(
            FoodList("점심 메뉴", mutableListOf("김치찌개", "햄버거", "라면", "피자", "만두")),
            FoodList("저녁 메뉴", mutableListOf("치킨", "족발", "회", "삼겹살"))
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // 헤더
        Header()

        // 리스트 목록
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            itemsIndexed(foodLists) { index, list ->
                ListCard(
                    list = list,
                    onClick = {
                        selectedList = list
                        showEditDialog = true
                    }
                )
            }
        }
    }

    // 편집 다이얼로그
    if (showEditDialog && selectedList != null) {
        EditListDialog(
            list = selectedList!!,
            onDismiss = {
                showEditDialog = false
                selectedList = null
            },
            onSave = { updatedList ->
                val index = foodLists.indexOfFirst { it == selectedList }
                if (index != -1) {
                    foodLists[index] = updatedList
                } else {
                    foodLists.add(updatedList)
                }
                showEditDialog = false
                selectedList = null
            },
            onDelete = {
                foodLists.remove(selectedList)
                showEditDialog = false
                selectedList = null
            }
        )
    }
}

@Composable
fun Header() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "내 리스트",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "리스트를 관리하세요",
            color = Color.White.copy(alpha = 0.9f),
            fontSize = 16.sp
        )
    }
}

@Composable
fun ListCard(
    list: FoodList,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = list.name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "${list.items.size}개 항목",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    list.items.take(3).forEach { item ->
                        Text(
                            item,
                            fontSize = 12.sp,
                            color = Color(0xFF6366F1),
                            modifier = Modifier
                                .background(
                                    Color(0xFF6366F1).copy(alpha = 0.1f),
                                    RoundedCornerShape(6.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                    if (list.items.size > 3) {
                        Text(
                            "+${list.items.size - 3}개",
                            fontSize = 12.sp,
                            color = Color.Gray,
                            modifier = Modifier
                                .background(
                                    Color.Gray.copy(alpha = 0.1f),
                                    RoundedCornerShape(6.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }
            Icon(
                Icons.Default.KeyboardArrowRight,
                contentDescription = null,
                tint = Color.Gray
            )
        }
    }
}

@Composable
fun EditListDialog(
    list: FoodList,
    onDismiss: () -> Unit,
    onSave: (FoodList) -> Unit,
    onDelete: () -> Unit
) {
    var listName by remember { mutableStateOf(list.name) }
    var items by remember { mutableStateOf(list.items.toMutableList()) }
    var newItemText by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(20.dp),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                // 헤더
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "리스트 편집",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "닫기")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 리스트 이름
                Text(
                    "리스트 이름",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = listName,
                    onValueChange = { listName = it },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF6366F1),
                        unfocusedBorderColor = Color.LightGray
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))

                // 항목 목록
                Text(
                    "항목 (${items.size}개)",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // 항목 리스트
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 250.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items.forEachIndexed { index, item ->
                        ItemRow(
                            text = item,
                            onDelete = { items.removeAt(index) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // 새 항목 입력
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = newItemText,
                        onValueChange = { newItemText = it },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("새 항목 입력", color = Color.Gray) },
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF6366F1),
                            unfocusedBorderColor = Color.LightGray
                        )
                    )
                    FloatingActionButton(
                        onClick = {
                            if (newItemText.isNotBlank()) {
                                items.add(newItemText)
                                newItemText = ""
                            }
                        },
                        modifier = Modifier.size(56.dp),
                        containerColor = Color(0xFF6366F1)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "추가", tint = Color.White)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // 저장 버튼
                Button(
                    onClick = {
                        onSave(FoodList(listName, items))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF6366F1)
                    ),
                    shape = RoundedCornerShape(12.dp),
                    enabled = listName.isNotBlank() && items.isNotEmpty()
                ) {
                    Text("저장", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(8.dp))

                // 취소 버튼
                OutlinedButton(
                    onClick = onDismiss,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color.Gray
                    )
                ) {
                    Text("취소", fontSize = 16.sp)
                }

                // 기존 리스트인 경우 삭제 버튼 표시
                if (list.name.isNotBlank()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedButton(
                        onClick = onDelete,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color(0xFFEF4444)
                        ),
                        border = ButtonDefaults.outlinedButtonBorder.copy(
                            width = 1.dp,
                            brush = androidx.compose.ui.graphics.SolidColor(Color(0xFFEF4444))
                        )
                    ) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = null,
                            tint = Color(0xFFEF4444)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("삭제", fontSize = 16.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun ItemRow(
    text: String,
    onDelete: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Gray.copy(alpha = 0.05f), RoundedCornerShape(8.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            modifier = Modifier.weight(1f)
        )
        IconButton(
            onClick = onDelete,
            modifier = Modifier.size(24.dp)
        ) {
            Icon(
                Icons.Default.Delete,
                contentDescription = "삭제",
                tint = Color(0xFFEF4444),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
