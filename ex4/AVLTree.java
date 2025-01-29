public class AVLTree  <K extends Comparable<K>, V> extends BinarySearchTree<K,V> {

    public AVLTree(){
        super();
    }

    public V insert(K key, V value){
        if (root == null) {
            root = new TreeNode<>(key, value);
            root.updateHeight();
            size++;
            return null;
        }
        V oldValue = find(key);
        root = insertHelper(root, key, value);
        if (oldValue != null) {
            return oldValue;
        }
        return null;
    }

    private TreeNode<K, V> insertHelper(TreeNode<K, V> node, K key, V value) {
        // node(aka. oldRoot) is balanced before insert
        // node will change during recursive
        // find the insert place
        if (node == null) {
            size ++;
            return new TreeNode<>(key, value);
        }

        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = insertHelper(node.left, key, value);
        } else if (cmp > 0) {
            node.right = insertHelper(node.right, key, value);
        } else {
            node.value = value;  // Key already exists, update the value
            return node;
        }

        // Update height of this ancestor node
        node.updateHeight();

        // Check the balance factor to determine if rotation is required
        int balance = getBalance(node);

        // Left-heavy
        if (balance > 1) {
            if (key.compareTo(node.left.key) < 0) {
                // left-left case
                return rotateRight(node);
            } else {
                // left-right case
                node.left = rotateLeft(node.left);
                return rotateRight(node);
            }
        }
        // Right-heavy
        if (balance < -1) {
            if (key.compareTo(node.right.key) > 0) {
                // right-right case
                return rotateLeft(node);
            } else {
                // right-left case
                node.right = rotateRight(node.right);
                return rotateLeft(node);
            }
        }
        return node;  // Return the unchanged node pointer
    }

    // Check the balance factor to determine if rotation is required
    private int getBalance(TreeNode<K, V> root) {
        if (root == null) {
            return 0;
        }
        int leftHeight = root.left != null ? root.left.height : -1;
        int rightHeight = root.right != null ? root.right.height : -1;
        return leftHeight - rightHeight;
    }

    // rotate left to balance the tree
    private TreeNode<K, V> rotateLeft(TreeNode<K, V> root) {
        if (root == null || root.right == null) return root;
        TreeNode<K, V> temp = root.right;
        root.right = temp.left;
        temp.left = root;
        root.updateHeight();
        temp.updateHeight();
        root = temp;
        return root;
    }
    // rotate right to balance the tree
    private TreeNode<K, V> rotateRight(TreeNode<K, V> root) {
        if (root == null || root.left == null) return root;
        TreeNode<K, V> temp = root.left;
        root.left = temp.right;
        temp.right = root;
        root.updateHeight();
        temp.updateHeight();
        root = temp;
        return root;
    }
}

