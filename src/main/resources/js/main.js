function setupFormMasks() {
    $(".mask-cpf").mask("000.000.000-00")
    $(".mask-cnpj").mask("00.000.000.0000-00")
    $(".mask-cns").mask("00.000-0")
    $(".input-year").maxLength = (new Date()).getFullYear()

    $(".mask-cpf-cnpj").keydown(function () {
        try {
            this.unmask();
        } catch (e) {
        }

        if (this.val().length < 11) {
            this.mask("000.000.000-00")
        } else {
            this.mask("00.000.000.0000-00")
        }

        let elem = this;
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