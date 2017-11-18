package com.emarsys.mnemonic.miner;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularExpressionTokenizer implements Tokenizer {
    final private Pattern regex;

    public RegularExpressionTokenizer(Pattern regex) {
        this.regex = regex;
    }

    public RegularExpressionTokenizer() {
        this(Pattern.compile("[a-zA-Z0-9]{3,12}"));
    }

    @Override
    public List<String> tokenize(final String text) {
        List<String> tokens = new ArrayList<>();
        Matcher matcher = regex.matcher(text);
        while (matcher.find()) {
            tokens.add(matcher.group().toLowerCase());
        }
        return tokens;
    }
}
