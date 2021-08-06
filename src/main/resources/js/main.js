function setupFormMasks() {
    $(".mask-cpf").mask("000.000.000-00")
    $(".mask-cnpj").mask("00.000.000.0000-00")
    $(".mask-cns").mask("00.000-0")
    $(".mask-date-time").mask("00/00/0000 00:00:00", {placeholder:"__/__/____ 00:00:00"})
    $(".mask-uuid").mask(
        "HHHHHHHH-HHHH-HHHH-HHHH-HHHHHHHHHHHH",
        {
            placeholder:"00000000-0000-0000-0000-000000000000",
            translation: {
                "H": {pattern:/[0-9a-f]/}
            }
        }
    )
    $(".input-year").maxLength = (new Date()).getFullYear()

    $(".mask-cpf-cnpj").keydown(function () {
        $(this).unmask();
        if ($(this).val().length < 11) {
            $(this).attr("name", "cpf")
            $(this).mask("000.000.000-00")
        } else if ($(this).val().length > 11) {
            $(this).attr("name", "cnpj")
            $(this).mask("00.000.000/0000-00")
        }

        let elem = $(this);
        setTimeout(function () {
            elem.selectionStart = elem.selectionEnd = 10000;
        }, 0);

        let currentValue = $(this).val();
        $(this).val('');
        $(this).val(currentValue);
    })
    $("form").submit(function () {
        $(".mask-cpf").unmask()
        $(".mask-cnpj").unmask()
        $(".mask-cns").unmask()
        $(".mask-cpf-cnpj").unmask()
    })
}

$(document).ready(function () {
    setupFormMasks();
})