package com.mackwu.languagegenerator

/**
 * ===================================================
 * Created by MackWu on 2020/5/6 17:48
 * <a href="mailto:wumengjiao828@163.com">Contact me</a>
 * <a href="https://github.com/mackwu828">Follow me</a>
 * ===================================================
 */
data class TranslateSource (
        val sourceName: String,
        val sourceValue: String,
        val manual: Boolean = false
)