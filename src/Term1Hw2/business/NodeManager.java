package Term1Hw2.business;

import Term1Hw2.entities.Node;
import Term1Hw2.repository.HashRepository;
import Term1Hw2.repository.Repository;

public class NodeManager implements NodeService {
	private Repository repository;

	public NodeManager() {
		this.repository = new HashRepository();
	}
	
	public Repository getRepository() {
		return repository;
	}
	public void setRepository(Repository repository) {
		this.repository = repository;
	}
	
	


	public Node Get(String id) {
		return repository.get(id);
	}

	@Override
	public void Add(Node newNode) {
		repository.add(newNode);
	}

	@Override
	public void Search(String id) {

	}

	@Override
	public void Traverse() {
		// TODO Auto-generated method stub

	}

	@Override
	public void PrintSubtree(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void PrintAncestor(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void MostRecentAncestor(String type1, String type2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void Height() {
		// TODO Auto-generated method stub

	}

	@Override
	public void Degree() {
		// TODO Auto-generated method stub

	}

	@Override
	public void Breadth() {
		// TODO Auto-generated method stub

	}

	@Override
	public void LongestEvolutionary() {
		// TODO Auto-generated method stub

	}

}
