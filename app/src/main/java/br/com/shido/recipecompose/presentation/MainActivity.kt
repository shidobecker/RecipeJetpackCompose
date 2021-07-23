package br.com.shido.recipecompose.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import br.com.shido.recipecompose.R
import br.com.shido.recipecompose.network.responses.RecipeService
import com.google.gson.GsonBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @ExperimentalUnitApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /* setContent {
             HappyMeal()
         }*/
        setContentView(R.layout.activity_main)

    }


    /*@Preview("Main")
    @Composable
    fun ComposablePreview(){
        ComposableContent()
    }*/

    @Composable
    fun ComposableContent() {
        Column() {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .border(border = BorderStroke(width = 1.dp, color = Color.Black)),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Text",
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Text(
                    text = "Text2",
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .border(border = BorderStroke(width = 1.dp, color = Color.Black)),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Text",
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Text(
                    text = "Text 2",
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }
    }

    @ExperimentalUnitApi
    @Composable
    fun HappyMeal() {
        Column(
            modifier = Modifier
                .background(color = Color(0xFFf2f2f2))
                .fillMaxSize()
                .scrollable(orientation = Orientation.Vertical, state = rememberScrollState())
        ) {
            Image(
                bitmap = ImageBitmap.imageResource(
                    res = resources,
                    id = R.drawable.happy_meal_small
                ),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Happy Meal",
                        style = TextStyle(
                            fontSize = TextUnit(30f, TextUnitType.Sp)
                        )
                    )
                    Text(
                        text = "$5.99",
                        style = TextStyle(
                            color = Color(0xFF85bb65),
                            fontSize = TextUnit(17f, TextUnitType.Sp)
                        ),
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }

                Spacer(modifier = Modifier.padding(top = 10.dp))

                Text(text = "800 calories")

                Spacer(modifier = Modifier.padding(top = 10.dp))

                Button(
                    onClick = {},
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red),
                    border = BorderStroke(color = Color.Blue, width = 1.dp),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(text = "ORDER NOW")
                }


            }
        }
    }

}