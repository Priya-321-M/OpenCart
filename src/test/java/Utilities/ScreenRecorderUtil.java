package Utilities;

import java.io.IOException;

public class ScreenRecorderUtil {

    private static Process process;

    public static void startRecording(String fileName) {

        String path = System.getProperty("user.dir") + "/Videos/" + fileName + ".mp4";

        try {
            new java.io.File(System.getProperty("user.dir") + "/Videos/").mkdirs();

            String command = "cmd /c ffmpeg -y -f gdigrab -framerate 15 -i desktop \"" + path + "\"";

            process = Runtime.getRuntime().exec(command);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void stopRecording() {

        if (process != null) {
            process.destroy();
        }
    }
}