function validatePurchaseForm() {

    var cityForm = $('#city-form');
    var isCityValid = false;

    var streetForm = $('#street-form');
    var isStreetValid = false;

    var isFormValid = false;

    var cityLabel = $("#city-label");
    var streetLabel = $("#street-label");

    var usernameInput = $("#username");
    var passwordInput = $('#password');

    var cityInput = $("#city");
    var streetInput = $("#street");

    //validationMarkup(isCityValid, cityForm, cityLabel, "City: ", "City: must be at least 4 character long");
    validationMarkup(isStreetValid, streetForm, streetLabel, "Street: ", "Street: must be at least 4 character long");

    /**
     *  street form
     */
    //cityInput.on('input', function () {
    //$(this).val($(this).val().replace(/ /g,''));

    //isCityValid = (cityInput.val().replace(/ /g,'').length > 3);
    //validationMarkup(isCityValid, cityForm, cityLabel, "City: ", "City: must be at least 4 character long");
    //});
    /**
     *  street form
     */
    streetInput.on('input', function () {
        //$(this).val($(this).val().replace(/ /g,''));
        isStreetValid = (streetInput.val().replace(/ /g, '').length > 3);
        validationMarkup(isStreetValid, streetForm, streetLabel, "Street: ", "Street: must be at least 4 character long");

    });

    $("#purchase-form").submit(function (e) {
        isFormValid = /*isCityValid &&*/isStreetValid;

        if (!isFormValid) {
            $('#FormsAreEmpty').removeClass('hidden');
        }

        return isFormValid;
    });
}

function validationMarkup (condition, form, label, validText, invalidText) {
    if (condition) {
        form.removeClass('has-error').addClass('has-success');
        label.text(validText);
    } else {
        form.removeClass('has-success').addClass('has-error');
        label.text(invalidText);
    }
}