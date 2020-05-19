/*
 * WiFiAnalyzer
 * Copyright (C) 2015 - 2020 VREM Software Development <VREMSoftwareDevelopment@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.vrem.wifianalyzer.navigation.availability

import com.vrem.util.EMPTY
import com.vrem.util.compatColor
import com.vrem.util.fromHtml
import com.vrem.util.textToHtml
import com.vrem.wifianalyzer.MainActivity
import com.vrem.wifianalyzer.MainContext
import com.vrem.wifianalyzer.R
import com.vrem.wifianalyzer.wifi.band.WiFiBand

internal val navigationOptionWiFiSwitchOff: NavigationOption = {
    val actionBar = it.supportActionBar
    if (actionBar != null) {
        actionBar.subtitle = String.EMPTY
    }
    menu(it, false)
}

internal val navigationOptionWiFiSwitchOn: NavigationOption = {
    actionBarOn(it)
    menu(it, true)
}

private fun menu(mainActivity: MainActivity, visible: Boolean) {
    val optionMenu = mainActivity.optionMenu
    if (optionMenu != null) {
        val menu = optionMenu.menu
        if (menu != null) {
            menu.findItem(R.id.action_wifi_band).isVisible = visible
        }
    }
}

private fun actionBarOn(mainActivity: MainActivity) {
    val actionBar = mainActivity.supportActionBar
    if (actionBar != null) {
        val colorSelected = mainActivity.compatColor(R.color.selected)
        val colorNotSelected = mainActivity.compatColor(R.color.regular)
        val resources = mainActivity.resources
        val wiFiBand2 = resources.getString(WiFiBand.GHZ2.textResource)
        val wiFiBand5 = resources.getString(WiFiBand.GHZ5.textResource)
        val wiFiBand = MainContext.INSTANCE.settings.wiFiBand()
        val subtitle = makeSubtitle(WiFiBand.GHZ2 == wiFiBand, wiFiBand2, wiFiBand5, colorSelected, colorNotSelected)
        actionBar.subtitle = fromHtml(subtitle)
    }
}

internal fun makeSubtitle(wiFiBand2Selected: Boolean, wiFiBand2: String?, wiFiBand5: String?, colorSelected: Int, colorNotSelected: Int): String {
    val stringBuilder = StringBuilder()
    if (wiFiBand2Selected) {
        stringBuilder.append(textToHtml(wiFiBand2!!, colorSelected, false))
    } else {
        stringBuilder.append(textToHtml(wiFiBand2!!, colorNotSelected, true))
    }
    stringBuilder.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;")
    if (wiFiBand2Selected) {
        stringBuilder.append(textToHtml(wiFiBand5!!, colorNotSelected, true))
    } else {
        stringBuilder.append(textToHtml(wiFiBand5!!, colorSelected, false))
    }
    return stringBuilder.toString()
}