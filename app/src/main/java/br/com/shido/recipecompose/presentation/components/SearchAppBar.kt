package br.com.shido.recipecompose.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import br.com.shido.recipecompose.presentation.ui.recipe_list.FoodCategory
import kotlinx.coroutines.CoroutineScope

@ExperimentalComposeUiApi
@Composable
fun SearchAppBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    onNewSearch: () -> Unit,
    scrollPosition: Int,
    selectedCategory: FoodCategory?,
    onSelectedCategoryChanged: (String) -> Unit,
    onChangedCategoryScrollPosition: (Int) -> Unit,
    scope: CoroutineScope
) {
    Surface(
        elevation = 8.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        color = Color.White,
    ) {

        Column {
            Row(modifier = Modifier.fillMaxWidth()) {
                SearchTextField(query, onQueryChanged, onNewSearch)
            }
            CategoriesRow(
                scrollPosition,
                scope,
                selectedCategory,
                onSelectedCategoryChanged,
                onChangedCategoryScrollPosition,
                onNewSearch
            )
        }

    }
}


@ExperimentalComposeUiApi
@Composable
private fun SearchTextField(
    queryViewModel: String,
    onQueryChanged: (String) -> Unit,
    onNewSearch: () -> Unit
) {
    val keyboardController = LocalFocusManager.current
    val keyboardController2 = LocalSoftwareKeyboardController.current

    TextField(
        value = queryViewModel,

        onValueChange = { newValue ->
            onQueryChanged(newValue)
        },

        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(8.dp),

        label = {
            Text(text = "Search")
        },

        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search,
            keyboardType = KeyboardType.Text,
        ),

        keyboardActions = KeyboardActions(onSearch = {
            onNewSearch()
            keyboardController.clearFocus()
            keyboardController2?.hide()
        }),

        leadingIcon = {
            Icon(imageVector = Icons.Filled.Search, contentDescription = "Search Icon")
        },

        colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.surface),

        textStyle = TextStyle(color = MaterialTheme.colors.onSurface),

        )


}