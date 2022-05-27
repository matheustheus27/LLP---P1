package LEXICAL;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
    private Map <String, TokenType> symbolTable;

    public SymbolTable() {
        symbolTable = new HashMap<String, TokenType>();
       
        // SYMBOLS
        symbolTable.put(";", TokenType.TK_SEMI_COLON);
        symbolTable.put(",", TokenType.TK_COMMA);
        symbolTable.put("(", TokenType.TK_OPEN_PAR);
        symbolTable.put("(", TokenType.TK_CLOSE_PAR);
        symbolTable.put("*", TokenType.TK_ASTERISK);

        // OPERATORS
        symbolTable.put("<", TokenType.TK_LOWER);
        symbolTable.put(">", TokenType.TK_GREATER);
        symbolTable.put("<=", TokenType.TK_LOWER_EQUAL);
        symbolTable.put(">=", TokenType.TK_GREATER_EQUAL);
        symbolTable.put("=", TokenType.TK_EQUALS);
        symbolTable.put("!=", TokenType.TK_NOT_EQUALS);

        // KEYWORDS
        symbolTable.put("SELECT", TokenType.TK_SELECT);
        symbolTable.put("FROM", TokenType.TK_FROM);
        symbolTable.put("WHERE", TokenType.TK_WHERE);
        symbolTable.put("AND", TokenType.TK_AND);
        symbolTable.put("OR", TokenType.TK_OR);
        symbolTable.put("BETWEEN", TokenType.TK_BETWEEN);
        symbolTable.put("IN", TokenType.TK_IN);
        symbolTable.put("ORDER", TokenType.TK_ORDER);
        symbolTable.put("BY", TokenType.TK_BY);
        symbolTable.put("ASC", TokenType.TK_ASC);
        symbolTable.put("DESC", TokenType.TK_DESC);
    }

    public boolean Contains(String token) {
        return symbolTable.containsKey(token);
    }

    public TokenType Find(String token) {
        return this.Contains(token) ? symbolTable.get(token) : TokenType.TK_NAME;
    }
}
