package ru.sp.fasten.core.data;

/**
 * Created by cobalt on 03.09.15.
 */
public class Response {
    private String type;
    private String sequence_id;
    private Object data;

    public Response(){}

    public Response(String s, Object o){
        type = s;
        data = o;
    }

    public Response(String s, String id, Object o){
        type = s;
        sequence_id = id;
        data = o;
    }

    public Response(String s){
        type = s;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getSequence_id() {
        return sequence_id;
    }

    public void setSequence_id(String sequence_id) {
        this.sequence_id = sequence_id;
    }
}
