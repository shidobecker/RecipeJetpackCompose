package br.com.shido.recipecompose.network.mapper

import br.com.shido.recipecompose.mapper.DomainMapper
import br.com.shido.recipecompose.model.Recipe
import br.com.shido.recipecompose.network.model.RecipeNetworkDto

class RecipeDtoMapper: DomainMapper<RecipeNetworkDto, Recipe> {
    override fun mapFromDto(dto: RecipeNetworkDto): Recipe {
            return Recipe(
                id = dto.pk,
                title = dto.title,
                featuredImage = dto.featuredImage,
                rating = dto.rating,
                publisher = dto.publisher,
                sourceUrl = dto.sourceUrl,
                description = dto.description,
                cookingInstructions = dto.cookingInstructions,
                ingredients = dto.ingredients ?: listOf(),
                dateAdded = dto.dateAdded,
                dateUpdated = dto.dateUpdated,

            )
    }

    override fun mapToDto(domainModel: Recipe): RecipeNetworkDto {
         return RecipeNetworkDto(
             pk = domainModel.id,
             title = domainModel.title,
             featuredImage = domainModel.featuredImage,
             rating = domainModel.rating,
             publisher = domainModel.publisher,
             sourceUrl = domainModel.sourceUrl,
             description = domainModel.description,
             cookingInstructions = domainModel.cookingInstructions,
             ingredients = domainModel.ingredients ?: listOf(),
             dateAdded = domainModel.dateAdded,
             dateUpdated = domainModel.dateUpdated,
         )
    }
}