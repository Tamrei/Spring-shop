/**
 * Created by Александр on 20.05.2015.
 */
function showCartCount(id) {
    $.ajax({
        url: 'cartCount.html',
        success: function (data) {
            $('#' + id).text(data);
        }
    });
}






