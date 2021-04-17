package document.controllers

import document.PhysicalPerson
import document.persistence.dao.PhysicalPersonDAO
import java.lang.Exception

/**
 * Classe controle pare user
 */
class PhysicalPersonController:CRUDController<PhysicalPerson, String> {
    override fun create(new: PhysicalPerson):PhysicalPerson{
        try {
            new.id = new.createId()
            return PhysicalPersonDAO.insert(new).toType()!!
        } catch (e:Exception){
            throw e
        }
    }

    override fun get(id:String):PhysicalPerson? {
        try {
            return PhysicalPersonDAO.select(id)?.toType()
        } catch (e:Exception){
            throw e
        }
    }

    override fun update(id: String, new: PhysicalPerson):PhysicalPerson {
        try {
            new.id = id
            PhysicalPersonDAO.update(new)
            return PhysicalPersonDAO.select(id)!!.toType()!!
        } catch (e:Exception) {
            throw e
        }
    }

    override fun delete(id: String) {
        try {
            PhysicalPersonDAO.remove(id)
        } catch (e:Exception) {
            throw e
        }
    }
}