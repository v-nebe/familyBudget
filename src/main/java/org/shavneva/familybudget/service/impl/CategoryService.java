package org.shavneva.familybudget.service.impl;

import org.shavneva.familybudget.entity.Category;
import org.shavneva.familybudget.exception.ResourceNotFoundException;
import org.shavneva.familybudget.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService extends BaseService<Category, Integer> {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        super(categoryRepository);
        this.categoryRepository = categoryRepository;
    }

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
        if(updatedCategory.getType() != null && !updatedCategory.getType().isEmpty()){
            existingCategory.setType(updatedCategory.getType());
        }

        return categoryRepository.save(existingCategory);
    }

    @Override
    public void delete(int id) {
        categoryRepository.deleteById(id);
    }
}