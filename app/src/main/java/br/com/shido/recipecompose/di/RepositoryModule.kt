package br.com.shido.recipecompose.di

import br.com.shido.recipecompose.network.mapper.RecipeDtoMapper
import br.com.shido.recipecompose.network.responses.RecipeService
import br.com.shido.recipecompose.repository.RecipeRepository
import br.com.shido.recipecompose.repository.RecipeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRecipeRepository(
        recipeService: RecipeService,
        recipeDtoMapper: RecipeDtoMapper
    ): RecipeRepository {
        return RecipeRepositoryImpl(recipeService, recipeDtoMapper)
    }

}