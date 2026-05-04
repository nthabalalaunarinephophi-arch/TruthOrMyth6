package com.example.truthormyth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.truthormyth.ui.theme.TruthOrMythTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TruthOrMythTheme {
                QuizApp()
            }
        }
    }
}

data class Question(
    val statement: String,
    val isHack: Boolean,
    val explanation: String
)

@Composable
fun QuizApp() {
    val questions = listOf(
        Question("Putting a wet phone in rice dries it out.", false, "Wrong! That's just an urban myth."),
        Question("Banana peels polish leather shoes.", true, "Correct! That's a real time-saver!"),
        Question("Cracking knuckles causes arthritis.", false, "Wrong! That's just an urban myth.")
    )

    var screen by remember { mutableStateOf("welcome") }
    var currentIndex by remember { mutableStateOf(0) }
    var score by remember { mutableStateOf(0) }
    var feedback by remember { mutableStateOf("") }
    var showNext by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (screen == "welcome") {
            Text(
                text = "Welcome to the Flashcard Quiz!",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Can you spot fake hacks?",
                fontSize = 28.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(48.dp))
            Button(
                onClick = { screen = "quiz" },
                modifier = Modifier.height(80.dp).width(300.dp)
            ) {
                Text("START", fontSize = 34.sp)
            }
        }

        if (screen == "quiz") {
            val currentQuestion = questions[currentIndex]

            Text(
                text = currentQuestion.statement,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = feedback,
                fontSize = 28.sp,
                color = Color.Red,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(48.dp))

            if (!showNext) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Button(
                        onClick = {
                            if (currentQuestion.isHack) {
                                score += 1
                            }
                            feedback = currentQuestion.explanation
                            showNext = true
                        },
                        modifier = Modifier.height(80.dp).weight(1f)
                    ) {
                        Text("Truth", fontSize = 30.sp)
                    }
                    Button(
                        onClick = {
                            if (!currentQuestion.isHack) {
                                score += 1
                            }
                            feedback = currentQuestion.explanation
                            showNext = true
                        },
                        modifier = Modifier.height(80.dp).weight(1f)
                    ) {
                        Text("Myth", fontSize = 30.sp)
                    }
                }
            }

            if (showNext) {
                Button(
                    onClick = {
                        if (currentIndex + 1 < questions.size) {
                            currentIndex += 1
                            feedback = ""
                            showNext = false
                        } else {
                            screen = "score"
                        }
                    },
                    modifier = Modifier.height(80.dp).width(300.dp)
                ) {
                    Text("Next", fontSize = 34.sp)
                }
            }
        }

        if (screen == "score") {
            val feedbackText = if (score >= 2) "Master Hacker!" else "Stay Safe Online!"

            Text(
                text = "Final Score: " + score + " / " + questions.size,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = feedbackText,
                fontSize = 28.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(48.dp))

            Button(
                onClick = {
                    currentIndex = 0
                    score = 0
                    feedback = ""
                    showNext = false
                    screen = "quiz"
                },
                modifier = Modifier.height(80.dp).width(300.dp)
            ) {
                Text("Play Again", fontSize = 28.sp)
            }
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = { screen = "review" },
                modifier = Modifier.height(80.dp).width(300.dp)
            ) {
                Text("Review Answers", fontSize = 34.sp)
            }
        }

        if (screen == "review") {
            Text(
                text = "Review",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(24.dp))

            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text("Q1: Rice dries phones? Ans: Myth", fontSize = 26.sp)
                Spacer(modifier = Modifier.height(12.dp))
                Text("Q2: Banana peels polish shoes? Ans: Hack", fontSize = 26.sp)
                Spacer(modifier = Modifier.height(12.dp))
                Text("Q3: Knuckles cause arthritis? Ans: Myth", fontSize = 26.sp)
            }

            Spacer(modifier = Modifier.height(48.dp))
            Button(
                onClick = { screen = "score" },
                modifier = Modifier.height(80.dp).width(300.dp)
            ) {
                Text("Back to Score", fontSize = 34.sp)
            }
        }
    }
}

