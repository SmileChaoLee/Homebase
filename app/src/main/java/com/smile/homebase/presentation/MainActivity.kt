package com.smile.homebase.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smile.homebase.domain.usecase.TaskUseCase
import com.smile.homebase.ui.theme.*
import com.smile.homebase.presentation.uiLayer.UserIntents
import com.smile.homebase.presentation.viewmodels.TaskViewModel
import com.smile.retrofitapp.domain.model.Task

class MainActivity : ComponentActivity() {

    private val viewModel = TaskViewModel(TaskUseCase())
    private val mFontSize = 20.sp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Log.d(TAG, "onCreate.setContent")
            HomebaseTheme {
                val buttonListener = object: ButtonClickListener {
                    override fun buttonOkClick(value: Task) {
                        Log.d(TAG, "onCreate.setContent.buttonOkClick")
                        viewModel.handleIntent(UserIntents.TaskWork(value))
                    }
                    override fun buttonCancelClick(value: Task) {
                    }
                }
                Log.d(TAG, "onCreate.setContent.Column")
                Column(modifier = Modifier.fillMaxSize()) {
                    InputTaskProperty(
                        modifier = Modifier.weight(3.0f),
                                buttonListener, "Task")
                    DisplayTasks(Modifier.weight(7.0f))
                }
            }
        }
    }

    interface ButtonClickListener {
        fun buttonOkClick(value: Task)
        fun buttonCancelClick(value: Task)
    }

    @Composable
    fun DisplayTasks(modifier: Modifier = Modifier) {
        Log.d(TAG, "DisplayTasks")
        Column(modifier = modifier.fillMaxWidth()
            .background(color = Color(0xff90e5c4))) {
            Text(text = "Task List", fontSize = mFontSize, color = Color.Blue)
            HorizontalDivider(color = Color.Black, thickness = 3.dp,
                modifier = Modifier.fillMaxWidth())

            val taskState by viewModel.taskState.collectAsState()
            Log.d(TAG, "DisplayTasks.taskState.size = ${taskState.size}")
            if (taskState.isEmpty()) return

            Log.d(TAG, "DisplayTasks.LazyColumn")
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(taskState) { task->
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(text = task.title,
                            modifier = Modifier.weight(1f),
                            fontFamily = FontFamily.Monospace, fontSize = mFontSize,
                            color = Color.Red)
                        Text(
                            text = task.status,
                            modifier = Modifier.weight(1f),
                            fontFamily = FontFamily.Monospace, fontSize = mFontSize,
                            color = Color.Red
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun InputTaskProperty(modifier: Modifier = Modifier,
                          buttonListener: ButtonClickListener,
                          dialogTitle: String) {
        Log.d(TAG, "InputTaskProperty")
        var isOpen by rememberSaveable { mutableStateOf(true) }
        if (isOpen) {
            Column(modifier = modifier.fillMaxWidth().fillMaxHeight()
                .background(Color(0xffffa500)),
                horizontalAlignment = Alignment.CenterHorizontally) {
                val okStr = "OK"
                val noStr = "Cancel"
                var titleValue by rememberSaveable { mutableStateOf("") }
                var statusValue by rememberSaveable { mutableStateOf("") }
                Column(modifier = Modifier.weight(3.0f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center) {
                    Text(text = dialogTitle, color = Color.Blue,
                        fontWeight = FontWeight.Bold, fontSize = mFontSize)
                    Spacer(modifier = Modifier.fillMaxWidth().height(10.dp))
                    TextField(value = titleValue,
                        onValueChange = {
                            titleValue = it
                        },
                        textStyle = LocalTextStyle.current.copy(fontSize = mFontSize),
                        placeholder = {
                            Text(text = "Input Title", color = Color.LightGray,
                                fontWeight = FontWeight.Light, fontSize = mFontSize) }
                    )
                    Spacer(modifier = Modifier.fillMaxWidth().height(5.dp))
                    TextField(value = statusValue,
                        onValueChange = {
                            statusValue = it
                        },
                        textStyle = LocalTextStyle.current.copy(fontSize = mFontSize),
                        placeholder = {
                            Text(text = "Input Status", color = Color.LightGray,
                                fontWeight = FontWeight.Light, fontSize = mFontSize) }
                    )
                }
                Row(modifier = Modifier.weight(1.0f),
                    verticalAlignment = Alignment.CenterVertically) {
                    Button(onClick = {
                        isOpen = false
                        buttonListener.buttonCancelClick(Task(titleValue, statusValue))
                        titleValue = ""
                        statusValue = ""
                        }, colors = ButtonColors(
                            containerColor = Color.LightGray,
                            disabledContainerColor = Color.LightGray,
                            contentColor = Color.Red,
                            disabledContentColor = Color.Red
                        )
                    ) { Text(text = noStr, fontSize = mFontSize) }
                    Button(modifier = Modifier.padding(start = 30.dp),
                        onClick = {
                            buttonListener.buttonOkClick(Task(titleValue, statusValue))
                            titleValue = ""
                            statusValue = ""
                        }, colors = ButtonColors(
                            containerColor = Color.DarkGray,
                            disabledContainerColor = Color.DarkGray,
                            contentColor = Color.Yellow,
                            disabledContentColor = Color.Yellow
                        )
                    )
                    { Text(text = okStr, fontSize = mFontSize) }
                }
            }
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}