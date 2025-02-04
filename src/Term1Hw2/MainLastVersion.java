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
import java.util.Scanner;

import Term1Hw2.entities.Node;
import Term1Hw2.repository.NaryTree;
import Term1Hw2.repository.TreeNode;

public class MainLastVersion {

	public static void main(String[] args) {

		HashMap<String, Node> hashTableNodes = new HashMap<String, Node>();
//		hashTableNodes = initializeHashTableNodes();
		NaryTree<Node> treeOfLife = new NaryTree<Node>();
		HashMap<String, TreeNode<Node>> hashTableLinks = new HashMap<String, TreeNode<Node>>();
//		treeOfLife = initializeTreeOfLife(hashTableNodes, hashTableLinks);

		System.out.println("Menu\nChose a value:");
		System.out.println(
				"1- Create Tree \n2- Search species\n3- Traverse the Tree\n4- Print the Subtree\n5- Print the ancestor path\n6- Most recent common ancestor \n7- Features of Tree \n8- Print the longest paths \n0- Exit");
		Scanner scanner = new Scanner(System.in);
		int choice = -1;
		String id = null;

		while (true) {
			try {
				System.out.print("Enter your choice: ");
				choice = scanner.nextInt();
				scanner.nextLine();

				switch (choice) {
				case 1:
					System.out.println("Creating Tree...");
					hashTableNodes = initializeHashTableNodes();
					treeOfLife = initializeTreeOfLife(hashTableNodes, hashTableLinks);
					break;
				case 2:
					System.out.println("What is the ID that you want to search?");
					id = scanner.nextLine();
					System.out.println("Searching species:");
					Search(id, hashTableNodes);
					break;
				case 3:
					System.out.println("Traversing the Tree");
					Traverse(treeOfLife);
					break;
				case 4:
					System.out.println("What is the ID that you want to search?");
					id = scanner.nextLine();
					System.out.println("Printing the subtree");
					PrintSubtree(id, hashTableLinks);
					break;
				case 5:
					System.out.println("What is the ID that you want to search?");
					id = scanner.nextLine();
					System.out.println("Printing the ancestor path");
					PrintAncestors(id, hashTableLinks, treeOfLife);
					break;
				case 6:
					System.out.println("Please enter the first ID:");
					id = scanner.nextLine();
					System.out.println("Please enter the second ID:");
					String id2 = scanner.nextLine();
					System.out.println("Finding the most recent common ancestor");
					MostRecentCommonAncestor(id, id2, hashTableLinks, treeOfLife);
					break;
				case 7:
					System.out.println("Printing the features of the tree");
					featuresOfTree(hashTableLinks, treeOfLife);
					break;
				case 8:
					System.out.println("Printing the longest evolutionary paths:");
					longestPath(hashTableLinks, treeOfLife);
					break;
				case 0:
					System.out.println("Exiting the program...");
					return;
				default:
					System.out.println("Invalid choice. Please choose a valid option.");
					break;
				}

				// Eğer kullanıcı çıkış yapmadıysa menüyü tekrar yazdır
				System.out.println("\nMenu\nChoose a value:");
				System.out.println(
						"1- Create Tree \n2- Search species\n3- Traverse the Tree\n4- Print the Subtree\n5- Print the ancestor path\n6- Most recent common ancestor \n7- Features of Tree \n8- Print the longest paths\n0- Exit");

			} catch (Exception e) {
				System.out.println("An error occurred: " + e.getMessage());
			}			
		}
	}

	public static HashMap<String, Node> initializeHashTableNodes() {

		HashMap<String, Node> hashTableNodes = new HashMap<String, Node>();
		// Creating files and required variables
		String pathName = "treeoflife_nodes.txt";
		File fileNodes = new File(pathName);
		String lineNodes = null;
		String[] dataNodes = new String[7];

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
				int isExtinct = Integer.parseInt(dataNodes[5]);
				int confidence = Integer.parseInt(dataNodes[6]);
				int phylesis = Integer.parseInt(dataNodes[7]);
				Node node = new Node(id, name, child_nodes, isLeaf, isLinkExist, isExtinct, confidence, phylesis);
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
			System.out.println("\n\n\n\n\n\n\n\n");
		} catch (FileNotFoundException e) {
			System.out.println("An error detected while creating Tree " + e.getMessage());
			System.out.println("\n\n\n\n\n\n\n\n");
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
			isExtinct = node.getIsExtinct();
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
				System.out.println("Confidence: No");
			else if (levelOfConfidence == 1)
				System.out.println("Confidence: Yes ");
			else
				System.out.println("Confidence: Unspecified Position");
			if (isPhylesis == 0)
				System.out.println("Phylesis: No");
			else if (isPhylesis == 1)
				System.out.println("Phylesis: Yes");
			else
				System.out.println("Phylesis: Not Monophyletic");

			System.out.println("\n\n\n\n\n\n\n\n");
		} else {
			System.out.println("Not Found such an ID");
			System.out.println("\n\n\n\n\n\n\n\n");
		}
	}

	public static void Traverse(NaryTree<Node> treeOfLife) {
		String filePath = "pre-order.txt";
		File file = new File(filePath);

		if (!file.exists()) {
			try {
				if (file.createNewFile()) {
					System.out.println("PreOrder file created: " + file.getName());
				}
			} catch (IOException e) {
				System.err.println("Failed to create the file: " + e.getMessage());
				e.printStackTrace();
				return;
			}
		} else {
			System.out.println("PreOrder file already exists: " + file.getName());

		}

		try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));) {

			Iterator<Node> preOrderIterator = treeOfLife.getPreorderIterator();
			Node node = new Node();
			while (preOrderIterator.hasNext()) {
				node = preOrderIterator.next();
				bufferedWriter.append(node.getId() + " -> " + node.getName() + "\n");
			}

			System.out.println("Pre-order traversal has been written to: " + filePath);
			System.out.println("\n\n\n\n\n\n\n\n");
		} catch (IOException e) {
			System.out.println("Error has been detected while writing to the file" + e.getMessage());
			System.out.println("\n\n\n\n\n\n\n\n");
		}
	}

	public static void PrintSubtree(String Id, HashMap<String, TreeNode<Node>> hashTableLinks) {
		NaryTree<Node> subTree = new NaryTree<Node>();
		TreeNode<Node> givenType = hashTableLinks.get(Id);

		if (givenType == null) {
			System.out.println("There is no such an ID!!!!");
			System.out.println("\n\n\n\n\n\n\n\n");
			return;
		}
		subTree.setRootNode(givenType);
		Iterator<Node> preOrderIterator = subTree.getPreorderIterator();
		while (preOrderIterator.hasNext()) {
			Node temp = preOrderIterator.next();
			TreeNode<Node> childNode = hashTableLinks.get(temp.getId());
			int minusCount = calculateDepth(childNode, givenType);
			for (int i = 0; minusCount > i; i++) {
				System.out.print("-");
			}
			System.out.print(temp.getId() + "-" + temp.getName());
			if (temp.getIsExtinct() == 1)
				System.out.println("(-)");
			else
				System.out.println("(+)");
		}
		System.out.println("\n\n\n\n\n\n\n\n");

	}

	private static int calculateDepth(TreeNode<Node> node, TreeNode<Node> root) {
		int depth = 0;

		if (root == null) {
			return -1; //null root
		}
		if (root == node) {
			return 0; // reached the target node
		}
		for (TreeNode<Node> rootsChild : root.getChilds()) {
			depth = calculateDepth(node, rootsChild);
			if (depth != -1) {
				return depth + 1; // reached the target node 
			}
		}
		return -1;// if target is not the child of that subtree then -1
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
			System.out.println("\n\n\n\n\n\n\n\n");
			return;
		}

		for (int i = ancestorList.size() - 1; i >= 0; i--) {
			int minusCount = calculateDepth(ancestorList.get(i), ancestorList.getLast());
			for (int j = 0; minusCount > j; j++) {
				System.out.print("-");
			}
			System.out.print(ancestorList.get(i).getData().getId() + " " + ancestorList.get(i).getData().getName());
			if (ancestorList.get(i).getData().getIsExtinct() == 1)
				System.out.println("(-)");
			else
				System.out.println("(+)");
		}
		System.out.println("\n\n\n\n\n\n\n\n");
	}

	public static void MostRecentCommonAncestor(String Id1, String Id2, HashMap<String, TreeNode<Node>> hashTableLinks,
			NaryTree<Node> treeOfLife) {

		if (!hashTableLinks.containsKey(Id1) || !hashTableLinks.containsKey(Id2)) {
			System.out.println("One or both nodes do not exist in the tree.");
			System.out.println("\n\n\n\n\n\n\n\n");
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
					System.out.println("\n\n\n\n\n\n\n\n");
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
		int degree = 0;

		while (treeIterator.hasNext()) {
			contNode = hashTableLinks.get(treeIterator.next().getId());
			if (contNode.isLeaf()) {
				sum++;
			}
		}

		int tempDegree = 0;
		treeIterator = treeOfLife.getPreorderIterator();
		while (treeIterator.hasNext()) {
			tempDegree = hashTableLinks.get(treeIterator.next().getId()).getChilds().size();
			if (tempDegree > degree) {
				degree = tempDegree;
			}
		}

		System.out.println("Height: " + treeOfLife.getHeight());
		System.out.println("Degree: " + degree);
		System.out.println("Leaf count: " + sum);
		System.out.println("\n\n\n\n\n\n\n\n");
	}

	public static void longestPath(HashMap<String, TreeNode<Node>> hashTableLinks, NaryTree<Node> treeOfLife) {

		Iterator<Node> treeIterator = treeOfLife.getPreorderIterator();
//		List<TreeNode<Node>> longestPath = new ArrayList<TreeNode<Node>>();
		List<TreeNode<Node>> controlList = new ArrayList<TreeNode<Node>>();
//		List<List<TreeNode<Node>>> longestPathsList = new ArrayList<List<TreeNode<Node>>>();
		TreeNode<Node> isLeafControlNode = new TreeNode<Node>();
		int maxHeight = treeOfLife.getHeight();
		int count = 0;

		while (treeIterator.hasNext()) {
			isLeafControlNode = hashTableLinks.get(treeIterator.next().getId());
			if (isLeafControlNode.isLeaf()) {
				controlList = ancestorList(isLeafControlNode.getData().getId(), hashTableLinks, treeOfLife);
				if (controlList.size() == maxHeight) {
					count++;
//					longestPathsList.add(longestPath);
					System.out.println("Longest Paths:");
					System.out.println("Path for ID: " + isLeafControlNode.getData().getId() + " "
							+ isLeafControlNode.getData().getName());
					for (int i = maxHeight - 1; i >= 0; i--) {
						System.out.print(controlList.get(i).getData().getId() + " -> "
								+ controlList.get(i).getData().getName() + " ");
					}
					System.out.println("\n");
				}
			}
			if (count == 4) {
				break;
			}
		}

		System.out.println("There are " + count + " Evolutionary path which has " + maxHeight + " height");

	}

}
