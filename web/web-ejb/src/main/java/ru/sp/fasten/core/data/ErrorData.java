package ru.sp.fasten.core.data;

public class ErrorData {

    public static final String ERR_JSON = "json.error"; // не верный json
    public static final String ERR_FUNC = "json.format"; // неизвестный метод
    public static final String ERR_AUTH = "customer.notFound"; // пользователь не найден
    public static final String ERR_SERV = "server.error"; // неизвестная ошибка

    private String error_code = null;
    private String error_description = null;

    public ErrorData() {
    }

    public ErrorData(String c, String t) {
        error_code = c;
        error_description = t;
    }

    public String getCode() {
        return error_code;
    }

    public void setCode(String code) {
        this.error_code = code;
    }

    public String getMessage() {
        return error_description;
    }

    public void setMessage(String text) {
        this.error_description = text;
    }
}
