package com.mediconnectapi.application.repository.mapper;

public interface EntityMapper<M, E> {

    E toEntity(M model);

    M toModel(E entity);
}
