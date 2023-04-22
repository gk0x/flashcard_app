import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class FlashcardApp extends JFrame implements ActionListener {
    private final JButton addButton;
    private final JButton deleteButton;
    private final JButton browseButton;
    private final JTextArea textArea;

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

        JPanel panel = new JPanel();
        panel.add(addButton);
        panel.add(deleteButton);
        panel.add(browseButton);

        //tworzenie textArea
        textArea = new JTextArea(20, 40);
        JScrollPane scrollPane = new JScrollPane(textArea);
        Methods.setTextArea(textArea);
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
        JFileChooser chooseFile;
        File selectedFile;
        if (e.getSource() == addButton) {
            chooseFile = new JFileChooser();
            chooseFile.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int result = chooseFile.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                selectedFile = chooseFile.getSelectedFile();
                String resourcesPath = "src/resources/";
                File newFile = new File(resourcesPath + selectedFile.getName());
                try {
                    Files.copy(selectedFile.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
            Methods.setTextArea(textArea);
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
        } else if (e.getSource() == deleteButton) {
            // Tworzenie okna dialogowego z pytaniem o numer pliku
            String fileNumber = JOptionPane.showInputDialog("Podaj numer pliku który chcesz usunąć");
            //usuwanie pliku o wybranym numerze
            File[] files = new File("src/resources/").listFiles();
            boolean isNumber;
            if (!Methods.isInt(fileNumber)) {
                JOptionPane.showMessageDialog(null, "Podaj liczbę!");
            } else {
                assert files != null;
                if ((Integer.parseInt(fileNumber) > files.length) || (Integer.parseInt(fileNumber) < 1)) {
                    JOptionPane.showMessageDialog(null, "Nie ma takiego numeru!");
                } else {
                    for (int i = 0; i <= files.length; i++) {
                        if ((i + 1) == Integer.parseInt(fileNumber)) {
                            files[i].delete();
                            JOptionPane.showMessageDialog(null, "Plik o numerze " + fileNumber + " został usunięty");
                            Methods.setTextArea(textArea);
                            break;
                        } else if ((i + 1) != Integer.parseInt(fileNumber)) {
                            continue;
                        } else JOptionPane.showMessageDialog(null, "nieprawidłowy numer pliku");
                    }
                }
            }
        }
    }

}

