package document.controllers

import document.Official
import document.persistence.dao.OfficialDAO
import java.lang.Exception

class OfficialController:CRUDController<Official,String> {
    override fun create(new: Official): Official {
        try {
            new.id = new.createId()
            return OfficialDAO.insert(new).toType()!!
        } catch (e: Exception){
            throw e
        }
    }

    override fun get(id: String): Official? {
        try {
            return OfficialDAO.select(id)?.toType()
        } catch (e:Exception){
            throw e
        }
    }

    override fun update(id: String, new: Official): Official {
        try {
            new.id = id
            OfficialDAO.update(new)
            return OfficialDAO.select(id)!!.toType()!!
        } catch (e:Exception) {
            throw e
        }
    }

    override fun delete(id: String) {
        try {
            OfficialDAO.remove(id)
        } catch (e:Exception) {
            throw e
        }
    }
}