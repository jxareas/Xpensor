package com.jxareas.xpensor.domain.mapper

import com.jxareas.xpensor.domain.model.Domain

interface DomainMapper<S, D : Domain> {
    fun toDomain(entity: S): D
    fun fromDomain(domain: D): S
    fun toDomainList(entityList: List<S>): List<D> = entityList.map(this::toDomain)
    fun fromDomainList(domainList: List<D>): List<S> = domainList.map(this::fromDomain)
}