package com.popov.resources.map;

import com.popov.resources.dto.Dto;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.smallrye.mutiny.Uni;

public interface PanacheEntityBaseDtoMap<D extends Dto, PEB extends PanacheEntityBase> {
    Uni<D> toDto(PEB peb);

    Uni<PEB> toPanacheEntityBase(D d);
}
