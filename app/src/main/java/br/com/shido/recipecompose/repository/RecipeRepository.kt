package br.com.shido.recipecompose.repository

import br.com.shido.recipecompose.model.Recipe

interface RecipeRepository {

    suspend fun search(token: String, page: Int, query: String): List<Recipe>

    suspend fun get(token: String, id: Int): Recipe

}