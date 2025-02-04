package Term1Hw2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;

import Term1Hw2.entities.Node;
import Term1Hw2.repository.NaryTree;
import Term1Hw2.repository.TreeNode;

public class MainV6 {

	public static void main(String[] args) {

		HashMap<String, Node> hashTableNodes = new HashMap<String, Node>();
		hashTableNodes = initializeHashTableNodes();

		NaryTree<Node> treeOfLife = new NaryTree<Node>();
//		treeOfLife = initializeTree();

		HashMap<String, TreeNode<Node>> hashTableLinks = new HashMap<String, TreeNode<Node>>();
		treeOfLife = initializeTreeOfLife(hashTableNodes, hashTableLinks);

		System.out.println(treeOfLife.getHeight());
		System.out.println(treeOfLife.getNumberOfNodes());

		Search("16299", hashTableNodes);
		System.out.println("\n\n\n\n\n\n\n");
		PrintSubtree("16299", hashTableLinks);
		System.out.println("\n\n\n\n\n\n\n");
		PrintAncestors("16421", hashTableLinks, treeOfLife);
		System.out.println("\n");
		System.out.println(getParentNode("16421", hashTableLinks, treeOfLife).getData().getName());
		MostRecentCommonAncestor("16421", "16954", hashTableLinks, treeOfLife);
		System.out.println("\n\n\n\n\n\n\n");
		featuresOfTree(hashTableLinks, treeOfLife);
		System.out.println("\n\n\n\n\n\n\n");
		longestPath(hashTableLinks, treeOfLife);
		
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

	public static NaryTree<Node> initializeTreeOfLife(HashMap<String, Node> hashTableNodes,
			HashMap<String, TreeNode<Node>> hashTableLinks) {

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
					TreeNode<Node> newParentTreeNode = new TreeNode<Node>(parentDataNode); // Creating Parent Node if it
																							// does not exist
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

	public static void Search(String speciesID, HashMap<String, Node> hashTableNodes) {

		String name = null;
		int childCount = 0;
		int isLeaf = 0;
		int isLink = 0;
		int isExtinct = 0;
		int levelOfConfidence = 0;
		int isPhylesis = 0;
		Node node = new Node();

		if (hashTableNodes.containsKey(speciesID)) {
			node = hashTableNodes.get(speciesID);
			name = node.getName();
			childCount = node.getChild_nodes();
			isLeaf = node.isLeaf();
			isLink = node.isLinkExist();
			levelOfConfidence = node.getConfidence();
			isPhylesis = node.getPhylesis();

			System.out.println("ID: " + speciesID);
			System.out.println("Name: " + name);
			System.out.println("Child Count: " + childCount);
			if (isLeaf == 1)
				System.out.println("Laf Node: Yes");
			else
				System.out.println("Leaf Node: No");
			if (isLink == 1)
				System.out.println("Link: http://tolweb.org/" + name.replace(" ", "_") + "/" + speciesID);
			else
				System.out.println("Link: No Link");
			if (isExtinct == 1)
				System.out.println("Extinct: Yes");
			else
				System.out.println("Extinct: No");
			if (levelOfConfidence == 0)
				System.out.println("Confidence: Yes");
			else if (levelOfConfidence == 1)
				System.out.println("Confidence: Problematic Position ");
			else
				System.out.println("Confidence: No");
			if (isPhylesis == 0)
				System.out.println("Phylesis: Monophyletic");
			else if (isPhylesis == 1)
				System.out.println("Phylesis: Uncertain Monophyly");
			else
				System.out.println("Phylesis: Not Monophyletic");
		} else {
			System.out.println("Not Found ");
		}
	}

	public static void Traverse(NaryTree<Node> treeOfLife) {
		String filePath = "pre-order.txt";
		File file = new File(filePath);

		try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));) {

			if (file.createNewFile()) {
				System.out.println("PreOrder file could not created: " + file.getName());
			} else {
				System.out.println("PreOrder file is exist");
			}
			Iterator<Node> preOrderIterator = treeOfLife.getPreorderIterator();
			while (preOrderIterator.hasNext()) {
				bufferedWriter.append(preOrderIterator.next().getId() + "\n");
			}

		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public static void PrintSubtree(String Id, HashMap<String, TreeNode<Node>> hashTableLinks) {
		NaryTree<Node> subTree = new NaryTree<Node>();
		TreeNode<Node> givenType = hashTableLinks.get(Id);

		subTree.setRootNode(givenType);
		Iterator<Node> preOrderIterator = subTree.getPreorderIterator();
		while (preOrderIterator.hasNext()) {
			Node temp = preOrderIterator.next();
			System.out.println(temp.getId() + "-" + temp.getName());
		}

	}

	public static TreeNode<Node> getParentNode(String Id, HashMap<String, TreeNode<Node>> hashTableLinks,
			NaryTree<Node> treeOfLife) {
		TreeNode<Node> givenChild = hashTableLinks.get(Id);
		Iterator<Node> treeIterator = treeOfLife.getPreorderIterator();
		TreeNode<Node> parentControlNode = new TreeNode<Node>();

		if (givenChild == null) {
			return null;
		}

		while (treeIterator.hasNext()) {
			parentControlNode = hashTableLinks.get(treeIterator.next().getId());
			if (parentControlNode.getChilds().contains(givenChild)) {
				return parentControlNode;
			}
		}
		return null;
	}

	public static List<TreeNode<Node>> ancestorList(String Id, HashMap<String, TreeNode<Node>> hashTableLinks,
			NaryTree<Node> treeOfLife) {

		List<TreeNode<Node>> ancestorList = new ArrayList<TreeNode<Node>>();
		TreeNode<Node> givenChild = hashTableLinks.get(Id);
		TreeNode<Node> parentNode = new TreeNode<Node>();
		ancestorList.add(givenChild);

		if (givenChild == null) {
			return ancestorList; 
		}

		while ((parentNode = getParentNode(givenChild.getData().getId(), hashTableLinks, treeOfLife)) != null) {
			ancestorList.add(parentNode);
			givenChild = parentNode;
		}
		return ancestorList;
	}

	public static void PrintAncestors(String Id, HashMap<String, TreeNode<Node>> hashTableLinks,
			NaryTree<Node> treeOfLife) {
		List<TreeNode<Node>> ancestorList = ancestorList(Id, hashTableLinks, treeOfLife);

		if (ancestorList == null || ancestorList.isEmpty()) {
			System.out.println("This node has not Parent Node");
			return;
		}
		
		for (int i = ancestorList.size() - 1; i >= 0; i--) {
			System.out.println(ancestorList.get(i).getData().getId() + " " + ancestorList.get(i).getData().getName());
		}
	}

	public static void MostRecentCommonAncestor(String Id1, String Id2, HashMap<String, TreeNode<Node>> hashTableLinks,
			NaryTree<Node> treeOfLife) {

		if (!hashTableLinks.containsKey(Id1) || !hashTableLinks.containsKey(Id2)) {
			System.out.println("One or both nodes do not exist in the tree.");
			return;
		}

		List<TreeNode<Node>> ancestorList1 = ancestorList(Id1, hashTableLinks, treeOfLife);
		List<TreeNode<Node>> ancestorList2 = ancestorList(Id2, hashTableLinks, treeOfLife);
		boolean flag = false;

		for (TreeNode<Node> treeNode : ancestorList1) {
			for (TreeNode<Node> treeNode2 : ancestorList2) {
				if (treeNode.getData().getId() == treeNode2.getData().getId()) {
					System.out.println("The most recent common ancestor of " + Id1 + "-"
							+ hashTableLinks.get(Id1).getData().getName() + " and  " + Id2 + "-"
							+ hashTableLinks.get(Id2).getData().getName() + "  is " + treeNode2.getData().getId() + "-"
							+ treeNode2.getData().getName());
					flag = true;
					break;
				}
				if (flag) {
					break;
				}
			}
		}

	}

	public static void featuresOfTree(HashMap<String, TreeNode<Node>> hashTableLinks, NaryTree<Node> treeOfLife) {
		Iterator<Node> treeIterator = treeOfLife.getPreorderIterator();
		TreeNode<Node> contNode = new TreeNode<Node>();
		int sum = 0;

		while (treeIterator.hasNext()) {
			contNode = hashTableLinks.get(treeIterator.next().getId());
			if (contNode.isLeaf()) {
				sum++;
			}
		}
		System.out.println(treeOfLife.getHeight());
		System.out.println(treeOfLife.getRootNode().getNumberOfNodes());
		System.out.println(sum);
	}

	public static void longestPath(HashMap<String, TreeNode<Node>> hashTableLinks, NaryTree<Node> treeOfLife) {
		

		Iterator<Node> treeIterator = treeOfLife.getPreorderIterator();
		List<TreeNode<Node>> longestPath = new ArrayList<TreeNode<Node>>();
		List<TreeNode<Node>> controlList = new ArrayList<TreeNode<Node>>();
		HashMap<String, List<TreeNode<Node>>> longestPathsHashMap = new HashMap<>();
		TreeNode<Node> isLeafControlNode = new TreeNode<Node>();
		int maxHeight = 0;
		
		long ft = System.currentTimeMillis();
		while(treeIterator.hasNext()) {
			isLeafControlNode = hashTableLinks.get(treeIterator.next().getId());
			if(isLeafControlNode.isLeaf()) {
				controlList = ancestorList(isLeafControlNode.getData().getId(), hashTableLinks, treeOfLife);
				if (controlList.size() >= maxHeight) {
					longestPath = controlList;
					maxHeight = longestPath.size();
					longestPathsHashMap.put(isLeafControlNode.getData().getId(), longestPath);		
				}
			}
		}
		long lt = System.currentTimeMillis();
		

		System.out.println("Longest Paths:");
		for (Entry<String, List<TreeNode<Node>>> entry : longestPathsHashMap.entrySet()) {
		    if (entry.getValue().size() == maxHeight) {
		        System.out.println("Path for Node ID: " + entry.getKey() + " " + hashTableLinks.get(entry.getKey()).getData().getName());
		        List<TreeNode<Node>> path = entry.getValue();
		        for (int i = path.size() - 1; i >= 0; i--) {
		            System.out.println(path.get(i).getData().getId() + " - " + path.get(i).getData().getName());
		        }
		        System.out.println("\n\n\n\n\n");
		    }
		}
		
		System.out.println("Time taken: " + (lt - ft) + " ms");
		
	}

}
