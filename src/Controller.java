import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;

public class Controller {
    private View view;
    private FileStuff file;

    public Controller() {
        view = new View();
        file = new FileStuff();

        ActionListener selectedFile = ev -> selectFile();

        view.setActionListenerSelectButton(selectedFile);
    }

    private void selectFile() {
        fileSelected();
    }

    private void fileSelected() {
        JLabel directorySize = new JLabel("Total");
        JLabel selectedFolderLabel = new JLabel("File");

        JPanel panel = new JPanel();
        panel.add(selectedFolderLabel);
        panel.add(directorySize);

        Container content = view.getFrame().getContentPane();
        content.add(panel, BorderLayout.SOUTH);

        File folder = file.fileSelecting(view.getFrame(), directorySize, selectedFolderLabel);
        view.getFrame().setSize(1000, 750);
        drawPieChart(content, folder, directorySize, selectedFolderLabel);
    }

    private void drawPieChart(Container content, File folder, JLabel directorySize, JLabel selectedFolderLabel) {
        //remove a JList and ChartPanel of the oldest one
        int index = -1, indexFiles = -1;
        for (int i = 0; i < content.getComponents().length; i++)
            if (content.getComponents()[i] instanceof ChartPanel)
                index = i;
            else if (content.getComponents()[i] instanceof JScrollPane)
                indexFiles = i;
        if (index != -1)
            content.remove(index);
        if (indexFiles != -1)
            content.remove(indexFiles);
        //define dataset for chart pie
        DefaultPieDataset dataset = new DefaultPieDataset();
        //set the files into the dataset for pie chart
        //and creat and initial a JList
        File[] listFiles = folder.listFiles();
        view.setFiles(listFiles);
        JScrollPane scrollPane = new JScrollPane(view.getFiles());
        content.add(scrollPane, BorderLayout.WEST);
        content.revalidate();
        content.repaint();
        view.getFiles().addListSelectionListener(ev -> selectFileList(content, directorySize, selectedFolderLabel));
        if (folder.isFile())
            dataset.setValue(folder.getName() + "\n" + file.formatSize(folder.length()), folder.length());
        long size;
        if (listFiles != null)
            for (File file : listFiles) {
                size = this.file.calculateFolderSize(file);
                dataset.setValue(file.getName() + "\n" + this.file.formatSize(size), size);
            }
        //creat chart from dataset and the other parameters
        JFreeChart chart = ChartFactory.createPieChart("Storage Analyzer", dataset, true, true, false);

        ChartPanel chartPanel = new ChartPanel(chart);
        content.add(chartPanel, BorderLayout.EAST);
    }

    private void selectFileList(Container content, JLabel directorySize, JLabel selectedFolderLabel) {
        File file = view.getFiles().getSelectedValue();
        if (file.isDirectory()) {
            File folder = new File(String.valueOf(file));
            updateLabels(folder.getAbsolutePath(), this.file.formatSize(this.file.calculateFolderSize(folder)), directorySize, selectedFolderLabel);
            drawPieChart(content, folder, directorySize, selectedFolderLabel);
        }
    }

    public void updateLabels(String absolutePath, String size, JLabel directorySize, JLabel selectedFolderLabel) {
        selectedFolderLabel.setText("Selected Folder: " + absolutePath);
        directorySize.setText("Total Size: " + size);
    }
}
