package qdproject.quickdiag.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Flow;

@Service
public class ChatService {

    public String runScriptWithInput(String userInput) {
        ProcessBuilder processBuilder = new ProcessBuilder("node", "src/main/resources/static/js/aiAsk.js", userInput);
        processBuilder.redirectErrorStream(true);

        try {
            Process process = processBuilder.start();
            StringBuilder output = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }

            int exitVal = process.waitFor();
            if (exitVal == 0) {
                System.out.println("Success!");
                System.out.println(output);
                return output.toString();
            } else {
                // 오류 처리
                return "An error occurred";
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "An error occurred";
        }
    }

}
