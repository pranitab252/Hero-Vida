package com.example.vida.common

import com.hbb20.CCPTalkBackTextProvider
import com.hbb20.CCPCountry

internal class CCPCustomTalkBackProvider : CCPTalkBackTextProvider {
    override fun getTalkBackTextForCountry(country: CCPCountry): String {
        return "Country code is +" + country.phoneCode
    }
}