package com.jxareas.xpensor.features.converter.data.mapper

import com.jxareas.xpensor.core.domain.mapper.Mapper
import com.jxareas.xpensor.features.converter.data.api.dto.BaseCurrencyDto
import com.jxareas.xpensor.features.converter.domain.model.BaseCurrency
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BaseCurrencyDtoMapper @Inject constructor() : Mapper<BaseCurrency, BaseCurrencyDto> {

    override fun mapFromDomain(source: BaseCurrency): BaseCurrencyDto =
        BaseCurrencyDto(source.name)

    override fun mapToDomain(destination: BaseCurrencyDto): BaseCurrency =
        BaseCurrency(destination.base)
}
