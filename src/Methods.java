import javax.swing.*;
import java.io.File;
import java.util.Arrays;

public class Methods {
    public static boolean isInt(String str){
        try {
            Integer.parseInt(str);
            return true;
        }catch (NumberFormatException e){
            e.printStackTrace();
            return false;
        }
    }
    //pobieranie zapisanych danych z resources
    public static void setTextArea(JTextArea textArea) {
        File folder = new File("src/resources/");
        File[] files = folder.listFiles();
        if (files == null) {
            textArea.setText("Folder pusty");
        } else {
            Arrays.sort(files);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < files.length; i++) {
                sb.append(i + 1).append(".").append(files[i].getName()).append("\n");
            }
            textArea.setText(sb.toString());
        }
    }
}
