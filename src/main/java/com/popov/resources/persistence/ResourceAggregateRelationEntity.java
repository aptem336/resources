package com.popov.resources.persistence;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity
@Getter
@Setter
public class ResourceAggregateRelationEntity extends PanacheEntityBase {
    @EmbeddedId
    private ResourceAggregateRelationEmbeddedId resourceAggregateRelationEmbeddedId = new ResourceAggregateRelationEmbeddedId();
    @ManyToOne
    @MapsId("aggregatorId")
    private ResourceEntity aggregator;
    @ManyToOne
    @MapsId("aggregatedId")
    private ResourceEntity aggregated;
    private Long unitsCount;
}
