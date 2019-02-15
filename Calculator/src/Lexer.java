import java.io.IOException;
import java.io.Reader;

class Lexer {

    private Reader reader;
    private int current;

    Lexer(Reader reader) {
        this.reader = reader;
        try {
            current = reader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Lexeme getLexeme() throws LexerException, IOException {
        while(Character.isWhitespace(current)) {
            current = reader.read();
        }
        switch (current) {
            case '+':
                current = reader.read();
                return new Lexeme(LexemeType.PLUS, "+");
            case '-':
                current = reader.read();
                return new Lexeme(LexemeType.MINUS, "-");
            case '/':
                current = reader.read();
                return new Lexeme(LexemeType.DIV, "/");
            case '*':
                current = reader.read();
                return new Lexeme(LexemeType.MULT, "*");
            case '^':
                current = reader.read();
                return new Lexeme(LexemeType.POW, "^");
            case '(':
                current = reader.read();
                return new Lexeme(LexemeType.OPEN, "(");
            case ')':
                current = reader.read();
                return new Lexeme(LexemeType.CLOSE, ")");
            case -1:
                return new Lexeme(LexemeType.EOF, null);
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                StringBuilder text = new StringBuilder();
                do {
                    try {
                        text.append(Character.getNumericValue(current));
                        current = reader.read();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } while (Character.isDigit(current));

                return new Lexeme(LexemeType.NUM, text.toString());
            default:
                throw new LexerException("Unknown type");
        }
    }
}
