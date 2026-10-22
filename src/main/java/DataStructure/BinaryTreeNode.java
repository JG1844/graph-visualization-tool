package DataStructure;

import java.util.ArrayList;

public class BinaryTreeNode<A> {
    public A value;
    public BinaryTreeNode<A> left;
    public BinaryTreeNode<A> right;

    public BinaryTreeNode(A value, BinaryTreeNode<A> left, BinaryTreeNode<A> right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }


    public String preOrderTraversal(BinaryTreeNode node) {
        // node -> left -> right
        if (node != null) {
            String out = "";
            out += node.value.toString() + " ";
            out += preOrderTraversal(node.left);
            out += preOrderTraversal(node.right);
            return out;
        } else {
            return "";
        }
    }

    public String inOrderTraversal(BinaryTreeNode node) {
        // left -> node -> right
        if (node != null) {
            String out = "";
            out += inOrderTraversal(node.left);
            out += node.value.toString() + " ";
            out += inOrderTraversal(node.right);
            return out;
        } else {
            return "";
        }
    }

    public ArrayList<A> inOrderTraverseArrayList(BinaryTreeNode<A> node) {
        ArrayList<A> output = new ArrayList<>();
        inOrderTraverseArrayListHelper(node, output);
        return output;
    }

    public void inOrderTraverseArrayListHelper(BinaryTreeNode<A> node, ArrayList<A> output) {
        if (node == null) {
            return;
        } else {
            inOrderTraverseArrayListHelper(node.left, output);
            output.add(node.value);
            inOrderTraverseArrayListHelper(node.right, output);
        }
    }


    public BinaryTreeNode<A> getLeft() {
        return this.left;
    }

    public A getValue() {
        return this.value;
    }

    public void setLeft(BinaryTreeNode<A> aBinaryTreeNode) {
        this.left = aBinaryTreeNode;
    }

    public BinaryTreeNode<A> getRight() {
        return this.right;
    }

    public void setRight(BinaryTreeNode<A> aBinaryTreeNode) {
        this.right = aBinaryTreeNode;
    }

}
