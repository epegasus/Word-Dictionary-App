package dev.pegasus.worddictionary.utilities

import androidx.core.text.HtmlCompat
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener

/**
 * Created by: Sohaib Ahmed
 * Date: 5/9/2025
 *
 * Links:
 * - LinkedIn: https://linkedin.com/in/epegasus
 * - GitHub: https://github.com/epegasus
 */

class WebViewUtils {

    /**
     *  Steps
     *   @see parseJsonDictionary: Get the parse json from response
     *   @see getWebViewData: Get WebView data for UI
     *   @see extractCleanText Get Clean Text for share, speak, translate, etc
     */

    fun getParseJsonDictionary(jsonResponse: String): String {
        return parseJsonDictionary(responseJson = JSONTokener(jsonResponse).nextValue()).toString()
    }

    fun getWebViewData(enableDarkMode: Boolean, parseJsonDictionary: String): String {
        val textColor = when (enableDarkMode) {
            true -> "white"  // Dark Mode
            false -> "black"  // Light Mode
        }
        val backgroundColor = when (enableDarkMode) {
            true -> "#1A2230"  // Dark Mode
            false -> "#ECF1F9"  // Light Mode
        }

        return """
        <!DOCTYPE html>
        <html>
        <head>
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <style>
                body {
                    font-family: sans-serif;
                    color: $textColor;
                    background-color: ${backgroundColor};
                    padding: 0px;
                    line-height: 1.6;
                }
                center b {
                    color: $textColor;
                }
                ul {
                    padding-left: 20px;
                }
                li {
                    margin-bottom: 4px;
                }
                i {
                    opacity: 0.75;
                }
            </style>
        </head>
        <body>
            $parseJsonDictionary
        </body>
        </html>
    """.trimIndent()
    }

    fun extractCleanText(parsedHtmlBuilder: String): String {
        return HtmlCompat.fromHtml(parsedHtmlBuilder, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
            .replace(Regex("[\\t\\x0B\\f\\r]+"), "") // Remove tabs/form feeds/etc.
            .replace(Regex(" +"), " ") // Normalize only multiple spaces
            .trim()
    }

    private fun parseJsonDictionary(responseJson: Any?): StringBuilder {
        val dictionaryContent = StringBuilder()
        try {
            when (responseJson) {
                is JSONObject -> {
                    dictionaryContent.append(getDictionaryDataFromJson(responseJson))
                }

                is JSONArray -> {
                    for (i in 0 until responseJson.length()) {
                        val obj = responseJson.getJSONObject(i)
                        dictionaryContent.append(getDictionaryDataFromJson(obj))
                    }
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return dictionaryContent
    }

    private fun getDictionaryDataFromJson(jsonObject: JSONObject?): StringBuilder {
        val word = "Word"
        val phonetic = "Phonetic"
        val origin = "Origin"
        val definition = "Definition"
        val example = "Example"
        val synonyms = "Synonyms"
        val antonyms = "Antonyms"
        val meaning = "Meaning"

        val sb = StringBuilder()
        if (jsonObject != null) {
            try {
                try {
                    if (jsonObject.has("word")) {
                        try {
                            sb.append(
                                "<center><b>$word</b><br/><span style=\"text-transform:uppercase\">${
                                    jsonObject.getString("word")
                                }</span></center>"
                            )
                        } catch (e1: Exception) {
                            e1.printStackTrace()
                        }
                    }
                } catch (_: Exception) {
                }
                try {
                    if (jsonObject.has("phonetics")) {
                        val jsonArray2 =
                            if (jsonObject.isNull("phonetics")) null else jsonObject.getJSONArray("phonetics")
                        if (jsonArray2 != null && jsonArray2.length() > 0) {
                            sb.append("<center><b>$phonetic</b>")
                            for (k in 0 until jsonArray2.length()) {
                                jsonArray2.getJSONObject(k).let {
                                    if (it.has("text")) {
                                        if (it["text"] != "") {
                                            sb.append("<br/>${it["text"]}")
                                        }
                                    }
                                }
                            }
                            sb.append("</center><br/>")
                        }
                    }
                } catch (_: Exception) {
                }
            } catch (_: Exception) {
            }
            try {
                if (jsonObject.has("origin")) {
                    try {
                        if (jsonObject.getString("origin") != "") {
                            sb.append(
                                "<center><b>$origin</b><br/>${
                                    jsonObject.getString("origin")
                                }</center><br/>"
                            )
                        }
                    } catch (e1: Exception) {
                        e1.printStackTrace()
                    }
                }
            } catch (_: Exception) {
            }
            try {
                if (jsonObject.has("meanings")) {
                    try {
                        val jsonArrayHead =
                            if (jsonObject.isNull("meanings")) null else jsonObject.getJSONArray("meanings")
                        if (jsonArrayHead != null && jsonArrayHead.length() > 0) {
                            sb.append("<center><b>$meaning</b></center>")
                            for (h in 0 until jsonArrayHead.length()) {
                                jsonArrayHead.getJSONObject(h)?.let {
                                    if (it.has("partOfSpeech")) {
                                        if (it["partOfSpeech"] != "") {
                                            sb.append(
                                                "<center><b>${
                                                    it["partOfSpeech"].toString().uppercase()
                                                }</b></center>"
                                            )
                                        }
                                    }
                                    val jsonArray2 =
                                        if (it.isNull("definitions")) null else it.getJSONArray(
                                            "definitions"
                                        )
                                    if (jsonArray2 != null && jsonArray2.length() > 0) {
                                        for (k in 0 until jsonArray2.length()) {
                                            jsonArray2.getJSONObject(k).let { subObject ->
                                                if (subObject.has("definition") && subObject["definition"].toString()
                                                        .isNotEmpty()
                                                ) {
                                                    sb.append("<br/>&nbsp;<b>$definition</b><br/>&emsp;&emsp;${subObject["definition"]}<br/>")
                                                }
                                                if (subObject.has("example")) {
                                                    if (subObject["example"] != "") {
                                                        sb.append("<br/>&nbsp;<b>$example</b><br/>&emsp;&emsp;${subObject["example"]}<br/>")
                                                    }
                                                }
                                                if (subObject.has("synonyms")) {
                                                    val jsonArray1 =
                                                        if (subObject.isNull("synonyms")) null else subObject.getJSONArray(
                                                            "synonyms"
                                                        )
                                                    if (jsonArray1 != null && jsonArray1.length() > 0) {
                                                        val synonymBuilder = StringBuilder()
                                                        for (j in 0 until jsonArray1.length()) {
                                                            val synonym = jsonArray1[j].toString()
                                                            if (synonym != "") {
                                                                synonymBuilder.append("<li>$synonym</li>")
                                                            }
                                                        }
                                                        if (synonymBuilder.toString() != "") {
                                                            sb.append("<br/>&nbsp;<b>$synonyms</b><ul>$synonymBuilder</ul>")
                                                        }
                                                    }
                                                }
                                                if (subObject.has("antonyms")) {
                                                    val jsonArray1 =
                                                        if (subObject.isNull("antonyms")) null else subObject.getJSONArray(
                                                            "antonyms"
                                                        )
                                                    if (jsonArray1 != null && jsonArray1.length() > 0) {
                                                        val antonymsBuilder = StringBuilder()
                                                        for (j in 0 until jsonArray1.length()) {
                                                            val antonym = jsonArray1[j].toString()
                                                            if (antonym != "") {
                                                                antonymsBuilder.append("<li>$antonym</li>")
                                                            }
                                                        }
                                                        if (antonymsBuilder.toString() != "") {
                                                            sb.append("<br/>&nbsp;<b>$antonyms</b><ul>$antonymsBuilder</ul>")
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } catch (_: Exception) {
                    }
                }
            } catch (_: Exception) {
            }
            if (sb.toString() != "") {
                sb.append("<br/><br/>")
            }
        }
        return sb
    }
}