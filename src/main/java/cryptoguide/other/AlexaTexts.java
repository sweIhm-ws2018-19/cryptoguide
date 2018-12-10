package cryptoguide.other;

public class AlexaTexts {

    AlexaTexts() throws IllegalStateException {
        throw new IllegalStateException("Utility class");
    }
    //SP = SPEECH; RP = REPROMPT; CTH = CARDTEXT_HEADER; CTT = CARDTEXT_TEXT

    //LaunchRequestHandler LR
    public static final String LR_SP = "Willkommen bei CryptoGuide. Wie kann ich dir helfen? Bist du zum ersten Mal hier dann sage: Hilfe.";
    public static final String LR_RP = "Was kann ich denn für dich tun?";
    public static final String LR_CTH = "Willkommen bei CryptoGuide!";

    //CancelandStopIntentHandler CSI
    public static final String CSI_SP = "Auf Wiedersehen. Danke, dass du CryptoGuide verwendet hast.";
    public static final String CSI_CTH = "CryptoGuide beendet";
    public static final String CSI_CTT = "Danke, dass du CryptoGuide verwendet hast.";

    //FallBackIntentHandler FBI
    public static final String FBI_SP = "Tut mir leid, das habe ich nicht verstanden. Bei weiteren Problemen sage einfach: Hilfe.";
    public static final String FBI_CTH = "Fehler aufgetreten";
    public static final String FBI_CTT = "Das habe ich nicht verstanden.";

    //HelpIntentHandler HI
    public static final String HI_SP = "Brauchst du Hilfe zu den Kursen allgemein sage 'Hilfe zu Kursen', brauchst du Hilfe zum Portfolio sage 'Hilfe zum Portfolio'.";
    public static final String HI_RP = "Zu was benötigst du Hilfe? Zu den Kursen oder zum Portfolio?";
    public static final String HI_CTH = "Hilfe";

    //GetHelpRatesIntentHandler GHRI
    public static final String GHRI_SP = "Um aktuelle Kurse von Währungen zu erfahren sage zum Beispiel 'Wie ist der aktuelle Kurs von Bitcoin zu Euro'. Du kannst auch Vergangene Kurse abfragen sage zum Beispiel 'Wie war der Kurs von Bitcoin zu Euro am zehnten elften 2015' oder 'Wie war der Kurs von Bitcoin zu Euro letzten Montag'. Um Währungscodes zu erfahren sage zum Beispiel 'Was ist der Währungscode zu Dollar'";
    public static final String GHRI_CTH = "Hilfe zu Kursen";

    //GetHelpPortfolioIntentHandler GHPI
    public static final String GHPI_SP = "Diese Funktion unterstütze ich aktuell noch nicht.";
    public static final String GHPI_CTH = "Hilfe zum Portfolio";

    //GetCurrencyCodeIntentHandler GCCI
    public static final String GCCI_SP_ERROR = "Diese Währung unterstütze ich leider noch nicht oder ich habe dich nicht richtig verstanden. Bitte versuche es erneut.";
    public static final String GCCI_CTH = "Währungscode";

    //GetCurrencyRateIntentHandler GCRI
    public static final String GCRI_SP_ERROR_INVALID_CURRENCY = "Ich habe eine der beiden Währungen nicht verstanden oder unterstütze diese noch nicht.";
    public static final String GCRI_SP_ERROR_CRYPTOCOMPARE = "Ich habe einen Fehler beim Ermitteln des Kurses festgestellt. Versuche es noch einmal.";
    public static final String GCRI_SP_ERROR_WRONGDATE = "Eine Berechnung des Kurses zum angegebenen Zeitpunkt ist nicht möglich.";
    public static final String GCRI_SP_ERROR_AMOUNT_TOO_SMALL = "Eine negative Menge oder null ist nicht zulässig. Versuche es noch einmal.";
    public static final String GCRI_CTH = "Ermittelter Kurs";
    public static final String GCRI_CTH_ERROR = "Fehler";
}
