import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

class LexerTest {

    @Test
    void getLexeme() throws LexerException, IOException {
        String[] testStrings = {"+", "-", "*", "/", "^", "(", ")", "111111", "          +"};
        Reader[] readers = new Reader[testStrings.length];
        Lexer[] lexers = new Lexer[testStrings.length];
        Lexeme[] expectedLexemes = {new Lexeme(LexemeType.PLUS, "+"), new Lexeme(LexemeType.MINUS, "-"), new Lexeme(LexemeType.MULT, "*"),
                new Lexeme(LexemeType.DIV, "/"), new Lexeme(LexemeType.POW, "^"), new Lexeme(LexemeType.OPEN, "("),
                new Lexeme(LexemeType.CLOSE, ")"), new Lexeme(LexemeType.NUM, "111111"), new Lexeme(LexemeType.PLUS, "+")};
        Lexeme[] actualLexemes = new Lexeme[testStrings.length];

        for (int i = 0; i < testStrings.length; i++) {
            readers[i] = new StringReader(testStrings[i]);
            lexers[i] = new Lexer(readers[i]);
            actualLexemes[i] = lexers[i].getLexeme();
            assertEquals(expectedLexemes[i].getLexemType(), actualLexemes[i].getLexemType());
        }
    }
}