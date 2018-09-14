package jags.misc;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TreeCounter {
	Map<String, Integer> map;
	
	
	public TreeCounter() {
		map = new HashMap<>();
		int k = 0;
		map.put("((3,4),(1,2))", k);
		map.put("((1,2),(3,4))", k++);
		map.put("((2,4),(1,3))", k);
		map.put("((1,3),(2,4))", k++);
		map.put("((2,3),(1,4))", k);
		map.put("((1,4),(2,3))", k++);
		
		map.put("(((1,2),3),4)", k++);
		map.put("(((1,2),4),3)", k++);
		map.put("(((1,3),2),4)", k++);
		map.put("(((1,3),4),2)", k++);
		map.put("(((1,4),2),3)", k++);
		map.put("(((1,4),3),2)", k++);
		map.put("(((2,3),1),4)", k++);
		map.put("(((2,3),4),1)", k++);
		map.put("(((2,4),1),3)", k++);
		map.put("(((2,4),3),1)", k++);
		map.put("(((3,4),1),2)", k++);
		map.put("(((3,4),2),1)", k++);
		
	}

	void run() {
		int [] order = new int[4];
		boolean [] done = new boolean[4];
		
		for (int i1 = 0; i1 < 4; i1++) {
			order[0] = i1+1;
			done[i1] = true;
			for (int i2 = 0; i2 < 4; i2++) {
				if (!done[i2]) {
				order[1] = i2+1;
				done[i2] = true;
				for (int i3 = 0; i3 < 4; i3++) {
					if (!done[i3]) {
					order[2] = i3+1;
					done[i3] = true;
					for (int i4 = 0; i4 < 4; i4++) {
						if (!done[i4]) {
						order[3] = i4+1;
						coverage(order);
						}
					}
					done[i3] = false;
					}
				}
				done[i2] = false;
				}
			}
			done[i1] = false;			
		}
	}
	
	
	int [][] coverage = new int[][]{
		
		{0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1}, // [1, 3, 4, 2]
		{0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0}, // [3, 1, 2, 4]
		{1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1}, // [1, 2, 4, 3]
		{1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1}, // [1, 2, 3, 4]
		{0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 1, 0, 0}, // [1, 3, 2, 4]
		{0, 0, 1, 0, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0}, // [2, 3, 1, 4]
		{1, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0},// [2, 1, 4, 3]
		{0, 0, 1, 1, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0}, // [3, 2, 1, 4]
		{0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 1, 1, 0, 0}, // [1, 4, 2, 3]
		{0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 1, 1}, // [1, 4, 3, 2]
		{0, 1, 0, 0, 0, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0}, // [2, 4, 1, 3]
		{1, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0}, // [2, 1, 3, 4]
	};
		
	int [][] coverageMap;	
	int optDepth = 12;

	//      0 out of   20736 ways (0.00% of 4 orderings) to cover with 4 orderings
	//  15840 out of  248832 ways (6.36% of 5 orderings) to cover with 5 orderings
	// 393120 out of 2985984 ways (13.2% of 6 orderings) to cover with 6 orderings

     	

	void findCoverage() {
		coverageMap = new int[12][5];
		for (int i = 0; i < 12; i++) {
			int k = 0;
			for (int j = 0; j < 15; j++) {
				if (coverage[i][j] > 0) {
					coverageMap[i][k++] = j;
				}
			}
		}
		
		findCoverage(0, new boolean[12], new int[15]);
	}
	
	void findCoverage(int depth, boolean [] done, int [] currentCoverage) {
//		if (depth >= optDepth - 1) {
//			return;
//		};
		
		if (depth >= 6) {
		return;
	};

		depth++;
		for (int i = 0; i < coverage.length; i++) {
			if (!done[i]) {
				done[i] = true;
				for (int x : coverageMap[i]) {
					currentCoverage[x]++;
				}
				if (match(currentCoverage)) {
					optDepth = depth;
					System.out.println(optDepth+": "+Arrays.toString(done));
				}
				findCoverage(depth, done, currentCoverage);
				for (int x : coverageMap[i]) {
					currentCoverage[x]--;
				}
				done[i] = false;
			}			
		}
	}
	
	private boolean match(int[] currentCoverage) {
		for (int i : currentCoverage) {
			if (i == 0) {
				return false;
			}
		}
		return true;
	}

	private void coverage(int[] order) {
		int [] done = new int[15];
		
		String s = "((" + min(0,1,order) + "," + max(0,1,order) + "),(" + min(2,3,order) + "," + max(2,3,order) + "))";
		Integer i = map.get(s);
		done[i]++;
		
		i = map.get("(((" + min(0,1,order) + "," + max(0,1,order) + ")," + order[2] + ")," + order[3] + ")");
		done[i]++;
		i = map.get("(((" + min(2,3,order) + "," + max(2,3,order) + ")," + order[1] + ")," + order[0] + ")");
		done[i]++;
		i = map.get("(((" + min(1,2,order) + "," + max(1,2,order) + ")," + order[0] + ")," + order[3] + ")");
		done[i]++;
		i = map.get("(((" + min(1,2,order) + "," + max(1,2,order) + ")," + order[3] + ")," + order[0] + ")");
		done[i]++;
		
		System.out.print(Arrays.toString(done));		
		System.out.print(Arrays.toString(order));
		System.out.println();
	}

	private int min(int i, int j, int[] order) {
		if (order[i] < order[j]) {
			return order[i];
		}
		return order[j];
	}

	private int max(int i, int j, int[] order) {
		if (order[i] > order[j]) {
			return order[i];
		}
		return order[j];
	}

	
	int N = 101;
	BigInteger [][] fragmentCount;
	/** counts unranked topologies for mountain space **/
	void count() {
		fragmentCount = new BigInteger[2][N];
		for (int i = 0; i < N; i++) {
			fragmentCount[0][i] = new BigInteger("0");
		}
		fragmentCount[1][0] = new BigInteger("1");
		for (int i = 3; i < N; i++) {
			count(i);
		}
	}
	/*
	 * 3: 2
4: 5
5: 14
6: 42
7: 132
8: 429
9: 1430
10: 4862
11: 16796
12: 58786
13: 208012
14: 742900
15: 2674440
16: 9694845
17: 35357670
18: 129644790
19: 477638700
20: 1767263190
21: 6564120420
22: 24466267020
23: 91482563640
24: 343059613650
25: 1289904147324
26: 4861946401452
27: 18367353072152
28: 69533550916004
29: 263747951750360
30: 1002242216651368
31: 3814986502092304
32: 14544636039226909
33: 55534064877048198
34: 212336130412243110
35: 812944042149730764
36: 3116285494907301262
37: 11959798385860453492
38: 45950804324621742364
39: 176733862787006701400
40: 680425371729975800390
41: 2622127042276492108820
42: 10113918591637898134020
43: 39044429911904443959240
44: 150853479205085351660700
45: 583300119592996693088040
46: 2257117854077248073253720
47: 8740328711533173390046320
48: 33868773757191046886429490
49: 131327898242169365477991900
50: 509552245179617138054608572
51: 1978261657756160653623774456
52: 7684785670514316385230816156
53: 29869166945772625950142417512
54: 116157871455782434250553845880
55: 451959718027953471447609509424
56: 1759414616608818870992479875972
57: 6852456927844873497549658464312
58: 26700952856774851904245220912664
59: 104088460289122304033498318812080
60: 405944995127576985730643443367112
61: 1583850964596120042686772779038896
62: 6182127958584855650487080847216336
63: 24139737743045626825711458546273312
64: 94295850558771979787935384946380125
65: 368479169875816659479009042713546950
66: 1440418573150919668872489894243865350
67: 5632681584560312734993915705849145100
68: 22033725021956517463358552614056949950
69: 86218923998960285726185640663701108500
70: 337485502510215975556783793455058624700
71: 1321422108420282270489942177190229544600
72: 5175569924646105559418940193995065716350
73: 20276890389709399862928998568254641025700
74: 79463489365077377841208237632349268884500
75: 311496878311103321137536291518809134027240
76: 1221395654430378811828760722007962130791020
77: 4790408930363303911328386208394864461024520
78: 18793142726809884575211361279087545193250040
79: 73745243611532458459690151854647329239335600
80: 289450081175264899454283846029490767264392230
81: 1136359577947336271931632877004667456667613940
82: 4462290049988320482463241297506133183499654740
83: 17526585015616776834735140517915655636396234280
84: 68854441132780194707888052034668647142985206100
85: 270557451039395118028642463289168566420671280440
86: 1063353702922273835973036658043476458723103404520
87: 4180080073556524734514695828170907458428751314320
88: 16435314834665426797069144960762886143367590394940
89: 64633260585762914370496637486146181462681535261000
90: 254224158304000796523953440778841647086547372026600
91: 1000134600800354781929399250536541864362461089950800
92: 3935312233584004685417853572763349509774031680023800
93: 15487357822491889407128326963778343232013931127835600
94: 60960876535340415751462563580829648891969728907438000
95: 239993345518077005168915776623476723006280827488229600
96: 944973797977428207852605870454939596837230758234904050
97: 3721443204405954385563870541379246659709506697378694300
98: 14657929356129575437016877846657032761712954950899755100
99: 57743358069601357782187700608042856334020731624756611000
100: 227508830794229349661819540395688853956041682601541047340 ~ 2.2E57

	 */
	
	void count(int current) {
		BigInteger [] prev = fragmentCount[current % 2];
		BigInteger [] next = fragmentCount[(current +1) % 2];
		next[current-2] = new BigInteger("1");

		BigInteger n = new BigInteger("0");
		for (int j = 0; j < next.length; j++) {
			if (prev[j] != null) {
				n = n.add(prev[j]);
			}
		}
		next[0] = n;
		
		for (int i = 1; i < current-2; i++) {
			n = new BigInteger("0");
			for (int j = i - 1; j < next.length; j++) {
				if (prev[j] != null) {
					n = n.add(prev[j]);
				}
			}
			next[i] = n;
		}		
				
		BigInteger sum = next[0].add(next[1]);
		for (int i = 2; i < next.length; i++) {
			if (next[i] != null) {
				sum = sum.add(next[i]);
			}
		}
		System.out.println(current + ": " + sum.toString());
	}
	
	
	/** counts unranked topologies for binary tree space **/

	void count2() {
		BigInteger n = new BigInteger("1");
		for (int i = 2; i < N; i++) {
			n = new BigInteger((2*i-1)+"").multiply(n);
			System.out.println((i+1) + ": " + n.toString());
		}
	}
	
	
	public static void main(String[] args) {
		TreeCounter tc = new TreeCounter();
		//tc.count();
		tc.count2();
		//tc.findCoverage();
		//tc.run();

	}

}
