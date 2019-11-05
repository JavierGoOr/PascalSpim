package org.pascal2spim.language.constants;

import org.pascal2spim.language.types.BooleanType;
import org.pascal2spim.mips32.GeneratedAssembly;
import org.pascal2spim.mips32.RegisterManager;

public class BooleanConstant extends Constant {
    private boolean value;

    public BooleanConstant() {
        super(new BooleanType());
    }

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean val) {
        value = val;
    }

    public void storePascalValue(int line, int column, String value) {
        this.value = value.compareToIgnoreCase("true") == 0;
    }

    public String toString() {
        if (value)
            return "true";
        else
            return "false";
    }

    public void generateCode(GeneratedAssembly generatedAssembly, RegisterManager registerManager) {
        int boolValue;
        register = registerManager.getFreeRegister(generatedAssembly);
        if (value)
            boolValue = 1;
        else
            boolValue = 0;
        generatedAssembly.addCodeLine("li " + register.getName() + ", " + boolValue);
    }
}