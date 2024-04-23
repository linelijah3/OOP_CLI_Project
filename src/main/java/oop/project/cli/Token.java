package oop.project.cli;

public class Token {
    String _typeName = "";
    String _value = "";
    String _commandName = "";

    Token(String typeName, String value) {
        _typeName = typeName;
        _value = value;
    }

    void setCommandName(String commandName) {
        _commandName = commandName;
    }
    void setTypeName(String typeName) {
        _typeName = typeName;
    }
}
