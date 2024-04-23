package oop.project.cli;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class ScenariosTests {

    @Nested
    class Add {

        @ParameterizedTest
        @MethodSource
        public void testAdd(String name, String command, Object expected) {
            test(command, expected);
        }

        public static Stream<Arguments> testAdd() {
            return Stream.of(
                Arguments.of("Add", "add 1 2", Map.of("left", 1, "right", 2)),
                Arguments.of("Missing Argument", "add 1", null),
                Arguments.of("Extraneous Argument", "add 1 2 3", null),
                Arguments.of("Not A Number", "add one two", null),
                Arguments.of("Not An Integer", "add 1.0 2.0", null),
                Arguments.of("Not An Integer", "add yippee wahoo", null),
                Arguments.of("empty", "add", null),
                Arguments.of("Overloaded add function", "add 1 2 3 4 5 6 7", null),
                Arguments.of("Addition overflow", "add 2147483647 2147483647" , null),
                Arguments.of("Addition underflow", "add -2147483647 -2147483647" , null)
                );
        }

    }

    @Nested
    class Div {

        @ParameterizedTest
        @MethodSource
        public void testSub(String name, String command, Object expected) {
            test(command, expected);
        }

        public static Stream<Arguments> testSub() {
            return Stream.of(
                Arguments.of("Sub", "sub --left 1.0 --right 2.0", Map.of("left", 1.0, "right", 2.0)),
                Arguments.of("Too many rights", "sub --left 1.0 --right 2.0 --right 1.0", null),
                Arguments.of("Too many lefts", "sub --left 1.0 --left 2.0 --right 1.0", null),
                Arguments.of("Left Only", "sub --left 1.0", null),
                Arguments.of("Right Only", "sub --right 2.0", Map.of("left", Optional.empty(), "right", 2.0)),
                Arguments.of("Missing Value", "sub --right", null),
                Arguments.of("Extraneous Argument", "sub --right 2.0 extraneous", null),
                Arguments.of("Misspelled Flag", "sub --write 2.0", null),
                Arguments.of("Not A Number", "sub --right two", null),
                Arguments.of("Overloaded sub function", "sub 1 2 3 4 5 6 7", null),
                Arguments.of("empty", "sub", null),
                Arguments.of("Incorrect format", "sub --left 1.0.0 --right -1.0" , null),
                Arguments.of("Subtraction overflow", "sub --left 2147483647 --right -2147483647" , null),
                Arguments.of("Subtraction underflow", "sub --left -2147483647 --right 2147483647" , null),
                Arguments.of("No named arguments", "sub 1.0 2.0", null)
                );
        }

    }

    @Nested
    class Sqrt {

        @ParameterizedTest
        @MethodSource
        public void testSqrt(String name, String command, Object expected) {
            test(command, expected);
        }

        public static Stream<Arguments> testSqrt() {
            return Stream.of(
                Arguments.of("Valid", "sqrt 4", Map.of("number", 4)),
                Arguments.of("Imperfect Square", "sqrt 3", Map.of("number", 3)),
                Arguments.of("Zero", "sqrt 0", Map.of("number", 0)),
                Arguments.of("Not an integer", "sqrt 1.0", null),
                Arguments.of("String", "sqrt hi", null),
                Arguments.of("Negative", "sqrt -1", null),
                Arguments.of("empty", "sqrt", null),
                Arguments.of("Additional arguments error", "sqrt 1 2", null)
            );
        }

    }

    @Nested
    class Calc {

        @ParameterizedTest
        @MethodSource
        public void testCalc(String name, String command, Object expected) {
            test(command, expected);
        }

        public static Stream<Arguments> testCalc() {
            return Stream.of(
                Arguments.of("Add", "calc add", Map.of("subcommand", "add")),
                Arguments.of("Sub", "calc sub", Map.of("subcommand", "sub")),
                Arguments.of("Sqrt", "calc sqrt", Map.of("subcommand", "sqrt")),
                Arguments.of("Number", "calc 1", null),
                Arguments.of("Too many arguments", "calc a a", null),
                Arguments.of("Character", "calc a", null),
                Arguments.of("Missing", "calc", null),
                Arguments.of("Invalid", "calc unknown", null)
            );
        }

    }

    @Nested
    class Date {

        @ParameterizedTest
        @MethodSource
        public void testDate(String name, String command, Object expected) {
            test(command, expected);
        }

        public static Stream<Arguments> testDate() {
            return Stream.of(
                Arguments.of("Date", "date 2024-01-01", Map.of("date", LocalDate.of(2024, 1, 1))),
                Arguments.of("Invalid", "date 20240401", null),
                Arguments.of("Too many arguments", "date 2024-01-01 2024-01-01", null),
                Arguments.of("Almost valid formatting", "date 2024-4-01", null),
                Arguments.of("Almost valid formatting 2", "date 2024-04-1", null),
                Arguments.of("Almost valid formatting 3", "date 224-04-01", null),
                Arguments.of("Valid formatting, invalid date", "date 2024-13-13", null)
            );
        }

    }

    @Nested
    class Help {

        @ParameterizedTest
        @MethodSource
        public void testHelp(String name, String command, Object expected) {
            test(command, expected);
        }

        public static Stream<Arguments> testHelp() {
            return Stream.of(
                    Arguments.of("Calc", "help calc", Map.of("calc", """
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
                """)),
                    Arguments.of("date", "help date", Map.of("date", """
                \n
                The date command takes in a date string formatted yyyy-mm-dd and turns it into a date object.
                The output is the date that you entered. If you provide no input, it returns today's date.
                \n
                """)),
                    Arguments.of("Too many arguments", "help date date", null),
                    Arguments.of("No arguments", "help", null)
            );
        }

    }

    private static void test(String command, Object expected) {
        if (expected != null) {
            var result = Scenarios.parse(command);
            Assertions.assertEquals(expected, result);
        } else {
            //TODO: Update with your specific Exception class or whatever other
            //error handling model you use to check for specific library issues.
            Assertions.assertThrows(Exception.class, () -> {
                Scenarios.parse(command);
            });
        }
    }

}
