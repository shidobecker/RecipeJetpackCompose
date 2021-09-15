package br.com.shido.recipecompose.presentation.ui.recipe_list

/**
 * Describing events that can happen on RecipeList UI
 */
sealed class RecipeListEvent {

    object NewSearchEvent : RecipeListEvent()

    object NextPageEvent : RecipeListEvent()

    //Restore after process death
    object RestoreStateEvent: RecipeListEvent()

}