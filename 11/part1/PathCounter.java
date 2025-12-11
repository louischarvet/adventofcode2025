import java.lang.Object;
import java.awt.geom.Point2D;
import java.awt.Point;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Map;
import java.util.HashMap;

import mypackage.FileReader;

// 136 too low

public class PathCounter {
	public static void	main(String args[]) {
		if (args.length != 1) {
			System.err.println("Usage: java PathCounter <filename>");
			return ;
		}
		FileReader reader = new FileReader(args[0]);
		List< String >	lines = reader.getFileLines();
		Map< String, List< String > >	linesMap = parseLines(lines);
		// for (Map.Entry< String, List < String > > entry : linesMap.entrySet()) {
		// 	System.out.print(entry.getKey() + ": ");
		// 	for (String s : entry.getValue()) {
		// 		System.out.print(s + " ");
		// 	}
		// 	System.out.println();
		// }
	//	Node	startingNode = new Node(linesMap, "you");

	//	startingNode.print();

	//	startingNode.countPaths();

	//	System.out.println("count = " + startingNode.getCount());

		NodeTree	nodeTree = new NodeTree(linesMap);

		nodeTree.print();

		nodeTree.countPaths();

		System.out.println("count = " + nodeTree.getCount());
	}

	private static Map< String, List< String > >	parseLines(List< String > lines) {
		Map< String, List< String > >	linesMap = new HashMap<>();

		for (String line : lines) {
			String	split[] = line.split(" ");
			String	key = split[0].substring(0, split[0].length() - 1);
			List< String >	nextNodes = new ArrayList<>();
			int	i = 1;

			while (i < split.length) {
				nextNodes.add(split[i]);
				i++;
			}
			linesMap.put(key, nextNodes);
		}

		return linesMap;
	}

	static class NodeTree {
		private Map< String, List< Node > >	_nodeTreePaths;
		private Set< String >	_nodeNames;
		private long	_pathCount;
		private Node	_nodes;

		public NodeTree(Map< String, List< String > > linesMap) {
			_nodeNames = new HashSet<>();
			_nodeNames.add("you");
			_nodeNames.add("out");

			_pathCount = 0;

//			_paths = new HashMap<>();
			setPaths(linesMap);
//			_nodes = new Node(linesMap, "you");
		}

		private void	setPaths(Map< String, List< String > > linesMap) {
			_nodeTreePaths = new HashMap<>();
			_nodeTreePaths.put("out", null);

			for (Map.Entry< String, List< String > > entry : linesMap.entrySet()) {
				String	key = entry.getKey();
				List< Node >	nodeList = new ArrayList<>();

				for (String s : entry.getValue()) {
					nodeList.add(new Node(linesMap, s));
				}
				_nodeTreePaths.put(key, nodeList);
			}
		}

		public void	countPaths() {
			_nodes.countPaths();
		}

		public long	getCount() { return _pathCount; }

		public void	print() {
			_nodes.print();
		}

		class Node {
			private String	_name;
			private Map< String, Node >	_paths;
			private boolean	_passed;

			public Node(Map< String, List< String > > linesMap, String currentName)  {
				_name = currentName;
				_paths = new HashMap<>();
			//	_paths.put("out", null);
				_passed = false;

			//	System.out.println("new node: " + currentName);
				if (! currentName.equals("out")) {
			
					List< String >	nextNodes = linesMap.get(currentName);
					for (String nextNode : nextNodes) {
					//	System.out.println("in constructor: " + nextNode);
					//	if (nextNode.equals(new String("out")))
					//		continue ;
					//	System.out.println("set: " + _nodeNames);
					//	System.out.println("nextNode: " + nextNode);
						if (_nodeTreePaths.containsKey(nextNode)) {
							_paths.put(nextNode, _nodeTreePaths.get(nextNode));
						} else {
							_paths.put(nextNode, new Node(linesMap, nextNode));
						}


						// if (_nodeNames.contains(nextNode)) {
						// 	System.out.println("contains " + nextNode + ": " + _paths.get(nextNode));
						// 	if (nextNode == "out")
						// 		_paths.put("out", null);
						// 	else
						// 		_paths.put(nextNode, _paths.get(nextNode));
						// } else { // la couille est la a mon avis
						// 	_nodeNames.add(nextNode);
						// 	_paths.put(nextNode, new Node(linesMap, nextNode));
						// } // else
						// // 	_paths.put(nextNode, null);
					}
				}
			}

			public Node(String name, Map< String, Node > paths) {
				this._name = name;
				this._paths = paths;
				this._passed = false;
			}

			public String	getName() { return _name; }
			public Map< String, Node >	getPaths() { return _paths; }
			public long	getCount() { return _pathCount; }

			public void	print() {
				System.out.print(_name + ": ");
				for (Map.Entry< String, Node > entry : _paths.entrySet()) {
					System.out.print(entry.getKey() + " ");
				}
				System.out.println();
				for (Map.Entry< String, Node > entry : _paths.entrySet()) {
				//	System.out.println("key: " + entry.getKey() + "\t" + "value: " + entry.getValue());
					if (entry.getValue() != null)
						entry.getValue().print();

				}
			}

			public void	countPaths() {
				// System.out.print(_name + " ");
				for (Map.Entry< String, Node > entry : _paths.entrySet()) {
					System.out.println("current: " + _name + " key: " + entry.getKey() + " value: " + entry.getValue());
					if (entry.getValue() != null)
						entry.getValue().countPaths();
					else {
						// System.out.println("out");
						_pathCount++;
					}
				}
			}
		}
	}
}