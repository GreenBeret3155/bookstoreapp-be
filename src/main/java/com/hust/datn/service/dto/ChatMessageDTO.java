package com.hust.datn.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.hust.datn.domain.ChatMessage} entity.
 */
public class ChatMessageDTO implements Serializable {
    
    private Long id;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChatMessageDTO)) {
            return false;
        }

        return id != null && id.equals(((ChatMessageDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChatMessageDTO{" +
            "id=" + getId() +
            "}";
    }
}
