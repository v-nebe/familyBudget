package org.shavneva.familybudget.repository;

import org.shavneva.familybudget.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    boolean existsByCategoryname(String categoryname);
}