package org.pascal2spim.language.constants;

import org.pascal2spim.language.types.RealType;
import org.pascal2spim.mips32.GeneratedAssembly;
import org.pascal2spim.mips32.RegisterManager;
import org.pascal2spim.parser.PascalParser;
import org.pascal2spim.parser.Token;

public class RealConstant extends Constant {
    private double value = 0;

    public RealConstant() {
        super(new RealType());
    }

    public double getValue() {
        return value;
    }

    public void setValue(double val) {
        value = val;
    }

    public void storePascalValue(String value, Token token, PascalParser parser) {
        try {
            this.value = Double.parseDouble(value);
        } catch (NumberFormatException e) {
            parser.printError(token.beginLine, token.beginColumn, "number exceeds REAL range");
        }
    }

    public String toString() {
        return "" + value;
    }

    public void generateCode(GeneratedAssembly generatedAssembly, RegisterManager registerManager) {
        register = registerManager.getFreeFloatRegister(generatedAssembly);
        int n = generatedAssembly.giveSequenceValueForConstants();
        generatedAssembly.addDataDefinition("\treal_ct" + n + ":\t.double " + this.toString().toLowerCase());
        generatedAssembly.addCodeLine("l.d " + register.getName() + ", real_ct" + n);
    }
}