package major.categories.controller;

import major.categories.dto.CategoriesDto;
import major.categories.model.Categories;
import major.categories.service.CategoriesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/categories")
@Slf4j
public class CategoriesController {
    private final CategoriesService service;

    @PostMapping
    public ResponseEntity<Categories> add(@Valid @RequestBody CategoriesDto categories) {
        log.info("Добавляем новую категорию");
        return new ResponseEntity<>(service.add(categories), HttpStatus.CREATED);
    }

    @DeleteMapping("/{catId}")
    public ResponseEntity<?> delete(@PathVariable Long catId) {
        log.info("Удаляем категорию");
        service.delete(catId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("{catId}")
    public Categories update(@PathVariable Long catId, @Valid @RequestBody CategoriesDto categories) {
        log.info("Обновляем категорию");
        return service.update(catId, categories);
    }
}
