package com.nguyenhoangtan222.lab303m;

import java.util.List;

import com.nguyenhoangtan222.lab303m.Category;

public interface CategoryService {
    Category createCategory(Category category);
    Category getCategoryById(Long id);
    List<Category> getAllCategories();
    Category updateCategory(Long id, Category category);
    void deleteCategory(Long id);
}