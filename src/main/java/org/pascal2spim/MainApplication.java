package org.pascal2spim;

import org.pascal2spim.language.functions.Program;
import org.pascal2spim.mips32.GeneratedAssembly;
import org.pascal2spim.mips32.RegisterManager;
import org.pascal2spim.parser.PascalParser;
import org.pascal2spim.symboltable.SymbolTable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Paths;

public class MainApplication {
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    public static void launch(String[] args) throws Exception {
        String file_in = "", file_out = "";
        if (args.length != 2) {
            System.out.println("Error: incorrect number of parameters.");
            System.out.println("The way to execute this program is:");
            System.out.println("\"java PascalParser <file_in> <file_out>\"");
            return;
        } else {
            file_in = args[0];
            file_out = args[1];
        }
        PascalParser pascalParser = null;
        try {
            Reader in = new BufferedReader(new FileReader(file_in));
            pascalParser = new PascalParser(in);
        } catch (IOException e) {
            System.out.println("Error: file \"" + file_in + "\" cannot be read. Please check whether it exists.");
            return;
        }
        SymbolTable st = SymbolTable.getInstance();
        st.init();
        GeneratedAssembly generatedAssembly = new GeneratedAssembly();
        generatedAssembly.init();

        Program n = pascalParser.programStart();
        if (st.getError()) {
            System.out.println("Process finished with errors");
        } else {
            System.out.println("Process finished correctly.");
            n.generateCode(generatedAssembly, new RegisterManager());
            generatedAssembly.writeTo(Paths.get(file_out));
        }
    }
}
