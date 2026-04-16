package kr.ac.hansung.cse.service;

import kr.ac.hansung.cse.exception.DuplicateCategoryException;
import kr.ac.hansung.cse.model.Category;
import kr.ac.hansung.cse.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public boolean categoryNameExists(String name) {
        if (name == null || name.isBlank()) {
            return false;
        }
        return categoryRepository.existsByName(name);
    }

    @Transactional
    public Category createCategory(String name) {
        if (categoryRepository.findByName(name).isPresent()) {
            throw new DuplicateCategoryException(name);
        }

        return categoryRepository.save(new Category(name));
    }

    @Transactional
    public void deleteCategory(Long id) {
        Long productCount = categoryRepository.countProductsByCategoryId(id);
        if (productCount > 0) {
            throw new IllegalStateException("상품 " + productCount + "개가 연결되어 있어 삭제할 수 없습니다.");
        }

        categoryRepository.delete(id);
    }
}
