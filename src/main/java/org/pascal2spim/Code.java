package org.pascal2spim;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Code {
    private int n;
    private int constN;
    private List<String> dataSegm;
    private List<String> sentences;
    private String label;

    public Code() {
        init();
    }

    public void init() {
        n = 0;
        constN = 0;
        dataSegm = new ArrayList<>();
        sentences = new ArrayList<>();
        label = null;
    }

    public int getConsecutive() {
        int result = n;
        n++;
        return result;
    }

    public int getConsecutiveForConst() {
        int result = constN;
        constN++;
        return result;
    }

    public void addDataSentence(String sentence) {
        dataSegm.add(sentence);
    }

    public void addSentence(String sentence) {
        if (label == null)
            sentences.add("\t" + sentence);
        else {
            sentences.add(label + ":\t" + sentence);
            label = null;
        }
    }

    public void addLabel(String lab) {
        if (label != null)
            sentences.add(label + ":");
        label = lab;
    }

    public void writeTo(Path path) {
        try (PrintWriter printWriter = new PrintWriter(Files.newBufferedWriter(path))) {
            writeTo(printWriter);
        } catch (IOException e) {
            System.err.println("Error: file \"" + path.toString() + "\" could not be written.");
        }
    }

    private void writeTo(PrintWriter writer) {
        if (!dataSegm.isEmpty()) {
            writer.println(".data");
            dataSegm.forEach(writer::println);
        }

        writer.println(".text");
        writer.println("\t.globl _main_");
        writer.print("_main_: ");

        sentences.forEach(writer::println);
    }

}