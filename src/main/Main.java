
package main;

import lexer.Lexer;
import lexer.Token;
import java.util.List;

import parser.Parser;

public class Main {
    public static void main(String[] args) {

        String codigo = """
                entero edad = 20;
                texto cultivo = "Jitomate";
                decimal humedad = 25;
                """;

        Lexer lexer = new Lexer(codigo);

        List<Token> tokens = lexer.analizar();

        Parser parser = new Parser(tokens);
        parser.analizar();

        System.out.println("=== ANALISIS LEXICO DE MILPA ===");

        for (Token token : tokens) {
            System.out.println(token);
        }
    }
}
