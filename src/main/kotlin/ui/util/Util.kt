package ui.util

import authentication.Role
import ui.values.Menus

class Util {
    companion object {
        fun addMenuToLayoutMap(map: MutableMap<String, Any?>, role: Role?) {
            map["role"] = role
            when(role) {
                null -> map["menu"] = Menus.anonimous
                Role.Manager -> map["menu"] = Menus.manager
                Role.Official -> map["menu"] = Menus.official
                Role.SysAdmin -> map["menu"] = Menus.sysAdmin
                Role.Client -> map["menu"] = Menus.client
            }
        }
    }
}