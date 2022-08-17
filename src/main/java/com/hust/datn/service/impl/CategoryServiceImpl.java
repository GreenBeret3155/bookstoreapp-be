package com.hust.datn.service.impl;

import com.hust.datn.domain.Author;
import com.hust.datn.service.CategoryService;
import com.hust.datn.domain.Category;
import com.hust.datn.repository.CategoryRepository;
import com.hust.datn.service.dto.AuthorDTO;
import com.hust.datn.service.dto.CategoryDTO;
import com.hust.datn.service.mapper.CategoryMapper;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Category}.
 */
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public CategoryDTO save(CategoryDTO categoryDTO) {
        log.debug("Request to save Category : {}", categoryDTO);
        Category category = categoryMapper.toEntity(categoryDTO);
        category = categoryRepository.save(category);
        return categoryMapper.toDto(category);
    }

    @Override
    public CategoryDTO saveName(CategoryDTO categoryDTO) throws NotFoundException {
        Category category = categoryMapper.toEntity(categoryDTO);
        if(categoryDTO.getId() == null){
            category = categoryRepository.save(category);
        } else {
            Category category1 = categoryRepository.findById(categoryDTO.getId()).orElseThrow(() -> new NotFoundException("Author" + categoryDTO.getId()));
            category1.setName(category.getName());
            category = categoryRepository.save(category1);
        }
        return categoryMapper.toDto(category);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Categories");
        return categoryRepository.findAll(pageable)
            .map(categoryMapper::toDto);
    }

    @Override
    public List<CategoryDTO> findAllLeaf() {
        return categoryRepository.findAllByIsLeaf(1).stream().map(categoryMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public Page<CategoryDTO> query(String q, Pageable pageable) {
        return categoryRepository.query(q, pageable).map(categoryMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<CategoryDTO> findOne(Long id) {
        log.debug("Request to get Category : {}", id);
        return categoryRepository.findById(id)
            .map(categoryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Category : {}", id);
        categoryRepository.deleteById(id);
    }
}
