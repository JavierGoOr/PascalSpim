package org.pascal2spim.mips32.instructions;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LabeledInstructionTest {
    @Test
    public void should_add_label_to_instruction_assembly() {
        // given
        final String label = "label";
        final Instruction instruction = instructionWithAssembly("li $v0, 1");
        final LabeledInstruction labeledInstruction = new LabeledInstruction(label, instruction);

        // when
        String actualAssembly = labeledInstruction.toAssembly();

        // then
        assertThat(actualAssembly).isEqualTo("label: li $v0, 1");
    }

    private Instruction instructionWithAssembly(String assembly) {
        final Instruction instruction = mock(Instruction.class);
        when(instruction.toAssembly()).thenReturn(assembly);
        return instruction;
    }
}