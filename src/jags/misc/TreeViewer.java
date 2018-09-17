package jags.misc;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import beast.evolution.tree.Node;
import beast.util.Randomizer;

public class TreeViewer extends JPanel {
	static int N = 3; // number of sliders
	JPanel treePanel;
	JPanel controlPanel;
	//Tree tree;
	Node root;
	Node [] nodes;
	
	public TreeViewer() {
		setLayout(new BorderLayout());
		
		controlPanel = new ControlPanel();
		add(controlPanel, BorderLayout.WEST);
		treePanel = new TreePanel();
		add(treePanel, BorderLayout.CENTER);
	}
	
	public class TreePanel extends JPanel {
		@Override
		protected void paintComponent(Graphics g) {
			int w = getWidth();
			int h = getHeight();
			g.setColor(Color.white);
			g.fillRect(0, 0, w, h);
			g.setColor(Color.black);
			if (root == null) {
				g.drawString("no tree", w/2, h/2);
			} else {
				double [] x = new double[N*2+2];
				int leafCount = N+1;
				for (int i = 0; i < leafCount; i++) {
					x[i] = i/(leafCount - 1.0);
				}
				x[x.length-1] = 0.5;
				setX(root, x, null);
				g.setColor(Color.blue);

				double [] x2 = new double[N*2+2];
				System.arraycopy(x, 0, x2, 0, x2.length);
				//for (int i = 0; i < 10; i++) {
				traverse2(root, x, x2);
				//	System.arraycopy(x2, 0, x, 0, x2.length);
				//}
				x2[x2.length - 1] = x2[root.getChild(0).getNr()];
				
				Graphics2D g2 = (Graphics2D) g;
				g2.setStroke(new BasicStroke(1.0f));
				traverse(root, g, w, h, x2);
				
				g2.setStroke(new BasicStroke(3.0f));
				Node current = nodes[0];
				List<Node> path = new ArrayList<>(); 
				// path down to node 1
				while (current != root) {
					int i = current.getNr();
					int j = current.getParent().getNr();
					double hn = current.getHeight();
					double hp = current.getParent().getHeight();
					g.drawLine((int)(x2[i] * w)-5, (int)(h-hn * h), (int)(x2[j] * w)-5, (int)(h-hp * h));
					current = current.getParent();
					path.add(current);
				}
				for (int i = 1; i <= N; i++) {
					current = nodes[i];
					List<Node> path2 = new ArrayList<>();
					// path down to next leaf
					while (!path.contains(current)) {
						int k = current.getNr();
						int j = current.getParent().getNr();
						double hn = current.getHeight();
						double hp = current.getParent().getHeight();
						int X1 = (int)(x2[k] * w)-5;
						int Y1 = (int)(h-hn * h);
						int X2 = (int)(x2[j] * w)-5;
						int Y2 = (int)(h-hp * h);
						if (path.contains(current.getParent())) {
							// cull last few pixels from line
							double a = Math.atan2(Y2-Y1, X2 - X1);
							X2 = (int)(X2 - Math.cos(a) * 15);
							Y2 = (int)(Y2 - Math.sin(a) * 15);
						}
						if (Y1 != Y2)
							g.drawLine(X1, Y1, X2, Y2);							
						current = current.getParent();
						path2.add(current);
					}

					// path up from previous leaf
					Node other = nodes[i-1];
					while (other != current) {
						int k = other.getNr();
						int j = other.getParent().getNr();
						double hn = other.getHeight();
						double hp = other.getParent().getHeight();
						int X1 = (int)(x2[k] * w)+5;
						int Y1 = (int)(h-hn * h);
						int X2 = (int)(x2[j] * w)+5;
						int Y2 = (int)(h-hp * h);
						if (current == other.getParent()) {
							// cull last few pixels from line
							double a = Math.atan2(Y2-Y1, X2 - X1);
							X2 = (int)(X2 - Math.cos(a) * 15);
							Y2 = (int)(Y2 - Math.sin(a) * 15);
						}
						if (Y1 != Y2)
							g.drawLine(X1, Y1, X2, Y2);							

						other = other.getParent();						
					}
					
					while (current != root) {
						current = current.getParent();
						path2.add(current);
					}
					path = path2;
				}
				
				for (int i = 0; i < N; i++) {
					current = nodes[i];
					int k = current.getNr();
					int j = current.getParent().getNr();
					double hn = current.getHeight();
					double hp = current.getParent().getHeight();
					int X1 = (int)(x2[k] * w)+5;
					int Y1 = (int)(h-hn * h);
					int X2 = (int)(x2[j] * w)+5;
					int Y2 = (int)(h-hp * h);
					double a = Math.atan2(Y2-Y1, X2 - X1);
					
					g.setColor(Color.red);
					g2.drawArc((int)(x2[k] * w)-5, h-10, 10, 10, (int)(-180*a/Math.PI)-90, -180);//(int)(-180*a/Math.PI) + 180, (int)(-180*a/Math.PI));
				}

				
				
				
			}
		}
		
		
		private void traverse2(Node node, double[] x, double[] x2) {
			int i = node.getNr();
			if (node.isLeaf()) {
				x2[i] = x[i];
			} else {
				double maxHeight = 0;
				for (Node child : node.getChildren()) {
					traverse2(child, x, x2);
					if (child.getHeight() > maxHeight) {
						maxHeight = child.getHeight();
					}
				}
				if (!node.isRoot()) {
					int p = node.getParent().getNr();
					double w = (node.getHeight()-maxHeight) / (node.getParent().getHeight() - maxHeight);
					x2[i] = (1-w) * x[i] + w*x[p];
				}
			}
		}


		private void traverse(Node node, Graphics g, int scaleX, int scaleY, double [] x) {
			int i = node.getNr();
			if (!node.isRoot()) {
				int j = node.getParent().getNr();
				double h = node.getHeight();
				double hp = node.getParent().getHeight();
				g.drawLine((int)(x[i] * scaleX), (int)(scaleY-h * scaleY), (int)(x[j] * scaleX), (int)(scaleY-hp * scaleY));
			}
			if (!node.isLeaf()) {
				for (Node child : node.getChildren()) {
					traverse(child, g, scaleX, scaleY, x);
				}
			}
		}


		private int setX(Node node, double [] x, double [] sumLeafX) {
			if (node.isLeaf()) {
				sumLeafX[0] = x[node.getNr()];
				return 1;
			} else {
				int leafCount = 0;
				double sumX = 0;
				for (Node child : node.getChildren()) {
					double [] s = new double[1];
					leafCount += setX(child, x, s);
					sumX += s[0];
				}
				x[node.getNr()] = sumX / leafCount;
				if (sumLeafX != null) {
					sumLeafX[0] = sumX;
				}
				return leafCount;
			}
		}
		
	}
	
	public class ControlPanel extends JPanel {
		JSlider [] slider;
		
		public ControlPanel() {
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			
			slider = new JSlider[N];
			for (int i = 0; i < N; i++) {
				slider[i] = new JSlider(0, 100, Randomizer.nextInt(100));
				slider[i].addChangeListener(new ChangeListener() {				
					@Override
					public void stateChanged(ChangeEvent e) {
						updateTree();
					}
				});
				add(slider[i]);
			}
		}
		
		private void updateTree() {
			int [] values = new int [N];
			for (int i = 0; i < N; i++) {
				values[i] = slider[i].getValue();
			}
			System.out.println(Arrays.toString(values));
			
			nodes = new Node[N*2+2];
			for (int i = 0; i < nodes.length; i++) {
				nodes[i] = new Node();
				nodes[i].setNr(i);
				nodes[i].setHeight(0);
			}
			
			root = nodes[N*2+1];
			root.setHeight(1);
			root.addChild(nodes[0]);

			Node current = nodes[0];
			int next = N + 1;
			for (int i = 0; i < N; i++) {
				double target = values[i] / 100.0;
				while (target > current.getParent().getHeight()) {
					current = current.getParent();
				}
				Node parent = current.getParent();
				parent.removeChild(current);
				parent.addChild(nodes[next]);
				nodes[next].addChild(current);
				nodes[next].addChild(nodes[i+1]);
				nodes[next].setHeight(target);
				current = nodes[i+1];
				next++;
			}
			
			System.out.print(root.toNewick());
			treePanel.repaint();
			
		}
		
	}
	
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setSize(1024, 768);
		TreeViewer viewer = new TreeViewer();
		frame.add(viewer);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
