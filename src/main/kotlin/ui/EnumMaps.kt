package ui

import document.CivilStatus
import document.Color
import document.Sex
import document.UF
import java.time.Month

object EnumMaps {
    val sex = mutableMapOf<String,String>()
    val month = mutableMapOf<String,String>(
        Month.JANUARY.name to "janeiro",
        Month.FEBRUARY.name to "fevereiro",
        Month.MARCH.name to "março",
        Month.APRIL.name to "abril",
        Month.MAY.name to "maio",
        Month.JUNE.name to "junho",
        Month.JULY.name to "julho",
        Month.AUGUST.name to "agosto",
        Month.SEPTEMBER.name to "setembro",
        Month.OCTOBER.name to "outubro",
        Month.NOVEMBER.name to "novembro",
        Month.DECEMBER.name to "dezembro",
    )
    val civilStatus = mutableMapOf<String,String>()
    val color = mutableMapOf<String,String>()
    val uf = mutableMapOf<String,String>()
    init {
        Sex.values().forEach {
            sex[it.name] = it.value
        }
        CivilStatus.values().forEach {
            civilStatus[it.name] = it.value
        }
        Color.values().forEach {
            color[it.name] = it.value
        }
        UF.values().forEach {
            uf[it.name] = it.value
        }
    }

}