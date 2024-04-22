package oop.project.cli;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    ArrayList<Token> tokenList;
    List<Token> parseArguments(String arguments) {
        String[] tempTokens = arguments.trim().split("\\s+");
        List<Token> tokenList = new ArrayList<>();
        String currentCommand = null;
        String currentSubcommand = null;
        String lastFlag = null;

        for (String s : tempTokens) {
            if (s.equals("calc") || s.equals("date") || s.equals("help")) {
                currentCommand = s;
                tokenList.add(new Token("Command", s));
                currentSubcommand = null;
                lastFlag = null;
            } else if (s.equals("add") || s.equals("sub") || s.equals("sqrt")) {
                if ("calc".equals(currentCommand)) {
                    currentSubcommand = s;
                    tokenList.add(new Token("Subcommand", s));
                    lastFlag = null;
                }
            } else if (s.startsWith("--")) {
                lastFlag = s;
            } else {
                if (lastFlag != null) {
                    Token argToken = new Token("Named Argument", s);
                    argToken.setCommandName(currentSubcommand + " " + lastFlag);
                    tokenList.add(argToken);
                    lastFlag = null;
                } else {
                    tokenList.add(new Token("Argument", s));
                }
            }
        }

        return tokenList;
    }

    Parser() {

    }
}
