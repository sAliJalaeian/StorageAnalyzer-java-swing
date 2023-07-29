import javax.swing.*;
import java.awt.*;
import java.io.File;

public class FileStuff {
    private final JFileChooser file;

    public FileStuff() {
        file = new JFileChooser();
    }

    public File fileSelecting(Component frame, JLabel directorySize, JLabel selectedFolderLabel) {
        file.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int option = file.showOpenDialog(frame);
        if (option == JFileChooser.APPROVE_OPTION) {
            File selectedFolder = file.getSelectedFile();
            selectedFolderLabel.setText("Selected Folder: " + selectedFolder.getAbsolutePath());
            directorySize.setText("Total Size: " + formatSize(calculateFolderSize(selectedFolder)));
            return selectedFolder;
        }
        return null;
    }

    public long calculateFolderSize(File folder) {
        if (folder.isFile())
            return folder.length();
        long totalSize = 0;
        File[] files = folder.listFiles();
        if (files != null)
            for (File file : files)
                totalSize += calculateFolderSize(file);
        return totalSize;
    }

    public String formatSize(long size) {
        if (size < 1024)
            return size + " B";
        else if (size < 1024 * 1024) {
            double kbSize = size / 1024.0;
            return String.format("%.2f KB", kbSize);
        } else if (size < 1024 * 1024 * 1024) {
            double mbSize = size / (1024.0 * 1024.0);
            return String.format("%.2f MB", mbSize);
        } else {
            double gbSize = size / (1024.0 * 1024.0 * 1024.0);
            return String.format("%.2f GB", gbSize);
        }
    }
}
