package msh;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import jnr.posix.POSIX;
import jnr.posix.POSIXFactory;

public class Minishell {
    private static POSIX posix = POSIXFactory.getNativePOSIX();

    public static void main(String[] args) {
        try (InputStreamReader r = new InputStreamReader(System.in);
             BufferedReader input = new BufferedReader(r)) {

            String line;
            while (true) {
                System.err.print("%% ");
                line = input.readLine();
                if (line == null) {
                    break;
                }
                processLine(line);
            }
        } catch (IOException e) {}
    }

    private static void processLine(String line) {
        System.err.printf("You typed: '%s'\n", line);
    }
}
