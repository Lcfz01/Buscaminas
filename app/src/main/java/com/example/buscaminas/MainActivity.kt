package com.example.buscaminas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.example.buscaminas.ui.theme.BuscaminasTheme
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Random


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BuscaminasTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                        color = MaterialTheme.colorScheme.background) {
                    Column(){
                        encabezado()
                        tablero("IOS")
                    }

                }
            }
        }
    }
}
@Composable
fun encabezado(modifier: Modifier= Modifier){
    Column (modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(0.1F)
        .background(color = Color.Blue),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Text(text = "Buena suerte en las minas",style= TextStyle(fontSize = 24.sp))
    }
}
@Composable
fun tablero(name: String, modifier: Modifier = Modifier) {
    val columnas = 6
    val filas = 10

    var estadoBotones = remember{List(filas*columnas){ mutableListOf<Boolean>()}}
    var minas = remember{List(filas*columnas){ mutableListOf(asignaMina())}}
    var verAlerta = remember {
        mutableListOf(false)
    }

    if (verAlerta.value){
        AlertDialog(onDismissRequest = {verAlerta.value = false},
            confirmButton = {
                Button(onClick = {
                    verAlerta.value=false
                    estadoBotones.forEach{it.value=true}
                    minas.forEach{it.value= asignaMina() }
                }) {
                    Text(text = "Aceptar y reiniciar el juego")
                }
            },
            title = { Text(text = "Perdiste")},
            text =  { Text(text = "Encontraste una mina")})
    }
    Column(modifier = Modifier.fillMaxSize(0.1F), verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){

        for(i in 0 until filas) {
            Row (modifier= Modifier
                .fillMaxSize()
                .weight(.1f)){
                for(j in 0 until columnas){
                    val index = i*columnas+j
                    Button(onClick = {
                        estadoBotones[index].value =! estadoBotones[index].value
                        if(minas[index].value){
                            verAlerta.value = true
                        }
                    },
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(.1f)
                            .padding(1.dp)
                            ,shape= RectangleShape
                            ,   enabled = estadoBotones[index].value){
                        Text(text = "x")
                    }
                }
            }
        }
    }
}

fun asignaMina(): Boolean
{
    val random = Random()
    val numeroRandom = random.nextInt(11)

    if (numeroRandom > 8){
        return true
    }
    return false
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BuscaminasTheme {
        Column {
            encabezado()
            tablero(name = "IOS")
        }
    }
}