package org.pascal2spim.language.constants;

import org.pascal2spim.language.types.StringType;
import org.pascal2spim.mips32.GeneratedAssembly;
import org.pascal2spim.mips32.RegisterManager;

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

    public void generateCode(GeneratedAssembly generatedAssembly, RegisterManager registerManager) {
    }
}