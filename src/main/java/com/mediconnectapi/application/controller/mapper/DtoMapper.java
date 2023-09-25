package com.mediconnectapi.application.controller.mapper;

public interface DtoMapper<M, D> {

    D toDto(M model);

    M toModel(D dto);
}
