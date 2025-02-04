package Term1Hw2.entities;

public class Node {
	private String id;
	private String name;
	private int child_nodes;
	private int isLeaf;
	private int isLinkExist;
	private int isExtinct;
	private int confidence;
	private int phylesis;

	public Node() {
		this("default_id", "default_name", 0, 0, 0, 0, 0, 0);
	}

	public Node(String id, String name, int child_nodes, int isLeaf, int isLinkExist, int isExtinct, int confidence,
			int phylesis) {
		this.id = id;
		this.name = name;
		this.child_nodes = child_nodes;
		this.isLeaf = isLeaf;
		this.isLinkExist = isLinkExist;
		this.isExtinct = isExtinct;
		this.confidence = confidence;
		this.phylesis = phylesis;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getChild_nodes() {
		return child_nodes;
	}

	public void setChild_nodes(int child_nodes) {
		this.child_nodes = child_nodes;
	}

	public int isLeaf() {
		return isLeaf;
	}

	public void setLeaf(int isLeaf) {
		this.isLeaf = isLeaf;
	}

	public int isLinkExist() {
		return isLinkExist;
	}

	public void setLinkExist(int isLinkExist) {
		this.isLinkExist = isLinkExist;
	}

	public int getIsExtinct() {
		return isExtinct;
	}

	public void setIsExtinct(int isExtinct) {
		this.isExtinct = isExtinct;
	}

	public int getConfidence() {
		return confidence;
	}

	public void setConfidence(int confidence) {
		this.confidence = confidence;
	}

	public int getPhylesis() {
		return phylesis;
	}

	public void setPhylesis(int phylesis) {
		this.phylesis = phylesis;
	}

}
