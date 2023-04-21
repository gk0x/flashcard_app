import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

public class FlashcardApp extends JFrame implements ActionListener {
    private JButton addButton, deleteButton, browseButton, saveButton;
    private JTextArea textArea;
    private JFileChooser chooseFile;
    private File selectedFile;

    //implementacja metod WindowListener(konieczne zaimplementowanie wszystkich metod). Po zamknieciu aplikacji dane
//będą automatycznie zapisywane

    public FlashcardApp() throws HeadlessException {
        super("FlashcardApp");
        setDefaultCloseOperation(EXIT_ON_CLOSE);



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

        //tworzenie textArea
        textArea = new JTextArea(20, 40);
        JScrollPane scrollPane = new JScrollPane(textArea);
        setTextArea(textArea);
        // Dodajemy obiekt nasłuchujący zdarzeń MouseListener do JTextArea:
        textArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });


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
            } setTextArea(textArea);
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
        } else if (e.getSource()==deleteButton) {
            // Tworzenie okna dialogowego z pytaniem o numer pliku
            String input = JOptionPane.showInputDialog("Podaj numer pliku który chcesz usunąć");
            String fileNumber = input;
            //usuwanie pliku o wybranym numerze
            File[]files = new File("src/resources/").listFiles();
            
            for (int i = 0; i<=files.length; i++){
                if ((i+1)==Integer.parseInt(fileNumber)){
                    files[i].delete();
                    JOptionPane.showMessageDialog(null,"Plik o numerze " +fileNumber + " został usunięty");
                    setTextArea(textArea);
                    break;
                } else if ((i+1)!=Integer.parseInt(fileNumber)) { //continue
                } else if ((i+1)>Integer.parseInt(fileNumber)||(i+1)<Integer.parseInt(fileNumber)) {
                    JOptionPane.showMessageDialog(null,"nieprawidłowy numer pliku");
            }else JOptionPane.showMessageDialog(null,"nieprawidłowy numer pliku");
            }
        }
    }

    //pobieranie zapisanych danych z resources
    public void setTextArea(JTextArea textArea) {
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

