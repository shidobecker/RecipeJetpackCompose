package br.com.shido.recipecompose.mapper

interface DomainMapper<DTO, DomainModel> {
    fun mapFromDto(dtoModel: DTO): DomainModel
    fun mapToDto(domainModel: DomainModel): DTO

    fun mapListFromDto(dtoModelList: List<DTO>): List<DomainModel> {
        return dtoModelList.map { mapFromDto(it) }
    }

    fun mapListToDto(domainModels: List<DomainModel>): List<DTO> {
        return domainModels.map { mapToDto(it) }
    }

}