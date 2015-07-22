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

    validationMarkup(isNameValid, usernameForm, usernameLabel, "Username: ", "Username: must be at least 2 character long");
    validationMarkup(isPasswordValid, passwordForm, passwordLabel, "Password: ", "Password: must be at least 4 character long");

    validationMarkup(isPasswordsMatch, confirmPasswordForm, confirmPasswordLable, "Confirm Password: ", "Confirm Password: passwords don't match");

    /**
     *  username form
     */
    usernameInput.on('input', function () {
        //usernameInput.val(usernameInput.val().replace(/ /g,''));
        $(this).val($(this).val().replace(/ /g,''));
        isNameValid = (usernameInput.val().replace(/ /g,'').length > 2);
        validationMarkup(isNameValid, usernameForm, usernameLabel, "Username: ", "Username: must be at least 2 character long");
    });

    /**
     *  password form
     */
    passwordInput.on('input', function () {
        $(this).val($(this).val().replace(/ /g,''));
        isPasswordValid = (passwordInput.val().replace(/ /g,'').length > 3);
        validationMarkup(isPasswordValid, passwordForm, passwordLabel, "Password: ", "Password: must be at least 4 character long");

        validationMarkup(passwordInput.val() === confirmPasswordInput.val(), confirmPasswordForm, confirmPasswordLable, "Confirm Password: ", "Confirm Password: passwords don't match")
    });

    /**
     *  confirm password form
     */
    confirmPasswordInput.on('input', function () {
        $(this).val($(this).val().replace(/ /g,''));
        isPasswordsMatch = passwordInput.val() === confirmPasswordInput.val();

        validationMarkup(isPasswordsMatch, confirmPasswordForm, confirmPasswordLable, "Confirm Password: ", "Confirm Password: passwords don't match");
    });

    $("#registration-form").submit(function (e) {
        isFormValid = isNameValid && isPasswordValid && isPasswordsMatch;

        if (!isFormValid) {
            $('#FormsAreEmpty').removeClass('hidden');
        }

        return isFormValid;
    });
}
