
package lexer;

import java.util.ArrayList;
import java.util.List;

public class Lexer {

    String codigo;
    int posicion;
    int linea;

    public Lexer(String codigo) {
        this.codigo = codigo;
        this.posicion = 0;
        this.linea = 1;
    }

    public List<Token> analizar() {
        List<Token> tokens = new ArrayList<>();

        while (posicion < codigo.length()) {
            char actual = codigo.charAt(posicion);

            // Ignorar espacios y saltos de linea
            if (actual == ' ' || actual == '\t' || actual == '\r') {
                posicion++;
                continue;
            }

            if (actual == '\n') {
                linea++;
                posicion++;
                continue;
            }

            // Reconocer numeros
            if (Character.isDigit(actual)) {
                String numero = "";

                while (posicion < codigo.length()
                        && Character.isDigit(codigo.charAt(posicion))) {
                    numero += codigo.charAt(posicion);
                    posicion++;
                }

                tokens.add(new Token(TokenType.NUMERO, numero, linea));
                continue;
            }

            // Reconocer palabras
            if (Character.isLetter(actual) || actual == '_') {
                String palabra = "";

                while (posicion < codigo.length()
                        && (Character.isLetterOrDigit(codigo.charAt(posicion))
                        || codigo.charAt(posicion) == '_')) {
                    palabra += codigo.charAt(posicion);
                    posicion++;
                }

                TokenType tipo = reconocerPalabra(palabra);

                tokens.add(new Token(tipo, palabra, linea));
                continue;
            }

            // Reconocer texto entre comillas
            if (actual == '"') {
                posicion++;
                String texto = "";

                while (posicion < codigo.length()
                        && codigo.charAt(posicion) != '"') {
                    texto += codigo.charAt(posicion);
                    posicion++;
                }

                if (posicion < codigo.length()) {
                    posicion++;
                    tokens.add(new Token(TokenType.CADENA, texto, linea));
                } else {
                    System.out.println(
                        "Error: texto sin cerrar en la linea " + linea
                    );
                }
                continue;
            }

            // Reconocer simbolos
            switch (actual) {
                case '=':
                    tokens.add(new Token(TokenType.IGUAL, "=", linea));
                    break;

                case '+':
                    tokens.add(new Token(TokenType.SUMA, "+", linea));
                    break;

                case '-':
                    tokens.add(new Token(TokenType.RESTA, "-", linea));
                    break;

                case ';':
                    tokens.add(new Token(
                        TokenType.PUNTO_Y_COMA, ";", linea
                    ));
                    break;

                default:
                    System.out.println(
                        "Error: caracter desconocido '" + actual
                        + "' en la linea " + linea
                    );
                    break;
            }

            posicion++;
        }

        tokens.add(new Token(TokenType.FIN, "FIN", linea));

        return tokens;
    }

    private TokenType reconocerPalabra(String palabra) {
        switch (palabra) {
            case "entero":
                return TokenType.ENTERO;

            case "decimal":
                return TokenType.DECIMAL;

            case "texto":
                return TokenType.TEXTO;

            case "booleano":
                return TokenType.BOOLEANO;
            
            case "verdadero":
                return TokenType.VERDADERO;
            
            case "falso":
                return TokenType.FALSO;

            case "si":
                return TokenType.SI;

            case "si_no":
                return TokenType.SI_NO;

            case "mientras":
                return TokenType.MIENTRAS;

            default:
                return TokenType.IDENTIFICADOR;
        }
    }
}
