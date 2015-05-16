/**
$("#s").keyup(function () {
    var regex = new RegExp($("#search").val(), "i");
    var rows = $('tr:gt(0)');
    rows.each(function (index) {
        var title = $(this).children("#itemName").html();
        if (title.search(regex) != -1) {
            $(this).show();
        } else {
            $(this).hide();
        }
    });
});
**/

function searchValue(value, id) {
    var regex = new RegExp($("#" + id).val(), "i");
    //var rows = $('tr:gt(0)'); //old version
    var rows = $('.type');
    rows.each(function (index) {
        var title = $(this).children(value).html();
        if (title.search(regex) != -1) {
            $(this).show();
        } else {
            $(this).hide();
        }
    });
}