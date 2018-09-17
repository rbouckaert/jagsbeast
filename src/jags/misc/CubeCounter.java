package jags.misc;

import java.io.IOException;
import java.util.*;

import beast.app.treeannotator.TreeAnnotator;
import beast.app.treeannotator.TreeAnnotator.FastTreeSet;
import beast.app.util.Application;
import beast.app.util.TreeFile;
import beast.core.Description;
import beast.core.Input;
import beast.core.Input.Validate;
import beast.evolution.tree.Node;
import beast.evolution.tree.Tree;

@Description("Heuristic attempt to count number of cubes covering posterior tree set")
public class CubeCounter extends beast.core.Runnable {

	final public Input<TreeFile> treesInput = new Input<>("trees", "beast.trees on which this operation is performed", Validate.REQUIRED);
	final public Input<String> orderInput = new Input<>("order", "space and/or pipe delimited string of taxa");
	private static final long serialVersionUID = 1L;
	
	
	@Override
	public void run() throws Exception {

		FastTreeSet trees = new TreeAnnotator().new FastTreeSet(treesInput.get().getAbsolutePath(), 0);
		trees.reset();
		Tree tree = trees.next();
		int n = tree.getLeafNodeCount();
		int [] order = new int[n];
		
		if (orderInput.get() == null) {
			double [][] fDist = new double[n][n];
	
			trees.reset();
			while (trees.hasNext()) {
				tree = trees.next();
				calcDistance(tree.getRoot(), fDist, 1.0, new Vector<Integer>(), new Vector<Double>());
			}
			int [] revOrder = closestOutsideFirst(fDist);
		    for (int i = 0; i < n ; i++) {
		    	order[revOrder[i]] = i;
		    }
		} else {
			String s = orderInput.get();
			StringBuilder b = new StringBuilder();
			for (int i = 0; i < s.length(); i++) {
				char c = s.charAt(i);
				if (c == '|') {
					c = ' ';
				}
				b.append(c);
			}
			String [] strs = b.toString().trim().split("\\s+");
			for (int i = 0; i < n; i++) {
				order[i] = Integer.parseInt(strs[i]);
			}
		}
	    for (int i = 0; i < n; i++) {
	    	System.out.print(tree.getTaxaNames()[order[i]] + " ");
	    }
	    System.out.println();

	    Set<BitSet> ordermap = order2orderMap(order);
	    
		trees.reset();
		List<Boolean> compatible = new ArrayList<>();
		int k = 0;
		while (trees.hasNext()) {
			tree = trees.next();
			if (isCompatible(tree.getRoot(), ordermap) != null) {
				k++;
				compatible.add(true);
			} else {
				compatible.add(false);
			}
		}
		System.out.println(k + " out of " + compatible.size() + " (" + (k*100.0/compatible.size()) + " %) are compatible with closestOutsideFirst ordering");	    
	}
	
	
	/**Order nodes by starting with two closest nodes. 
	 * Then add node left or right that is closest to the
	 * most left or most right node respectively, till all nodes are ordered.
	 * @param fDist
	 * @return
	 */
	int [] closestOutsideFirst(double [][] fDist) {
		int n = fDist.length;
		int [] nOrder = new int[n];
		boolean [] bDone = new boolean[n];
		
		// find the closest pair
		int iMax = 0;
		int jMax = 1;
		double fMax = fDist[0][1];
		for (int i = 0; i < n; i++) {
			for (int j = i+1; j < n; j++) {
				if (fDist[i][j] < fMax) {
					fMax = fDist[i][j];
					iMax = i;
					jMax = j;
				}
			}
		}
		nOrder[0] = iMax;
		nOrder[1] = jMax;
		bDone[iMax] = true;
		bDone[jMax] = true;
		// find the order of remaining nodes
		for (int k = 2; k < n; k++) {
			iMax = -1;
			jMax = -1;
			fMax = Double.MAX_VALUE;
			for (int i = 0; i < n; i++) {
				if (!bDone[i]) {
					if (fDist[nOrder[k-1]][i] < fMax) {
						fMax = fDist[nOrder[k-1]][i];
						iMax = k-1;
						jMax = i;
					}
					if (fDist[nOrder[0]][i] < fMax) {
						fMax = fDist[nOrder[0]][i];
						iMax = 0;
						jMax = i;
					}
				}
			}
			if (iMax == k-1) {
				nOrder[k] = jMax;
				bDone[jMax] = true;
			} else if (iMax == 0) {
				for (int j = k; j >0; j--) {
					nOrder[j] = nOrder[j-1];
				}
				nOrder[0] = jMax;
				bDone[jMax] = true;
			} else {
				System.err.println("Something's wrong");
			}
		}	
		return nOrder;
	} // closestOutsideFirst
	
	/** calculate the distance between leafs in a consensus tree
	 * and update the distance matrix weighted with the relative
	 * frequency of the tree
	 * @param node: current node
	 * @param nOrder: mapping of node label to [0,...,NrOfLeafs-1]
	 * @param fDistMatrix: distance matrix to be updated
	 * @param fWeight: relative consensus tree frequency
	 * @param iLabel: used to report set of leafs in sub tree below node
	 * @param fLength: used to report set of lengths from current node to leafs in iLabel
	 */
	void calcDistance(Node node, /*int [] nOrder, */double[][] fDistMatrix, double fWeight, Vector<Integer> iLabel, Vector<Double> fLength) {
		if (node == null) {
			return;
		}
		if (node.isLeaf()) {
			//iLabel.add(nOrder[node.m_iLabel]);
			iLabel.add(node.getNr());
			//fLength.add(node.m_fLength);
			fLength.add(node.getLength() + 1.0f);
		} else {
			Vector<Integer> iLeft = new Vector<>();
			Vector<Integer> iRight = new Vector<>();
			Vector<Double> fLeft = new Vector<>();
			Vector<Double> fRight = new Vector<>();
			calcDistance(node.getLeft(), /*nOrder, */fDistMatrix, fWeight, iLeft, fLeft);
			calcDistance(node.getRight(), /*nOrder, */fDistMatrix, fWeight, iRight, fRight);
			for (int i = 0; i < iLeft.size(); i++) {
				int i1 = iLeft.elementAt(i);
				double f = fWeight * fLeft.elementAt(i);
				for (int j = 0; j < iRight.size(); j++) {
					int i2 = iRight.elementAt(j);
					double f2 = f + fWeight * fRight.elementAt(j);
					fDistMatrix[i1][i2] += f2;
					fDistMatrix[i2][i1] += f2;
				}
			}
			for (int i = 0; i < fLeft.size(); i++) {
				iLabel.add(iLeft.elementAt(i));
				//fLength.add(fLeft.elementAt(i) + node.m_fLength);
				fLength.add(fLeft.elementAt(i) + node.getLength() + 1.0f);
				//fLength.add(fLeft.elementAt(i) + 1.0f);
			}
			for (int i = 0; i < fRight.size(); i++) {
				iLabel.add(iRight.elementAt(i));
				//fLength.add(fRight.elementAt(i) + node.m_fLength);
				fLength.add(fRight.elementAt(i) + node.getLength() + 1.0f);
				//fLength.add(fRight.elementAt(i) + 1.0f);
			}
		}
	} // calcDistance
	
	/** create orderMap[clade size][lowest node nr][] of sorted clades compatible with order
	 * If a clade is compatible with this order, there will be an entry in ordermap containing
	 * a sorted list of the taxa numbers 
	 * @param order : permutation of unique integers from 0 to order.length-1 
	 */
	Set<BitSet> order2orderMap(int [] order) {
		Set<BitSet> clades = new HashSet<>();
		for (int size = 2; size < order.length; size++) {

			for (int j = 0; j < order.length - size + 1; j++) {
				BitSet bs = new BitSet(order.length);
				for (int k = 0; k < size; k++) {
					bs.set(order[j + k]);
				}
				clades.add(bs);
			}
		}
		return clades;
	}
	
	
	/** 
	 * return nodes in clades below node, or null if not compatible with ordermap 
	 * **/
	BitSet isCompatible(Node node, Set<BitSet> ordermap) {
		if (node.isLeaf()) {
			BitSet bs = new BitSet();
			bs.set(node.getNr());
			return bs;
		} else {
			BitSet left = isCompatible(node.getLeft(), ordermap);
			if (left == null) {
				return null;
			}
			BitSet right = isCompatible(node.getRight(), ordermap);
			if (right == null) {
				return null;
			}
			if (node.isRoot()) {
				return left;
			}

			left.or(right);
			if (ordermap.contains(left)) {
				return left;
			}
			return null;
		}
		
	}

	String file;
	
	public CubeCounter() throws IOException {
	}

	public static void main(String[] args) throws Exception {
		new Application(new CubeCounter(), "Cube Counter", args);
	}

}
