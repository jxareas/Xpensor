package com.jxareas.xpensor.core.domain.mapper

import com.jxareas.xpensor.core.domain.model.Domain

interface Mapper<S : Domain, D> {
    fun mapFromDomain(source: S): D
    fun mapToDomain(destination: D): S
    fun mapFromList(entityList: List<S>): List<D> = entityList.map(this::mapFromDomain)
    fun mapToList(domainList: List<D>): List<S> = domainList.map(this::mapToDomain)
}
