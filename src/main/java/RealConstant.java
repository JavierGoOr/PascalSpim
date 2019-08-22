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

    public void generateCode() {
        RegisterManager rm = RegisterManager.getInstance();
        Code code = Code.getInstance();
        register = rm.getFreeFloatRegister();
        int n = code.getConsecutiveForConst();
        code.addDataSentence("\treal_ct" + n + ":\t.double " + this.toString().toLowerCase());
        code.addSentence("l.d " + register.getName() + ", real_ct" + n);
    }
}