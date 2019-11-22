package org.pascal2spim.language.constants;

import org.pascal2spim.language.types.CharType;
import org.pascal2spim.mips32.GeneratedAssembly;
import org.pascal2spim.mips32.RegisterManager;
import org.pascal2spim.parser.PascalParser;
import org.pascal2spim.parser.Token;

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

    public void storePascalValue(String value, Token token, PascalParser parser) {
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