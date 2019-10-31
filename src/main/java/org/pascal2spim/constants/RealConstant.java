package org.pascal2spim.constants;

import org.pascal2spim.GeneratedAssembly;
import org.pascal2spim.PascalSpim;
import org.pascal2spim.RegisterManager;
import org.pascal2spim.types.RealType;

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

    public void storePascalValue(int line, int column, String value) {
        try {
            this.value = Double.parseDouble(value);
        } catch (NumberFormatException e) {
            PascalSpim.printError(line, column, "number exceeds REAL range");
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