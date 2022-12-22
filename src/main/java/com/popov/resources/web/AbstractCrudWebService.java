package com.popov.resources.web;

import com.popov.resources.dto.ResourceDto;
import com.popov.resources.service.CrudService;
import io.smallrye.mutiny.Uni;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public abstract class AbstractCrudWebService implements CrudWebService<ResourceDto> {
    abstract CrudService<ResourceDto> resourceCrudService();

    @POST
    @Override
    public Uni<Void> createUpdate(ResourceDto resourceDto) {
        return resourceCrudService().createUpdate(resourceDto);
    }

    @GET
    @Path("/{id}")
    @Override
    public Uni<ResourceDto> getById(@PathParam("id") String id) {
        return resourceCrudService().getById(id);
    }

    @GET
    @Override
    public Uni<List<ResourceDto>> getList(@QueryParam("pageIndex") int pageIndex, @QueryParam("pageSize") int pageSize) {
        return resourceCrudService().getList(pageIndex, pageSize);
    }

    @DELETE
    @Path("/{id}")
    @Override
    public Uni<Boolean> deleteById(@PathParam("id") String id) {
        return resourceCrudService().deleteById(id);
    }
}
