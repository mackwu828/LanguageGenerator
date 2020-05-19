package com.mackwu.languagegenerator

import com.mackwu.languagegenerator.http.OkHttpManager
import com.mackwu.languagegenerator.http.TranslateRepository
import java.io.File
import java.io.FileOutputStream

/**
 * ===================================================
 * Created by MackWu on 2020/5/6 17:22
 * <a href="mailto:wumengjiao828@163.com">Contact me</a>
 * <a href="https://github.com/mackwu828">Follow me</a>
 * ===================================================
 */
object LanguageGenerator {

    //    private val moduleName: String = "app"
    private const val moduleName: String = "language-generator"
    private val modulePath: String = File("").canonicalPath + "/$moduleName/src/main/res"
    private val translateRepository = TranslateRepository()

    /**
     *
     */
    fun makeAllLanguages(translateSources: List<TranslateSource>, skipTranslateLanguages: List<Languages>) {
        val languages = Languages.values()
        for (language in languages) {
            // skip
            if (language in skipTranslateLanguages) continue
            //
            language.run {
                // new dir
                val dir = File("$modulePath/values-$code")
                println(dir)
                if (!dir.exists()) {
                    dir.mkdirs()
                }

                // code
                val codes = code.split("-")
                val realCode = if (codes.size == 1) code else codes[0] + "-" + codes[1].substring(1, codes[1].length)

                // write
                try {
                    val fos = FileOutputStream(dir.absolutePath + "/strings.xml")
                    val sb = StringBuilder()
                    //
                    sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n")
                            .append("<resources>\r\n")
                            .apply {
                                for (translateSource in translateSources) {
                                    translateSource.run {
                                        translateRepository.translate(realCode, sourceValue) { translateValue ->
                                            append("   <string name=\"$sourceName\">$translateValue</string>\r\n")
                                        }
                                    }
                                }
                            }
                            .append("</resources>\r\n")
                    fos.write(sb.toString().toByteArray())
                    fos.flush()
                    fos.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}