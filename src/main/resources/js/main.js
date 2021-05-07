$(".mask-cpf").mask("000.000.000-00")
$(".mask-cnpj").mask("00.000.000.0000-00")
$(".mask-cns").mask("00.000-0")
$(".input-year").maxLength = (new Date()).getFullYear()

$("form").submit(function () {
    $(".mask-cpf").unmask()
    $(".mask-cnpj").unmask()
    $(".mask-cns").unmask()
})