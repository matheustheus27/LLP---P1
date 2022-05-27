package LEXICAL;

public enum TokenType {
    // SPECIALS
    TK_UNEXPECTED_EOF,
    TK_INVALID_TOKEN,
    TK_END_OF_FILE,

    // SYMBOLS
    TK_SEMI_COLON,    // ;
    TK_COMMA,         // ,
    TK_OPEN_PAR,      // (
    TK_CLOSE_PAR,     // )
    TK_ASTERISK,      // *

    // OPERATORS
    TK_LOWER,         // <
    TK_GREATER,       // >
    TK_LOWER_EQUAL,   // <=
    TK_GREATER_EQUAL, // >=
    TK_EQUALS,        // =
    TK_NOT_EQUALS,    // !=

    // KEYWORDS
    TK_SELECT,        // SELECT
    TK_FROM,          // FROM
    TK_WHERE,         // WHERE
    TK_AND,           // AND
    TK_OR,            // OR
    TK_BETWEEN,       // BETWEEN
    TK_IN,            // IN
    TK_ORDER,         // ORDER
    TK_BY,            // BY
    TK_ASC,           // ASC
    TK_DESC,          // DESC

    // OTHERS
    TK_NAME,          // identifier
    TK_NUMBER,        // integer
    TK_TEXT           // string
}
