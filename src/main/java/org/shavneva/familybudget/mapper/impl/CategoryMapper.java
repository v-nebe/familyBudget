package org.shavneva.familybudget.mapper.impl;

import org.shavneva.familybudget.dto.CategoryDTO;
import org.shavneva.familybudget.entity.Category;
import org.shavneva.familybudget.mapper.IMapper;
import org.springframework.stereotype.Service;

@Service
public class CategoryMapper  implements IMapper<Category, CategoryDTO> {
    @Override
    public CategoryDTO mapToDTO(Category entity) {
        if(entity == null){
            return null;
        }
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setIdcategory(entity.getIdcategory());
        categoryDTO.setCategoryname(entity.getCategoryname());
        categoryDTO.setType(entity.getType());

        return  categoryDTO;
    }

    @Override
    public Category mapToEntity(CategoryDTO categoryDTO) {
        if(categoryDTO == null){
            return null;
        }
        Category category = new Category();
        category.setIdcategory(categoryDTO.getIdcategory());
        category.setCategoryname(categoryDTO.getCategoryname());
        category.setType(categoryDTO.getType());

        return category;

    }
}
