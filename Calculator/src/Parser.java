import java.io.*;
import java.text.ParseException;

class Parser {

    private Lexeme current;
    private Reader reader;
    private Lexer lexer;

    Parser(File file) {
        try {
            this.reader = new FileReader(file);
            this.lexer = new Lexer(reader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    Parser(String text) {
        try {
            this.reader = new StringReader(text);
            this.lexer = new Lexer(reader);
            this.current = lexer.getLexeme();
        } catch (LexerException | IOException e) {
            e.printStackTrace();
        }
    }

    int calculate() {
        int temp = 0;
        try {
            temp = parseExpression();
            if (current.getLexemType() != LexemeType.EOF) {
                throw new ParseException("Wrong expression. Last calculated expression ", temp);
            }
        } catch (LexerException | IOException e) {
            System.out.println(e.getMessage());
        } catch (ParseException e) {
            System.out.println(e.getMessage() + e.getErrorOffset());
        }
        return temp;
    }

    private int parseExpression() throws LexerException, ParseException, IOException {
        int temp = parseTerm();

        while(current.getLexemType() == LexemeType.PLUS || current.getLexemType() == LexemeType.MINUS) {
            if (current.getLexemType() == LexemeType.PLUS) {
                current = lexer.getLexeme();
                temp += parseTerm();
            } else if (current.getLexemType() == LexemeType.MINUS){
                current = lexer.getLexeme();
                temp -= parseTerm();
            }
        }

        return temp;
    }

    private int parseTerm() throws LexerException, ParseException, IOException {
        int temp = parseFactor();

        while(current.getLexemType() == LexemeType.MULT || current.getLexemType() == LexemeType.DIV) {
            if (current.getLexemType() == LexemeType.MULT) {
                current = lexer.getLexeme();
                temp *= parseFactor();
            } else if (current.getLexemType() == LexemeType.DIV){
                current = lexer.getLexeme();
                temp /= parseFactor();
            }
        }

        return temp;
    }

    private int parseFactor() throws LexerException, ParseException, IOException {
        int temp = parsePower();

        if (current.getLexemType() == LexemeType.POW) {
            current = lexer.getLexeme();
            temp = (int) Math.pow(temp, parseFactor());
        }

        return temp;
    }

    private int parsePower() throws LexerException, ParseException, IOException {
        if (current.getLexemType() == LexemeType.MINUS) {
            current = lexer.getLexeme();
            return -(parseAtom());
        } else {
            return parseAtom();
        }
    }

    private int parseAtom() throws ParseException, LexerException, IOException {
        if (current.getLexemType() == LexemeType.NUM) {
            int temp = Integer.parseInt(current.getText());
            current = lexer.getLexeme();
            return temp;
        } else if (current.getLexemType() == LexemeType.OPEN) {
            current = lexer.getLexeme();
            int temp = parseExpression();
            if (current.getLexemType() != LexemeType.CLOSE) {
                throw new ParseException("Closing bracket wasn't found. Last calculated expression: ", temp);
            }
            current = lexer.getLexeme();
            return temp;
        } else {
            throw new ParseException("Wrong lexeme found ", 0);
        }
    }
}
