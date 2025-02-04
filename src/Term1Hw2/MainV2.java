package Term1Hw2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

import Term1Hw2.entities.Node;
import Term1Hw2.repository.NaryTree;
import Term1Hw2.repository.TreeNode;

public class MainV2 {

	public static void main(String[] args) {
		
		// hashtable that stores organism information by their IDs
		HashMap<String, Node> hashTableNodes = new HashMap<String, Node>();
		// hashtable to construct tree structure, it keeps TreeNodes by their IDs
		HashMap<String, TreeNode<Node>> hashTableLinks = new HashMap<String, TreeNode<Node>>();
		
		//Creating files and required variables
		String pathName = "treeoflife_nodes.txt";
		File fileNodes = new File(pathName);
		String path2 = "treeoflife_links.txt";
		File fileLinks = new File(path2);
		String preOrderPath = "pre-order.txt";
		File preOrderFile = new File(preOrderPath);
		String lineNodes = null;
		String lineLinks = null;
		String[] dataNodes = new String[6];
		
		//Reading datas from the given NodeFile
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
		
		
		// Creating the root node and TreeOfLife
		TreeNode<Node> rootTreeNode = new TreeNode<>(hashTableNodes.get("1"));
		NaryTree<Node> treeOfLife = new NaryTree<>(rootTreeNode.getData());
		treeOfLife.setRootNode(rootTreeNode);
		hashTableLinks.put("1", rootTreeNode);

		//Constructing Tree through Links file
		try (Scanner scanner = new Scanner(fileLinks)) {

			lineLinks = scanner.nextLine();
			String[] dataLinks = new String[2];
			//Reading parent nodes and saving them to Link Hashtable 
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				dataLinks = line.split(",");
				String parentNodeId = dataLinks[0];	
				String childNodeId = dataLinks[1];
				Node parentDataNode = hashTableNodes.get(parentNodeId);		//parent Node's datas
				Node childDataNode = hashTableNodes.get(childNodeId);		//child Nodes's datas
				TreeNode<Node> childTreeNode = new TreeNode<Node>();		//create a TreeNode for child
				
				// Checking whether the Parent Node is in the table or not
				if (hashTableLinks.containsKey(parentNodeId)) { 
					childTreeNode.setData(childDataNode);				      // Building child node's data
					hashTableLinks.get(parentNodeId).addChild(childTreeNode); // Saving child node into its Parents ChildList
					hashTableLinks.put(childNodeId, childTreeNode);			  //Saving Child Node into Link Hashtable 

				} 
				else {
					TreeNode<Node> newParentTreeNode = new TreeNode<Node>(parentDataNode);	// Creating Parent Node if it does not exist 
					newParentTreeNode.addChild(new TreeNode<Node>(childDataNode));			// Setting new Parent Node's Child
					hashTableLinks.put(parentNodeId, newParentTreeNode);					//Saving Parent Node into Link Hashtable 
					hashTableLinks.put(childNodeId, childTreeNode);							//Saving Child Node into Link Hashtable 
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		
		
		System.out.println(treeOfLife.getHeight());
//		System.out.println(rootTreeNode.getData().getName());
		System.out.println(treeOfLife.getNumberOfNodes());
		Iterator<Node> alii = treeOfLife.getPreorderIterator();

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(preOrderFile))) {
			while (alii.hasNext()) {
				writer.append(alii.next().getId() + "\n");
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}
	
	public void initializeTable() {
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
