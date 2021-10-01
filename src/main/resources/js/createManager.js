function validateCreateManagerForm(evt) {
    clearMasks()
}

$(document).ready(function () {
    $("#create-manage-form").submit(function (evt) {
        validateCreateManagerForm(evt)
    })
})