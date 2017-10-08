package br.com.spakoapi.excecao;


public class DAOException extends RuntimeException{
    
    private static final long serialVersionUID = 3965087475900464946L;
    
    private final int code;
    
    public DAOException(String msg, int code) {
        super(msg);
        this.code = code;
    }
    
    public int getCode() {
        return code;
    }
}
