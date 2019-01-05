package cryptoguide.other;

public class AlexaTexts {

    AlexaTexts() {
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
    public static final String GHRI_SP = "Um aktuelle Kurse von Währungen zu erfahren sage zum Beispiel 'Wie ist der aktuelle Kurs von Bitcoin zu Euro'. " +
                                         "Du kannst auch Vergangene Kurse abfragen sage zum Beispiel 'Wie war der Kurs von Bitcoin zu Euro am zehnten elften 2015' " +
                                         "oder 'Wie war der Kurs von Bitcoin zu Euro letzten Montag'. " +
                                         "Außerdem kannst du nachfragen wie sich der Kurs zwischen zwei währungen entwickelt hat. Sage zum Beispiel " +
                                         "'Wie hat sich der Kurs von Bitcoin zu Euro seit zehnten elften 2015 entwickelt'. " +
                                         "Wenn du wissen willst wie viel du von einer Währung benötigst um eine andere zu bekommen sage zum Beispiel" +
                                         "'Wie viel Bitcoin bekomme ich für 20 Euro'. " +
                                         "Um Währungscodes zu erfahren sage zum Beispiel 'Was ist der Währungscode zu Dollar'";
    public static final String GHRI_CTH = "Hilfe zu Kursen";

    //GetHelpPortfolioIntentHandler GHPI
    public static final String GHPI_SP = "Um Währungen dem Portfolio hinzuzufügen sage zum Beispiel: " +
                                         "'Füge 2 Bitcoin meinem Portfolio hinzu'. " +
                                         "Außerdem kannst du Währungen aus dem Portfolio wieder entfernen. Sage dafür einfach: " +
                                         "'Entferne 1 Bitcoin von meinem Portfolio'. " +
                                         "Um den Inhalt des Porfolios abzufragen sage zum Beispiel: " +
                                         "'Was befindet sich in meinem Portfolio'. " +
                                         "Du kannst dir auch den aktuellen Wert des Portfolios ausgeben lassen. Sage dafür: " +
                                         "'Wie viel ist mein Portfolio in Euro Wert'. " +
                                         "Um dir den vergangenen Wert des Portfolios ausgeben zu lassen sage zum Beispiel: " +
                                         "'Wie viel war mein Portfolio in Euro am ersten Oktober 2019 wert'. ";
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

    //GetPortfolioContentIntentHandler GPCI
    public static final String GPCI_CTH = "Portfolioinhalt";

    //RemoveFromPortfolioIntentHandler RFPI
    public static final String RFPI_CTH = "Löschen aus dem Portfolio";
    public static final String RFPI_AMOUNTLOW_ERROR = "Die Anzahl der zu löschenden Währung muss größer als Null sein!";
    public static final String RFPI_UNKNOWN_ERROR = "Beim Löschen der Währungen ist ein Fehler aufgetreten.";

    //GetPorrtfolioValueIntentHandler
    public static final String GPVI_CTH = "Portfoliowert";

}
