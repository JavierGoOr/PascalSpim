package org.pascal2spim;

import org.pascal2spim.types.StringType;

class StringConstant extends Constant {
    private String value;

    public StringConstant() {
        super(new StringType());
    }

    public String getValue() {
        return value;
    }

    public void setValue(String val) {
        value = val;
    }

    public void storePascalValue(int line, int column, String value) {
        this.value = value.substring(1, value.length() - 1);
    }

    public String toString() {
        return value;
    }

    public void generateCode() {
    }
}