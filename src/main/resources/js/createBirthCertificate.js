function validateCreateBirthCertificateForm(evt) {
    clearMasks()
}

$(document).ready(function () {
    $("#create-birth-certificate-form").submit(function (evt) {
        validateCreateBirthCertificateForm(evt)
    })
})