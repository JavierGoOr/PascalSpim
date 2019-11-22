package org.pascal2spim.parser;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@RunWith(JUnitParamsRunner.class)
public class PascalParserTokenManagerTest {
    @Test
    @Parameters(method = "tokenImageWithKind")
    public void should_parse_correctly_token(String tokenImage, Integer expectedTokenKind) {
        // given
        PascalParserTokenManager tokenManager = tokenManagerFor(tokenImage);

        // when
        List<Token> allTokens = readAllTokensFrom(tokenManager);

        // then
        assertThat(allTokens).hasSize(1);
        Token actualToken = allTokens.get(0);

        assertThat(actualToken.kind).isEqualTo(expectedTokenKind);
    }

    private Object[] tokenImageWithKind() {
        return new Object[]{
                new Object[]{"program", PascalParserConstants.PROGRAM},
                new Object[]{"begin", PascalParserConstants.BEGIN},
                new Object[]{"end", PascalParserConstants.END},
                new Object[]{"if", PascalParserConstants.IF},
                new Object[]{"then", PascalParserConstants.THEN},
                new Object[]{"else", PascalParserConstants.ELSE},
                new Object[]{"while", PascalParserConstants.WHILE},
                new Object[]{"case", PascalParserConstants.CASE},
                new Object[]{"of", PascalParserConstants.OF},
                new Object[]{"do", PascalParserConstants.DO},
                new Object[]{"function", PascalParserConstants.FUNCTION},
                new Object[]{"procedure", PascalParserConstants.PROCEDURE},
                new Object[]{"var", PascalParserConstants.VAR},
                new Object[]{"const", PascalParserConstants.CONST},
                new Object[]{"type", PascalParserConstants.TYPE},
                new Object[]{"true", PascalParserConstants.TRUE},
                new Object[]{"false", PascalParserConstants.FALSE},
                new Object[]{";", PascalParserConstants.EOS},
                new Object[]{",", PascalParserConstants.COMMA},
                new Object[]{":", PascalParserConstants.COLON},
                new Object[]{"..", PascalParserConstants.DDOT},
                new Object[]{".", PascalParserConstants.DOT},
                new Object[]{"[", PascalParserConstants.OP_BRAC},
                new Object[]{"]", PascalParserConstants.CL_BRAC},
                new Object[]{"(", PascalParserConstants.OP_PAR},
                new Object[]{")", PascalParserConstants.CL_PAR},
                new Object[]{"+", PascalParserConstants.PLUS},
                new Object[]{"-", PascalParserConstants.MINUS},
                new Object[]{"*", PascalParserConstants.MULT},
                new Object[]{"div", PascalParserConstants.DIV},
                new Object[]{"mod", PascalParserConstants.MOD},
                new Object[]{"/", PascalParserConstants.DIVR},
                new Object[]{"and", PascalParserConstants.AND},
                new Object[]{"or", PascalParserConstants.OR},
                new Object[]{"not", PascalParserConstants.NOT},
                new Object[]{":=", PascalParserConstants.ASSIGN},
                new Object[]{"<", PascalParserConstants.LT},
                new Object[]{">", PascalParserConstants.GT},
                new Object[]{"=", PascalParserConstants.EQ},
                new Object[]{"<=", PascalParserConstants.LET},
                new Object[]{">=", PascalParserConstants.GET},
                new Object[]{"<>", PascalParserConstants.NE},
                new Object[]{"integer", PascalParserConstants.INTEGER},
                new Object[]{"real", PascalParserConstants.REAL},
                new Object[]{"boolean", PascalParserConstants.BOOLEAN},
                new Object[]{"char", PascalParserConstants.CHAR},
                new Object[]{"array", PascalParserConstants.ARRAY},
        };
    }

    private PascalParserTokenManager tokenManagerFor(String tokenImage) {
        Reader reader = new StringReader(tokenImage);
        SimpleCharStream charStream = new SimpleCharStream(reader, 1, 1);
        PascalParser parser = mock(PascalParser.class);
        return new PascalParserTokenManager(parser, charStream);
    }

    private List<Token> readAllTokensFrom(PascalParserTokenManager tokenManager) {
        List<Token> allTokens = new ArrayList<>();
        Token nextToken = tokenManager.getNextToken();
        while (nextToken.kind != PascalParserConstants.EOF) {
            allTokens.add(nextToken);
            nextToken = tokenManager.getNextToken();
        }
        return allTokens;
    }
}