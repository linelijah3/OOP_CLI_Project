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
            if (s.equals("date") || s.equals("help")) {
                currentCommand = s;
                tokenList.add(new Token("Command", s));
                lastFlag = null;
            } else if (s.equals("add") || s.equals("sub") || s.equals("sqrt")) {
                currentSubcommand = s;
                tokenList.add(new Token("Subcommand", s));
                lastFlag = null;
            } else if (s.startsWith("--")) {
                lastFlag = s;
            } else {
                if (lastFlag != null) {
                    Token argToken = new Token("filler", s);
                    //left, right
                    argToken.setCommandName(lastFlag);
                    argToken.setTypeName(parseType(argToken));
                    tokenList.add(argToken);
                    lastFlag = null;
                } else {
                    Token argToken = new Token("filler", s);
                    argToken.setTypeName(parseType(argToken));
                    tokenList.add(argToken);
                }
            }
        }
        return tokenList;
    }
    String parseType(Token token) {
        //1 char and not int -> char
        //multiple chars and not int or double -> string
        //int = int
        //one decimal point = double
        if (token._value.length()==1) {
            if (token._value.matches("[^1234567890]")) {
                return "Character";
            }
            return "Integer";
        } else if (token._value.length()>1) {
            if (token._value.contains(".")) {
                String subString = token._value.substring(token._value.indexOf(".")+1);
                if (subString.contains(".")) {
                    return "String";
                } else {
                    if (token._value.indexOf(".")!=token._value.length()-1) {
                        for (int i = 0; i < subString.length(); i++) {
                            if ((String.valueOf(subString.charAt(i))).matches("[^1234567890]")) {
                                return "String";
                            }
                        }
                        return "Double";
                    } else {
                        return "String";
                    }
                }
            } else {
                if (token._value.matches("-[123456789][1234567890]*")) {
                    return "Integer";
                }
                for (int i = 0; i < token._value.length(); i++) {
                    if ((String.valueOf(token._value.charAt(i))).matches("[^1234567890]")) {
                        return "String";
                    }
                }
                return "Integer";
            }
        }
        return "Error";
    }

    Parser() {
        tokenList = new ArrayList<>();
    }
}
