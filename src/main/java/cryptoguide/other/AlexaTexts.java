package cryptoguide.other;

public class AlexaTexts {

    //SP = SPEECH; RP = REPROMPT; CTH = CARDTEXT_HEADER; CTT = CARDTEXT_TEXT

    //LaunchRequestHandler LRM
    public static final String LRM_SP = "Willkommen bei CryptoGuide. Wie kann ich dir helfen? Bist du zum ersten Mal hier dann sage: Hilfe.";
    public static final String LRM_RP = "Was kann ich denn für dich tun?";
    public static final String LRM_CTH = "Willkommen bei CryptoGuide!";

    //CancelandStopIntentHandler CSI
    public static final String CSI_SP = "Auf Wiedersehen. Danke, dass du CryptoGuide verwendet hast.";
    public static final String CSI_CTH = "CryptoGuide beendet";
    public static final String CSI_CTT = "Danke, dass du CryptoGuide verwendet hast.";

    //FallBackIntentHandler FBI
    public static final String FBI_SP = "Tut mir leid, das habe ich nicht verstanden. Bei weiteren Problemen sage einfach: Hilfe.";
    public static final String FBI_CTH = "Fehler aufgetreten";
    public static final String FBI_CTT = "Das habe ich nicht verstanden.";

    //HelpIntentHandler HI
    public static final String HI_SP = "Benötigst du Hilfe zu A oder B?";
    public static final String HI_RP = "Zu was benötigst du Hilfe? A oder B?";
    public static final String HI_CTH = "Hilfe";

    //GetCurrentCurrencyRateIntentHandler
    public static final String GCCRI_SP_ERROR = "Es gab einen Fehler bei der Anfrage. Bitte versuche es erneut.";
    public static final String GCCRI_CTH = "Aktueller Kurs";


}
