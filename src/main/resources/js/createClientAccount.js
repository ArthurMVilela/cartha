function validateCreateClientAccountForm(evt) {
    let failedValidation = false

    let cpf = $("#cpf")
    let cpfError = $("#cpf-error-message")
    let password = $("#password")
    let passwordError = $("#password-invalid-msg")
    let passwordRepeat = $("#password-repeat")
    let passwordRepeatError = $("#repeat-password-invalid-msg")

    if (cpf.val().length !== 14) {
        cpf.addClass("is-invalid")
        cpfError.text("CPF inválido.")
        failedValidation = true
    }

    if (password.val() !== passwordRepeat.val()) {
        password.addClass("is-invalid")
        passwordRepeat.addClass("is-invalid")
        passwordError.text("Você deve digitar usa senha corretamente duas vezes.")
        passwordRepeatError.text("Você deve digitar usa senha corretamente duas vezes.")
        failedValidation = true
    }

    if(failedValidation) {
        evt.stopPropagation()
        evt.preventDefault()
    } else {
        cpf.removeClass("is-invalid")
        password.removeClass("is-invalid")
        passwordRepeat.removeClass("is-invalid")
        clearMasks()
    }
}

$(document).ready(function () {
    $("#create-client-account-form").submit(function (evt) {
        validateCreateClientAccountForm(evt)
    })
})