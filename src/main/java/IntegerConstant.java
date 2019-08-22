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

    public void generateCode() {
        RegisterManager rm = RegisterManager.getInstance();
        Code code = Code.getInstance();
        register = rm.getFreeRegister();
        code.addSentence("li " + register.getName() + ", " + value + "");
    }
}