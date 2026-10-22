package GraphCalculator;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AutomorphismDialog extends JDialog {
    public AutomorphismDialog(Frame parent, GraphRemoteControl graphs) {
        super(parent, "Automorphism Details", true);
        setSize(550, 650);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10,10));

        ArrayList<ArrayList<String>> autoList = graphs.getAutomorphisms();
        StringBuilder textContent = new StringBuilder();
        textContent.append("Total Automorphisms: ").append(autoList.size()).append("\n\n");

        // 核心修复：每个映射单独换行
        for (int i = 0; i < autoList.size(); i++) {
            textContent.append("===== Automorphism ").append(i+1).append(" =====\n");
            for(String singleMap : autoList.get(i)){
                textContent.append(singleMap).append("\n");
            }
            textContent.append("\n");
        }

        JTextArea textArea = new JTextArea(textContent.toString());
        textArea.setFont(new Font("Monaco", Font.PLAIN, 14));
        textArea.setEditable(false);
        textArea.setLineWrap(false); // 关闭自动换行，防止错乱
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        JButton closeBtn = new JButton("Close");
        closeBtn.addActionListener(e -> dispose());
        bottomPanel.add(closeBtn);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public static void showAutomorphismDialog(Frame parentWindow, GraphRemoteControl graphs) {
        SwingUtilities.invokeLater(() -> {
            AutomorphismDialog dialog = new AutomorphismDialog(parentWindow, graphs);
            dialog.setVisible(true);
        });
    }
}
