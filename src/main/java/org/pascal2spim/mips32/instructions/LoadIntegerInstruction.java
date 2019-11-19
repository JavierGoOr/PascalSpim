package org.pascal2spim.mips32.instructions;

import org.pascal2spim.mips32.Register;

public class LoadIntegerInstruction extends IntToRegisterInstruction {
    public LoadIntegerInstruction(Register destinationRegister, int constantValue) {
        super(InstructionType.LOAD_IMMEDIATE, destinationRegister, constantValue);
    }
}
