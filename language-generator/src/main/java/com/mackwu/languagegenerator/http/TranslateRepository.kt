package com.mackwu.languagegenerator.http

/**
 * ===================================================
 * Created by MackWu on 2019/12/24 14:43
 * <a href="mailto:wumengjiao828@163.com">Contact me</a>
 * <a href="https://github.com/mackwu828">Follow me</a>
 * ===================================================
 */

class TranslateRepository {

    /**
     * @param translateCode 目标文本语言
     * @param sourceValue 源文本
     */
    fun translate(translateCode: String, sourceValue: String, onTranslate: (translateValue: String) -> Unit) {
        val url = "http://translate.google.cn/translate_a/single"
        val param = hashMapOf<String, String>()

        // client gtx
        param["client"] = "gtx"
        // 决定了最终返回的数据。t: 源text的翻译、at: 会额外返回一些近义词
        param["dt"] = "t"
        // 源文本语言。auto: 自动检测、 en: 英文
        param["sl"] = "auto"
        // tl 目标文本语言。zh-CN: 中文
        param["tl"] = translateCode
        // q 源文本。
        param["q"] = sourceValue

        //
        OkHttpManager.get(url, param) { result ->
            val results = result.split("\"")
            println(results[1])
            onTranslate.invoke(results[1])
        }
    }
}
