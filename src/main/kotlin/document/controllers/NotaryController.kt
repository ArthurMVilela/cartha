package document.controllers

import document.Notary
import document.persistence.dao.NotaryDAO
import java.lang.Exception

class NotaryController:CRUDController<Notary, String> {
    override fun create(new: Notary): Notary {
        try {
            new.id = new.createId()
            return NotaryDAO.insert(new).toType()!!
        } catch (e: Exception){
            throw e
        }
    }

    override fun get(id: String): Notary? {
        try {
            return NotaryDAO.select(id)?.toType()
        } catch (e:Exception){
            throw e
        }
    }

    override fun update(id: String, new: Notary): Notary {
        try {
            new.id = id
            NotaryDAO.update(new)
            return NotaryDAO.select(id)!!.toType()!!
        } catch (e:Exception) {
            throw e
        }
    }

    override fun delete(id: String) {
        try {
            NotaryDAO.remove(id)
        } catch (e:Exception) {
            throw e
        }
    }
}