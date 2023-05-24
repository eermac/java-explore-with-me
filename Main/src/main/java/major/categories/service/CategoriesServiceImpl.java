package major.categories.service;

import major.categories.dto.CategoriesDto;
import major.categories.mapper.CategoriesMapper;
import major.categories.model.Categories;
import major.categories.repository.CategoriesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoriesServiceImpl implements CategoriesService{
    private final CategoriesRepository repository;

    @Override
    public Categories add(CategoriesDto dto) {
        try {
            return repository.save(CategoriesMapper.map(dto));
        } catch (DataAccessException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (DataAccessException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @Override
    public Categories update(Long id, CategoriesDto dto) {
        try {
            Categories updateCategories = repository.findById(id).get();
            updateCategories.setName(dto.getName());

            return repository.save(updateCategories);
        } catch (DataAccessException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @Override
    public List<Categories> getAll(Integer from, Integer size) {
        PageRequest page = PageRequest.of(from > 0 ? from / size : 0, size);
        return repository.findAll(page).getContent();
    }

    @Override
    public Categories get(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
