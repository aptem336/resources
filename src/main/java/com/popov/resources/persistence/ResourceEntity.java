package com.popov.resources.persistence;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class ResourceEntity extends PanacheEntityBase {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @OneToMany(mappedBy = "aggregator", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<ResourceAggregateRelationEntity> aggregatedSet = new HashSet<>();
}
