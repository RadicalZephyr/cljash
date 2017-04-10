package msh;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Map;

import jnr.posix.POSIX;
import jnr.posix.POSIXFactory;
import jnr.posix.SpawnFileAction;

public class Minishell {
    private static POSIX posix;

    public static void main(String[] args) {
        posix = POSIXFactory.getNativePOSIX();

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
        ArrayList<String> envp = new ArrayList<String>();
        for (Map.Entry<String, String> entry : System.getenv().entrySet()) {
            envp.add(entry.getKey() + "=" + entry.getValue());
        }

        long childId = posix.posix_spawnp(line, new ArrayList<SpawnFileAction>(), Arrays.asList(line), envp);
        int[] status = new int[1];
        posix.wait(status);
    }
}
