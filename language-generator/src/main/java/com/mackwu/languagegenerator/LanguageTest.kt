package com.mackwu.languagegenerator

/**
 * ===================================================
 * Created by MackWu on 2020/5/7 15:39
 * <a href="mailto:wumengjiao828@163.com">Contact me</a>
 * <a href="https://github.com/mackwu828">Follow me</a>
 * ===================================================
 */
fun main() {
    val translateSources = listOf(
            TranslateSource("search_title", "Search"),
            TranslateSource("search_no_result", "No search result"),
            TranslateSource("search_result_for", "Results for"),
            TranslateSource("search_hint_tip", "Country、City or Township"),
            TranslateSource("search_no_net_tip", "No Network, Please check the network and try again later")
    )
    val skipTranslateLanguages = listOf(
            Languages.NONE
    )
    LanguageGenerator.makeAllLanguages(translateSources, skipTranslateLanguages)
}