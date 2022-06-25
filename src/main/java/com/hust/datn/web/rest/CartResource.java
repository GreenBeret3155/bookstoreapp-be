package com.hust.datn.web.rest;

import com.hust.datn.repository.UserRepository;
import com.hust.datn.security.SecurityUtils;
import com.hust.datn.service.CartItemService;
import com.hust.datn.service.CartService;
import com.hust.datn.service.ProductService;
import com.hust.datn.service.dto.*;
import com.hust.datn.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing {@link com.hust.datn.domain.Cart}.
 */
@RestController
@RequestMapping("/api")
public class CartResource {

    private final Logger log = LoggerFactory.getLogger(CartResource.class);

    private static final String ENTITY_NAME = "cart";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserRepository userRepository;

    private final CartService cartService;

    public CartResource(CartService cartService) {
        this.cartService = cartService;
    }

    /**
     * {@code POST  /carts} : Save (create/update) a cart.
     *
     * @param cartItemDTOS the cartDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cartDTO, or with status {@code 400 (Bad Request)} if the cart has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
//    @PostMapping("/cart")
//    public ResponseEntity<CartDetailsDTO> saveCart(@RequestBody CartDetailsDTO cartDetailsDTO) throws URISyntaxException {
//        CartDetailsDTO result = new CartDetailsDTO();
//        List<CartItemDTO> cartItemDTOS = cartItemService.saveCartItems(cartDetailsDTO.getCartItemList());
//        CartDTO cartDTO =  cartService.findOne(cartDetailsDTO.getCart().getId()).orElse(null);
//        if(car)
//
//        CartDTO result = cartService.save(cartDTO);
//        return ResponseEntity.created(new URI("/api/carts/" + result.getId()))
//            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
//            .body(result);
//    }
    @PostMapping("/cart")
    public ResponseEntity<List<?>> saveCart(@RequestBody List<CartItemDTO> cartItemDTOS) throws URISyntaxException {
        if(cartItemDTOS == null | cartItemDTOS.size() == 0){
            return ResponseEntity.ok().body(new ArrayList<>());
        }
        Long userId = SecurityUtils.getCurrentUserId(userRepository).orElseThrow(() -> new BadRequestAlertException("User not exists", ENTITY_NAME, "idnull"));
        CartDTO cartDTO =  cartService.findOneByUserId(userId).orElse(null);
        if(cartDTO == null){
            cartDTO = cartService.save(new CartDTO(userId));
        }

        CartDTO finalCartDTO = cartDTO;
        cartItemDTOS = cartItemDTOS.stream().map(e -> {
            e.setCartId(finalCartDTO.getId());
            e.setProductId(e.getId());
            e.setIsSelected(0);
            return e;
        }).collect(Collectors.toList());

        List<CartItemDTO> cartItemDTOSResult = cartItemService.saveCartItems(cartItemDTOS);

        return ResponseEntity.ok().body(cartItemDTOSResult);
    }

    /**
     * {@code GET  /carts} : get all the carts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of carts in body.
     */
    @GetMapping("/cart")
    public ResponseEntity<List<?>> getAllCartItems() {
        log.debug("REST request to get a page of Carts");
        Long userId = SecurityUtils.getCurrentUserId(userRepository).orElseThrow(() -> new BadRequestAlertException("User not exists", ENTITY_NAME, "idnull"));
        CartDTO cartDTO =  cartService.findOneByUserId(userId).orElse(null);
        if(cartDTO == null){
            return ResponseEntity.ok().body(new ArrayList<>());
        }
        List<CartItemDTO> listCartItems = cartItemService.findAllByCartId(cartDTO.getId());
        List<CartItemDetailDTO> result = new ArrayList<>();
        listCartItems.forEach(e -> {
            ProductDTO productDTO = productService.findOne(e.getProductId()).orElse(new ProductDTO());
            result.add(new CartItemDetailDTO(productDTO, e));
        });
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code GET  /carts/:id} : get the "id" cart.
     *
     * @param id the id of the cartDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cartDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/carts/{id}")
    public ResponseEntity<CartDTO> getCart(@PathVariable Long id) {
        log.debug("REST request to get Cart : {}", id);
        Optional<CartDTO> cartDTO = cartService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cartDTO);
    }

    /**
     * {@code DELETE  /carts/:id} : delete the "id" cart.
     *
     * @param id the id of the cartDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/carts/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long id) {
        log.debug("REST request to delete Cart : {}", id);
        cartService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
