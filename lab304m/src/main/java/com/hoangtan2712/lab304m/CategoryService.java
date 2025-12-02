package com.hoangtan2712.lab304m;

import java.util.List;
import com.hoangtan2712.lab304m.Category;
public interface CategoryService {
Category createCategory(Category category);
Category getCategoryById(Long categoryId);
List<Category> getAllCategories();
Category updateCategory(Category category);
void deleteCategory(Long categoryId);
}
