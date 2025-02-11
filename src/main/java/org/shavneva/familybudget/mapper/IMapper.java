package org.shavneva.familybudget.mapper;

import java.util.List;

import static java.util.stream.Collectors.toList;

public interface IMapper<E, DTO>{
    DTO mapToDTO(E entity);

    E mapToEntity(DTO dto);

    default List<DTO> mapAll(List<E> e){
        return e.stream()
                .map(this::mapToDTO)
                .collect(toList());
    }
}
