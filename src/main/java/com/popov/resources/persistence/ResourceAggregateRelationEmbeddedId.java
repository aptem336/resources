package com.popov.resources.persistence;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class ResourceAggregateRelationEmbeddedId implements Serializable {
    @Column(name = "aggregator_id")
    private Long aggregatorId;
    @Column(name = "aggregated_id")
    private Long aggregatedId;
}
