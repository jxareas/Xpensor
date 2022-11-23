package com.jxareas.xpensor.core.domain.mapper

interface Mapper<S, D> {
    fun mapTo(source: S): D
    fun mapFrom(destination: D): S
    fun toList(entityList: List<S>): List<D> = entityList.map(this::mapTo)
    fun fromList(domainList: List<D>): List<S> = domainList.map(this::mapFrom)
}