package com.popov.resources.service;

import com.popov.resources.dto.Dto;
import io.smallrye.mutiny.Uni;

import java.util.List;

public interface CrudService<D extends Dto> {
    Uni<Void> createUpdate(D d);

    Uni<D> getById(String id);

    Uni<List<D>> getList(int pageIndex, int pageSize);

    Uni<Boolean> deleteById(String id);
}
