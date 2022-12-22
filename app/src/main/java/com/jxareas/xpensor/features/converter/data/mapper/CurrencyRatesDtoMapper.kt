package com.jxareas.xpensor.features.converter.data.mapper

import com.jxareas.xpensor.core.domain.mapper.Mapper
import com.jxareas.xpensor.features.converter.data.api.dto.CurrencyRatesDto
import com.jxareas.xpensor.features.converter.domain.model.Currencies
import com.jxareas.xpensor.features.converter.domain.model.Currency
import com.jxareas.xpensor.features.converter.domain.model.CurrencyRates
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyRatesDtoMapper @Inject constructor() : Mapper<CurrencyRates, CurrencyRatesDto> {

    override fun mapFromDomain(source: CurrencyRates): CurrencyRatesDto =
        CurrencyRatesDto(
            source.usd.rate,
            source.eur.rate,
            source.nio.rate,
            source.crc.rate,
            source.gbp.rate
        )

    override fun mapToDomain(destination: CurrencyRatesDto): CurrencyRates =
        CurrencyRates(
            crc = Currency(Currencies.CRC, destination.crc),
            usd = Currency(Currencies.USD, destination.usd),
            eur = Currency(Currencies.EUR, destination.eur),
            nio = Currency(Currencies.NIO, destination.nio),
            gbp = Currency(Currencies.GBP, destination.gbp),
        )
}
