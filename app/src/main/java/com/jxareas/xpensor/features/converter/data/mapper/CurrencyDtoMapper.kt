package com.jxareas.xpensor.features.converter.data.mapper

import com.jxareas.xpensor.features.converter.data.api.dto.BaseCurrencyDto
import com.jxareas.xpensor.features.converter.data.api.dto.CurrencyDto
import com.jxareas.xpensor.features.converter.data.api.dto.CurrencyRatesDto
import com.jxareas.xpensor.features.converter.domain.model.BaseCurrency
import com.jxareas.xpensor.features.converter.domain.model.Currencies
import com.jxareas.xpensor.features.converter.domain.model.Currency
import com.jxareas.xpensor.features.converter.domain.model.CurrencyComparison
import com.jxareas.xpensor.features.converter.domain.model.CurrencyRates

fun BaseCurrencyDto.toDomain(): BaseCurrency =
    BaseCurrency(name = currencyName)

fun CurrencyRatesDto.toDomain(): CurrencyRates =
    CurrencyRates(
        crc = Currency(Currencies.CRC, crc),
        usd = Currency(Currencies.USD, usd),
        eur = Currency(Currencies.EUR, eur),
        nio = Currency(Currencies.NIO, nio),
        gbp = Currency(Currencies.GBP, gbp),
    )

fun CurrencyDto.toDomain(): CurrencyComparison =
    CurrencyComparison(
        baseCurrency = baseCurrency.toDomain(),
        currencyRates = rates.toDomain(),
    )
