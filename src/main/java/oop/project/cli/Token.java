package oop.project.cli;

public class Token {
    String _typeName = "";
    String _value = "";
    boolean commandName = false;

    String getTypeName() {
        return _typeName;
    }

    String getValue() {
        return _value;
    }
    boolean getCommandName() {
        return commandName;
    }
    Token(String typeName, String value) {
        _typeName = typeName;
        _value = value;
    }
    void setCommandName() {
        commandName = true;
    }
}
