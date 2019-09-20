package org.pascal2spim.constants;

import org.pascal2spim.Code;
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

    public void generateCode(Code code, RegisterManager registerManager) {
        register = registerManager.getFreeFloatRegister(code);
        int n = code.getConsecutiveForConst();
        code.addDataSentence("\treal_ct" + n + ":\t.double " + this.toString().toLowerCase());
        code.addSentence("l.d " + register.getName() + ", real_ct" + n);
    }
}