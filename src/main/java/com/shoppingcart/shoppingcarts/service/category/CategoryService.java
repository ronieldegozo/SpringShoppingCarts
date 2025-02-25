package com.shoppingcart.shoppingcarts.service.category;

import com.shoppingcart.shoppingcarts.exceptions.AlreadyExistsException;
import com.shoppingcart.shoppingcarts.exceptions.InvalidRequest;
import com.shoppingcart.shoppingcarts.exceptions.ResourceNotFoundException;
import com.shoppingcart.shoppingcarts.model.Category;
import com.shoppingcart.shoppingcarts.repository.CategoryRepository;
import com.shoppingcart.shoppingcarts.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService  implements InterfaceCategoryService{

    private final CategoryRepository categoryRepository;

    private final ProductRepository productRepository;

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new InvalidRequest("No category found for id " + id));
    }

   @Override
   public Category getCategoryByName(String name) {
       return Optional.ofNullable(categoryRepository.findByName(name))
               .orElseThrow(() -> new InvalidRequest("Category with name '" + name + "' not found!"));
   }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category addCategory(Category category) {
        return Optional.of(category)
                .filter(c -> !categoryRepository.existsByName(c.getName()))
                .map(categoryRepository::save)
                .orElseThrow(()-> new AlreadyExistsException(category.getName() + " already exists"));
    }

    @Override
    public Category updateCategory(Category category, Long id) {
        return Optional.ofNullable(getCategoryById(id))
                .map(oldCategory -> {
                    oldCategory.setName(category.getName());
                    return categoryRepository.save(oldCategory);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Could not find category"));
    }

    @Override
    public void deleteCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                            .orElseThrow(() -> new InvalidRequest("No category found for id " + id));

        boolean isReferenced = productRepository.existsByCategory(category);

        if (isReferenced) {
            throw new InvalidRequest("Category ID " + id + " has product references.");
        }
        categoryRepository.delete(category);
    }
}
