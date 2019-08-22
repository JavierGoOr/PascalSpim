public class Variable implements SymbolTableObject {
    private Type type;
    private boolean isParameter; //it is found in a parameterList nonterminal
    private boolean isOutput;  //it is a parameter which must be returned as output
    private boolean isLocal;
    private int displ; //if it is a parameter, displacement in the stack

    public Variable(Type type, boolean isParameter, boolean isOutput, boolean isLocal) {
        this.type = type;
        this.isParameter = isParameter;
        this.isOutput = isOutput;
        this.isLocal = isLocal;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type tp) {
        type = tp;
    }

    public boolean getIsLocal() {
        return isLocal;
    }

    public int getDisplacement() {
        return displ;
    }

    public void setDisplacement(int disp) {
        displ = disp;
    }

    public boolean getIsParameter() {
        return isParameter;
    }

    public void setIsParameter(boolean isParameter) {
        this.isParameter = isParameter;
    }

    public boolean getIsOutput() {
        return isOutput;
    }

    public void setIsOutput(boolean isOutput) {
        this.isOutput = isOutput;
    }
}
