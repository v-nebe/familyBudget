package org.shavneva.familybudget.controller.impl;

import org.shavneva.familybudget.dto.CategoryDTO;
import org.shavneva.familybudget.entity.Category;
import org.shavneva.familybudget.mapper.impl.CategoryMapper;
import org.shavneva.familybudget.service.impl.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController extends BaseController<Category, CategoryDTO>{

    @Autowired
    public CategoryController(CategoryService categoryService, CategoryMapper categoryMapper, CategoryService categoryService1) {
        super(categoryService, categoryMapper);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CategoryDTO create(CategoryDTO categoryDTO) {
        return super.create(categoryDTO);
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public List<CategoryDTO> read() {
        return super.read();
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CategoryDTO update(CategoryDTO categoryDTO) {
        return super.update(categoryDTO);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void delete(int id) {
        super.delete(id);
    }
}
