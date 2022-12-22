package com.jxareas.xpensor.features.converter.domain.model

import com.jxareas.xpensor.core.domain.model.Domain

data class Currency(
    val name: Currencies,
    val rate: Double,
) : Domain
