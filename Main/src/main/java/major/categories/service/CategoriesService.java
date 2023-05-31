package major.categories.service;

import major.categories.dto.CategoriesDto;
import major.categories.model.Categories;

import java.util.List;

public interface CategoriesService {
    Categories add(CategoriesDto name);

    void delete(Long id);

    Categories update(Long id, CategoriesDto name);

    List<Categories> getAll(Integer from, Integer size);

    Categories get(Long id);
}
