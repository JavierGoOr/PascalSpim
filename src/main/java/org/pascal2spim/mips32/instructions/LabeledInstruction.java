package org.pascal2spim.mips32.instructions;

public class LabeledInstruction implements Instruction {
    private String label;
    private Instruction instruction;

    public LabeledInstruction(String label, Instruction instruction) {
        this.label = label;
        this.instruction = instruction;
    }

    @Override
    public String toAssembly() {
        return label + ": " + instruction.toAssembly();
    }
}
