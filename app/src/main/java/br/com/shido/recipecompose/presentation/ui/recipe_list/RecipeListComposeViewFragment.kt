package br.com.shido.recipecompose.presentation.ui.recipe_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import br.com.shido.recipecompose.HorizontalDottedProgress
import br.com.shido.recipecompose.R
import br.com.shido.recipecompose.presentation.BaseApplication
import br.com.shido.recipecompose.presentation.components.*
import br.com.shido.recipecompose.utils.SnackbarController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RecipeListComposeViewFragment : Fragment() {

    @Inject
    lateinit var application: BaseApplication

    private val viewModel: RecipeListViewModel by viewModels()
    //val viewModel2: RecipeListViewModel by activityViewModels() -> Shared View Model

    private val snackbarController = SnackbarController(lifecycleScope)

    @ExperimentalComposeUiApi
    @ExperimentalUnitApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AppThemeCompose()
            }
        }
    }

    @ExperimentalComposeUiApi
    @Composable
    private fun AppThemeCompose() {
        AppTheme(darkTheme = application.isDark.value) {

            //Everytime viewModel.recipes changes, this changes and any composable using this will recompose
            val recipes = viewModel.recipes.value

            val queryRemember =
                remember { mutableStateOf("beef") }// Remember (url: state in composable) a value computed by remember
            // is stored in the composition during initial composition, and the stored value is returned during recomposition.
            // Remember can be used to store both mutable and immutable objects

            val queryViewModel = viewModel.query.value

            val querySavedInstanceState = rememberSaveable { mutableStateOf("init") }
            //Remember saved holds value for configuration changes


            val loading = viewModel.loading.value

            val scaffoldState = rememberScaffoldState()

            Scaffold(
                topBar = {
                    SearchAppBar(
                        query = queryViewModel,
                        onQueryChanged = viewModel::onQueryChanged,
                        onNewSearch = viewModel::newSearch,
                        scrollPosition = viewModel.categoryScrollPosition,
                        selectedCategory = viewModel.selectedCategory.value,
                        onSelectedCategoryChanged = viewModel::onSelectedCategoryChanged,
                        onChangedCategoryScrollPosition = viewModel::onChangeCategoryScrollPosition,
                        scope = lifecycleScope,
                        onToggleTheme = {
                            application.toggleLightTheme()
                            snackbarController.showSnackbar(scaffoldState, "Hello there", "Hide")
                            /*lifecycleScope.launch {
                                scaffoldState.snackbarHostState.showSnackbar(
                                    "Hello there",
                                    "Hide",
                                    SnackbarDuration.Short
                                )
                            }*/
                        }
                    )

                },
                bottomBar = {//No top bar yet//
                    //MyBottomBar()
                },
                drawerContent = {
                    MyDrawer()
                },
                scaffoldState = scaffoldState,

                snackbarHost = {
                    scaffoldState.snackbarHostState
                }
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = MaterialTheme.colors.background)
                ) { //All it's children will overlay on top of each other, whatever is lower, that thing will be on top on screen

                    LazyColumn(
                        modifier = Modifier.padding(16.dp)
                    ) {


                        if (loading) {
                            items(5) {
                                ShimmerRecipeCardItem(imageHeight = 200.dp)
                            }
                        } else {
                            itemsIndexed(items = recipes) { index, recipe ->
                                RecipeCard(recipe = recipe, onClick = {})
                            }
                        }
                    }


                    // CircularIndeterminateProgressBar(isDisplayed = loading)

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
        }
    }

    @Composable
    fun SnackbarDemoCode() {
        /*     val isShowing = remember { mutableStateOf(false) }
                  Column {
                      Button(onClick = {
                          isShowing.value = true
                      }) {
                          Text("Show Snack")
                      }
                  }
                  SnackBarDemo(isShowing.value) {
                      isShowing.value = false
                  }*/
        /*   val snackbarHostState = remember { SnackbarHostState() }
           val scope = rememberCoroutineScope()

           DecoupledSnackbarDemo(snackbarHostState = snackbarHostState)

           Column {
               Button(onClick = {
                   scope.launch {
                       snackbarHostState.showSnackbar(
                           "Hey Look a snackbar",
                           actionLabel = "Hide",
                           duration = SnackbarDuration.Short
                       )
                   }
               }) {
                   Text("Show Snack")
               }
           }*/
    }

    @Composable
    fun DecoupledSnackbarDemo(
        snackbarHostState: SnackbarHostState
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val snackbar = createRef()
            SnackbarHost(
                modifier = Modifier.constrainAs(snackbar) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }, hostState = snackbarHostState,
                snackbar = { SnackbarT(snackbarHostState = snackbarHostState) })

        }
    }

    @Composable
    fun SnackbarT(snackbarHostState: SnackbarHostState) {
        Snackbar(action = {
            TextButton(onClick = {
                snackbarHostState.currentSnackbarData?.dismiss()
            }) {
                Text(
                    text = snackbarHostState.currentSnackbarData?.actionLabel ?: "Hide",
                    style = TextStyle(color = Color.White)
                )
            }
        }) {
            Text(snackbarHostState.currentSnackbarData?.message ?: "hey")
        }
    }

    @Composable
    fun SnackBarDemo(
        isShowing: Boolean,
        onHideSnackBar: () -> Unit
    ) {
        if (isShowing) {
            ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                val snackbar = createRef()
                Snackbar(
                    modifier = Modifier.constrainAs(snackbar) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                    action = {
                        Text(
                            text = "Hide",
                            modifier = Modifier.clickable(onClick = onHideSnackBar),
                            style = MaterialTheme.typography.h5
                        )
                    },
                ) {
                    Text("Hey look a snackbar")
                }
            }
        }
    }


    @Composable
    fun MyBottomBar() {
        BottomNavigation(
            elevation = 12.dp
        ) {
            BottomNavigationItem(selected = false, onClick = { }, icon = {
                Icon(
                    imageVector = Icons.Default.BrokenImage, contentDescription = null
                )
            })

            BottomNavigationItem(selected = false, onClick = { }, icon = {
                Icon(
                    imageVector = Icons.Default.Search, contentDescription = null
                )
            })

            BottomNavigationItem(selected = false, onClick = { }, icon = {
                Icon(
                    imageVector = Icons.Default.AccountBalanceWallet,
                    contentDescription = null
                )
            })
        }
    }

    @Composable
    fun MyDrawer() {
        Column() {
            Text("Item 1")
            Text("Item 2")
            Text("Item 3")
            Text("Item 4")
            Text("Item 5")
            Text("Item 6")
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