import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class FlashcardApp extends JFrame implements ActionListener {
   private JButton addButton, deleteButton, browseButton;
   private JTextArea textArea;
   private JFileChooser chooseFile;
   private File selectedFile;
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

        textArea = new JTextArea(20,40);
        JScrollPane scrollPane = new JScrollPane(textArea);


        add(panel,BorderLayout.NORTH);
        add(scrollPane);

        pack();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
if (e.getSource() == addButton){
    chooseFile = new JFileChooser();
    chooseFile.setFileSelectionMode(JFileChooser.FILES_ONLY);
    int result = chooseFile.showOpenDialog(null);

    if (result==JFileChooser.APPROVE_OPTION){
        selectedFile = chooseFile.getSelectedFile();
        textArea.append("Wybrany plik: " + selectedFile.getName() + "\n");
    }
}

    }
    public static void main(String[] args) {
        new FlashcardApp();
    }


}
