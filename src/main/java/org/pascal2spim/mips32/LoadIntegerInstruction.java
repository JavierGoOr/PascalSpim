package org.pascal2spim.mips32;

public class LoadIntegerInstruction extends IntToRegisterInstruction {
    public LoadIntegerInstruction(Register destinationRegister, int constantValue) {
        super(InstructionType.LOAD_IMMEDIATE, destinationRegister, constantValue);
    }
}
