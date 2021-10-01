function validateLoginForm(evt) {
    let failedValidation = false

    let email = $("#email")
    let emailError = $("#email-invalid-msg")
    let cpf = $("#cpf")
    let cpfError = $("#cpf-invalid-msg")

    if (email.val().length === 0 && cpf.val().length === 0){
        evt.preventDefault()
        evt.stopPropagation()
        email.addClass("is-invalid")
        emailError.text("Você preencher ou seu email ou seu CPF.")
        cpf.addClass("is-invalid")
        cpfError.text("Você preencher ou seu email ou seu CPF.")

        failedValidation = true
    }

    console.log(failedValidation)

    console.log(cpf.val().length)
    if (email.val().length === 0 && cpf.val().length !== 14) { //14 incluindo os 2 pontos e o traço
        cpf.addClass("is-invalid")
        cpfError.text("CPF inválido.")
        failedValidation = true
    }

    console.log(failedValidation)

    if(failedValidation) {
        evt.stopPropagation()
        evt.preventDefault()
    } else {
        cpf.removeClass("is-invalid")
        email.removeClass("is-invalid")
        clearMasks()
    }
}

$(document).ready(function () {
    $("#login-form").submit(function (evt) {
        validateLoginForm(evt)
    })
})