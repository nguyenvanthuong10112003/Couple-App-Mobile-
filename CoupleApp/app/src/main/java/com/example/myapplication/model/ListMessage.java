package com.example.myapplication.model;

import java.util.LinkedList;

public class ListMessage {
    private Couple couple;
    private LinkedList<Message> messages;

    public ListMessage(Couple couple, LinkedList<Message> messages) {
        this.couple = couple;
        this.messages = messages;
    }

    public Couple getCouple() {
        return couple;
    }

    public void setCouple(Couple couple) {
        this.couple = couple;
    }

    public LinkedList<Message> getMessages() {
        return messages;
    }

    public void setMessages(LinkedList<Message> messages) {
        this.messages = messages;
    }
}
