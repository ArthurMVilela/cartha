package ui.util

import authentication.Role
import ui.values.EnumMaps
import ui.values.Menus
import java.time.LocalDate

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

        fun dateFullString(date: LocalDate): String {
            var day = dayString(date.dayOfMonth)
            val month = EnumMaps.month[date.month.name]
            val year = date.year
            return "$day de $month de $year"
        }

        private fun dayString(day: Int):String {
            return when (day) {
                1 -> "Primeiro"
                2 -> "Dois"
                3 -> "Três"
                4 -> "Quatro"
                5 -> "Cinco"
                6 -> "Seis"
                7 -> "Sete"
                8 -> "Oito"
                9 -> "Nove"
                10 -> "Dez"
                11 -> "Onze"
                12 -> "Doze"
                13 -> "Treze"
                14 -> "Quatorze"
                15 -> "Quinze"
                16 -> "Dezeseis"
                17 -> "Dezete"
                18 -> "Dezoito"
                19 -> "Dezenove"
                20 -> "Vinte"
                21 -> "Vinte e um"
                22 -> "Vinte e dois"
                23 -> "Vinte e três"
                24 -> "Vinte e quatro"
                25 -> "Vinte e cinco"
                26 -> "Vinte e seis"
                27 -> "Vinte e sete"
                28 -> "Vinte e oito"
                29 -> "Vinte e nove"
                30 -> "Trinta"
                else -> ""
            }
        }
    }
}