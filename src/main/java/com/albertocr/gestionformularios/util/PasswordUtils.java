package com.albertocr.gestionformularios.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Utilidad para el manejo de contraseñas.
 * <p>
 * ADVERTENCIA: Esta es una implementación de hashing básica solo para fines de demostración.
 * En un entorno de producción, se debe utilizar una biblioteca de hashing de contraseñas robusta
 * y probada como BCrypt o Argon2 para proteger adecuadamente las contraseñas de los usuarios.
 *
 * @author Alberto Castro <AlbertoCastroCR>
 * @version 1.0
 */
public final class PasswordUtils {

    private static final Logger logger = LoggerFactory.getLogger(PasswordUtils.class);
    private static final String HASH_ALGORITHM = "SHA-256";

    private PasswordUtils() {
        throw new UnsupportedOperationException("Esta es una clase de utilidad y no puede ser instanciada.");
    }

    /**
     * Genera un hash de una contraseña utilizando SHA-256.
     *
     * @param password La contraseña en texto plano.
     * @return El hash de la contraseña en formato Base64, o la contraseña original si ocurre un error.
     */
    public static String hashPassword(String password) {
        if (password == null || password.isEmpty()) {
            return null;
        }
        try {
            MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
            byte[] hash = digest.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            logger.error("Algoritmo de hashing no encontrado: {}", HASH_ALGORITHM, e);
            // En un caso real, se debería lanzar una excepción para detener el proceso.
            // Devolver la contraseña sin hashear es un riesgo de seguridad.
            return password; // ¡No hacer esto en producción!
        }
    }

    /**
     * Comprueba si una contraseña en texto plano coincide con un hash.
     *
     * @param rawPassword    La contraseña en texto plano.
     * @param hashedPassword El hash de la contraseña almacenado.
     * @return true si las contraseñas coinciden, false en caso contrario.
     */
    public static boolean checkPassword(String rawPassword, String hashedPassword) {
        if (rawPassword == null || hashedPassword == null) {
            return false;
        }
        String newHash = hashPassword(rawPassword);
        return hashedPassword.equals(newHash);
    }
}
