package net.meteorr.dev.meteorrcomett.server.console;

import net.meteorr.dev.meteorrcomett.server.utils.ColorCode;

/**
 * @author RedLux
 *
 * Énumération listant les différents niveaux de message
 */
public enum MessageLevel {
    /**
     * Représente un niveau d'info
     */
    INFO("INFO", ColorCode.CYAN, ColorCode.NO),
    /**
     * Représente un niveau d'avertissement
     */
    WARNING("WARNING", ColorCode.YELLOW, ColorCode.NO),
    /**
     * Représente un niveau d'erreur
     */
    ERROR("ERROR", ColorCode.RED, ColorCode.RED),
    /**
     * Représente un niveau d'erreur critique
     */
    CRITICAL("ERROR", ColorCode.RED, ColorCode.BG_RED),
    /**
     * Représente un niveau de debug
     */
    DEBUG("DEBUG", ColorCode.PURPLE, ColorCode.PURPLE);

    /**
     * Représente l'identifiant du niveau
     */
    private final String identifier;
    /**
     * Représente la couleur de premier plan du niveau
     */
    private final ColorCode fgColor;
    /**
     * Représente la couleur d'arrière plan du niveau
     */
    private final ColorCode bgColor;

    /**
     * Constructeur d'un niveau
     *
     * @param identifier identifiant du niveau
     * @param fgColor couleur de premier plan du niveau
     * @param bgColor couleur d'arrière plan du niveau
     */
    MessageLevel(String identifier, ColorCode fgColor, ColorCode bgColor) {
        this.identifier = identifier;
        this.fgColor = fgColor;
        this.bgColor = bgColor;
    }

    /**
     * Récupère l'identifiant du niveau
     *
     * @return un {@link java.lang.String} correspondant à l'identifiant du niveau
     */
    public String getIdentifier() {
        return this.identifier;
    }

    /**
     * Récupère la couleur de premier plan du niveau
     *
     * @return un {@link net.meteorr.dev.meteorrcomett.server.utils.ColorCode} correspondant à la couleur de premier plan du niveau
     */
    public ColorCode getFgColor() {
        return this.fgColor;
    }

    /**
     * Récupère la couleur d'arrière plan du niveau
     *
     * @return un {@link net.meteorr.dev.meteorrcomett.server.utils.ColorCode} correspondant à la couleur d'arrière plan du niveau
     */
    public ColorCode getBgColor() {
        return this.bgColor;
    }
}
