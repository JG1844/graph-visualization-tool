package GraphCalculator;

import GraphCalculator.GraphState.CustomGraph;
import GraphCalculator.GraphIO.GraphMLExporter;
import GraphCalculator.GraphIO.GraphMLImporter;
import DataStructure.Graph;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class GraphCalculatorUI extends JFrame {
    private final GraphRemoteControl graphHandler = new GraphRemoteControl();
    private JTextField displayNumber;
    private JTextField displayM, displayN;
    private JPanel numberInputPanel;
    private JComboBox<String> mainCategorySelector;
    private JButton[] numButtons;
    private JButton deleteBtn, enterBtn, cleanBtn;
    private JPanel graphBtnPanel;
    private JPanel treeBtnPanel;
    private String selectedMainCategory = null;
    private String selectedGraphType = null;
    private String selectedTreeType = null;
    private JTextField currentFocusField;

    private static final Color BTN_COLOR = Color.WHITE;
    private static final Color BTN_HOVER_COLOR = new Color(230, 230, 230);
    private static final Color BTN_BORDER_COLOR = new Color(200, 200, 200);
    private static final Color BTN_HOVER_BORDER = new Color(100, 100, 100);
    private static final Color BTN_FONT_COLOR = Color.BLACK;
    private static final Color ENTER_COLOR = Color.BLACK;
    private static final Color ENTER_HOVER_COLOR = new Color(50, 50, 50);
    private static final Color ENTER_FONT_COLOR = Color.BLACK;
    private static final Color DELETE_COLOR = new Color(240, 240, 240);
    private static final Color DELETE_HOVER_COLOR = new Color(200, 200, 200);
    private static final Color DELETE_FONT_COLOR = Color.BLACK;
    private static final Color CLEAN_COLOR = Color.BLACK;
    private static final Color CLEAN_FONT_COLOR = Color.WHITE;
    private static final Color FOCUS_BORDER_COLOR = new Color(30, 144, 255);

    public GraphCalculatorUI() {
        setTitle("Graph Calculator");
        setSize(950, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));
        ((JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 顶部：输入框独占一行
        numberInputPanel = new JPanel(new CardLayout());
        displayNumber = new JTextField("Enter number");
        displayNumber.setFont(new Font("Arial", Font.PLAIN, 22));
        displayNumber.setHorizontalAlignment(JTextField.CENTER);
        displayNumber.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));

        JPanel doublePanel = new JPanel(new GridLayout(1, 2, 10, 0));
        displayM = new JTextField("m");
        displayM.setFont(new Font("Arial", Font.PLAIN, 22));
        displayM.setHorizontalAlignment(JTextField.CENTER);
        displayM.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
        displayN = new JTextField("n");
        displayN.setFont(new Font("Arial", Font.PLAIN, 22));
        displayN.setHorizontalAlignment(JTextField.CENTER);
        displayN.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
        doublePanel.add(displayM);
        doublePanel.add(displayN);
        numberInputPanel.add(displayNumber, "SINGLE");
        numberInputPanel.add(doublePanel, "DOUBLE");
        add(numberInputPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        centerPanel.add(createKeypadPanel());
        centerPanel.add(createRightControlPanel());
        add(centerPanel, BorderLayout.CENTER);

        displayNumber.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (displayNumber.getText().equals("Enter number")) {
                    displayNumber.setText("");
                }
                displayNumber.setBorder(BorderFactory.createLineBorder(FOCUS_BORDER_COLOR, 2));
                currentFocusField = displayNumber;
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                displayNumber.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
            }
        });
        displayM.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (displayM.getText().equals("m")) {
                    displayM.setText("");
                }
                displayM.setBorder(BorderFactory.createLineBorder(FOCUS_BORDER_COLOR, 2));
                currentFocusField = displayM;
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                displayM.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
            }
        });
        displayN.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (displayN.getText().equals("n")) {
                    displayN.setText("");
                }
                displayN.setBorder(BorderFactory.createLineBorder(FOCUS_BORDER_COLOR, 2));
                currentFocusField = displayN;
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                displayN.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
            }
        });
        setVisible(true);
    }

    private JPanel createKeypadPanel() {
        JPanel keypadPanel = new JPanel(new GridLayout(4, 3, 10, 10));
        keypadPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        keypadPanel.setBackground(Color.WHITE);
        numButtons = new JButton[10];
        numButtons[1] = createNumButton("1");
        numButtons[2] = createNumButton("2");
        numButtons[3] = createNumButton("3");
        keypadPanel.add(numButtons[1]);
        keypadPanel.add(numButtons[2]);
        keypadPanel.add(numButtons[3]);
        numButtons[4] = createNumButton("4");
        numButtons[5] = createNumButton("5");
        numButtons[6] = createNumButton("6");
        keypadPanel.add(numButtons[4]);
        keypadPanel.add(numButtons[5]);
        keypadPanel.add(numButtons[6]);
        numButtons[7] = createNumButton("7");
        numButtons[8] = createNumButton("8");
        numButtons[9] = createNumButton("9");
        keypadPanel.add(numButtons[7]);
        keypadPanel.add(numButtons[8]);
        keypadPanel.add(numButtons[9]);
        deleteBtn = new JButton("Delete");
        styleDeleteButton(deleteBtn);
        deleteBtn.addActionListener(e -> deleteCurrentInput());
        numButtons[0] = createNumButton("0");
        enterBtn = new JButton("Enter");
        styleEnterButton(enterBtn);
        enterBtn.addActionListener(new EnterButtonListener());
        keypadPanel.add(deleteBtn);
        keypadPanel.add(numButtons[0]);
        keypadPanel.add(enterBtn);
        return keypadPanel;
    }

    private JButton createNumButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.PLAIN, 32));
        btn.setBackground(BTN_COLOR);
        btn.setForeground(BTN_FONT_COLOR);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(BTN_BORDER_COLOR, 2));
        btn.addActionListener(new NumButtonListener());
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(BTN_HOVER_COLOR);
                btn.setBorder(BorderFactory.createLineBorder(BTN_HOVER_BORDER, 2));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(BTN_COLOR);
                btn.setBorder(BorderFactory.createLineBorder(BTN_BORDER_COLOR, 2));
            }
        });
        return btn;
    }

    private void styleDeleteButton(JButton btn) {
        btn.setFont(new Font("Arial", Font.PLAIN, 20));
        btn.setBackground(DELETE_COLOR);
        btn.setForeground(DELETE_FONT_COLOR);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(BTN_BORDER_COLOR, 2));
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(DELETE_HOVER_COLOR);
                btn.setBorder(BorderFactory.createLineBorder(BTN_HOVER_BORDER, 2));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(DELETE_COLOR);
                btn.setBorder(BorderFactory.createLineBorder(BTN_BORDER_COLOR, 2));
            }
        });
    }

    private void styleEnterButton(JButton btn) {
        btn.setFont(new Font("Arial", Font.BOLD, 20));
        btn.setBackground(ENTER_COLOR);
        btn.setForeground(ENTER_FONT_COLOR);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(ENTER_HOVER_COLOR);
                btn.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(ENTER_COLOR);
                btn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            }
        });
    }

    private JPanel createRightControlPanel() {
        JPanel rightPanel = new JPanel(new BorderLayout(0, 15));
        rightPanel.setBackground(Color.BLACK);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel topDropPanel = new JPanel();
        topDropPanel.setLayout(new BoxLayout(topDropPanel, BoxLayout.X_AXIS));
        topDropPanel.setBackground(Color.BLACK);
        String[] mainCategories = {"Graph", "Tree"};
        mainCategorySelector = new JComboBox<>(mainCategories);
        mainCategorySelector.setFont(new Font("Arial", Font.PLAIN, 20));
        mainCategorySelector.setBackground(Color.WHITE);
        mainCategorySelector.setMaximumSize(new Dimension(Short.MAX_VALUE, 40));
        mainCategorySelector.addActionListener(e -> {
            selectedMainCategory = (String) mainCategorySelector.getSelectedItem();
            switchInputMode(false);
            resetAllFields();
            if ("Tree".equals(selectedMainCategory)) {
                graphBtnPanel.setVisible(false);
                treeBtnPanel.setVisible(true);
                displayNumber.setText("Enter number / level");
                displayNumber.requestFocus();
            } else {
                graphBtnPanel.setVisible(true);
                treeBtnPanel.setVisible(false);
                displayNumber.setText("Enter number");
                displayNumber.requestFocus();
            }
        });
        topDropPanel.add(mainCategorySelector);
        rightPanel.add(topDropPanel, BorderLayout.NORTH);

        JPanel middleContainer = new JPanel(new CardLayout());
        middleContainer.setBackground(Color.BLACK);
        graphBtnPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        graphBtnPanel.setBackground(Color.BLACK);
        String[] baseGraphList = {
                "Kn (Complete Graph)",
                "Kmn (BiPartite Graph)",
                "Petersen Graph"
        };
        for (String graphName : baseGraphList) {
            JButton btn = new JButton(graphName);
            btn.setFont(new Font("Arial", Font.PLAIN, 22));
            btn.addActionListener(evt -> {
                selectedGraphType = graphName;
                selectedMainCategory = "Graph";
                mainCategorySelector.setSelectedItem("Graph");
                if (graphName.equals("Kmn (BiPartite Graph)")) {
                    switchInputMode(true);
                    displayM.requestFocus();
                } else {
                    switchInputMode(false);
                    displayNumber.requestFocus();
                }
                resetAllFields();
            });
            graphBtnPanel.add(btn);
        }
        middleContainer.add(graphBtnPanel, "GRAPH");

        treeBtnPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        treeBtnPanel.setBackground(Color.BLACK);
        String[] actualTreeTypes = {"Line Tree", "Perfect Binary Tree", "Star Tree"};
        for (String treeName : actualTreeTypes) {
            JButton btn = new JButton(treeName);
            btn.setFont(new Font("Arial", Font.PLAIN, 22));
            btn.addActionListener(evt -> {
                selectedTreeType = treeName;
                selectedMainCategory = "Tree";
                mainCategorySelector.setSelectedItem("Tree");
                switchInputMode(false);
                if ("Perfect Binary Tree".equals(treeName)) {
                    displayNumber.setText("Enter tree level");
                } else {
                    displayNumber.setText("Enter node count");
                }
                displayNumber.requestFocus();
            });
            treeBtnPanel.add(btn);
        }
        middleContainer.add(treeBtnPanel, "TREE");
        graphBtnPanel.setVisible(true);
        treeBtnPanel.setVisible(false);
        rightPanel.add(middleContainer, BorderLayout.CENTER);

        JPanel bottomBtnPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        bottomBtnPanel.setBackground(Color.BLACK);
        JButton drawCustomBtn = new JButton("Draw Custom Graph");
        drawCustomBtn.setFont(new Font("Arial", Font.PLAIN, 22));
        drawCustomBtn.setBackground(Color.WHITE);
        drawCustomBtn.setForeground(Color.BLACK);
        drawCustomBtn.setFocusPainted(false);
        drawCustomBtn.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        drawCustomBtn.addActionListener(e -> openDrawGraphWindow());
        bottomBtnPanel.add(drawCustomBtn);

        cleanBtn = new JButton("Clean");
        cleanBtn.setFont(new Font("Arial", Font.PLAIN, 22));
        cleanBtn.setBackground(CLEAN_COLOR);
        cleanBtn.setForeground(CLEAN_FONT_COLOR);
        cleanBtn.setFocusPainted(false);
        cleanBtn.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        cleanBtn.addActionListener(e -> resetAll());
        bottomBtnPanel.add(cleanBtn);
        rightPanel.add(bottomBtnPanel, BorderLayout.SOUTH);
        return rightPanel;
    }

    private class NumButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (currentFocusField == null) return;
            String inputNum = ((JButton) e.getSource()).getText();
            String currentText = currentFocusField.getText();
            if (currentText.equals("Enter number")
                    || currentText.equals("Enter number / level")
                    || currentText.equals("Enter tree level")
                    || currentText.equals("Enter node count")
                    || currentText.equals("m")
                    || currentText.equals("n")) {
                currentFocusField.setText(inputNum);
            } else {
                currentFocusField.setText(currentText + inputNum);
            }
        }
    }

    private void deleteCurrentInput() {
        if (currentFocusField == null) return;
        String text = currentFocusField.getText();
        if (text.equals("Enter number")
                || text.equals("Enter number / level")
                || text.equals("Enter tree level")
                || text.equals("Enter node count")
                || text.equals("m")
                || text.equals("n")) {
            return;
        }
        if (!text.isEmpty()) {
            currentFocusField.setText(text.substring(0, text.length() - 1));
        }
    }

    private class EnterButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (selectedMainCategory == null) {
                JOptionPane.showMessageDialog(GraphCalculatorUI.this, "Please select Graph or Tree category first!");
                return;
            }
            String finalSelectedType = "";
            JScrollPane scrollPane;
            try {
                if ("Graph".equals(selectedMainCategory)) {
                    if (selectedGraphType == null) {
                        JOptionPane.showMessageDialog(GraphCalculatorUI.this, "Pick a graph type (Kn / Kmn / Petersen)");
                        return;
                    }
                    finalSelectedType = selectedGraphType;
                    switch (selectedGraphType) {
                        case "Kn (Complete Graph)":
                            int knN = Integer.parseInt(displayNumber.getText().trim());
                            if (knN <= 0) throw new NumberFormatException();
                            graphHandler.completeGraph_Kn(knN);
                            break;
                        case "Kmn (BiPartite Graph)":
                            int m = Integer.parseInt(displayM.getText().trim());
                            int n2 = Integer.parseInt(displayN.getText().trim());
                            if (m <= 0 || n2 <= 0) throw new NumberFormatException();
                            graphHandler.biPartiteGraph_Kmn(m, n2);
                            break;
                        case "Petersen Graph":
                            int petN = Integer.parseInt(displayNumber.getText().trim());
                            if (petN <= 0) throw new NumberFormatException();
                            graphHandler.petersenGraph(petN);
                            break;
                    }
                } else {
                    if (selectedTreeType == null) {
                        JOptionPane.showMessageDialog(GraphCalculatorUI.this, "Pick a tree type (Line / Perfect Binary / Star)");
                        return;
                    }
                    finalSelectedType = selectedTreeType;
                    int inputVal = Integer.parseInt(displayNumber.getText().trim());
                    if (inputVal <= 0) throw new NumberFormatException();
                    switch (selectedTreeType) {
                        case "Line Tree":
                            graphHandler.line(inputVal);
                            break;
                        case "Perfect Binary Tree":
                            graphHandler.perfectBinaryTree(inputVal);
                            break;
                        case "Star Tree":
                            graphHandler.starTree(inputVal);
                            break;
                    }
                }

                // 统一只计算一次自同构
                graphHandler.automorphism();
                int autoTotal = graphHandler.getSizeAuto();
                int vertexCount = graphHandler.countVertex();
                int edgeCount = graphHandler.countEdge();

                // 构建弹窗文本
                StringBuilder htmlBuilder = new StringBuilder();
                htmlBuilder.append("<html><body style='font-size:18px; padding:10px;'>");
                htmlBuilder.append("Selected Type: ").append(finalSelectedType).append("<br><br>");
                htmlBuilder.append("Vertices: ").append(vertexCount).append("<br>");
                htmlBuilder.append("Edges: ").append(edgeCount).append("<br><br>");
                htmlBuilder.append("<b>Automorphism Information</b><br>");
                htmlBuilder.append("Total Automorphisms: ").append(autoTotal).append("<br>");
                htmlBuilder.append("Click buttons below to view graph and all mappings");
                htmlBuilder.append("</body></html>");

                JTextPane resultPane = new JTextPane();
                resultPane.setContentType("text/html");
                resultPane.setText(htmlBuilder.toString());
                resultPane.setEditable(false);
                resultPane.setBackground(null);

                scrollPane = new JScrollPane(resultPane);
                scrollPane.setPreferredSize(new Dimension(620, 450));

                // 底部四个按钮：导出 + 导入 + 可视化 + 自同构详情
                JPanel bottomBtnPanel = new JPanel(new GridLayout(1, 4, 6, 0));
                JButton exportBtn = new JButton("Export Graph");
                exportBtn.setFont(new Font("Arial", Font.PLAIN, 14));
                exportBtn.addActionListener(evt -> exportCurrentGraph());
                JButton importBtn = new JButton("Import Graph");
                importBtn.setFont(new Font("Arial", Font.PLAIN, 14));
                importBtn.addActionListener(evt -> importGraphFromFile((Component) evt.getSource()));
                JButton visualizeBtn = new JButton("Open Graph Visualization");
                visualizeBtn.setFont(new Font("Arial", Font.PLAIN, 14));
                visualizeBtn.addActionListener(evt -> {
                    try {
                        graphHandler.visulize();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(GraphCalculatorUI.this,
                                "Cannot open visualization: " + ex.getMessage());
                    }
                });
                JButton viewAutoBtn = new JButton("View Automorphisms");
                viewAutoBtn.setFont(new Font("Arial", Font.PLAIN, 14));
                viewAutoBtn.addActionListener(evt ->
                        AutomorphismDialog.showAutomorphismDialog(GraphCalculatorUI.this, graphHandler));
                bottomBtnPanel.add(exportBtn);
                bottomBtnPanel.add(importBtn);
                bottomBtnPanel.add(visualizeBtn);
                bottomBtnPanel.add(viewAutoBtn);

                // 组装完整面板，只弹一次窗口
                JPanel panelWithBtn = new JPanel(new BorderLayout(0, 10));
                panelWithBtn.add(scrollPane, BorderLayout.CENTER);
                panelWithBtn.add(bottomBtnPanel, BorderLayout.SOUTH);

                JOptionPane.showMessageDialog(GraphCalculatorUI.this, panelWithBtn,
                        "Graph Result", JOptionPane.INFORMATION_MESSAGE);

            } catch (NumberFormatException err) {
                JOptionPane.showMessageDialog(GraphCalculatorUI.this, "Error: Please input positive integers only!");
            } catch (Exception err) {
                JOptionPane.showMessageDialog(GraphCalculatorUI.this, "Runtime Error: " + err.getMessage());
                err.printStackTrace();
            }
        }
    }

    private void resetAllFields() {
        displayNumber.setText("Enter number");
        displayM.setText("m");
        displayN.setText("n");
        currentFocusField = null;
    }

    private void resetAll() {
        resetAllFields();
        selectedMainCategory = null;
        selectedGraphType = null;
        selectedTreeType = null;
        mainCategorySelector.setSelectedItem("Graph");
        graphBtnPanel.setVisible(true);
        treeBtnPanel.setVisible(false);
        switchInputMode(false);
        displayNumber.requestFocus();
    }

    private void switchInputMode(boolean isDoubleMode) {
        CardLayout cardLayout = (CardLayout) numberInputPanel.getLayout();
        if (isDoubleMode) {
            cardLayout.show(numberInputPanel, "DOUBLE");
            displayM.requestFocus();
        } else {
            cardLayout.show(numberInputPanel, "SINGLE");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GraphCalculatorUI::new);
    }

    private void openDrawGraphWindow() {
        JFrame drawFrame = new JFrame("Draw Custom Graph Board");
        drawFrame.setSize(1050, 780);
        drawFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        drawFrame.setLayout(new BorderLayout(10, 10));
        GraphDrawPanel drawPanel = new GraphDrawPanel();
        JScrollPane scrollCanvas = new JScrollPane(drawPanel);
        drawFrame.add(scrollCanvas, BorderLayout.CENTER);

        JPanel topBar = new JPanel();
        JButton clearCanvasBtn = new JButton("Clear Canvas");
        JButton importToCalcBtn = new JButton("Import Graph To Calculator");
        topBar.add(clearCanvasBtn);
        topBar.add(importToCalcBtn);
        clearCanvasBtn.addActionListener(e -> drawPanel.clearAll());
        importToCalcBtn.addActionListener(e -> {
            Map<Integer, List<Integer>> adj = drawPanel.getAdjacencyMap();
            if (adj.isEmpty()) {
                JOptionPane.showMessageDialog(drawFrame, "Canvas is empty! Draw nodes and edges first.");
                return;
            }
            selectedMainCategory = "Graph";
            selectedGraphType = "Custom Drawn Graph";
            graphHandler.setState(new CustomGraph<>(graphHandler, adj));
            graphHandler.automorphism();
            new EnterButtonListener().actionPerformed(null);
            drawFrame.dispose();
        });
        drawFrame.add(topBar, BorderLayout.NORTH);

        JPanel tipPanel = new JPanel();
        tipPanel.setBackground(Color.LIGHT_GRAY);
        JLabel tipLabel = new JLabel("Tips: Click blank to add node | Click two nodes to create edge | Right click delete node");
        tipPanel.add(tipLabel);
        drawFrame.add(tipPanel, BorderLayout.SOUTH);
        drawFrame.setLocationRelativeTo(this);
        drawFrame.setVisible(true);
    }

    private void exportCurrentGraph() {
        Graph<?> g = graphHandler.buildGraph();
        if (g == null || g.getAdjacencyList().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No graph to export. Build a graph first.");
            return;
        }
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Export Graph as GraphML");
        String defaultName = "graph_"
                + new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) + ".graphml";
        chooser.setSelectedFile(new File(defaultName));
        chooser.setFileFilter(new FileNameExtensionFilter("GraphML files (*.graphml)", "graphml"));
        if (chooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) return;
        File file = chooser.getSelectedFile();
        if (!file.getName().toLowerCase(java.util.Locale.ROOT).endsWith(".graphml")) {
            file = new File(file.getParentFile(), file.getName() + ".graphml");
        }
        try {
            GraphMLExporter.export(g, file, graphHandler.getStateName());
            JOptionPane.showMessageDialog(this, "Graph exported to:\n" + file.getAbsolutePath());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Export failed: " + ex.getMessage());
        }
    }

    private void importGraphFromFile(Component source) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Import Graph from GraphML");
        chooser.setFileFilter(new FileNameExtensionFilter("GraphML files (*.graphml)", "graphml"));
        if (chooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) return;
        File file = chooser.getSelectedFile();
        try {
            Map<String, List<String>> adj = GraphMLImporter.importGraph(file);
            if (adj.isEmpty()) {
                JOptionPane.showMessageDialog(this, "The file contains no nodes.");
                return;
            }
            selectedMainCategory = "Graph";
            selectedGraphType = "Imported Graph";
            graphHandler.setState(new CustomGraph<>(graphHandler, adj, "Imported: " + file.getName()));
            // 关闭当前结果弹窗，再用同一套 Enter 流程为导入的图刷新结果
            Window win = SwingUtilities.getWindowAncestor(source);
            if (win != null) win.dispose();
            new EnterButtonListener().actionPerformed(null);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Import failed: " + ex.getMessage());
        }
    }
}
