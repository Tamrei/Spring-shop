function setStatus(isEnabled, userID) {
    var status = $("#status" + userID);

    if (isEnabled) {
        status.css("color", "darkgreen").text("enabled");
    } else {
        status.css("color", "darkred").text("disabled");
    }

}

function deleteUser(userID) {
    $.ajax({
        type: "Post",
        url: "deleteUser.html",
        data: "userID=" + userID,
        success: function (data) {
            $("#table" + userID).hide();
        },
        error: function (e) {
            alert("error!");
        }
    });
}

function enableDisableUser(userID) {
    $.ajax({
        type: "Post",
        url: "enableDisableUser.html",
        data: "userID=" + userID,
        success: function (data) {
            if (data == "true") {
                setStatus(true, userID);
            } else {
                setStatus(false, userID);
            }
        },
        error: function (e) {
            alert("error!");
        }
    });
}