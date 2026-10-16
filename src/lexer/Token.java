
package lexer;

public class Token {
    public TokenType tipo;
    public String valor;
    public int linea;

    public Token(TokenType tipo, String valor, int linea) {
        this.tipo = tipo;
        this.valor = valor;
        this.linea = linea;
    }

    @Override
    public String toString() {
        return tipo + " : " + valor;
    }
}
