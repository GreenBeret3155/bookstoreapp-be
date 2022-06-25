package com.hust.datn.repository.impl;

import com.hust.datn.domain.CartItem;
import com.hust.datn.repository.CartItemCustomRepository;
import com.hust.datn.repository.CartItemRepository;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@Transactional
public class CartItemRepositoryImpl implements CartItemCustomRepository {

    @PersistenceContext
    private EntityManager em;

    private final Logger log = LoggerFactory.getLogger(CartItemRepositoryImpl.class);

    public List<CartItem> saveCartItems(List<CartItem> cartItemList){
        StringBuilder sb = new StringBuilder("INSERT INTO cart_item (cart_id, product_id, quantity, is_selected)");
        sb.append("VALUES (:cart_id, :product_id, :quantity, :is_selected)");
        sb.append(" ON DUPLICATE KEY update cart_id=:cart_id, product_id=:product_id, quantity=:quantity, is_selected=:is_selected");

        List<CartItem> results = new ArrayList<>();
        cartItemList.stream().forEach(e -> {
            try {
                int result = em.createNativeQuery(String.valueOf(sb), CartItem.class)
                    .setParameter("cart_id", e.getCartId())
                    .setParameter("product_id", e.getProductId())
                    .setParameter("quantity", e.getQuantity())
                    .setParameter("is_selected", e.getIsSelected())
                    .executeUpdate();
                log.debug("Update cart" + String.valueOf(result));
                if(result != 0) {
                    results.add(e);
                }
            } catch (Exception exception) {
                log.error("Error: ", exception);
            }
        });
        return results;
    }

    @Override
    public void deleteRemovedCartItems(List<CartItem> cartItemList) {
        List<Long> cartItemIds = cartItemList.stream().map(e -> e.getProductId()).collect(Collectors.toList());
        String cartItemIdsString = StringUtils.join(cartItemIds,",");
        StringBuilder sb = new StringBuilder("DELETE FROM cart_item\n");
        sb.append("WHERE cart_id = :cart_id\n");
        sb.append("AND product_id not in (");
        sb.append(cartItemIdsString);
        sb.append(")");
        try {
            int result = em.createNativeQuery(sb.toString()).setParameter("cart_id", cartItemList.get(0).getCartId()).executeUpdate();
            log.debug("Delete removed items " + result);
        } catch (Exception exception) {
            log.error("Error: ", exception);
        }
    }
}
