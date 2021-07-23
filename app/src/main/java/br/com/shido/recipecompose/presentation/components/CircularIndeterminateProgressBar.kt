package br.com.shido.recipecompose.presentation.components

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet


@ExperimentalUnitApi
@Composable
fun CircularIndeterminateProgressBar(
    isDisplayed: Boolean
) {

    if (isDisplayed) {
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) { //Calculated based on screen size
            //Check for portrait or landscape

            val constraintSet = if (minWidth < 600.dp) { //Portrait mode
                myDecoupledConstraints(0.3f)
            } else {
                myDecoupledConstraints(0.6f)
            }


            ConstraintLayout(modifier = Modifier.fillMaxSize(), constraintSet = constraintSet) {

                CircularProgressIndicator(
                    modifier = Modifier.layoutId("progressBar"), //Referencing
                    color = MaterialTheme.colors.primary
                )

                Text(
                    text = "Loading...", style = TextStyle(
                        color = Color.Black, fontSize = TextUnit(
                            15f,
                            TextUnitType.Sp
                        )
                    ),
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .layoutId("text")
                )
            }


        }


    }
}

private fun myDecoupledConstraints(verticalBias: Float): ConstraintSet {
    return ConstraintSet {
        val guideline = createGuidelineFromTop(verticalBias)
        val progressBar = createRefFor("progressBar") //Create and gives this id
        val text = createRefFor("text")

        constrain(progressBar) {
            top.linkTo(guideline)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(text) {
            top.linkTo(progressBar.bottom)
            start.linkTo(progressBar.start)
            end.linkTo(progressBar.end)
        }
    }
}


/*
 CircularProgressIndicator(
                    modifier = Modifier.constrainAs(progressBar) { //Adding Constraint
                        top.linkTo(topGuideline)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                    color = MaterialTheme.colors.primary
                )
 */