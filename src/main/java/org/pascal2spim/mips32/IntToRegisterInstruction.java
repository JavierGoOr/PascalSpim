package org.pascal2spim.mips32;

public abstract class IntToRegisterInstruction {
    private InstructionType type;
    private Register destinationRegister;
    private int constantValue;

    public IntToRegisterInstruction(InstructionType type, Register destinationRegister, int constantValue) {
        this.type = type;
        this.destinationRegister = destinationRegister;
        this.constantValue = constantValue;
    }

    public String toAssembly() {
        return String.format("%s %s, %d", type.getCode(), destinationRegister.getName(), constantValue);
    }
}
