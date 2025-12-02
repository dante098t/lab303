package com.nguyenhoangtan222.lab303m;

import com.nguyenhoangtan222.lab303m.Category;
import com.nguyenhoangtan222.lab303m.CategoryRepository;
import com.nguyenhoangtan222.lab303m.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    @Override
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category updateCategory(Long id, Category categoryDetails) {
        Category category = getCategoryById(id);
        category.setCategoryName(categoryDetails.getCategoryName());
        category.setCategoryDescription(categoryDetails.getCategoryDescription());
        category.setParentId(categoryDetails.getParentId());
        return categoryRepository.save(category);
    }
    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}