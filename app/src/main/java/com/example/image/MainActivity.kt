package com.example.image

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShapeConstructorApp()
        }
    }
}

data class ShapeAttributes(
    val color: String = "",
    val figure: String = "",
    val filling: String = "",
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShapeConstructorApp() {
    var shapeAttributes by remember { mutableStateOf(ShapeAttributes()) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Выбор цвета фигуры") }) },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ShapeImage(shapeAttributes)

                Spacer(modifier = Modifier.height(16.dp))


                ShapeCustomizationMenu(
                    attributes = shapeAttributes,
                    onAttributesChange = { shapeAttributes = it }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row {
                    Button(onClick = { shapeAttributes = ShapeAttributes() }) {
                        Text("Reset")
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(onClick = { shapeAttributes = randomShapeAttributes() }) {
                        Text("Randomize")
                    }
                }
            }
        }
    )
}

@Composable
fun ShapeImage(attributes: ShapeAttributes) {
    val colorRectangle = when (attributes.color) {
        "Черный" -> R.drawable.quare_black
        "Синий" -> R.drawable.quare_blue
        "Оранжевый" -> R.drawable.square_orange
        else -> R.drawable.quare_black
    }
    val colorTriangle = when (attributes.color) {
        "Черный" -> R.drawable.triangle_black
        "Синий" -> R.drawable.triangle_blue
        "Оранжевый" -> R.drawable.triangle_orange
        else -> R.drawable.triangle_black
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        if (attributes.figure == "Треугольник"){

            Image(painter = painterResource(id = colorTriangle), contentDescription = "")
        }
        if (attributes.figure == "Квадрат") {
            Image(painter = painterResource(id = colorRectangle), contentDescription = "")
        }


    }
}

@Composable
fun ShapeCustomizationMenu(
    attributes: ShapeAttributes,
    onAttributesChange: (ShapeAttributes) -> Unit
) {
    val color = listOf("Черный", "Синий", "Оранжевый")
    val figure = listOf("Квадрат", "Треугольник")



    DropdownMenuSelection("Цвет фигуры", color, attributes.color) {
        onAttributesChange(attributes.copy(color = it))
    }
    DropdownMenuSelection("Форма", figure, attributes.figure) {
        onAttributesChange(attributes.copy(figure = it))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuSelection(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
            TextField(
                value = label,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor()
            )

            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                options.forEach { option ->
                    DropdownMenuItem(text = { Text(text = option) },
                        onClick = {
                            onOptionSelected(option)
                            expanded = false
                        })}
            }

        }
    }
}

    fun randomShapeAttributes(): ShapeAttributes {
        val color = listOf("Черный", "Синий", "Оранжевый")
        val figure = listOf("Квадрат", "Треугольник")

        return ShapeAttributes(
            color = color.random(),
            figure = figure.random(),
        )
    }
