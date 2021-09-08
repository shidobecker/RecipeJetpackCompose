package br.com.shido.recipecompose.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
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
    onToggleTheme: () -> Unit,
    scope: CoroutineScope
) {
    Surface(
        elevation = 8.dp,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.surface),
    ) {

        Column {
            Row(modifier = Modifier.fillMaxWidth()) {
                SearchTextField(query, onQueryChanged, onNewSearch)
                ConstraintLayout(modifier = Modifier.align(Alignment.CenterVertically)) {
                    val menu = createRef()
                    IconButton(onClick = {
                        onToggleTheme()
                    }, modifier = Modifier.constrainAs(menu) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }) {
                        Icon(imageVector = Icons.Filled.MoreVert, contentDescription = null)
                    }
                }
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

        textStyle = MaterialTheme.typography.button,

        colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.surface),
    )


}