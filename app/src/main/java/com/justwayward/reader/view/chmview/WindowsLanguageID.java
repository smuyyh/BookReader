package com.justwayward.reader.view.chmview;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class WindowsLanguageID {

    private static final Map<Integer, Locale> map = loadMap();

    private static Map<Integer, Locale> loadMap() {
        Map<Integer, Locale> map = new HashMap<>();
        map.put(0x0404, new Locale("zh", "TW", ""));
        map.put(0x0804, new Locale("zh", "CN", ""));
        map.put(0x0436, new Locale("af", "ZA", "")); // Afrikaans
        map.put(0x041c, new Locale("sq", "AL", "")); // Albanian
        map.put(0x0401, new Locale("ar", "SA", "")); // Arabic - Saudi Arabia
        map.put(0x0801, new Locale("ar", "IQ", "")); // Arabic - Iraq
        map.put(0x0c01, new Locale("ar", "EG", "")); // Arabic - Egypt
        map.put(0x1001, new Locale("ar", "LY", "")); // Arabic - Libya
        map.put(0x1401, new Locale("ar", "DZ", "")); // Arabic - Algeria
        map.put(0x1801, new Locale("ar", "MA", "")); // Arabic - Morocco
        map.put(0x1c01, new Locale("ar", "TN", "")); // Arabic - Tunisia
        map.put(0x2001, new Locale("ar", "OM", "")); // Arabic - Oman
        map.put(0x2401, new Locale("ar", "YE", "")); // Arabic - Yemen
        map.put(0x2801, new Locale("ar", "SY", "")); // Arabic - Syria
        map.put(0x2c01, new Locale("ar", "JO", "")); // Arabic - Jordan
        map.put(0x3001, new Locale("ar", "LB", "")); // Arabic - Lebanon
        map.put(0x3401, new Locale("ar", "KW", "")); // Arabic - Kuwait
        map.put(0x3801, new Locale("ar", "AE", "")); // Arabic - United Arab Emirates
        map.put(0x3c01, new Locale("ar", "BH", "")); // Arabic - Bahrain
        map.put(0x4001, new Locale("ar", "QA", "")); // Arabic - Qatar
        map.put(0x042b, new Locale("hy", "AM", "")); // Armenian
        map.put(0x042c, new Locale("az", "AZ", "")); // Azeri Latin
        map.put(0x082c, new Locale("az", "AZ", "")); // Azeri - Cyrillic
        map.put(0x042d, new Locale("eu", "ES", "")); // Basque
        map.put(0x0423, new Locale("be", "BY", "")); // Belarusian
        map.put(0x0445, new Locale("bn", "IN", "")); // Begali
        map.put(0x201a, new Locale("bs", "BA", "")); // Bosnian
        map.put(0x141a, new Locale("bs", "BA", "")); // Bosnian - Cyrillic
        map.put(0x047e, new Locale("br", "FR", "")); // Breton - France
        map.put(0x0402, new Locale("bg", "BG", "")); // Bulgarian
        map.put(0x0403, new Locale("ca", "ES", "")); // Catalan
        map.put(0x0004, new Locale("zh", "CHS", "")); // Chinese - Simplified
        map.put(0x0404, new Locale("zh", "TW", "")); // Chinese - Taiwan
        map.put(0x0804, new Locale("zh", "CN", "")); // Chinese - PRC
        map.put(0x0c04, new Locale("zh", "HK", "")); // Chinese - Hong Kong S.A.R.
        map.put(0x1004, new Locale("zh", "SG", "")); // Chinese - Singapore
        map.put(0x1404, new Locale("zh", "MO", "")); // Chinese - Macao S.A.R.
        map.put(0x7c04, new Locale("zh", "CHT", "")); // Chinese - Traditional
        map.put(0x041a, new Locale("hr", "HR", "")); // Croatian
        map.put(0x101a, new Locale("hr", "BA", "")); // Croatian - Bosnia
        map.put(0x0405, new Locale("cs", "CZ", "")); // Czech
        map.put(0x0406, new Locale("da", "DK", "")); // Danish
        map.put(0x0413, new Locale("nl", "NL", "")); // Dutch (Netherlands)
        map.put(0x0409, new Locale("en", "US", "")); // English (United States)
        map.put(0x0809, new Locale("en", "UK", "")); // English (United Kingdom)
        map.put(0x0c09, new Locale("en", "AU", "")); // English (Australian)
        map.put(0x1009, new Locale("en", "CA", "")); // English (Canadian)
        map.put(0x1409, new Locale("en", "NZ", "")); // English (New Zealand)
        map.put(0x1809, new Locale("en", "IE", "")); // English (Ireland)
        map.put(0x1c09, new Locale("en", "ZA", "")); // English (South Africa)
        map.put(0x048c, new Locale("gbz", "AF", "")); // Dari - Afghanistan
        map.put(0x0465, new Locale("div", "MV", "")); // Divehi - Maldives
        map.put(0x0413, new Locale("nl", "NL", "")); // Dutch - The Netherlands
        map.put(0x0813, new Locale("nl", "BE", "")); // Dutch - Belgium
        map.put(0x0409, new Locale("en", "US", "")); // English - United States
        map.put(0x0809, new Locale("en", "GB", "")); // English - United Kingdom
        map.put(0x0c09, new Locale("en", "AU", "")); // English - Australia
        map.put(0x1009, new Locale("en", "CA", "")); // English - Canada
        map.put(0x1409, new Locale("en", "NZ", "")); // English - New Zealand
        map.put(0x1809, new Locale("en", "IE", "")); // English - Ireland
        map.put(0x1c09, new Locale("en", "ZA", "")); // English - South Africa
        map.put(0x2009, new Locale("en", "JA", "")); // English - Jamaica
        map.put(0x2409, new Locale("en", "CB", "")); // English - Carribbean
        map.put(0x2809, new Locale("en", "BZ", "")); // English - Belize
        map.put(0x2c09, new Locale("en", "TT", "")); // English - Trinidad
        map.put(0x3009, new Locale("en", "ZW", "")); // English - Zimbabwe
        map.put(0x3409, new Locale("en", "PH", "")); // English - Phillippines
        map.put(0x0425, new Locale("et", "EE", "")); // Estonian
        map.put(0x0438, new Locale("fo", "FO", "")); // Faroese
        map.put(0x0464, new Locale("fil", "PH", "")); // Filipino
        map.put(0x040b, new Locale("fi", "FI", "")); // Finnish
        map.put(0x040c, new Locale("fr", "FR", "")); // French (Standard)
        map.put(0x080c, new Locale("fr", "BE", "")); // French (Belgian)
        map.put(0x0c0c, new Locale("fr", "CA", "")); // French (Canadian)
        map.put(0x100c, new Locale("fr", "CH", "")); // French (Switzerland)
        map.put(0x0407, new Locale("de", "DE", "")); // German (Standard)
        map.put(0x040c, new Locale("fr", "FR", "")); // French - France
        map.put(0x080c, new Locale("fr", "BE", "")); // French - Belgium
        map.put(0x0c0c, new Locale("fr", "CA", "")); // French - Canada
        map.put(0x100c, new Locale("fr", "CH", "")); // French - Switzerland
        map.put(0x140c, new Locale("fr", "LU", "")); // French - Luxembourg
        map.put(0x180c, new Locale("fr", "MC", "")); // French - Monaco
        map.put(0x0462, new Locale("fy", "NL", "")); // Frisian - Netherlands
        map.put(0x0456, new Locale("gl", "ES", "")); // Galician
        map.put(0x0437, new Locale("ka", "GE", "")); // Georgian
        map.put(0x0407, new Locale("de", "DE", "")); // German - Germany
        map.put(0x0807, new Locale("de", "CH", "")); // German - Switzerland
        map.put(0x0c07, new Locale("de", "AT", "")); // German - Austria
        map.put(0x1007, new Locale("de", "LU", "")); // German - Luxembourg
        map.put(0x1407, new Locale("de", "LI", "")); // German - Liechtenstein
        map.put(0x0408, new Locale("el", "GR", "")); // Greek
        map.put(0x040d, new Locale("iw", "IL", "")); // Hebrew
        map.put(0x0447, new Locale("gu", "IN", "")); // Gujarati
        map.put(0x040d, new Locale("he", "IL", "")); // Hebrew
        map.put(0x0439, new Locale("hi", "IN", "")); // Hindi
        map.put(0x040e, new Locale("hu", "HU", "")); // Hungarian
        map.put(0x040f, new Locale("is", "IS", "")); // Icelandic
        map.put(0x0410, new Locale("it", "IT", "")); // Italian (Standard)
        map.put(0x0411, new Locale("ja", "JA", "")); // Japanese
        map.put(0x0414, new Locale("no", "NO", "")); // Norwegian (Bokmal)
        map.put(0x0816, new Locale("pt", "PT", "")); // Portuguese (Standard)
        map.put(0x0c0a, new Locale("es", "ES", "")); // Spanish (Modern Sort)
        map.put(0x0441, new Locale("sw", "KE", "")); // Swahili (Kenya)
        map.put(0x041d, new Locale("sv", "SE", "")); // Swedish
        map.put(0x081d, new Locale("sv", "FI", "")); // Swedish (Finland)
        map.put(0x0421, new Locale("id", "ID", "")); // Indonesian
        map.put(0x045d, new Locale("iu", "CA", "")); // Inuktitut
        map.put(0x085d, new Locale("iu", "CA", "")); // Inuktitut - Latin
        map.put(0x083c, new Locale("ga", "IE", "")); // Irish - Ireland
        map.put(0x0434, new Locale("xh", "ZA", "")); // Xhosa - South Africa
        map.put(0x0435, new Locale("zu", "ZA", "")); // Zulu
        map.put(0x0410, new Locale("it", "IT", "")); // Italian - Italy
        map.put(0x0810, new Locale("it", "CH", "")); // Italian - Switzerland
        map.put(0x0411, new Locale("ja", "JP", "")); // Japanese
        map.put(0x044b, new Locale("kn", "IN", "")); // Kannada - India
        map.put(0x043f, new Locale("kk", "KZ", "")); // Kazakh
        map.put(0x0457, new Locale("kok", "IN", "")); // Konkani
        map.put(0x0412, new Locale("ko", "KR", "")); // Korean
        map.put(0x0440, new Locale("ky", "KG", "")); // Kyrgyz
        map.put(0x0426, new Locale("lv", "LV", "")); // Latvian
        map.put(0x0427, new Locale("lt", "LT", "")); // Lithuanian
        map.put(0x046e, new Locale("lb", "LU", "")); // Luxembourgish
        map.put(0x042f, new Locale("mk", "MK", "")); // FYRO Macedonian
        map.put(0x043e, new Locale("ms", "MY", "")); // Malay - Malaysia
        map.put(0x083e, new Locale("ms", "BN", "")); // Malay - Brunei
        map.put(0x044c, new Locale("ml", "IN", "")); // Malayalam - India
        map.put(0x043a, new Locale("mt", "MT", "")); // Maltese
        map.put(0x0481, new Locale("mi", "NZ", "")); // Maori
        map.put(0x047a, new Locale("arn", "CL", "")); // Mapudungun
        map.put(0x044e, new Locale("mr", "IN", "")); // Marathi
        map.put(0x047c, new Locale("moh", "CA", "")); // Mohawk - Canada
        map.put(0x0450, new Locale("mn", "MN", "")); // Mongolian
        map.put(0x0461, new Locale("ne", "NP", "")); // Nepali
        map.put(0x0414, new Locale("nb", "NO", "")); // Norwegian - Bokmal
        map.put(0x0814, new Locale("nn", "NO", "")); // Norwegian - Nynorsk
        map.put(0x0482, new Locale("oc", "FR", "")); // Occitan - France
        map.put(0x0448, new Locale("or", "IN", "")); // Oriya - India
        map.put(0x0463, new Locale("ps", "AF", "")); // Pashto - Afghanistan
        map.put(0x0429, new Locale("fa", "IR", "")); // Persian
        map.put(0x0415, new Locale("pl", "PL", "")); // Polish
        map.put(0x0416, new Locale("pt", "BR", "")); // Portuguese - Brazil
        map.put(0x0816, new Locale("pt", "PT", "")); // Portuguese - Portugal
        map.put(0x0446, new Locale("pa", "IN", "")); // Punjabi
        map.put(0x046b, new Locale("quz", "BO", "")); // Quechua (Bolivia)
        map.put(0x086b, new Locale("quz", "EC", "")); // Quechua (Ecuador)
        map.put(0x0c6b, new Locale("quz", "PE", "")); // Quechua (Peru)
        map.put(0x0418, new Locale("ro", "RO", "")); // Romanian - Romania
        map.put(0x0417, new Locale("rm", "CH", "")); // Raeto-Romanese
        map.put(0x0419, new Locale("ru", "RU", "")); // Russian
        map.put(0x243b, new Locale("smn", "FI", "")); // Sami Finland
        map.put(0x103b, new Locale("smj", "NO", "")); // Sami Norway
        map.put(0x143b, new Locale("smj", "SE", "")); // Sami Sweden
        map.put(0x043b, new Locale("se", "NO", "")); // Sami Northern Norway
        map.put(0x083b, new Locale("se", "SE", "")); // Sami Northern Sweden
        map.put(0x0c3b, new Locale("se", "FI", "")); // Sami Northern Finland
        map.put(0x203b, new Locale("sms", "FI", "")); // Sami Skolt
        map.put(0x183b, new Locale("sma", "NO", "")); // Sami Southern Norway
        map.put(0x1c3b, new Locale("sma", "SE", "")); // Sami Southern Sweden
        map.put(0x044f, new Locale("sa", "IN", "")); // Sanskrit
        map.put(0x0c1a, new Locale("sr", "SP", "")); // Serbian - Cyrillic
        map.put(0x1c1a, new Locale("sr", "BA", "")); // Serbian - Bosnia Cyrillic
        map.put(0x081a, new Locale("sr", "SP", "")); // Serbian - Latin
        map.put(0x181a, new Locale("sr", "BA", "")); // Serbian - Bosnia Latin
        map.put(0x046c, new Locale("ns", "ZA", "")); // Northern Sotho
        map.put(0x0432, new Locale("tn", "ZA", "")); // Setswana - Southern Africa
        map.put(0x041b, new Locale("sk", "SK", "")); // Slovak
        map.put(0x0424, new Locale("sl", "SI", "")); // Slovenian
        map.put(0x040a, new Locale("es", "ES", "")); // Spanish - Spain
        map.put(0x080a, new Locale("es", "MX", "")); // Spanish - Mexico
        map.put(0x0c0a, new Locale("es", "ES", "")); // Spanish - Spain (Modern)
        map.put(0x100a, new Locale("es", "GT", "")); // Spanish - Guatemala
        map.put(0x140a, new Locale("es", "CR", "")); // Spanish - Costa Rica
        map.put(0x180a, new Locale("es", "PA", "")); // Spanish - Panama
        map.put(0x1c0a, new Locale("es", "DO", "")); // Spanish - Dominican Republic
        map.put(0x200a, new Locale("es", "VE", "")); // Spanish - Venezuela
        map.put(0x240a, new Locale("es", "CO", "")); // Spanish - Colombia
        map.put(0x280a, new Locale("es", "PE", "")); // Spanish - Peru
        map.put(0x2c0a, new Locale("es", "AR", "")); // Spanish - Argentina
        map.put(0x300a, new Locale("es", "EC", "")); // Spanish - Ecuador
        map.put(0x340a, new Locale("es", "CL", "")); // Spanish - Chile
        map.put(0x380a, new Locale("es", "UR", "")); // Spanish - Uruguay
        map.put(0x3c0a, new Locale("es", "PY", "")); // Spanish - Paraguay
        map.put(0x400a, new Locale("es", "BO", "")); // Spanish - Bolivia
        map.put(0x440a, new Locale("es", "SV", "")); // Spanish - El Salvador
        map.put(0x480a, new Locale("es", "HN", "")); // Spanish - Honduras
        map.put(0x4c0a, new Locale("es", "NI", "")); // Spanish - Nicaragua
        map.put(0x500a, new Locale("es", "PR", "")); // Spanish - Puerto Rico
        map.put(0x0441, new Locale("sw", "KE", "")); // Swahili
        map.put(0x041d, new Locale("sv", "SE", "")); // Swedish - Sweden
        map.put(0x081d, new Locale("sv", "FI", "")); // Swedish - Finland
        map.put(0x045a, new Locale("syr", "SY", "")); // Syriac
        map.put(0x0449, new Locale("ta", "IN", "")); // Tamil
        map.put(0x0444, new Locale("tt", "RU", "")); // Tatar
        map.put(0x044a, new Locale("te", "IN", "")); // Telugu
        map.put(0x041e, new Locale("th", "TH", "")); // Thai
        map.put(0x041f, new Locale("tr", "TR", "")); // Turkish
        map.put(0x0422, new Locale("uk", "UA", "")); // Ukrainian
        map.put(0x0420, new Locale("ur", "PK", "")); // Urdu
        map.put(0x0820, new Locale("ur", "IN", "")); // Urdu - India
        map.put(0x0443, new Locale("uz", "UZ", "")); // Uzbek - Latin
        map.put(0x0843, new Locale("uz", "UZ", "")); // Uzbek - Cyrillic
        map.put(0x042a, new Locale("vi", "VN", "")); // Vietnamese
        map.put(0x0452, new Locale("cy", "GB", "")); // Welsh
        return map;
    }

    public static Locale getLocale(int code) {
        return map.get(code);
    }
}
