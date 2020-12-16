package com.rahil.poc.presentation.mapper

interface Mapper<out V, in D> {
    fun mapToView(type: D): V
}
