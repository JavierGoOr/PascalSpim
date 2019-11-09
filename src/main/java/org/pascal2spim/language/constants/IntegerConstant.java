package org.pascal2spim.language.constants;

import org.pascal2spim.language.types.IntegerType;
import org.pascal2spim.mips32.GeneratedAssembly;
import org.pascal2spim.mips32.RegisterManager;
import org.pascal2spim.parser.PascalParser;

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
            PascalParser.printError(line, column, "number exceeds INTEGER range");
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