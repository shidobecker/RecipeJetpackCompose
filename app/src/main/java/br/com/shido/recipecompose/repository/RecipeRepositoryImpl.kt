package br.com.shido.recipecompose.repository

import br.com.shido.recipecompose.model.Recipe
import br.com.shido.recipecompose.network.mapper.RecipeDtoMapper
import br.com.shido.recipecompose.network.responses.RecipeService

class RecipeRepositoryImpl(
    private val recipeService: RecipeService,
    private val mapper: RecipeDtoMapper
) : RecipeRepository {

    override suspend fun search(token: String, page: Int, query: String): List<Recipe> {
        val result = recipeService.search(token, page, query).recipes
        return mapper.mapListFromDto(result)
    }

    override suspend fun get(token: String, id: Int): Recipe {
        val result = recipeService.get(token, id)
        return mapper.mapFromDto(result)
    }
}