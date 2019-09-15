package org.pascal2spim.acceptance;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import mars.MarsLaunch;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pascal2spim.PascalSpim;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Permission;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class AcceptanceTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ClassLoader classloader = getClass().getClassLoader();

    @Test
    @Parameters({"fibonacci", "factorial", "variables", "switch_case_int", "switch_case_real"})
    public void should_print_the_expected_outputs_when_executing_assembly_output
            (final String programName) throws Exception {

        //given
        File outputAssemblyFile = getOutputAssemblyFile();
        File pascalFile = getPascalFile(programName);
        String expectedOutput = getExpectedOutput(programName);

        //when
        compile(pascalFile, outputAssemblyFile);
        String actualOutput = executeAssembly(outputAssemblyFile);

        //then
        assertThat(actualOutput).isEqualToIgnoringNewLines(expectedOutput);
    }

    private File getPascalFile(final String programName) throws URISyntaxException {
        URL resource = classloader.getResource("acceptancetests/input/" + programName + ".pas");
        return Paths.get(resource.toURI()).toFile();
    }

    private String getExpectedOutput(final String programName) throws IOException, URISyntaxException {
        URL urlForExpectedOutput = classloader.getResource("acceptancetests/output/" + programName + ".out");
        Path pathForExpectedOutput = Paths.get(urlForExpectedOutput.toURI());
        return readFile(pathForExpectedOutput);
    }

    private File getOutputAssemblyFile() throws IOException {
        File outputAssemblyFile = File.createTempFile("pascal2spim-", ".s");
        outputAssemblyFile.deleteOnExit();
        return outputAssemblyFile;
    }

    private void compile(File pascalFile, File outputAssemblyFile) throws Exception {
        PascalSpim.launch(new String[]{pascalFile.getAbsolutePath(), outputAssemblyFile.getAbsolutePath()});
    }

    private String readFile(Path pathToFile) throws IOException {
        Stream<String> lines = Files.lines(pathToFile);
        return lines.collect(Collectors.joining("\n"));
    }

    private String executeAssembly(File outputAssemblyFile) {
        changeSystemDefaultsForMARS();
        executeWithMARS(outputAssemblyFile);
        restoreSystemDefaults();
        return getMARSExecutionOutput();
    }

    private void executeWithMARS(File outputAssemblyFile) {
        try {
            new MarsLaunch(new String[]{outputAssemblyFile.getAbsolutePath()});
        } catch (SecurityException e) {
            // do nothing
        }
    }

    private String getMARSExecutionOutput() {
        String completeOutput = outContent.toString();
        int startExecutionOutput = completeOutput.indexOf("\r\n") + 1;
        int endExecutionOutput = completeOutput.lastIndexOf("\r\n");

        return completeOutput.substring(startExecutionOutput, endExecutionOutput);
    }

    private void changeSystemDefaultsForMARS() {
        System.setOut(new PrintStream(outContent));
        System.setSecurityManager(new IgnoreExitManager());
    }

    private void restoreSystemDefaults() {
        System.setOut(System.out);
        System.setSecurityManager(null);
    }

    static class IgnoreExitManager extends SecurityManager {
        @Override
        public void checkExit(int status) {
            throw new SecurityException();
        }

        @Override
        public void checkPermission(Permission perm) {
            // Allow other activities by default
        }
    }
}
