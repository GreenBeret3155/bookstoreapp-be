package com.hust.datn.repository.impl;

import com.hust.datn.domain.CartItem;
import com.hust.datn.domain.ChatRoomDetail;
import com.hust.datn.repository.ChatRoomDetailRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ChatRoomDetailRepositoryImpl implements ChatRoomDetailRepository {

    private EntityManager em;
    private final Logger log = LoggerFactory.getLogger(ChatRoomDetailRepositoryImpl.class);

    public ChatRoomDetailRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<?> findAllRoomsAdmin(Long userAdminId){
        StringBuilder sb = new StringBuilder("select cr.*, cru.user_id as user_admin_id from chat_room cr \n" +
            "join chat_room_user cru on cr.id = cru.chat_room_id \n" +
            "where cr.id in\n" +
            "(select cr2.id from chat_room cr2\n" +
            "join chat_room_user cru2 on cru2.chat_room_id = cr2.id \n" +
            "where cru2.user_id = :userAdminId)\n" +
            "order by cr.id");
        Query query = em.createNativeQuery(sb.toString(),ChatRoomDetail.class);
        query.setParameter("userAdminId", userAdminId);
        List<?> results = query.getResultList();
        return results;
    }
}
