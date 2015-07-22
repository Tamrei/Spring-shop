function validateAddNewItemForm() {

    var itemNameForm = $("#itemName-form");
    var isItemNameValid = false;

    var typeForm = $("#type-form");
    var isTypeValid = false;

    var priceForm = $("#price-from");
    var isPriceValid = false;

    var leftOnStoreForm = $("#leftOnStore-form");
    var isLeftOnStoreValid = false;

    var imageForm = $("#image-form");
    var isImageValid = false;

    var isFormValid = false;

    var itemNameLabel = $("#itemName-label");
    var typeLabel = $("#type-label");
    var priceLable = $("#price-label");
    var leftOnStoreLabel = $("#leftOnStore-label");
    var imageLabel = $("#image-label");


    var itemNameInput = $("#itemName");
    var typeInput = $("#type");
    var priceInput = $("#price");
    var leftOnStoreInput = $("#leftOnStore");
    var imageInput = $("#image");


    validationMarkup(itemNameInput.val().length > 2, itemNameForm, itemNameLabel, "Item Name: ", "Item Name: must be at least 3 character long");

    validationMarkup(typeInput.val.length > 2, typeForm, typeLabel, "Type: ", "Type: must be at least 2 character long");

    /**
     *  itemName form
     */
    itemNameInput.on('input', function () {
        isItemNameValid = itemNameInput.val().replace(/ /g,'').length > 2;
        validationMarkup(isItemNameValid, itemNameForm, itemNameLabel, "Item Name: ", "Item Name: must be at least 3 character long");
    });

    /**
     *  type form
     */
    typeInput.on('input', function () {
        isTypeValid = typeInput.val().replace(/ /g,'').length > 2;
        validationMarkup(isTypeValid, typeForm, typeLabel, "Type: ", "Type: must be at least 3 character long");
    });

    /**
     *  price form
     */
    priceInput.on('focusout', function () {
        var input = $(this);
        var priceValue = input.val();

        if (priceValue.length === 0) {
            input.val(1);
        }
    });

    /**
     *  leftOnStore form
     */
    leftOnStoreInput.on('focusout', function () {
        var input = $(this);
        var leftOnStoreValue = input.val();

        if (leftOnStoreValue.length === 0) {
            leftOnStoreInput.val(0);
        }
    });

    isImageValid = !(imageInput.val() == "");
    validationMarkup(isImageValid, imageForm, imageLabel, "Image: ", "Image: no image");

    $('input[name=file]').change(function() {
        isImageValid = !(imageInput.val() == "");
        validationMarkup(isImageValid, imageForm, imageLabel, "Image: ", "Image: no image");
    });

    $("#item-form").submit(function (e) {
        if (priceInput.val().length === 0) {
            priceInput.val(1);
        }

        if (leftOnStoreInput.val().length === 0) {
            leftOnStoreInput.val(0);
        }

        isFormValid = isItemNameValid && isTypeValid;

        if (!isFormValid) {
            //$('#FormsAreEmpty').show();
            //$('#FormsAreEmpty').removeClass('hidden');
        }

        return isFormValid;
    });
}
