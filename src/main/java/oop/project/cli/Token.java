package oop.project.cli;

public class Token {
    String _typeName = "";
    String _value = "";
    String _commandName = "";

    String getTypeName() {
        return _typeName;
    }

    String getValue() {
        return _value;
    }
    String getCommandName() {
        return _commandName;
    }
    Token(String typeName, String value) {
        _typeName = typeName;
        _value = value;
    }
    void setCommandName(String commandName) {
        _commandName = commandName;
    }
}
