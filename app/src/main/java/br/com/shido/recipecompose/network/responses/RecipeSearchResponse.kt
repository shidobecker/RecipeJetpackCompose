package br.com.shido.recipecompose.network.responses

import br.com.shido.recipecompose.network.model.RecipeNetworkDto
import com.google.gson.annotations.SerializedName

class RecipeSearchResponse(
    @SerializedName("count")
    var count: Int,

    @SerializedName("results")
    var recipes: List<RecipeNetworkDto>
)