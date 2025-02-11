package org.shavneva.familybudget.controller.impl;

import lombok.AllArgsConstructor;
import org.shavneva.familybudget.controller.ICrudController;
import org.shavneva.familybudget.mapper.IMapper;
import org.shavneva.familybudget.service.ICrudService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@AllArgsConstructor
public class BaseController <T, DTO> implements ICrudController<DTO> {
    private final ICrudService<T> service;
    private final IMapper<T, DTO> mapper;

    @Override
    public DTO create(DTO dto) {
        T entity = mapper.mapToEntity(dto);
        T createdEntity = service.create(entity);
        return mapper.mapToDTO(createdEntity);
    }

    @Override
    public List<DTO> read() {
        return mapper.mapAll(service.read());
    }

    @Override
    public DTO getById(int id) {
        return mapper.mapToDTO(service.getById(id));
    }

    @Override
    public DTO update(DTO newDTO) {
        T entity = mapper.mapToEntity(newDTO);
        service.update(entity);
        return mapper.mapToDTO(entity);
    }

    @Override
    public void delete(int id) {
        service.delete(id);
    }
}
