package br.com.shido.recipecompose.presentation.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import br.com.shido.recipecompose.presentation.ui.recipe_list.FoodCategory
import br.com.shido.recipecompose.presentation.ui.recipe_list.getAllFoodCategories
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun CategoriesRow(
    scrollPosition: Int, scope: CoroutineScope, selectedCategory: FoodCategory?,
    onSelectedCategoryChanged: (String) -> Unit,
    onChangeCategoryScrollPosition: (Int) -> Unit,
    onNewSearch: () -> Unit
) {
    val scrollState = rememberScrollState()

    Row(
        modifier = Modifier.horizontalScroll(state = scrollState)
    ) {

        getAllFoodCategories().forEach { category ->
            FoodCategoryChip(
                category = category.value,
                isSelected = selectedCategory == category,
                onSelectedCategoryChanged = {
                    onSelectedCategoryChanged(it)
                    onChangeCategoryScrollPosition(scrollState.value)
                },
                onExecuteSearch = { onNewSearch() }
            )
        }

        scope.launch {
            scrollState.scrollTo(scrollPosition)
        }

    }
}
