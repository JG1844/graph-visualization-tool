package GraphCalculator;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 唯一的public顶层类，类名与文件名一致
public class GraphDrawPanel extends JPanel {
    // ========== 将DrawNode改为静态内部类 ==========
    static class DrawNode {
        int id;
        int x, y;
        public static final int RADIUS = 20;
        DrawNode(int id, int x, int y) {
            this.id = id;
            this.x = x;
            this.y = y;
        }
    }

    // ========== 将DrawEdge改为静态内部类 ==========
    static class DrawEdge {
        int from, to;
        DrawEdge(int f, int t) {
            from = f;
            to = t;
        }
    }

    // 原有成员变量不变
    private final List<DrawNode> nodes = new ArrayList<>();
    private final List<DrawEdge> edges = new ArrayList<>();
    private int nextNodeId = 1;
    private DrawNode draggingNode = null;
    private DrawNode selectedNode = null;
    private int dragOffsetX, dragOffsetY;

    // 原有构造方法、鼠标监听、工具方法、绘制方法等完全不变
    public GraphDrawPanel() {
        setBackground(Color.WHITE);
        setBorder(new LineBorder(Color.GRAY, 2));
        MouseAdapter mouseListener = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                DrawNode hitNode = getNodeByPos(e.getX(), e.getY());
                if (hitNode != null) {
                    draggingNode = hitNode;
                    dragOffsetX = e.getX() - hitNode.x;
                    dragOffsetY = e.getY() - hitNode.y;
                    if (selectedNode == null) {
                        selectedNode = hitNode;
                    } else if (selectedNode.id != hitNode.id) {
                        boolean edgeExist = false;
                        for (DrawEdge ed : edges) {
                            if ((ed.from == selectedNode.id && ed.to == hitNode.id)
                                    || (ed.from == hitNode.id && ed.to == selectedNode.id)) {
                                edgeExist = true;
                                break;
                            }
                        }
                        if (!edgeExist) edges.add(new DrawEdge(selectedNode.id, hitNode.id));
                        selectedNode = null;
                    }
                } else {
                    nodes.add(new DrawNode(nextNodeId++, e.getX(), e.getY()));
                    selectedNode = null;
                }
                repaint();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (draggingNode != null) {
                    draggingNode.x = e.getX() - dragOffsetX;
                    draggingNode.y = e.getY() - dragOffsetY;
                    repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                draggingNode = null;
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    DrawNode hit = getNodeByPos(e.getX(), e.getY());
                    if (hit != null) {
                        nodes.remove(hit);
                        edges.removeIf(ed -> ed.from == hit.id || ed.to == hit.id);
                        if (selectedNode == hit) selectedNode = null;
                        repaint();
                    }
                }
            }
        };
        addMouseListener(mouseListener);
        addMouseMotionListener(mouseListener);
    }

    private DrawNode getNodeByPos(int mx, int my) {
        for (DrawNode n : nodes) {
            int dx = mx - n.x;
            int dy = my - n.y;
            if (dx * dx + dy * dy <= DrawNode.RADIUS * DrawNode.RADIUS) {
                return n;
            }
        }
        return null;
    }

    private DrawNode getNodeById(int id) {
        for (DrawNode n : nodes) if (n.id == id) return n;
        return null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(2));
        g2.setColor(Color.DARK_GRAY);
        for (DrawEdge edge : edges) {
            DrawNode f = getNodeById(edge.from);
            DrawNode t = getNodeById(edge.to);
            if (f == null || t == null) continue;
            g2.drawLine(f.x, f.y, t.x, t.y);
        }
        for (DrawNode n : nodes) {
            if (n == selectedNode) g2.setColor(Color.ORANGE);
            else g2.setColor(new Color(30, 144, 255));
            g2.fillOval(n.x - DrawNode.RADIUS, n.y - DrawNode.RADIUS,
                    DrawNode.RADIUS * 2, DrawNode.RADIUS * 2);
            g2.setColor(Color.BLACK);
            g2.drawOval(n.x - DrawNode.RADIUS, n.y - DrawNode.RADIUS,
                    DrawNode.RADIUS * 2, DrawNode.RADIUS * 2);
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 16));
            g2.drawString(String.valueOf(n.id), n.x - 7, n.y + 6);
        }
    }

    public Map<Integer, List<Integer>> getAdjacencyMap() {
        Map<Integer, List<Integer>> adj = new HashMap<>();
        for (DrawNode n : nodes) adj.put(n.id, new ArrayList<>());
        for (DrawEdge e : edges) {
            adj.get(e.from).add(e.to);
            adj.get(e.to).add(e.from);
        }
        return adj;
    }

    public String getAdjacencyText() {
        Map<Integer, List<Integer>> adj = getAdjacencyMap();
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Integer, List<Integer>> entry : adj.entrySet()) {
            sb.append("节点").append(entry.getKey()).append(": [");
            for (int i = 0; i < entry.getValue().size(); i++) {
                if (i > 0) sb.append(", ");
                sb.append(entry.getValue().get(i));
            }
            sb.append("]\n");
        }
        return sb.isEmpty() ? "" : sb.toString();
    }

    public void clearAll() {
        nodes.clear();
        edges.clear();
        nextNodeId = 1;
        selectedNode = null;
        repaint();
    }
}