
function getCartCount(id) {
    $.ajax({
        url: 'cartCount.html',
        success: function (data) {
            $('#' + id).text(data);
        }
    });
}






