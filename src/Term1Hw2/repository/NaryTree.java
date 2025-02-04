package Term1Hw2.repository;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class NaryTree<T> implements NaryTreeInterface<T> {
	private TreeNode<T> root;

	public NaryTree() {
		root = null;
	}

	public NaryTree(T rootData) {
		root = new TreeNode<>(rootData);
	}

	public NaryTree(T rootData, List<? extends NaryTreeInterface<T>> subTrees) {
		initializeTree(rootData, subTrees);
	}

	public void setTree(T rootData, List<? extends NaryTreeInterface<T>> subTrees) {
		initializeTree(rootData, subTrees);
	}

	private void initializeTree(T rootData, List<? extends NaryTreeInterface<T>> subTrees) {
		root = new TreeNode<>(rootData);

		for (NaryTreeInterface<T> subTreeInterface : subTrees) {
			if (subTreeInterface != null && !subTreeInterface.isEmpty()) {
				NaryTree<T> subTree = (NaryTree<T>) subTreeInterface;
				this.root.addChild(subTree.getRootNode());
				subTree.clear();
			}
		}
	}

	@Override
	public T getRootData() {
		if (isEmpty())
			throw new EmptyTreeException();
		else
			return root.getData();
	}
	
	public void setRootData(T rootData) {
	    if (rootData != null) {
	        root.setData(rootData);  
	    } else {
	        throw new NullPointerException("Root data cannot be null");
	    }
	}

	public TreeNode<T> getRootNode() {
		return root;
	}

	public void setRootNode(TreeNode<T> rootNode) {
		if(rootNode != null) {
			root = rootNode;
		}else {
			throw new NullPointerException();
		}
	}

	@Override
	public int getHeight() {
		int height = 1;
		if (root != null)
			height = root.getHeight();
		return height;
	}

	@Override
	public int getNumberOfNodes() {
		int numberOfNodes = 0;
		if (root != null)
			numberOfNodes = root.getNumberOfNodes();
		return numberOfNodes;
	}

	@Override
	public boolean isEmpty() {
		return root == null;
	}

	@Override
	public void clear() {
		if (root != null) {
			root.clear();
			root = null;
		}
	}

	@Override
	public Iterator<T> getPreorderIterator() {
		return new PreorderIterator(); 
	}

	private class PreorderIterator implements Iterator<T> {

		private StackInterface<TreeNode<T>> nodeStack;

		public PreorderIterator() {
			nodeStack = new LinkedStack<>();
			if (root != null)
				nodeStack.push(root);
		} // end default constructor

		public boolean hasNext() {
			return !nodeStack.isEmpty();
		} // end hasNext

		public T next() {
			TreeNode<T> nextNode;

			if (hasNext()) {
				nextNode = nodeStack.pop();
				for(int i = nextNode.getChilds().size() -1 ; i >= 0; i--) {
					TreeNode<T> childNode = nextNode.getChilds().get(i);
					if (childNode != null) {
						nodeStack.push(childNode);
					}
				}
			} else {
				throw new NoSuchElementException();
			}
			return nextNode.getData();
		} // end next

		public void remove() {
			throw new UnsupportedOperationException();
		} // end remove
	} // end PreorderIterator

	@Override
	public Iterator<T> getPostorderIterator() {
	    throw new UnsupportedOperationException("There is no such an option: Postorder Iterator");
	}

	@Override
	public Iterator<T> getInorderIterator() {
	    throw new UnsupportedOperationException("There is no such an option: Inorder Iterator");
	}

	@Override
	public Iterator<T> getLevelOrderIterator() {
	    throw new UnsupportedOperationException("There is no such an option: Level Order Iterator");
	}


	
}
