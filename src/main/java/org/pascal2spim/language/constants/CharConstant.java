package org.pascal2spim.language.constants;

import org.pascal2spim.language.types.CharType;
import org.pascal2spim.mips32.GeneratedAssembly;
import org.pascal2spim.mips32.RegisterManager;

public class CharConstant extends Constant {
    private char value;

    public CharConstant() {
        super(new CharType());
    }

    public char getValue() {
        return value;
    }

    public void setValue(char val) {
        value = val;
    }

    public void storePascalValue(int line, int column, String value) {
        this.value = value.charAt(1);
    }

    public String toString() {
        return "" + value;
    }

    public void generateCode(GeneratedAssembly generatedAssembly, RegisterManager registerManager) {
        register = registerManager.getFreeRegister(generatedAssembly);
        generatedAssembly.addCodeLine("li " + register.getName() + ", '" + value + "'");
    }
}