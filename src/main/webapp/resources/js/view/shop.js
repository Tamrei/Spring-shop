/**
 * Load banners in shop page (home page).
 */
function getHomePageImages() {
    $.ajax({
        type: "Get",
        url: 'carouselController.html',
        async: false,
        success: function (data) {
        },
        error: function (e) {
            alert("Home page image error!");
        }
    });
}

/**
 *
 *
 * @param itemID id of item that we want to put in our cart.
 * @param itemName we need this param to show popover that
 * notifaes customer that he/she succesfuly put item in cart.
 */
function putItemInCart(itemID, itemName) {
    var amount = $('#amount' + itemID).val();

    $.ajax({
        type: "Post",
        url: 'putItemInTheCart.html',
        data: "itemID=" + itemID + "&amount=" + amount,
        success: function (data) {
            getCartCount('cartCount'); // update cart count
            $('.modal.in').modal('hide'); // hide modal

            /* display popover with info */
            var cartPopover = $('#navCart');
            cartPopover.attr("data-original-title", "<h5> success </h5>").attr("data-content", "You successful added " + amount + " " + itemName + "'s.");

            cartPopover.popover("show");

            $('.popover').css("position", "fixed").css("top", "50px");

            setTimeout(function () {
                cartPopover.popover('hide');
            }, 3500);
        },
        error: function (e) {
            alert("error! make sure your amount is integer.");
        }
    });
}

/**
 * This method controls panel that allow to sort items by their types.
 */
function sortPanel() {
    var rows = $('div.type');

    $("#searchBar").children().click(function () {
        var col = $(this).text();
        var ww = rows.filter("#" + col).show();
        rows.not(ww).hide();

        $("#searchBar").children().each(function () {
            if ($(this).text() != col) {
                $(this).removeClass("list-group-item active").addClass("list-group-item");
            } else {
                $(this).removeClass("list-group-item").addClass("list-group-item active");
            }
        });
    });

    $('#All').click(function () {
        rows.show();
    });
}