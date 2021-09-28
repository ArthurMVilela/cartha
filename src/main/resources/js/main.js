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
    $(".mask-dn").mask(
        "00-00000000-0",
        {
            placeholder:"00-00000000-0"
        }
    )

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
        $(".mask-dn").unmask()
    })
}

function setUpAffiliationForm() {
    let firstAffiliation = $(".affiliation__item")
    let containerEl = $("#affiliation__container")
    let addAffiliationBtn = $("#btn-add-affiliation")

    appendAffiliationFormEvents(firstAffiliation)
    updateAffiliationIndexes()

    addAffiliationBtn.click(function () {
        containerEl.append(createAffiliationForm(firstAffiliation.clone()))
        updateAffiliationIndexes()
    })
}

function setUpGrandparentForm() {
    let firstGrandparent = $(".grandparent__item")
    let containerEl = $("#grandparent__container")
    let addAffiliationBtn = $("#btn-add-grandparent")

    appendGrandparentFormEvents(firstGrandparent)
    updateGrandparentIndexes()

    addAffiliationBtn.click(function () {
        containerEl.append(createGrandparentForm(firstGrandparent.clone()))
        updateGrandparentIndexes()
    })
}

function createAffiliationForm(template) {
    let item = template.clone()

    appendAffiliationFormEvents(item)

    return item
}

function createGrandparentForm(template) {
    let item = template.clone()

    appendGrandparentFormEvents(item)

    return item
}

function appendAffiliationFormEvents(form) {
    form.find(".affiliation__item__remove-btn").click(function () {
        form.remove()
        updateAffiliationIndexes()
    })
}

function appendGrandparentFormEvents(form) {
    form.find(".grandparent__item__remove-btn").click(function () {
        form.remove()
        updateGrandparentIndexes()
    })
}

function updateAffiliationIndexes() {
    let containerEl = $("#affiliation__container")
    let items = containerEl.find(".affiliation__item");

    items.each(function (index) {
        $(this).find(".affiliation__item__name").attr("name", "affiliation[" + index + "]name")
        $(this).find(".affiliation__item__cpf").attr("name", "affiliation[" + index + "]cpf")
        $(this).find(".affiliation__item__municipality-name").attr("name", "affiliation[" + index + "]municipality-name")
        $(this).find(".affiliation__item__municipality-uf").attr("name", "affiliation[" + index + "]municipality-uf")
    })

    $("input[name='affiliation-length']").attr("value", items.length)
}

function updateGrandparentIndexes() {
    let containerEl = $("#grandparent__container")
    let items = containerEl.find(".grandparent__item");

    items.each(function (index) {
        $(this).find(".grandparent__item__name").attr("name", "grandparent[" + index + "]name")
        $(this).find(".grandparent__item__cpf").attr("name", "grandparent[" + index + "]cpf")
        $(this).find(".grandparent__item__type").attr("name", "grandparent[" + index + "]type")
        $(this).find(".grandparent__item__municipality-name").attr("name", "grandparent[" + index + "]municipality-name")
        $(this).find(".grandparent__item__municipality-uf").attr("name", "grandparent[" + index + "]municipality-uf")
    })

    $("input[name='grandparent-length']").attr("value", items.length)
}

$(document).ready(function () {
    setupFormMasks();
    setUpAffiliationForm();
    setUpGrandparentForm();
})