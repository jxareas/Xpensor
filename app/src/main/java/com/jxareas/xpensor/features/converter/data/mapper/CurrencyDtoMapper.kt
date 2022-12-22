package com.jxareas.xpensor.features.converter.data.mapper

import com.jxareas.xpensor.core.domain.mapper.Mapper
import com.jxareas.xpensor.features.converter.data.api.dto.CurrencyDto
import com.jxareas.xpensor.features.converter.domain.model.CurrencyComparison
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyDtoMapper @Inject constructor(
    private val currencyRatesDtoMapper: CurrencyRatesDtoMapper,
    private val baseCurrencyDtoMapper: BaseCurrencyDtoMapper,
) : Mapper<CurrencyComparison, CurrencyDto> {

    override fun mapFromDomain(source: CurrencyComparison): CurrencyDto =
        CurrencyDto(
            rates = currencyRatesDtoMapper.mapFromDomain(source.currencyRates),
            baseCurrency = baseCurrencyDtoMapper.mapFromDomain(source.baseCurrency),
        )

    override fun mapToDomain(destination: CurrencyDto): CurrencyComparison =
        CurrencyComparison(
            baseCurrency = baseCurrencyDtoMapper.mapToDomain(destination.baseCurrency),
            currencyRates = currencyRatesDtoMapper.mapToDomain(destination.rates)
        )
}
