var requestSuccess = {
    type: "LOGIN_CUSTOMER",
    sequence_id: "09caaa73-b2b1-187e-2b24-683550a49b23",
    data: {
        email: "SUCCESS_USER",
        password: "PASSWORD"
    }
};
var requestFail = {
    type: "LOGIN_CUSTOMER",
    sequence_id: "10caaa73-b2b1-187e-2b24-683550a49b24",
    data: {
        email: "FAIL_USER",
        password: "PASSWORD"
    }
};
var connection;

function connect() {
    connection = new WebSocket(res.url.auth);
    connection.onopen = function () {
        $("#receive-log").prepend("<li class=\"list-group-item list-group-item-success\">Соеденение успешно</li>");
    };
    connection.onerror = function (event) {
        console.log(event);
        $("#receive-log").prepend("<li class=\"list-group-item list-group-item-danger\">Ошибка соединения " + event.data + "</li>");
    };
    connection.onmessage = function (event) {
        console.log(event);
        $("#receive-log").prepend("<li class=\"list-group-item\">" + event.data + "</li>");
    };
}

$(document).ready(function () {
    connect();

    $("#btn-send").on("click", function () {
        if (connection) {
            connection.send($("#text-send").val());
        }
    });
    $("#btn-set-success").on("click", function () {
        $("#text-send").val(JSON.stringify(requestSuccess));
    });
    $("#btn-set-fail").on("click", function () {
        $("#text-send").val(JSON.stringify(requestFail));
    });
});