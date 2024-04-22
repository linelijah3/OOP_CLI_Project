package oop.project.cli;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    ArrayList<Token> tokenList;
    List<Token> parseArguments(String arguments) {
        //split with whitespace
        String[] tempTokens = arguments.trim().split("\\s+");
        for (String s : tempTokens) {
            boolean namedArgument = false;
            if (s.charAt(0)=='-'&&s.charAt(1)=='-') {
                //named argument, for sub
                
            }
        }
        return tokenList;
    }
    Parser() {

    }
}
