package oop.project.cli;

public class Token {
    String _typeName = "";
    String _value = "";

    String getTypeName() {
        return _typeName;
    }

    String getValue() {
        return _value;
    }
    Token(String typeName, String value) {
        _typeName = typeName;
        _value = value;
    }
}
