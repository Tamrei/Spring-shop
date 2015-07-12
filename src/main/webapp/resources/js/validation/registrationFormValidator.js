function validateRegistrationForm() {

    var usernameForm = $('#username-form');
    var isNameValid = false;

    var passwordForm = $('#password-form');
    var isPasswordValid = false;

    var confirmPasswordForm = $("#confirmPassword-form");
    var isPasswordsMatch = false;

    var isFormValid = false;

    var passwordLabel = $('#password-label');
    var usernameLabel = $('#username-label');

    var confirmPasswordLable = $("#confirmPassword-label");

    var usernameInput = $("#username");
    var passwordInput = $('#password');

    var confirmPasswordInput = $("#confirmPassword");

    setItemNameLabel(isNameValid, usernameForm, usernameLabel, "Username: ", "Username: must be at least 2 character long");
    setItemNameLabel(isPasswordValid, passwordForm, passwordLabel, "Password: ", "Password: must be at least 4 character long");

    setItemNameLabel(isPasswordsMatch, confirmPasswordForm, confirmPasswordLable, "Confirm Password: ", "Confirm Password: passwords don't match");

    /**
     *  username form
     */
    usernameInput.on('input', function () {
        //usernameInput.val(usernameInput.val().replace(/ /g,''));
        $(this).val($(this).val().replace(/ /g,''));
        isNameValid = (usernameInput.val().replace(/ /g,'').length > 2);
        setItemNameLabel(isNameValid, usernameForm, usernameLabel, "Username: ", "Username: must be at least 2 character long");
    });

    /**
     *  password form
     */
    passwordInput.on('input', function () {
        $(this).val($(this).val().replace(/ /g,''));
        isPasswordValid = (passwordInput.val().replace(/ /g,'').length > 3);
        setItemNameLabel(isPasswordValid, passwordForm, passwordLabel, "Password: ", "Password: must be at least 4 character long");

        setItemNameLabel(passwordInput.val() === confirmPasswordInput.val(), confirmPasswordForm, confirmPasswordLable, "Confirm Password: ", "Confirm Password: passwords don't match")
    });

    /**
     *  confirm password form
     */
    confirmPasswordInput.on('input', function () {
        $(this).val($(this).val().replace(/ /g,''));
        isPasswordsMatch = passwordInput.val() === confirmPasswordInput.val();

        setItemNameLabel(isPasswordsMatch, confirmPasswordForm, confirmPasswordLable, "Confirm Password: ", "Confirm Password: passwords don't match");
    });

    //$('#FormsAreEmpty').hide();

    $("#registration-form").submit(function (e) {
        isFormValid = isNameValid && isPasswordValid && isPasswordsMatch;

        if (!isFormValid) {
            //$('#FormsAreEmpty').show();
            $('#FormsAreEmpty').removeClass('hidden');
        }

        return isFormValid;
    });
}

/**
 *
 *
 * @param condition
 * @param form
 * @param label
 * @param validText
 * @param invalidText
 */
/*
function setItemNameLabel (condition, form, label, validText, invalidText) {
    if (condition) {
        form.removeClass('has-error').addClass('has-success');
        label.text(validText);
    } else {
        form.removeClass('has-success').addClass('has-error');
        label.text(invalidText);
    }
}
    */