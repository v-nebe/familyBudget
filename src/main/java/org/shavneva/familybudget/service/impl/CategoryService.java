package org.shavneva.familybudget.service.impl;

import lombok.RequiredArgsConstructor;
import org.shavneva.familybudget.entity.Category;
import org.shavneva.familybudget.exception.ResourceNotFoundException;
import org.shavneva.familybudget.repository.CategoryRepository;
import org.shavneva.familybudget.service.ICrudService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICrudService<Category> {

    private final CategoryRepository categoryRepository;

    @Override
    public Category create(Category category) {
        if (categoryRepository.existsByCategoryname(category.getCategoryname())) {
            throw new IllegalArgumentException("Категория с таким названием уже существует: " + category.getCategoryname());
        }
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> read() {
        return categoryRepository.findAll();
    }

    public Category getById(int id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Категория не найдена с id: " + id));
    }

    @Override
    public Category update(Category updatedCategory) {
        Category existingCategory = getById(updatedCategory.getIdcategory());

        if (updatedCategory.getCategoryname() != null && !updatedCategory.getCategoryname().isEmpty()) {
            existingCategory.setCategoryname(updatedCategory.getCategoryname());
        }

        return categoryRepository.save(existingCategory);
    }

    @Override
    public void delete(int id) {
        categoryRepository.deleteById(id);
    }
}