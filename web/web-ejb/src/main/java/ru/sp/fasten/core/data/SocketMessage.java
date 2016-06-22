package ru.sp.fasten.core.data;

public class SocketMessage<T> {

    private String type;
    private String sequence_id;
    private T data;

    public SocketMessage() {
    }

    public SocketMessage(String name, String sequence_id, T args) {
        this.type = name;
        this.data = args;
        this.sequence_id = sequence_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSequence_id() {
        return sequence_id;
    }

    public void setSequence_id(String sequence_id) {
        this.sequence_id = sequence_id;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}