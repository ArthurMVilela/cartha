package document

/**
 * Classe controller para o pacode de documentos
 */
class DocumentController {
    fun createPerson(person: Person):Person {
        //TODO:Persistẽncia e validação
        return Person(createPersonId(), person.name, person.cpf, person.sex)
    }

    fun getPerson(id: String):Person {
        //TODO:Persistência e validação
        return Person(id, "test", "99988877766", Sex.Male)
    }

    private fun createPersonId():String {
        //TODO:implementar de verdade um método para criar id
        return "111111"
    }
}