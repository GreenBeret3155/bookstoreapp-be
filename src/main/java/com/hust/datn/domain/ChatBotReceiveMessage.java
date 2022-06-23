package com.hust.datn.domain;

public class ChatBotReceiveMessage {
    private String recipient_id;
    private String text;

    public ChatBotReceiveMessage() {
    }

    public ChatBotReceiveMessage(String recipient_id, String text) {
        this.recipient_id = recipient_id;
        this.text = text;
    }

    public String getRecipient_id() {
        return recipient_id;
    }

    public void setRecipient_id(String recipient_id) {
        this.recipient_id = recipient_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "{" +
            "\"recipient_id\":\"" + recipient_id + '\"' +
            ", \"text\": \"" + text + '\"' +
            '}';
    }

}
