package Term1Hw2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Scanner;

import Term1Hw2.entities.Node;
import Term1Hw2.repository.NaryTree;
import Term1Hw2.repository.TreeNode;

public class MainV4 {

	public static void main(String[] args) {

		HashMap<String, Node> hashTableNodes = new HashMap<String, Node>();
		hashTableNodes = initializeHashTableNodes();

		
		NaryTree<Node> treeOfLife = new NaryTree<Node>();
//		treeOfLife = initializeTree();
		
		HashMap<String, TreeNode<Node>> hashTableLinks = new HashMap<String, TreeNode<Node>>();
		treeOfLife = initializeTreeOfLife(hashTableNodes,hashTableLinks);
		
		
		System.out.println(treeOfLife.getHeight());
		System.out.println(treeOfLife.getNumberOfNodes());

	}

	public static HashMap<String, Node> initializeHashTableNodes() {

		HashMap<String, Node> hashTableNodes = new HashMap<String, Node>();
		// Creating files and required variables
		String pathName = "treeoflife_nodes.txt";
		File fileNodes = new File(pathName);
		String lineNodes = null;
		String[] dataNodes = new String[6];

		// Reading datas from the given NodeFile
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileNodes))) {
			lineNodes = bufferedReader.readLine();
			while ((lineNodes = bufferedReader.readLine()) != null) {

				dataNodes = lineNodes.split("\t");
				String id = dataNodes[0];
				String name = dataNodes[1];
				int child_nodes = Integer.parseInt(dataNodes[2]);
				int isLeaf = Integer.parseInt(dataNodes[3]);
				int isLinkExist = Integer.parseInt(dataNodes[4]);
				int confidence = Integer.parseInt(dataNodes[5]);
				int phylesis = Integer.parseInt(dataNodes[6]);
				Node node = new Node(id, name, child_nodes, isLeaf, isLinkExist, confidence, phylesis);
				hashTableNodes.put(id, node);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return hashTableNodes;
	}

	
	
	public static  NaryTree<Node>  initializeTreeOfLife(HashMap<String, Node> hashTableNodes, HashMap<String, TreeNode<Node>> hashTableLinks) {

		// hashtable to construct tree structure, it keeps TreeNodes by their IDs

		NaryTree<Node> treeOfLife = new NaryTree<Node>();
		TreeNode<Node> rootNode = new TreeNode<>(hashTableNodes.get("1"));
		treeOfLife.setRootNode(rootNode);
		rootNode = treeOfLife.getRootNode();
		hashTableLinks.put("1", rootNode);
		
		// Creating files and required variables
		String path2 = "treeoflife_links.txt";
		File fileLinks = new File(path2);
		String lineLinks = null;
		String[] dataLinks = new String[2];
		
		// Constructing Tree through Links file
		try (Scanner scanner = new Scanner(fileLinks)) {

			lineLinks = scanner.nextLine();
			// Reading parent nodes and saving them to Link Hashtable
			while (scanner.hasNextLine()) {
				lineLinks = scanner.nextLine();
				dataLinks = lineLinks.split(",");
				String parentNodeId = dataLinks[0];
				String childNodeId = dataLinks[1];
				Node parentDataNode = hashTableNodes.get(parentNodeId); // parent Node's datas
				Node childDataNode = hashTableNodes.get(childNodeId); // child Nodes's datas
				TreeNode<Node> childTreeNode = new TreeNode<Node>(); // create a TreeNode for child

				// Checking whether the Parent Node is in the table or not
				if (hashTableLinks.containsKey(parentNodeId)) {
					childTreeNode.setData(childDataNode); // Building child node's data
					hashTableLinks.get(parentNodeId).addChild(childTreeNode); // Saving child node into its Parents
																				// ChildList
					hashTableLinks.put(childNodeId, childTreeNode); // Saving Child Node into Link Hashtable

				} else {
					TreeNode<Node> newParentTreeNode = new TreeNode<Node>(parentDataNode); // Creating Parent Node if it does not exist
					newParentTreeNode.addChild(new TreeNode<Node>(childDataNode)); // Setting new Parent Node's Child
					hashTableLinks.put(parentNodeId, newParentTreeNode); // Saving Parent Node into Link Hashtable
					hashTableLinks.put(childNodeId, childTreeNode); // Saving Child Node into Link Hashtable
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return treeOfLife;
	}

}
