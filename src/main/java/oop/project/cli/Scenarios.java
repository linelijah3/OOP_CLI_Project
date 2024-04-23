package oop.project.cli;

import java.time.LocalDate;
import java.util.*;

public class Scenarios {

    /**
     * Parses and returns the arguments of a command (one of the scenarios
     * below) into a Map of names to values. This method is provided as a
     * starting point that works for most groups, but depending on your command
     * structure and requirements you may need to make changes to adapt it to
     * your needs - use whatever is convenient for your design.
     */
    public static Map<String, Object> parse(String command) {
        //This assumes commands follow a similar structure to unix commands,
        //e.g. `command [arguments...]`. If your project uses a different
        //structure, e.g. Lisp syntax like `(command [arguments...])`, you may
        //need to adjust this a bit to work as expected.
        var split = command.split(" ", 2);
        var base = split[0];
        var arguments = split.length == 2 ? split[1] : "";
        return switch (base) {
            case "add" -> add(arguments);
            case "sub" -> sub(arguments);
            case "sqrt" -> sqrt(arguments);
            case "calc" -> calc(arguments);
            case "date" -> date(arguments);
            case "help" -> help(arguments);
            default -> throw new IllegalArgumentException("Unknown command.");
        };
    }

    /**
    * Parser helper function that gets rid of white space within the inputted strings
    * Uses regex in order to trim and split the arguments input
    * May have to modify later on for other functionality, however, works for simple
    * functions such as the add and subtract as of right now
    * */
    private static List<Token> parseArguments(String arguments) {
        Parser parser= new Parser();
        System.out.println("placeholder");
        return parser.parseArguments(arguments);
    }

    private static Double parseDouble(Object obj) {
        try {
            return Double.parseDouble(obj.toString());
        } catch(NumberFormatException e) {
            throw new IllegalArgumentException("invalid type, expected double, but got: " + obj);
        }
    }

    /**
     * Takes two positional arguments:
     *  - {@code left: <your integer type>}
     *  - {@code right: <your integer type>}
     *  - add 1 2
     *  - left is 1, right is 2
     */
    private static Map<String, Object> add(String arguments) {
        //TODO: Parse arguments and extract values.
        List<Token> parsedArgs = parseArguments(arguments);
        System.out.println("this is for the add function indices");
        System.out.println("this is the first arg index 0: " + parsedArgs.get(0));
        System.out.println("this is the first arg index 1: " + parsedArgs.get(1));

        if (parsedArgs.size() != 2) {
            throw new IllegalArgumentException("The add function expects two integer input values");
        }
        if (!Objects.equals(parsedArgs.get(0)._typeName, "Integer") || !Objects.equals(parsedArgs.get(1)._typeName, "Integer")) {
            throw new IllegalArgumentException("One of the arguments is not an integer");
        }
        if (Double.valueOf(parsedArgs.get(0)._value) + Double.valueOf(parsedArgs.get(1)._value) <-2147483648) {
            throw new IllegalArgumentException("End value cannot be less than -2147483648.");
        }
        if (Double.valueOf(parsedArgs.get(0)._value) + Double.valueOf(parsedArgs.get(1)._value) > 2147483647) {
            throw new IllegalArgumentException("End value cannot be greater than 2147483647.");
        }
        // cast the strings to int types
        int left = Integer.valueOf(parsedArgs.get(0)._value);
        int right = Integer.valueOf(parsedArgs.get(1)._value);
        return Map.of("left", left, "right", right);
    }

    /**
     * Takes two <em>named</em> arguments:
     *  - {@code left: <your decimal type>} (optional)
     *     - If your project supports default arguments, you could also parse
     *       this as a non-optional decimal value using a default of 0.0.
     *  - {@code right: <your decimal type>} (required)
     */
    static Map<String, Object> sub(String arguments) {
        List<Token> tokens = parseArguments(arguments);
        Map<String, Object> resultMap = new HashMap<>();
        Double left = 0.0;
        Double right = null;
        boolean leftExists = false, rightExists = false;
        int leftCount = 0, rightCount = 0, leftIndex = 0, rightIndex = 0;
        for (int i  =0; i < tokens.size(); i++) {
            if (tokens.get(i)._commandName.equals("--left")) {
                leftExists = true;
                leftCount++;
                leftIndex = i;
            } else if (tokens.get(i)._commandName.equals("--right")) {
                rightExists = true;
                rightCount++;
                rightIndex = i;
            } else {
                throw new IllegalArgumentException("Unknown arguments are not allowed.");
            }
        }

        if (!rightExists) {
            throw new IllegalArgumentException("Right operand is required for the sub command.");
        }

        if (leftCount>1||rightCount>1) {
            throw new IllegalArgumentException("Sub function expects at most one left argument and one right argument.");
        }
        if ((tokens.size()>2&&!leftExists)||(leftExists&&tokens.size()>3)) {
            throw new IllegalArgumentException("Too many arguments.");
        }
        if (leftExists) {
            left = Double.valueOf(tokens.get(leftIndex)._value);
        }
        right = Double.valueOf(tokens.get(rightIndex)._value);
        if (left - right <-2147483648) {
            throw new IllegalArgumentException("End value cannot be less than -2147483648.");
        }
        if (left - right > 2147483647) {
            throw new IllegalArgumentException("End value cannot be greater than 2147483647.");
        }
        // if left isn't given put optional empty
        if (!leftExists) {
            resultMap.putIfAbsent("left", Optional.empty());
        } else {
            resultMap.put("left", left);
        }
        // if right is given, add it
        resultMap.put("right", right);

        return resultMap;
    }

    /**
     * Takes one positional argument:
     *  - {@code number: <your integer type>} where {@code number >= 0}
     */
    static Map<String, Object> sqrt(String arguments) {
        //TODO: Parse arguments and extract values.
        List<Token> tokens = parseArguments(arguments);
        if (tokens.size() != 1) {
            throw new IllegalArgumentException("Sqrt requires 1 argument.");
        }
        if (!Objects.equals(tokens.getFirst()._typeName, "Integer")) {
            throw new IllegalArgumentException("Argument must be an integer.");
        }
        if (Integer.parseInt(tokens.getFirst()._value)<0) {
            throw new IllegalArgumentException("Argument must be 0 or larger.");
        }
        return Map.of("number", Integer.parseInt(tokens.getFirst()._value));
    }

    /**
     * Takes one positional argument:
     *  - {@code subcommand: "add" | "div" | "sqrt" }, aka one of these values.
     *     - Note: Not all projects support subcommands, but if yours does you
     *       may want to take advantage of this scenario for that.
     */
    static Map<String, Object> calc(String arguments) {
        //TODO: Parse arguments and extract values.
        List<Token> tokens = parseArguments(arguments);
        if (tokens.size()!= 1) {
            throw new IllegalArgumentException("Calc requires 1 argument.");
        }
        String subcommand=tokens.getFirst()._value;
        if (!Objects.equals(subcommand, "add")&&!Objects.equals(subcommand, "sub")&&!Objects.equals(subcommand, "div")&&!Objects.equals(subcommand, "sqrt")&&!Objects.equals(subcommand, "calc")&&!Objects.equals(subcommand, "date")&&!Objects.equals(subcommand, "help")){
            throw new IllegalArgumentException("Subcommand is invalid.");
        }
        return Map.of("subcommand", subcommand);
    }

    /**
     * Takes one positional argument:
     *  - {@code date: Date}, a custom type representing a {@code LocalDate}
     *    object (say at least yyyy-mm-dd, or whatever you prefer).
     *     - Note: Consider this a type that CANNOT be supported by your library
     *       out of the box and requires a custom type to be defined.
     */
    static Map<String, Object> date(String arguments) {
        //TODO: Parse arguments and extract values.
        List<Token> tokens = parseArguments(arguments);
        if (tokens.size()!=1) {
            throw new IllegalArgumentException("Date requires 1 argument.");
        }
        if (!Objects.equals(tokens.getFirst()._typeName, "String")) {
            throw new IllegalArgumentException("Argument is invalid.");
        }
        String value = tokens.getFirst()._value;
        if (!value.matches("[1234567890]{4}-((0[1-9])|1[0-2])-((0[1-9])|1[0-2])")) {
            throw new IllegalArgumentException("Date is invalid.");
        }
        LocalDate date = LocalDate.parse(value);
        return Map.of("date", date);
    }

    //TODO: Add your own scenarios based on your software design writeup. You
    //should have a couple from pain points at least, and likely some others
    //for notable features. This doesn't need to be exhaustive, but this is a
    //good place to test/showcase your functionality in context.

    static Map<String, Object> help(String input) {
        List<Token> tokens = parseArguments(input);
        if (tokens.size()==1) {
            if (Objects.equals(tokens.getFirst()._value, "calc")) {
                return Map.of("calc", """
                \n
                The calc command allows you to run basic math calculations in the CLI.
                Available operations (command):
                1. Addition (add)
                2. Subtraction (sub)
                3. Square Root (sqrt)
                
                Calc is used by entering (calc) followed by the operation, then the input.
                For example: calc add 1 2 returns 3
                calc sqrt 4 returns 2
                calc sub 4 3 returns 1.
                \n
                """);
            } else if (Objects.equals(tokens.getFirst()._value, "date")) {
                return Map.of("date", """
                \n
                The date command takes in a date string formatted yyyy-mm-dd and turns it into a date object.
                The output is the date that you entered. If you provide no input, it returns today's date.
                \n
                """);
            } else {
                throw new IllegalArgumentException("Subcommand is invalid.");
            }
        } else {
            throw new IllegalArgumentException("Calc requires 1 argument.");
        }
    }

}
