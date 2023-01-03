package hw6.bst;

import hw6.OrderedMap;
import java.util.Iterator;
import java.util.Stack;

/**
 * Map implemented as an AVL Tree.
 *
 * @param <K> Type for keys.
 * @param <V> Type for values.
 */
public class AvlTreeMap<K extends Comparable<K>, V> implements OrderedMap<K, V> {

  /*** Do not change variable name of 'root'. ***/
  private Node<K, V> root;
  private int size;

  private int getHeight(Node<K, V> node) {
    if (node == null) {
      return -1;
    }

    if ((node.left == null) && (node.right == null)) { //no child
      return 0;
    }
    return 1 + max(getHeight(node.left), getHeight(node.right));
  }

  private int bf(Node<K, V> left, Node<K, V> right) {
    return getHeight(left) - getHeight(right);
  }

  private int max(int leftHeight, int rightHeight) {
    if (leftHeight > rightHeight) {
      return leftHeight;
    }
    return rightHeight;
  }

  private Node<K, V> right(Node<K, V> subRoot) {
    Node<K, V> newRoot = subRoot.left;

    subRoot.left = newRoot.right;
    newRoot.right = subRoot;


    //Setting new heights
    subRoot.height = getHeight(subRoot);
    newRoot.height = getHeight(newRoot);

    return newRoot;
  }

  private Node<K, V> left(Node<K, V> subRoot) {
    Node<K, V> newRoot = subRoot.right;

    subRoot.right = newRoot.left;
    newRoot.left = subRoot;

    //Setting new heights
    subRoot.height = getHeight(subRoot);
    newRoot.height = getHeight(newRoot);

    return newRoot;
  }

  private Node<K, V> rightLeft(Node<K, V> subRoot) {
    subRoot.right = right(subRoot.right);
    return left(subRoot);
  }

  private Node<K, V> leftRight(Node<K, V> subRoot) {
    subRoot.left = left(subRoot.left);
    return right(subRoot);
  }

  private Node<K, V> rotate(Node<K, V> n, int bf) {
    if (bf < -1) {
      if (bf(n.right.left, n.right.right) > 0) {
        n = rightLeft(n);
      } else {
        n = left(n);
      }
    } else if (bf > 1) {
      if (bf(n.left.left, n.left.right) < 0) {
        n = leftRight(n);
      } else {
        n = right(n);
      }
    }
    return n;
  }

  // Insert given key and value into subtree rooted at given node;
  // return changed subtree with a new node added.
  private Node<K, V> insert(Node<K, V> n, K k, V v) {
    if (n == null) {
      return new Node<>(k, v);
    }

    int cmp = k.compareTo(n.key);
    if (cmp < 0) {
      n.left = insert(n.left, k, v);
    } else if (cmp > 0) {
      n.right = insert(n.right, k, v);
    } else {
      throw new IllegalArgumentException("duplicate key " + k);
    }

    n.height = 1 + max(getHeight(n.left), getHeight(n.right));
    int bf = bf(n.left, n.right);
    if (bf < -1 || bf > 1) {
      n = rotate(n, bf);
    }

    return n;
  }

  @Override
  public void insert(K k, V v) throws IllegalArgumentException {
    if (k == null) {
      throw new IllegalArgumentException("cannot handle null key");
    }
    root = insert(root, k, v);
    size++;
  }

  // Remove node with given key from subtree rooted at given node;
  // Return changed subtree with given key missing.
  private Node<K, V> remove(Node<K, V> subtreeRoot, Node<K, V> toRemove) {
    int cmp = subtreeRoot.key.compareTo(toRemove.key);
    if (cmp == 0) {
      return remove(subtreeRoot);
    } else if (cmp > 0) {
      subtreeRoot.left = remove(subtreeRoot.left, toRemove);
      subtreeRoot.height = getHeight(subtreeRoot);
    } else {
      subtreeRoot.right = remove(subtreeRoot.right, toRemove);
      subtreeRoot.height = getHeight(subtreeRoot);
    }

    subtreeRoot.height = 1 + max(getHeight(subtreeRoot.left), getHeight(subtreeRoot.right));
    int bf = bf(subtreeRoot.left, subtreeRoot.right);
    if (bf < -1 || bf > 1) {
      subtreeRoot = rotate(subtreeRoot, bf);
    }

    return subtreeRoot;
  }

  // Remove given node and return the remaining tree (structural change).
  private Node<K, V> remove(Node<K, V> node) {
    // Easy if the node has 0 or 1 child.
    if (node.right == null) {
      return node.left;
    } else if (node.left == null) {
      return node.right;
    }

    // If it has two children, find the predecessor (max in left subtree),
    Node<K, V> toReplaceWith = maxNode(node);
    // then copy its data to the given node (value change),
    node.key = toReplaceWith.key;
    node.value = toReplaceWith.value;
    // then remove the predecessor node (structural change).
    node.left = remove(node.left, toReplaceWith);

    node.height = 1 + max(getHeight(node.left), getHeight(node.right));
    int bf = bf(node.left, node.right);
    if (bf < -1 || bf > 1) {
      node = rotate(node, bf);
    }

    return node;
  }

  @Override
  public V remove(K k) throws IllegalArgumentException {
    Node<K, V> node = findForSure(k);
    V value = node.value;
    root = remove(root, node);
    size--;
    return value;
  }

  // Return a node with maximum key in subtree rooted at given node.
  private Node<K, V> maxNode(Node<K, V> node) {
    Node<K, V> curr = node.left;
    while (curr.right != null) {
      curr = curr.right;
    }
    return curr;
  }



  @Override
  public void put(K k, V v) throws IllegalArgumentException {
    Node<K, V> n = findForSure(k);
    n.value = v;
  }

  // Return node for given key,
  // throw an exception if the key is not in the tree.
  private Node<K, V> findForSure(K k) throws IllegalArgumentException {
    Node<K, V> n = find(k);
    if (n == null) {
      throw new IllegalArgumentException("cannot find key " + k);
    }
    return n;
  }

  @Override
  public V get(K k) {
    Node<K, V> n = findForSure(k);
    return n.value;
  }

  @Override
  public boolean has(K k) {
    if (k == null) {
      return false;
    }
    return find(k) != null;
  }

  @Override
  public int size() {
    return size;
  }

  // Return node for given key.
  private Node<K, V> find(K k) {
    if (k == null) {
      throw new IllegalArgumentException("cannot handle null key");
    }
    Node<K, V> n = root;
    while (n != null) {
      int cmp = k.compareTo(n.key);
      if (cmp < 0) {
        n = n.left;
      } else if (cmp > 0) {
        n = n.right;
      } else {
        return n;
      }
    }
    return null;
  }

  @Override
  public Iterator<K> iterator() {
    return new InorderIterator();
  }

  // Iterative in-order traversal over the keys
  private class InorderIterator implements Iterator<K> {
    private final Stack<Node<K, V>> stack;

    InorderIterator() {
      stack = new Stack<>();
      pushLeft(root);
    }

    private void pushLeft(Node<K, V> curr) {
      while (curr != null) {
        stack.push(curr);
        curr = curr.left;
      }
    }

    @Override
    public boolean hasNext() {
      return !stack.isEmpty();
    }

    @Override
    public K next() {
      Node<K, V> top = stack.pop();
      pushLeft(top.right);
      return top.key;
    }
  }

  /*** Do not change this function's name or modify its code. ***/
  @Override
  public String toString() {
    return BinaryTreePrinter.printBinaryTree(root);
  }

  /**
   * Feel free to add whatever you want to the Node class (e.g. new fields).
   * Just avoid changing any existing names, deleting any existing variables,
   * or modifying the overriding methods.
   *
   * <p>Inner node class, each holds a key (which is what we sort the
   * BST by) as well as a value. We don't need a parent pointer as
   * long as we use recursive insert/remove helpers.</p>
   **/
  private static class Node<K, V> implements BinaryTreeNode {
    Node<K, V> left;
    Node<K, V> right;
    K key;
    V value;
    int height;

    // Constructor to make node creation easier to read.
    Node(K k, V v) {
      // left and right default to null
      key = k;
      value = v;
      height = 0;
    }

    @Override
    public String toString() {
      return key + ":" + value;
    }

    @Override
    public BinaryTreeNode getLeftChild() {
      return left;
    }

    @Override
    public BinaryTreeNode getRightChild() {
      return right;
    }
  }

}
