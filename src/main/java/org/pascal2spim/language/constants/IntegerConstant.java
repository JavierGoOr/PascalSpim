package org.pascal2spim.language.constants;

import org.pascal2spim.language.types.IntegerType;
import org.pascal2spim.mips32.GeneratedAssembly;
import org.pascal2spim.mips32.RegisterManager;
import org.pascal2spim.parser.PascalParser;
import org.pascal2spim.parser.Token;

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

    public void storePascalValue(String value, Token token, PascalParser parser) {
        try {
            this.value = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            parser.printError(token.beginLine, token.beginColumn, "number exceeds INTEGER range");
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