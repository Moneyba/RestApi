package com.theam.rest.api.converter;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public abstract class GenericConverter<E, D> {
    public abstract E convertFromDto(D dto);
    public abstract D convertFromEntity(E entity);

    public List<D> convertFromEntityCollection(Collection<E> entityCollection) {
        return entityCollection
                .stream()
                .map(this::convertFromEntity)
                .collect(Collectors.toList());
    }
}
