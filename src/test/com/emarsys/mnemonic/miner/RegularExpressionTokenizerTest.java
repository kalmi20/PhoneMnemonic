package com.emarsys.mnemonic.miner;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RegularExpressionTokenizerTest {

    Tokenizer regularExpressionTokenizer;

    @Before
    public void setup() {
        regularExpressionTokenizer = new RegularExpressionTokenizer();
    }

    @Test
    public void shouldFilterOutShortStrings() throws Exception {
        List<String> tokens = regularExpressionTokenizer.tokenize("ad re as as be soo");
        assertEquals(1, tokens.size());
        assertTrue(tokens.contains("soo"));
    }

    @Test
    public void shouldTransFormToLowerCase() {
        List<String> tokens;
        tokens = regularExpressionTokenizer.tokenize("ad re FROM as as be somEthinG");
        assertEquals(2, tokens.size());
        assertTrue(tokens.contains("from"));
        assertTrue(tokens.contains("something"));
    }

    @Test
    public void shouldFilterOutLongStrings() {
        List<String> tokens = regularExpressionTokenizer.tokenize("ThisIsAVeryLongStringWhichShouldBeRemoved");
        assertEquals(0, tokens.size());
    }

    @Test
    public void shouldDiscardSpecialCharacters() {
        List<String> tokens = regularExpressionTokenizer.tokenize("foo. bar, barfoo");
        assertEquals(3, tokens.size());
    }

}