package ru.ifmo.models;

public class Message {
    private String text;
    private String lastAnswer;
    private String photo;
    private int peerId;

    public Message(String text, int peerId) {
        this.text = text;
        this.peerId = peerId;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getPeerId() {
        return peerId;
    }

    public void setPeerId(int peerId) {
        this.peerId = peerId;
    }

    public String getLastAnswer() {
        return lastAnswer;
    }

    public void setLastAnswer(String lastAnswer) {
        this.lastAnswer = lastAnswer;
    }
}
