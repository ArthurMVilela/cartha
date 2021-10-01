function validateCreateOfficialForm(evt) {
    clearMasks()
}

$(document).ready(function () {
    $("#create-official-form").submit(function (evt) {
        validateCreateOfficialForm(evt)
    })
})