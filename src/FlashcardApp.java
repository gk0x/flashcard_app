import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

public class FlashcardApp extends JFrame implements ActionListener, WindowListener {
    private JButton addButton, deleteButton, browseButton, saveButton;
    private JTextArea textArea;
    private JFileChooser chooseFile;
    private File selectedFile;

    //implementacja metod WindowListener(konieczne zaimplementowanie wszystkich metod). Po zamknieciu aplikacji dane
//będą automatycznie zapisywane
    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        try {
            FileWriter writer = new FileWriter("textarea.txt");
            writer.write(textArea.getText());
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    public FlashcardApp() throws HeadlessException {
        super("FlashcardApp");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        addWindowListener(this);


        addButton = new JButton("Dodaj plik");
        addButton.addActionListener(this);
        deleteButton = new JButton("Usuń plik");
        deleteButton.addActionListener(this);
        browseButton = new JButton("Przeglądaj pliki");
        browseButton.addActionListener(this);
        saveButton = new JButton("Zapisz");
        saveButton.addActionListener(this);

        JPanel panel = new JPanel();
        panel.add(addButton);
        panel.add(deleteButton);
        panel.add(browseButton);
        panel.add(saveButton);

        //tworzenie textArea i pobieranie zapisanych danych z textarea.txt
        textArea = new JTextArea(20, 40);
        JScrollPane scrollPane = new JScrollPane(textArea);
        //pobieranie danych z textarea.txt
        try {
            FileReader fileReader = new FileReader("textarea.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                textArea.append(line + "\n");
            }
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        sort(textArea);


        add(panel, BorderLayout.NORTH);
        add(scrollPane);

        pack();
        setVisible(true);
    }

    //obsluga przycisków
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            chooseFile = new JFileChooser();
            chooseFile.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int result = chooseFile.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                selectedFile = chooseFile.getSelectedFile();
                String filePath = selectedFile.getAbsolutePath();
                String resourcesPath = "src/resources/";
                File newFile = new File(resourcesPath + selectedFile.getName());
                try {
                    Files.copy(selectedFile.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
                textArea.append(selectedFile.getName() + "\n");
            }
        } else if (e.getSource() == browseButton) {
            chooseFile = new JFileChooser("C:/PROGRAMOWANIE/JAVA/java_flashcards/src/resources");
            int result = chooseFile.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                selectedFile = chooseFile.getSelectedFile();
                try {
                    Desktop.getDesktop().open(selectedFile);

                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        } else if (e.getSource() == saveButton) {
            try {
                FileWriter fileWriter = new FileWriter("textarea.txt");
                fileWriter.write(textArea.getText());
                fileWriter.close();

            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }
    public void sort(JTextArea textArea){
        String[]lines = textArea.getText().split("\\n");
        Arrays.sort(lines);
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<lines.length;i++){
            sb.append((i+1)+"."+lines[i]+"\n");
        }
        textArea.setText(sb.toString());
    }
}


