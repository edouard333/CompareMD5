package com.phenix.comparemd5.exception;

import jakarta.validation.constraints.NotNull;

/**
 * Exception de base pour toutes les erreurs survenant dans le projet.<br>
 * <br>
 * Toutes les exceptions spécifiques doivent hériter de cette classe.
 *
 * @author <a href="mailto:edouard128@hotmail.com">Edouard Jeanjean</a>
 */
public class CompareMD5Exception extends Exception {

    /**
     * Construit une {@link CompareMD5Exception} avec un message.
     *
     * @param message Le message.
     */
    public CompareMD5Exception(String message) {
        super(message);
    }

    /**
     * Construit une {@link CompareMD5Exception} avec un message et une cause.
     *
     * @param message Le message.
     * @param cause La cause.
     */
    public CompareMD5Exception(String message, @NotNull Throwable cause) {
        super(message, cause);
    }
}
