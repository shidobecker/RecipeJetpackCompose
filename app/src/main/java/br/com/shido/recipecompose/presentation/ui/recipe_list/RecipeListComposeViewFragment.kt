package br.com.shido.recipecompose.presentation.ui.recipe_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import br.com.shido.recipecompose.HorizontalDottedProgress
import br.com.shido.recipecompose.R
import br.com.shido.recipecompose.presentation.components.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeListComposeViewFragment : Fragment() {

    private val viewModel: RecipeListViewModel by viewModels()
    //val viewModel2: RecipeListViewModel by activityViewModels() -> Shared View Model

    @ExperimentalComposeUiApi
    @ExperimentalUnitApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {

                //Everytime viewModel.recipes changes, this changes and any composable using this will recompose
                val recipes = viewModel.recipes.value

                val queryRemember =
                    remember { mutableStateOf("beef") }// Remember (url: state in composable) a value computed by remember
                // is stored in the composition during initial composition, and the stored value is returned during recomposition.
                // Remember can be used to store both mutable and immutable objects

                val queryViewModel = viewModel.query.value

                val querySavedInstanceState = rememberSaveable { mutableStateOf("init") }
                //Remember saved holds value for configuration changes


                Column {
                    SearchAppBar(
                        query = queryViewModel,
                        onQueryChanged = viewModel::onQueryChanged,
                        onNewSearch = viewModel::newSearch,
                        scrollPosition = viewModel.categoryScrollPosition,
                        selectedCategory = viewModel.selectedCategory.value,
                        onSelectedCategoryChanged = viewModel::onSelectedCategoryChanged,
                        onChangedCategoryScrollPosition = viewModel::onChangeCategoryScrollPosition,
                        scope = lifecycleScope
                    )


                    val loading = viewModel.loading.value

                    Box(modifier = Modifier.fillMaxSize()) { //All it's children will overlay on top of each other, whatever is lower, that thing will be on top on screen

                        LazyColumn(
                            modifier = Modifier.padding(16.dp)
                        ) {

                            if(loading) {
                                items(5){
                                    ShimmerRecipeCardItem(imageHeight = 200.dp)
                                }
                            }else{

                                itemsIndexed(items = recipes) { index, recipe ->
                                    RecipeCard(recipe = recipe, onClick = {})
                                }
                            }
                        }


                       // CircularIndeterminateProgressBar(isDisplayed = loading)

                    }


                }
            }
        }
    }


    @Composable
    private fun CategoriesRowLazy() {
        val categories = getAllFoodCategories()

        LazyRow(content = {
            items(categories) { category ->

                if (category == FoodCategory.BEEF) {
                    val annotatedText = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.Gray)) {
                            append(category.value)
                        }
                    }

                    ClickableText(
                        text = annotatedText,
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(8.dp),
                        onClick = {
                            Log.w("TAG", "CLICKED $it")
                            Log.w("TAG", "CLICKED ${category.value}")
                        }
                    )
                } else {
                    Text(
                        text = category.value,
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.secondary,
                        modifier = Modifier.padding(8.dp)
                    )

                }
            }
        })
    }


    /**
     * Example returning a view with a custom view inside
     */
    private fun viewWithCustomView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): View {
        val view = inflater.inflate(R.layout.frag_recipe_list, container, false)
        view.findViewById<ComposeView>(R.id.compose_view).setContent {
            Column(
                modifier = Modifier
                    .border(border = BorderStroke(2.dp, Color.Black))
                    .padding(16.dp)
            ) {
                Text("This is a composable inside the fragment")
                Spacer(modifier = Modifier.padding(10.dp))
                CircularProgressIndicator()
                Spacer(modifier = Modifier.padding(10.dp))
                Text(text = "Neat")
                Spacer(modifier = Modifier.padding(10.dp))

                val customView = HorizontalDottedProgress(LocalContext.current)
                AndroidView(modifier = Modifier.fillMaxSize(), factory = { context ->
                    customView.apply {
                        this.setOnClickListener {
                            Log.w("TAG", "Cliick ON Custom")
                        }
                    }
                })
            }
        }
        return view
    }
}