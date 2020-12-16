package com.rahil.poc.remote.mapper

interface ModelMapper<in M, out E> {
    fun mapFromModel(model: M): E
}
