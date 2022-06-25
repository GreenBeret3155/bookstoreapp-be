package com.hust.datn.service.impl;

import com.hust.datn.repository.CartItemCustomRepository;
import com.hust.datn.service.CartItemService;
import com.hust.datn.domain.CartItem;
import com.hust.datn.repository.CartItemRepository;
import com.hust.datn.service.dto.CartItemDTO;
import com.hust.datn.service.mapper.CartItemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link CartItem}.
 */
@Service
@Transactional
public class CartItemServiceImpl implements CartItemService {

    private final Logger log = LoggerFactory.getLogger(CartItemServiceImpl.class);

    @Autowired
    private CartItemCustomRepository cartItemCustomRepository;

    private final CartItemRepository cartItemRepository;

    private final CartItemMapper cartItemMapper;

    public CartItemServiceImpl(CartItemRepository cartItemRepository, CartItemMapper cartItemMapper) {
        this.cartItemRepository = cartItemRepository;
        this.cartItemMapper = cartItemMapper;
    }

    @Override
    public CartItemDTO save(CartItemDTO cartItemDTO) {
        log.debug("Request to save CartItem : {}", cartItemDTO);
        CartItem cartItem = cartItemMapper.toEntity(cartItemDTO);
        cartItem = cartItemRepository.save(cartItem);
        return cartItemMapper.toDto(cartItem);
    }

    @Override
    public List<CartItemDTO> saveCartItems(List<CartItemDTO> cartItemDTOs) {
        log.debug("Request to save CartItem : {}", cartItemDTOs);
        Map<Long, CartItemDTO> itemsMap = new HashMap<>();
        cartItemDTOs.stream().forEach(e -> {
            itemsMap.put(e.getProductId(), e);
        });
        cartItemDTOs = new ArrayList<CartItemDTO>(itemsMap.values());
        List<CartItem> cartItems = cartItemDTOs.stream().map(cartItemMapper::toEntity).collect(Collectors.toList());
        cartItemCustomRepository.deleteRemovedCartItems(cartItems);
        List<CartItem> results = cartItemCustomRepository.saveCartItems(cartItems);
        return results.stream().map(cartItemMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CartItemDTO> findAllByCartId(Long cartId) {
        log.debug("Request to get all CartItems");
        return cartItemRepository.findAllByCartId(cartId).stream().map(cartItemMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CartItemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CartItems");
        return cartItemRepository.findAll(pageable)
            .map(cartItemMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<CartItemDTO> findOne(Long id) {
        log.debug("Request to get CartItem : {}", id);
        return cartItemRepository.findById(id)
            .map(cartItemMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CartItem : {}", id);
        cartItemRepository.deleteById(id);
    }
}
