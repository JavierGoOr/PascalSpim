package org.pascal2spim.language.constants;

import org.pascal2spim.language.types.BooleanType;
import org.pascal2spim.mips32.GeneratedAssembly;
import org.pascal2spim.mips32.RegisterManager;
import org.pascal2spim.mips32.instructions.LoadIntegerInstruction;
import org.pascal2spim.parser.PascalParser;
import org.pascal2spim.parser.Token;

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

    public void storePascalValue(String value, Token token, PascalParser parser) {
        this.value = value.compareToIgnoreCase("true") == 0;
    }

    public String toString() {
        return String.valueOf(value);
    }

    public void generateCode(GeneratedAssembly generatedAssembly, RegisterManager registerManager) {
        register = registerManager.getFreeRegister(generatedAssembly);

        LoadIntegerInstruction instruction = new LoadIntegerInstruction(register, getIntegerValue());
        generatedAssembly.addCodeLine(instruction.toAssembly());
    }

    private int getIntegerValue() {
        if (value) {
            return 1;
        }
        return 0;
    }
}