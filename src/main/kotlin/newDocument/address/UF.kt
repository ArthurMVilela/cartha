package newDocument.address

/**
 * Enumera todas as unidades federativas do Brasil.
 */
enum class UF(val value:String) {
    AC("Acre"),
    AL("Alagoas"),
    AP("Amapa"),
    AM("Amazonas"),
    BA("Bahia"),
    CE("Ceará"),
    DF("Distrito Federal"),
    ES("Espírito Santo"),
    GO("Goiás"),
    MA("Maranhão"),
    MT("Mato Grosso"),
    MS("Mato Grosso do Sul"),
    MG("Minas Gerais"),
    PA("Pará"),
    PB("Paraiba"),
    PR("Paraná"),
    PI("Piauí"),
    RJ("Rio de Janeiro"),
    RS("Rio Grande do Sul"),
    RO("Rondônia"),
    RR("Roraima"),
    SC("Santa Catarina"),
    SP("São Paulo"),
    SE("Sergipe"),
    TO("Tocantins");

    override fun toString(): String {
        return value
    }
}