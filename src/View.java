import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;

public class View {
    private JFrame frame;
    private JButton selectedFile;
    private JList<File> files;

    public View() {
        makeFrame();
    }

    private void makeFrame() {
        frame = new JFrame("Storage Analyzer");
        Container contentPane = frame.getContentPane();

        selectedFile = new JButton("Select File");
        JPanel panel = new JPanel();
        panel.add(selectedFile);
        contentPane.add(panel, BorderLayout.NORTH);

        frame.pack();
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void setActionListenerSelectButton(ActionListener listener) {
        selectedFile.addActionListener(listener);
    }

    public JFrame getFrame() {
        return frame;
    }

    public JList<File> getFiles() {
        return files;
    }

    public void setFiles(File[] listFiles) {
        files = new JList<>(listFiles);
    }
}
