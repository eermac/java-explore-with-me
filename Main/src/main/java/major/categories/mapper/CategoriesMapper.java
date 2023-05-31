package major.categories.mapper;

import major.categories.dto.CategoriesDto;
import major.categories.model.Categories;

public class CategoriesMapper {
    public static Categories map(CategoriesDto dto) {
        return new Categories(null, dto.getName());
    }
}
