package LEXICAL;

import java.io.FileInputStream;
import java.io.PushbackInputStream;

public class LexicalAnalysis implements AutoCloseable {
    private int line;
    private SymbolTable symbolTable;
    private PushbackInputStream input;

    public LexicalAnalysis(String filename) {
        try {
            input = new PushbackInputStream(new FileInputStream(filename), 2);
        } catch(Exception e) {
            throw new LexicalException("Unable to open file");
        }

        symbolTable = new SymbolTable();
        line = 1;
    }

    public void close() {
        try {
            input.close();
        } catch(Exception e) {
            throw new LexicalException("Unable to close file");
        }
    }

    public int GetLine() {
        return this.line;
    }

    private int Getc() {
        try {
            return input.read();
        } catch(Exception e) {
            throw new LexicalException("Unable to read file");
        }
    }

    private void Ungetc(int c) {
        if(c != -1) {
            try {
                input.unread(c);
            } catch(Exception e) {
                throw new LexicalException("Unable to ungetc");
            }
        }
    }

    public Lexeme NextToken() {
        Lexeme lex = new Lexeme("", TokenType.TK_END_OF_FILE);
        int state = 1;
        int beforeChar = 0;

        while(state != 7 && state != 8) {
            int c = Getc();

            switch(state) {
                case 1:
                    if(c == ' ' || c == '\t' || c == '\r') {
                        state = 1;
                    } else if(c == '\n') {
                        line++;
                        state = 1;
                    } else if(c == '*') {
                        lex.token += (char) c;
                        state = 7;
                    } else if(c == '(') {
                        lex.token += (char) c;
                        state = 7;
                    } else if(c == ')') {
                        lex.token += (char) c;
                        state = 7;
                    } else if(c == ',') {
                        lex.token += (char) c;
                        state = 7;
                    } else if(c == ';') {
                        lex.token += (char) c;
                        state = 7;
                    } else if(c == '_') {
                        lex.token += (char) c;
                        state = 2;
                    } else if(Character.isLetter(c)) {
                        lex.token += (char) c;
                        state = 9;
                    } else if(Character.isDigit(c)) {
                        lex.token += (char) c;
                        state = 3;
                    } else if(c == '=') {
                        lex.token += (char) c;
                        state = 4;
                    } else if(c == '!' || c == '<' || c == '>') {
                        beforeChar = c;
                        lex.token += (char) c;
                        state = 5;
                    } else if(c == 39) {
                        lex.token += (char) c;
                        state = 6;
                    } else if(c == -1) {
                        lex.token += (char) c;
                        lex.type = TokenType.TK_END_OF_FILE;
                        state = 8;
                    } else {
                        lex.token += (char) c;
                        lex.type = TokenType.TK_INVALID_TOKEN;
                        state = 8;
                    }
                break;
                case 2:
                    if(c == '_' || Character.isLetter(c) || Character.isDigit(c)) {
                        lex.token += (char) c;
                        state = 2;
                    } else {
                        Ungetc(c);
                        lex.type = TokenType.TK_NAME;
                        state = 8;
                    }
                break;
                case 9:
                    if(Character.isLetter(c)) {
                        lex.token += (char) c;
                        state = 9;
                    } else {
                        Ungetc(c);
                        state = 7;
                    }
                break;
                case 3:
                    if(Character.isDigit(c)) {
                        lex.token += (char) c;
                        state = 3;
                    } else {
                        Ungetc(c);
                        lex.type = TokenType.TK_NUMBER;
                        state = 8;
                    }
                break;
                case 4:
                    if(c != '=') {
                        Ungetc(c);    
                        state = 7;
                    } else {
                        lex.token += (char) c;
                        lex.type = TokenType.TK_INVALID_TOKEN;
                        state = 8; 
                    }
                break;
                case 5:
                    if(c == '=') {
                        lex.token += (char) c;
                        state = 7;
                    } else {
                        Ungetc(c);
                        state = 7;
                    }
                break;
                case 6:
                    if(c != 39) {
                        lex.token += (char) c;
                        state = 6;
                    }else {
                        lex.token += (char) c;
                        lex.type = TokenType.TK_TEXT;
                        state = 8;                        
                    }
                break;
                default:
                    throw new LexicalException("Unreachable");
            }
        }

        if(state == 7) {
            lex.type = symbolTable.Find(lex.token);
        }

        return lex;
    }
}
