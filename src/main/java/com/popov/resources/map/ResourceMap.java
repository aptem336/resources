package com.popov.resources.map;

import com.popov.resources.dto.ResourceDto;
import com.popov.resources.persistence.ResourceAggregateRelationEntity;
import com.popov.resources.persistence.ResourceEntity;
import io.smallrye.mutiny.Uni;
import org.hibernate.reactive.mutiny.Mutiny;

import javax.enterprise.context.ApplicationScoped;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ResourceMap implements PanacheEntityBaseDtoMap<ResourceDto, ResourceEntity> {
    @Override
    public Uni<ResourceDto> toDto(ResourceEntity resourceEntity) {
        return Mutiny.fetch(resourceEntity.getAggregatedSet())
                .<List<ResourceDto>>flatMap(aggregatedResourceEntities ->
                        aggregatedResourceEntities == null || aggregatedResourceEntities.isEmpty()
                                ? Uni.createFrom().item(Collections.emptyList())
                                : Uni.join().all(aggregatedResourceEntities
                                        .stream()
                                        .map(resourceAggregateRelationEntity -> toDto(
                                                resourceAggregateRelationEntity.getAggregated())
                                                .invoke(resourceDto -> resourceDto.setUnitsCount(
                                                        resourceAggregateRelationEntity.getUnitsCount())))
                                        .collect(Collectors.toList()))
                                .andFailFast())
                .map(HashSet::new)
                .map(aggregatedResourceDTOs -> ResourceDto.builder()
                        .id(resourceEntity.getId())
                        .name(resourceEntity.getName())
                        .aggregatedSet(aggregatedResourceDTOs)
                        .build());
    }

    @Override
    public Uni<ResourceEntity> toPanacheEntityBase(ResourceDto resourceDto) {
        return Uni.createFrom().item(resourceDto.getAggregatedSet())
                .<List<ResourceAggregateRelationEntity>>flatMap(aggregatedResourceDTOs -> aggregatedResourceDTOs == null || aggregatedResourceDTOs.isEmpty()
                        ? Uni.createFrom().item(Collections.emptyList())
                        : Uni.join().all(aggregatedResourceDTOs
                                .stream()
                                .map(aggregatedResourceDTO -> toPanacheEntityBase(aggregatedResourceDTO)
                                        .map(aggregatedResourceEntity -> {
                                            ResourceAggregateRelationEntity resourceAggregateRelationEntity = new ResourceAggregateRelationEntity();
                                            resourceAggregateRelationEntity.setAggregated(aggregatedResourceEntity);
                                            resourceAggregateRelationEntity.setUnitsCount(aggregatedResourceDTO.getUnitsCount());
                                            return resourceAggregateRelationEntity;
                                        }))
                                .collect(Collectors.toList()))
                        .andFailFast())
                .flatMap(aggregateRelationEntities -> ResourceEntity.<ResourceEntity>findById(resourceDto.getId())
                        .replaceIfNullWith(ResourceEntity::new)
                        .flatMap(resourceEntity -> {
                            resourceEntity.setName(resourceDto.getName());
                            resourceEntity.setAggregatedSet(aggregateRelationEntities
                                    .stream()
                                    .peek(resourceAggregateRelationEntity -> resourceAggregateRelationEntity.setAggregator(resourceEntity))
                                    .collect(Collectors.toSet()));
                            return resourceEntity.persistAndFlush();
                        }));
    }
}
