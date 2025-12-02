package com.nguyenhoangtan222.lab303m;



import com.nguyenhoangtan222.lab303m.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}