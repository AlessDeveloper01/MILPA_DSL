
package parser;

import lexer.Token;
import lexer.TokenType;
import java.util.List;

public class Parser {

    private List<Token> tokens;
    private int posicion;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.posicion = 0;
    }

    public void analizar() {
        while (actual().tipo != TokenType.FIN) {
            declaracion();
        }

        System.out.println("Programa sintacticamente correcto.");
    }

    private void declaracion() {
        Token tipo = actual();

        if (tipo.tipo != TokenType.ENTERO
                && tipo.tipo != TokenType.DECIMAL
                && tipo.tipo != TokenType.TEXTO
                && tipo.tipo != TokenType.BOOLEANO) {

            error("Se esperaba un tipo de dato");
        }

        avanzar();

        if (actual().tipo != TokenType.IDENTIFICADOR) {
            error("Se esperaba el nombre de la variable");
        }

        avanzar();

        if (actual().tipo != TokenType.IGUAL) {
            error("Se esperaba el signo =");
        }

        avanzar();

        Token valor = actual();

        if (!valorValido(tipo.tipo, valor.tipo)) {
            error("El valor no corresponde al tipo de dato");
        }

        avanzar();

        if (actual().tipo != TokenType.PUNTO_Y_COMA) {
            error("Falta el punto y coma");
        }

        avanzar();
    }

    private boolean valorValido(TokenType tipo, TokenType valor) {
        switch (tipo) {
            case ENTERO:
            case DECIMAL:
                return valor == TokenType.NUMERO;

            case TEXTO:
                return valor == TokenType.CADENA;

            case BOOLEANO:
                return valor == TokenType.VERDADERO
                        || valor == TokenType.FALSO;

            default:
                return false;
        }
    }

    private Token actual() {
        return tokens.get(posicion);
    }

    private void avanzar() {
        if (posicion < tokens.size() - 1) {
            posicion++;
        }
    }

    private void error(String mensaje) {
        throw new RuntimeException(
            "Error sintactico en la linea "
            + actual().linea + ": " + mensaje
        );
    }
}
