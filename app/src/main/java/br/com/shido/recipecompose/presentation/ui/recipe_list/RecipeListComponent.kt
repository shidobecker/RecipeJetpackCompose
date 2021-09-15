package br.com.shido.recipecompose.presentation.ui.recipe_list

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.shido.recipecompose.R
import br.com.shido.recipecompose.model.Recipe
import br.com.shido.recipecompose.presentation.components.CircularIndeterminateProgressBar
import br.com.shido.recipecompose.presentation.components.DefaultSnackbar
import br.com.shido.recipecompose.presentation.components.RecipeCard
import br.com.shido.recipecompose.presentation.components.ShimmerRecipeCardItem
import br.com.shido.recipecompose.utils.SnackbarController
import kotlinx.coroutines.launch

@ExperimentalUnitApi
@Composable
fun RecipeList(
    loading: Boolean,
    recipes: List<Recipe>,
    onChangeRecipeScrollPosition: (Int) -> Unit,
    page: Int,
    onTriggerEvent: (RecipeListEvent) -> Unit,
    scaffoldState: ScaffoldState,
    snackbarController: SnackbarController,
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
    ) { //All it's children will overlay on top of each other, whatever is lower, that thing will be on top on screen

        LazyColumn(
            modifier = Modifier.padding(16.dp)
        ) {


            if (loading && recipes.isEmpty()) {
                items(5) {
                    ShimmerRecipeCardItem(imageHeight = 200.dp)
                }
            } else {
                itemsIndexed(items = recipes) { index, recipe ->
                    onChangeRecipeScrollPosition(index)//Keep track of scroll position
                    if ((index + 1) >= (page * PAGE_SIZE) && !loading) {
                        onTriggerEvent(RecipeListEvent.NextPageEvent) //When reach last item and is not loading
                    }
                    RecipeCard(recipe = recipe, onClick = {
                        if (recipe.id != null) {
                            val bundle = Bundle()
                            bundle.putInt("recipeId", recipe.id)
                            navController.navigate(R.id.viewRecipe, bundle)

                        } else {
                            snackbarController.getScope().launch {
                                snackbarController.showSnackbar(
                                    scaffoldState = scaffoldState,
                                    message = "Recipe Error",
                                    actionLabel = "Ok"
                                )
                            }
                        }
                    })
                }
            }
        }


        CircularIndeterminateProgressBar(isDisplayed = loading)

        DefaultSnackbar(
            snackbarHostState = scaffoldState.snackbarHostState,
            modifier = Modifier.align(
                Alignment.BottomCenter
            )
        ) {
            scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
        }

    }
}