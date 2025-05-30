package org.shavneva.familybudget.controller.impl;

import org.shavneva.familybudget.controller.ICrudController;
import org.shavneva.familybudget.mapper.IMapper;
import org.shavneva.familybudget.service.ICrudService;

import java.util.List;

public class BaseController <T, DTO> implements ICrudController<DTO> {
    private final ICrudService<T> service;
    private final IMapper<T, DTO> mapper;

    public BaseController(ICrudService<T> service, IMapper<T, DTO> mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    public DTO create(DTO dto) {
        T entity = mapper.mapToEntity(dto);
        T createdEntity = service.create(entity);
        return mapper.mapToDTO(createdEntity);
    }

    public List<DTO> read() {
        return mapper.mapAll(service.read());
    }

    public DTO update(DTO newDTO) {
        T entity = mapper.mapToEntity(newDTO);
        service.update(entity);
        return mapper.mapToDTO(entity);
    }

    public void delete(int id) {
        service.delete(id);
    }
}
