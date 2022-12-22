package com.popov.resources.service;

import com.popov.resources.dto.Dto;
import com.popov.resources.map.PanacheEntityBaseDtoMap;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.smallrye.mutiny.Uni;

import java.util.List;

public abstract class AbstractCrudService<D extends Dto, PEB extends PanacheEntityBase> implements CrudService<D> {
    abstract PanacheEntityBaseDtoMap<D, PEB> map();

    @ReactiveTransactional
    @Override
    public Uni<Void> createUpdate(D d) {
        return map()
                .toPanacheEntityBase(d)
                .flatMap(PanacheEntityBase::persistAndFlush)
                .replaceWithVoid();
    }

    @Override
    public abstract Uni<D> getById(String id);

    @Override
    public abstract Uni<List<D>> getList(int pageIndex, int pageSize);

    @Override
    public abstract Uni<Boolean> deleteById(String id);
}
