/**
 *
 *
 * @param condition
 * @param form
 * @param label
 * @param validText
 * @param invalidText
 */
function validationMarkup (condition, form, label, validText, invalidText) {
    if (condition) {
        form.removeClass('has-error').addClass('has-success');
        label.text(validText);
    } else {
        form.removeClass('has-success').addClass('has-error');
        label.text(invalidText);
    }
}