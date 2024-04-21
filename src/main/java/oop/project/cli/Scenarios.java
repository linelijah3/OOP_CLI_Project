package oop.project.cli;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
            default -> throw new IllegalArgumentException("Unknown command.");
        };
    }

    private static List<Object> parseArguments(String arguments) {
        System.out.println("placeholder");
        String[] tokens = arguments.trim().split("//s+");
        // //s+ will get rid of white space in the arguments passed in
        List<Object> parsedArgs = new ArrayList<>();
        for (String token : tokens) {
            try {
                parsedArgs.add(Integer.parseInt(token));
            }
            catch (NumberFormatException e) {
                parsedArgs.add(token);
            }
        }
        return parsedArgs;
    }

    /**
     * Takes two positional arguments:
     *  - {@code left: <your integer type>}
     *  - {@code right: <your integer type>}
     */
    private static Map<String, Object> add(String arguments) {
        //TODO: Parse arguments and extract values.
        List<Object> parsedArgs = parseArguments(arguments);
        if (parsedArgs.size() != 2 || !(parsedArgs.get(0) instanceof Integer) || !(parsedArgs.get(1) instanceof Integer)) {
            throw new IllegalArgumentException("One of the arguments is not an integer\nThe function expects two integer input values");
        }

        // cast the strings to int types
        int left = (int) parsedArgs.get(0);
        int right = (int) parsedArgs.get(1);
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
        //TODO: Parse arguments and extract values.
        Optional<Double> left = Optional.empty();
        double right = 0.0;
        return Map.of("left", left, "right", right);
    }

    /**
     * Takes one positional argument:
     *  - {@code number: <your integer type>} where {@code number >= 0}
     */
    static Map<String, Object> sqrt(String arguments) {
        //TODO: Parse arguments and extract values.
        int number = 0;
        return Map.of("number", number);
    }

    /**
     * Takes one positional argument:
     *  - {@code subcommand: "add" | "div" | "sqrt" }, aka one of these values.
     *     - Note: Not all projects support subcommands, but if yours does you
     *       may want to take advantage of this scenario for that.
     */
    static Map<String, Object> calc(String arguments) {
        //TODO: Parse arguments and extract values.
        String subcommand = "";
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
        LocalDate date = LocalDate.EPOCH;
        return Map.of("date", date);
    }

    //TODO: Add your own scenarios based on your software design writeup. You
    //should have a couple from pain points at least, and likely some others
    //for notable features. This doesn't need to be exhaustive, but this is a
    //good place to test/showcase your functionality in context.

}
