/**
 * if order form contains errors
 * this fun
 */
function showModalWithErrors() {
    var url = window.location.href; // get current url
    if (url.endsWith('#error')) {
        $('#order').modal('show');
        $('#invalidFormAlert').show();
    }
    else $('#invalidFormAlert').hide();
}