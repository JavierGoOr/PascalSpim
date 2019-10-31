package org.pascal2spim;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GeneratedAssembly {
    private int nextSequenceValue;
    private int nextSequenceValueForConstants;
    private List<String> dataDefinitions;
    private List<String> codeLines;
    private String label;

    public GeneratedAssembly() {
        init();
    }

    public void init() {
        nextSequenceValue = 0;
        nextSequenceValueForConstants = 0;
        dataDefinitions = new ArrayList<>();
        codeLines = new ArrayList<>();
        label = null;
    }

    public int giveSequenceValue() {
        return nextSequenceValue++;
    }

    public int giveSequenceValueForConstants() {
        return nextSequenceValueForConstants++;
    }

    public void addDataDefinition(String newDataDefinition) {
        dataDefinitions.add(newDataDefinition);
    }

    public void addCodeLine(String newCodeLine) {
        String attachedLabel = "";
        if (label != null) {
            attachedLabel = label + ":";
        }

        codeLines.add(attachedLabel + "\t" + newCodeLine);
        label = null;
    }

    public void addLabel(String newLabel) {
        if (label != null)
            codeLines.add(label + ":");
        label = newLabel;
    }

    public void writeTo(Path path) {
        try (PrintWriter printWriter = new PrintWriter(Files.newBufferedWriter(path))) {
            writeTo(printWriter);
        } catch (IOException e) {
            System.err.println("Error: file \"" + path.toString() + "\" could not be written.");
        }
    }

    private void writeTo(PrintWriter writer) {
        if (!dataDefinitions.isEmpty()) {
            writer.println(".data");
            dataDefinitions.forEach(writer::println);
        }

        writer.println(".text");
        writer.println("\t.globl _main_");
        writer.print("_main_: ");

        codeLines.forEach(writer::println);
    }

}