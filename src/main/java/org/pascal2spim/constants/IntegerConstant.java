package org.pascal2spim.constants;

import org.pascal2spim.GeneratedAssembly;
import org.pascal2spim.PascalSpim;
import org.pascal2spim.RegisterManager;
import org.pascal2spim.types.IntegerType;

public class IntegerConstant extends Constant {
    private int value = 0;

    public IntegerConstant() {
        super(new IntegerType());
    }

    public int getValue() {
        return value;
    }

    public void setValue(int val) {
        value = val;
    }

    public void storePascalValue(int line, int column, String value) {
        try {
            this.value = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            PascalSpim.printError(line, column, "number exceeds INTEGER range");
        }
    }

    public String toString() {
        return "" + value;
    }

    public void generateCode(GeneratedAssembly generatedAssembly, RegisterManager registerManager) {
        register = registerManager.getFreeRegister(generatedAssembly);
        generatedAssembly.addCodeLine("li " + register.getName() + ", " + value + "");
    }
}