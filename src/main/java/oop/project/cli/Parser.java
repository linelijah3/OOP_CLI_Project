package oop.project.cli;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    ArrayList<Token> tokenList;

    /**
     * Parses a string of arguments into the tokenList
     * Categorizes each part of the input string and filters them
     * as commands, subcommands, and arguments
     * Commands and subcommands that are caught are then used to create
     * their corresponding tokens
     * @param arguments the string containing the CLI input string
     * @return a list of the tokens stored in tokenList, such as the
     * commands, subcommands, etc
     * */
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

    /**
     * Determines the type of data being taken in by the value of the token passed
     * It classifies the token's value based on its traits such as single character
     * length, more than one character string, numeric strings, numeric strings with
     * a decimal point, and other formats that fall under String.
     * @param token object taken in to be classified
     * @return a string representing the token passed in, such as an integer, double,
     * or else return error for invalid formatting
     * */
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
