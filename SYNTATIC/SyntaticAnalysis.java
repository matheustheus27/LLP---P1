package SYNTATIC;

import LEXICAL.LexicalAnalysis;
import LEXICAL.LexicalException;
import LEXICAL.TokenType;

import java.io.IOException;

import LEXICAL.Lexeme;

public class SyntaticAnalysis {
    private LexicalAnalysis lex;
    private Lexeme current;

    public SyntaticAnalysis(LexicalAnalysis lex) {
        this.lex = lex;
        this.current = lex.NextToken();
    }

    public void Start() throws LexicalException, IOException {
        ProcSelect();
        ProcFrom();
        
        if(current.type == TokenType.TK_WHERE){
            ProcWhere();
        }

        if(current.type == TokenType.TK_ORDER){
            ProcOrderBy();
        }

        ProcSemiColon();
        
        Eat(TokenType.TK_END_OF_FILE);
    }

    private void Advance() {
        current = lex.NextToken();
    }

    private void Eat(TokenType type) {
        if(type == current.type) {
            current = lex.NextToken();
        } else {
            DisplayError();
        }
    }

    private void DisplayError() {
        System.out.println("Não");
        System.exit(1);
    }

    //#region KEYWORDS
    private void ProcSelect() throws LexicalException, IOException {
        Eat(TokenType.TK_SELECT);
        
        ProcSelectParameters();
    }

    private void ProcSelectParameters() {
        boolean comma = true;

        if(current.type == TokenType.TK_ASTERISK) {
            ProcAsterisk();
        } else {
            while(comma) {
                ProcName();
                if(current.type == TokenType.TK_COMMA) {
                    ProcComma();
                } else {
                    comma = false;
                }
            }
        }
    }

    private void ProcFrom() {
        Eat(TokenType.TK_FROM);
    
        ProcName();
    }

    private void ProcWhere() {
        boolean andOr = true;
        Eat(TokenType.TK_WHERE);

        while(andOr) {
            ProcName();

            if(current.type == TokenType.TK_BETWEEN) {
                ProcBetween();
            } else if(current.type == TokenType.TK_IN){
                ProcIn();
            } else {
                ProcOperation();
            }

            if(current.type == TokenType.TK_AND) {
                ProcAnd();
            } else if(current.type == TokenType.TK_OR) {
                ProcOR();
            } else {
                andOr = false;
            }
        }
    }

    private void ProcAnd() {
        Eat(TokenType.TK_AND);
    }

    private void ProcOR() {
        Eat(TokenType.TK_OR);
    }

    private void ProcOperation() {
        switch(current.type) {
            case TK_EQUALS:
                Eat(TokenType.TK_EQUALS);
            break;
            case TK_NOT_EQUALS:
                Eat(TokenType.TK_NOT_EQUALS);
            break;
            case TK_LOWER:
                Eat(TokenType.TK_LOWER);
            break;
            case TK_GREATER:
                Eat(TokenType.TK_GREATER);
            break;
            case TK_LOWER_EQUAL:
                Eat(TokenType.TK_LOWER_EQUAL);
            break;
            case TK_GREATER_EQUAL:
                Eat(TokenType.TK_GREATER_EQUAL);
            break;
            default:
                DisplayError();
        }

        ProcValue();
    }

    private void ProcBetween() {
        Eat(TokenType.TK_BETWEEN);
        
        ProcValue();
        ProcAnd();
        ProcValue();
    }

    private void ProcIn() {
        Eat(TokenType.TK_IN);
        ProcOpenPar();

        while(current.type != TokenType.TK_CLOSE_PAR) {
            ProcValue();

            if(current.type == TokenType.TK_COMMA) {
                ProcComma();
            }
        }

        ProcClosePar();
    }

    private void ProcOrderBy() {
        Eat(TokenType.TK_ORDER);
        Eat(TokenType.TK_BY);

        ProcName();
        ProcOrderByParameters();
    }

    private void ProcOrderByParameters() {
        if(current.type == TokenType.TK_ASC) {
            Eat(TokenType.TK_ASC);
        } else if(current.type == TokenType.TK_DESC) {
            Eat(TokenType.TK_DESC);
        } else {
            DisplayError();
        }
    }
    //#endregion

    //#region SYMBOLS
    private void ProcSemiColon() {
        Eat(TokenType.TK_SEMI_COLON);
    }

    private void ProcComma() {
        while(current.type == TokenType.TK_COMMA) {
            Eat(TokenType.TK_COMMA);
        }
    }

    private void ProcOpenPar() {
        Eat(TokenType.TK_OPEN_PAR);
    }

    private void ProcClosePar() {
        Eat(TokenType.TK_CLOSE_PAR);
    }

    private void ProcAsterisk() {
        Eat(TokenType.TK_ASTERISK);
        
    }
    //#endregion

    //#region OTHERS
    private void ProcName() {
        Eat(TokenType.TK_NAME);
    }

    private void ProcValue() {
        if(current.type == TokenType.TK_NUMBER) {
            ProcNumber();
        } else if(current.type == TokenType.TK_TEXT) {
            ProcText();
        } else {
            DisplayError();
        }
    }

    private void ProcNumber() {
        Eat(TokenType.TK_NUMBER);
    }

    private void ProcText() {
        Eat(TokenType.TK_TEXT);
    }
    //#endregion
}
