package Term1Hw2.business;

import Term1Hw2.entities.Node;

public interface NodeService {
	public void Add(Node newNode);
	public Node Get(String id);
	public void Search(String id);
	public void Traverse();
	public void PrintSubtree(String id);
	public void PrintAncestor(String id);
	public void MostRecentAncestor(String type1,String type2);
	public void Height();
	public void Degree();
	public void Breadth();
	public void LongestEvolutionary();
	
}
