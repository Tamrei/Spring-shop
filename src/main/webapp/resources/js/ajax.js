
function showCartCount(id) {
    $.ajax({
        url: 'cartCount.html',
        success: function (data) {
            $('#' + id).text(data);
        }
    });
}






