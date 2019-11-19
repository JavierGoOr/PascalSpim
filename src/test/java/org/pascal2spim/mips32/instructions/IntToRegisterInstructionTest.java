package org.pascal2spim.mips32.instructions;

import org.junit.Test;
import org.pascal2spim.mips32.Register;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class IntToRegisterInstructionTest {
    private class IntToRegisterInstructionSubclass extends IntToRegisterInstruction {
        public IntToRegisterInstructionSubclass(InstructionType type, Register destinationRegister, int constantValue) {
            super(type, destinationRegister, constantValue);
        }
    }

    @Test
    public void toAssembly() {
        Register register = registerWithName("$v0");
        IntToRegisterInstruction instruction = new IntToRegisterInstructionSubclass(
                InstructionType.LOAD_IMMEDIATE,
                register,
                1);

        String actualAssembly = instruction.toAssembly();

        assertThat(actualAssembly).isEqualTo("li $v0, 1");
    }

    private Register registerWithName(String name) {
        Register register = mock(Register.class);
        when(register.getName()).thenReturn(name);
        return register;
    }
}