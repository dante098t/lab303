package com.hoangtan2712.lab304m;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hoangtan2712.lab304m.Category;
public interface CategoryRepository extends JpaRepository<Category, Long> {
}