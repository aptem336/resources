package com.popov.resources.web;

import com.popov.resources.dto.Dto;
import com.popov.resources.service.CrudService;

public interface CrudWebService<D extends Dto> extends CrudService<D> {
}
