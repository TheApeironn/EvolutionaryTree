package Term1Hw2.repository;

import java.util.ArrayList;
import java.util.List;

public class TreeNode<T>{
	private T data;
	private List<TreeNode<T>> childs;

	public TreeNode() {
		 this(null, new ArrayList<>());
	}

	public TreeNode(T dataT) {
		this(dataT, new ArrayList<>());
	}

	public TreeNode(T dataT, List<TreeNode<T>> childList) {
		data = dataT;
		childs = childList;
	}

	public T getData() {
		if (data == null) {
			System.out.println("Data is null");
			return null;
		}
		return data;
	}

	public void setData(T newData) {
		this.data = newData;
	}

	public List<TreeNode<T>> getChilds() {
		return childs;
	}

	public void setChild(List<TreeNode<T>> childs) {
		this.childs = childs;
	}
	
	public void addChild(TreeNode<T> newNode) {
		this.childs.add(newNode);
	}
	
	public void removeChild(TreeNode<T> node) {
		this.childs.remove(node);
	}
	
	public boolean hasChild() {
		return !childs.isEmpty();
	}

	public boolean isLeaf() {
		return this.childs.size() ==0;
	}

	public int getNumberOfNodes() {
		if (childs == null || childs.isEmpty()) {
			return 0;
		}
		int sum = childs.size();
		for (TreeNode<T> node : childs) {
			sum += node.getNumberOfNodes();
		}
		return sum ;
	}

	public int getHeight() {
		return getHeight(this);
	}

	private int getHeight(TreeNode<T> node) {
	    int height = 1; 
	    for (TreeNode<T> child : node.childs) {
	        height = Math.max(height, 1 + getHeight(child));
	    }
	    return height;
	}
	
	public void clear() {
		this.data = null;  
        if (this.childs != null) {
            this.childs.clear();  
        }
	}

}
