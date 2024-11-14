package br.com.SistemaControleInsumos.exceptions;

public class UserExists extends RuntimeException {

    public UserExists(String msg) {
        super(msg);
    }
}
