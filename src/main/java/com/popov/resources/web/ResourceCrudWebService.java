package com.popov.resources.web;

import com.popov.resources.dto.ResourceDto;
import com.popov.resources.service.CrudService;
import com.popov.resources.service.ResourceCrudService;

import javax.inject.Inject;
import javax.ws.rs.Path;

@Path("/resource")
public class ResourceCrudWebService extends AbstractCrudWebService {
    @Inject
    ResourceCrudService resourceCrudService;
    @Override
    CrudService<ResourceDto> resourceCrudService() {
        return resourceCrudService;
    }
}
