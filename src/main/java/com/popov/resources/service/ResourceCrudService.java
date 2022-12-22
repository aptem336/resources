package com.popov.resources.service;

import com.popov.resources.dto.ResourceDto;
import com.popov.resources.map.PanacheEntityBaseDtoMap;
import com.popov.resources.map.ResourceMap;
import com.popov.resources.persistence.ResourceEntity;
import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ResourceCrudService extends AbstractCrudService<ResourceDto, ResourceEntity> {
    @Inject
    ResourceMap resourceMap;

    @Override
    PanacheEntityBaseDtoMap<ResourceDto, ResourceEntity> map() {
        return resourceMap;
    }

    @Override
    public Uni<ResourceDto> getById(String id) {
        return ResourceEntity.<ResourceEntity>findById(Long.valueOf(id))
                .flatMap(resourceMap::toDto);
    }

    @Override
    public Uni<List<ResourceDto>> getList(int pageIndex, int pageSize) {
        return ResourceEntity.<ResourceEntity>findAll().page(pageIndex, pageSize)
                .list()
                .flatMap(resourceEntities -> Uni.join().all(resourceEntities
                                .stream()
                                .map(resourceMap::toDto)
                                .collect(Collectors.toList()))
                        .andFailFast());
    }

    @ReactiveTransactional
    @Override
    public Uni<Boolean> deleteById(String id) {
        return ResourceEntity.deleteById(Long.valueOf(id));
    }
}
