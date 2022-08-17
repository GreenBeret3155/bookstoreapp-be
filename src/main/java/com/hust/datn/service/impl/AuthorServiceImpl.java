package com.hust.datn.service.impl;

import com.hust.datn.service.AuthorService;
import com.hust.datn.domain.Author;
import com.hust.datn.repository.AuthorRepository;
import com.hust.datn.service.dto.AuthorDTO;
import com.hust.datn.service.mapper.AuthorMapper;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Author}.
 */
@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {

    private final Logger log = LoggerFactory.getLogger(AuthorServiceImpl.class);

    private final AuthorRepository authorRepository;

    private final AuthorMapper authorMapper;

    public AuthorServiceImpl(AuthorRepository authorRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    @Override
    public AuthorDTO save(AuthorDTO authorDTO) {
        log.debug("Request to save Author : {}", authorDTO);
        Author author = authorMapper.toEntity(authorDTO);
        author = authorRepository.save(author);
        return authorMapper.toDto(author);
    }

    @Override
    public AuthorDTO saveName(AuthorDTO authorDTO) throws NotFoundException {
        Author author = authorMapper.toEntity(authorDTO);
        if(authorDTO.getId() == null){
            author = authorRepository.save(author);
        } else {
            Author author1 = authorRepository.findById(authorDTO.getId()).orElseThrow(() -> new NotFoundException("Author" + authorDTO.getId()));
            author1.setName(author.getName());
            author = authorRepository.save(author1);
        }
        return authorMapper.toDto(author);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AuthorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Authors");
        return authorRepository.findAll(pageable)
            .map(authorMapper::toDto);
    }

    @Override
    public Page<AuthorDTO> query(String q, Pageable pageable) {
        return authorRepository.query(q, pageable).map(authorMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<AuthorDTO> findOne(Long id) {
        log.debug("Request to get Author : {}", id);
        return authorRepository.findById(id)
            .map(authorMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Author : {}", id);
        authorRepository.deleteById(id);
    }
}
