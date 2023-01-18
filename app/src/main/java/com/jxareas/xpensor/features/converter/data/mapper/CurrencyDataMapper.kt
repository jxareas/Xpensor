package com.jxareas.xpensor.features.converter.data.mapper

import com.jxareas.xpensor.features.converter.data.remote.dto.BaseCurrencyDto
import com.jxareas.xpensor.features.converter.data.remote.dto.CurrencyConversionDto
import com.jxareas.xpensor.features.converter.data.remote.dto.CurrencyRatesDto
import com.jxareas.xpensor.features.converter.domain.model.BaseCurrency
import com.jxareas.xpensor.features.converter.domain.model.Currencies
import com.jxareas.xpensor.features.converter.domain.model.Currency
import com.jxareas.xpensor.features.converter.domain.model.CurrencyComparison
import com.jxareas.xpensor.features.converter.domain.model.CurrencyRates

fun BaseCurrencyDto.toBaseCurrency(): BaseCurrency =
    BaseCurrency(name = currencyName)

fun CurrencyRatesDto.toCurrencyRates(): CurrencyRates =
    CurrencyRates(
        crc = Currency(Currencies.CRC, crc),
        usd = Currency(Currencies.USD, usd),
        eur = Currency(Currencies.EUR, eur),
        nio = Currency(Currencies.NIO, nio),
        gbp = Currency(Currencies.GBP, gbp),
    )

fun CurrencyConversionDto.toCurrencyComparison(): CurrencyComparison =
    CurrencyComparison(
        baseCurrency = baseCurrencyDto.toBaseCurrency(),
        currencyRates = ratesDto.toCurrencyRates(),
    )
