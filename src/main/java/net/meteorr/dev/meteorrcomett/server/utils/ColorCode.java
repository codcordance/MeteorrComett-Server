package net.meteorr.dev.meteorrcomett.server.utils;

/**
 * @author RedLux
 *
 * Énumération listant les couleurs et leur code Ascii
 */
public enum ColorCode {
    /**
     * Représente une réinitialisation de la couleur
     */
    RESET("\u001B[0m"),
    /**
     * Représente un texte en noir
     */
    BLACK("\u001B[30m"),
    /**
     * Représente un texte en rouge
     */
    RED("\u001B[31m"),
    /**
     * Représente un texte en vert
     */
    GREEN("\u001B[32m"),
    /**
     * Représente un texte en vert
     */
    YELLOW("\u001B[33m"),
    /**
     * Représente un texte en bleu
     */
    BLUE("\u001B[34m"),
    /**
     * Représente un texte en violet
     */
    PURPLE("\u001B[35m"),
    /**
     * Représente un texte en cyan
     */
    CYAN("\u001B[36m"),
    /**
     * Représente un texte en blanc
     */
    WHITE("\u001B[37m"),
    /**
     * Représente un texte sur fond noir
     */
    BG_BLACK("\u001B[40m"),
    /**
     * Représente un texte sur fond rouge
     */
    BG_RED("\u001B[41m"),
    /**
     * Représente un texte sur fond vert
     */
    BG_GREEN("\u001B[42m"),
    /**
     * Représente un texte sur fond jaune
     */
    BG_YELLOW("\u001B[43m"),
    /**
     * Représente un texte sur fond bleu
     */
    BG_BLUE("\u001B[44m"),
    /**
     * Représente un texte sur fond violet
     */
    BG_PURPLE("\u001B[45m"),
    /**
     * Représente un texte sur fond cyan
     */
    BG_CYAN("\u001B[46m"),
    /**
     * Représente un texte sur fond blanc
     */
    BG_WHITE("\u001B[47m"),
    /**
     * Représente un texte sans changements
     */
    NO("");

    /**
     * Représente le code Ascii de la couleur
     */
    private final String asciiCode;

    /**
     * Constructeur d'une couleur
     *
     * @param asciiCode code Ascii de la couleur
     */
    ColorCode(String asciiCode) {
        this.asciiCode = asciiCode;
    }

    /**
     * Récupère le code Ascii de la couleur
     *
     * @return une chaine de caractères correspondant au code Ascii
     */
    public String getAsciiCode() {
        return this.asciiCode;
    }
}
