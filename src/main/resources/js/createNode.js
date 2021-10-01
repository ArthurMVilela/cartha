function validateCreateNodeForm(evt) {
    clearMasks()
}

$(document).ready(function () {
    $("#create-node-form").submit(function (evt) {
        validateCreateNodeForm(evt)
    })
})