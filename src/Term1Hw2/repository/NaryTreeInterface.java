package Term1Hw2.repository;

import java.util.List;

public interface NaryTreeInterface<T> extends TreeInterface<T>, TreeIteratorInterface<T> {

	public void setRootData(T rootData);

	public void setTree(T rootData, List<? extends NaryTreeInterface<T>> childList);
}
