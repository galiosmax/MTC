class Lexeme {

    private LexemeType lexemType;
    private String text;

    Lexeme(LexemeType lexemType, String text) {
        this.lexemType = lexemType;
        this.text = text;
    }

    LexemeType getLexemType() {
        return lexemType;
    }

    String getText() {
        return text;
    }
}
