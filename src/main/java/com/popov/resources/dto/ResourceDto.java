package com.popov.resources.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class ResourceDto implements Dto {
    private Long id;
    private String name;
    private Set<ResourceDto> aggregatedSet;
    private Long unitsCount;
}
