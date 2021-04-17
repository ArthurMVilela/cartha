package document.controllers

import document.PhysicalPerson
import document.persistence.dao.PhysicalPersonDAO
import java.lang.Exception

/**
 * Classe controle pare user
 */
class PhysicalPersonController {
    fun create(person: PhysicalPerson):PhysicalPerson{
        try {
            person.id = person.createId()
            return PhysicalPersonDAO.insert(person).toType()!!
        } catch (e:Exception){
            throw e
        }
    }

    fun get(id:String):PhysicalPerson? {
        try {
            return PhysicalPersonDAO.select(id)?.toType()
        } catch (e:Exception){
            throw e
        }
    }

    fun update(id: String, new: PhysicalPerson):PhysicalPerson {
        try {
            new.id = id
            PhysicalPersonDAO.update(new)
            return PhysicalPersonDAO.select(id)!!.toType()!!
        } catch (e:Exception) {
            throw e
        }
    }

    fun delete(id: String) {
        try {
            PhysicalPersonDAO.remove(id)
        } catch (e:Exception) {
            throw e
        }
    }
}