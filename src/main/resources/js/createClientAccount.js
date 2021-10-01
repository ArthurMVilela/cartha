function validateCreateClientAccountForm(evt) {
    let password = $("#password")
    let passwordRepeat = $("#password-repeat")

    if (password.val() !== passwordRepeat.val()) {
        password.addClass("is-invalid")
        passwordRepeat.addClass("is-invalid")
        evt.preventDefault()
        evt.stopPropagation()
    }

    clearMasks()
}

$(document).ready(function () {
    $("#create-client-account-form").submit(function (evt) {
        validateCreateClientAccountForm(evt)
    })
})