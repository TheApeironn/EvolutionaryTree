package Term1Hw2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;

import Term1Hw2.entities.Node;
import Term1Hw2.repository.LinkNode;
import Term1Hw2.repository.NaryTree;
import Term1Hw2.repository.TreeNode;

public class Main {

	public static void main(String[] args) {

		HashMap<String, Node> hashTableNodes = new HashMap<String, Node>();
		HashMap<LinkNode, TreeNode<Node>> hashTableLinks = new HashMap<LinkNode, TreeNode<Node>>();
		
		
		String pathName = "treeoflife_nodes.txt";
		File fileNodes = new File(pathName);
		String path2 = "treeoflife_links.txt";
		File fileLinks = new File(path2);
		BufferedReader bufferedReader = null;
		String lineNodes = null;
		String lineLinks = null;
		String[] dataNodes = new String[6];
		int count = 0;
		int counter = 0;
		try {
			bufferedReader = new BufferedReader(new FileReader(fileNodes));
			lineNodes = bufferedReader.readLine();
			while ((lineNodes = bufferedReader.readLine()) != null) {
				count++;
				dataNodes = lineNodes.split("\t");
//				for (String string : data) {
//					System.out.println(string);
//				}

				String id = dataNodes[0];
				String name = dataNodes[1];
				int child_nodes = Integer.parseInt(dataNodes[2]);
				int isLeaf = Integer.parseInt(dataNodes[3]);
				int isLinkExist = Integer.parseInt(dataNodes[4]);
				int confidence = Integer.parseInt(dataNodes[5]);
				int phylesis = Integer.parseInt(dataNodes[6]);
				Node node = new Node(id, name, child_nodes, isLeaf, isLinkExist, confidence, phylesis);
				hashTableNodes.put(id, node);

				System.out.println(count);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e1) {
					System.out.println(e1.getMessage());
				}
			}
		}

		
		Node rootNodeData = hashTableNodes.get("1"); 
		LinkNode linkNode = new LinkNode("1", "2");
		TreeNode<Node> rootTreeNode = new TreeNode<>(rootNodeData);  
		hashTableLinks.put(linkNode, rootTreeNode);

		
		NaryTree<Node> treeOfLife = new NaryTree<>(rootNodeData);
		treeOfLife.setRootNode(rootTreeNode); 

		
		try (Scanner scanner = new Scanner(fileLinks)) {

			List<TreeNode<Node>> childList = new ArrayList<TreeNode<Node>>();
			lineLinks = scanner.nextLine();
			String[] dataLinks = new String[2];
			
			 while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
			    dataLinks = line.split(",");
			    
				String sourceNodeId = dataLinks[0];
				String targetNodeId = dataLinks[1];
				linkNode = new LinkNode(sourceNodeId, targetNodeId);
				
				counter++;
				System.out.println(counter);

				Node sourceNode = hashTableNodes.get(linkNode.getSourceID());
				Node targetNode = hashTableNodes.get(linkNode.getTargetID());
				TreeNode<Node> tempNode = new TreeNode<Node>();

				if (hashTableLinks.containsKey(linkNode)) {
					tempNode = hashTableLinks.get(linkNode);
					tempNode.addChild(new TreeNode<Node>(targetNode));
					
				} else {
					TreeNode<Node> newNode = new TreeNode<Node>(sourceNode);
					
					newNode.addChild(new TreeNode<Node>(targetNode));
					hashTableLinks.put(linkNode, newNode);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		System.out.println(treeOfLife.getHeight());
		System.out.println(rootTreeNode.getData());
		for (TreeNode<Node> ali : rootTreeNode.getChilds()) {
			System.out.println(ali.getData().getName());
		}
		

	}

}
