package bruteforce;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class working {
	
	static int cnt2 = 0;
	static int N, M, B, Q;
	static int maxReward = Integer.MIN_VALUE;
	static List<List<Byte>> mat = new ArrayList<List<Byte>>();
	static List< List<Byte> > seqs = new ArrayList<List<Byte>>();
	static List<Integer> rewards = new ArrayList<Integer>();
	static HashMap<String, Byte> masterMap = new HashMap<String, Byte>();
	static List<Byte> bs = new ArrayList<Byte>();
	static Stack<Byte> chosen = new Stack<Byte>();
	static List<Byte> answer = new ArrayList<Byte>();
	static List<List<String>> matrix = new ArrayList<List<String>>();
	static List<String[]> allline = new ArrayList<String[]>();
	static long timeinMs = 150;
	static int bufLeng = 0;
	
	working(List<String[]> allline, List<List<String>> matrix, List<Integer> rewards, int N, int M, int B, int Q){
		working.allline = allline;
		working.matrix = matrix;
		working.rewards = rewards;
		working.N = N;
		working.M = M;
		working.B = B;
		working.Q = Q;
		working.maxReward = Integer.MIN_VALUE;
		working.cnt2 = 0;
		working.mat.clear();
		working.seqs.clear();
		working.masterMap.clear();
		working.bs.clear();
		working.chosen.clear();
		working.answer.clear();
		working.timeinMs = 150;
		working.bufLeng = 0;
	}
	/*
	public static void bruteForce(int cur, boolean ver) {
		if(bs.get(cur) == (byte)0) {
			System.out.println("FATAL");
		}
		if(chosen.size() == B) {
			cnt2++;
			int totalReward = 0;
			boolean noExist = true;
			int curLengOpt = 0;
			for(int i = 0; i < Q; ++i) {
				boolean allok = false;
				for(int j = 0; j < B - seqs.get(i).size() + 1 && !allok; ++j) {
					boolean ok = true;
					for(int k = 0; k < seqs.get(i).size() && ok; ++k) {
						byte curRow = (byte)(chosen.get(k + j) / M);
						byte curCol = (byte)(chosen.get(k + j) % M);
						if(seqs.get(i).get(k) != mat.get(curRow).get(curCol)) {
							ok = false;
						}
						
					}
					if(ok) {
						curLengOpt = Math.max(curLengOpt, j + seqs.get(i).size());
						allok = true;
						noExist = false;
					}
				}
				if(allok) {
					totalReward += rewards.get(i);
				}
			}
			if(noExist) {
				return;
			}
			bufLeng = Math.max(bufLeng, curLengOpt);
			if(maxReward < totalReward) {
				maxReward = totalReward;
				for(int i = 0; i < B; ++i) {
					answer.set(i, chosen.get(i));
				}
			}
			return;
		}
		int k = M;
		if(ver) {
			k = N;
		}
		boolean stuck = true;
		int curRow = (cur / M);
		int curCol = (cur % M);
		for(int i = 0; i < k; ++i) {
			int cell;
			if(ver) {
				cell = i * M + curCol;
			}else {
				cell = curRow * M + i;
			}
			if(bs.get(cell) == (byte)1) {
				continue;
			}
			stuck = false;
			bs.set(cell, (byte)1);
			chosen.push((byte)cell);
			bruteForce(cell, !ver);
			bs.set(cell, (byte)0);
			chosen.pop();
		}
		if(!stuck) {
			return;
		}
		int curLengOpt = 0;
		int totalReward = 0;
		boolean noExist = true;
		for(int i = 0; i < Q; ++i) {
			boolean allok = false;
			for(int j = 0; j < chosen.size() - seqs.get(i).size() + 1 && !allok; ++j) {
				boolean ok = true;
				for(int k1 = 0; k1 < seqs.get(i).size() && ok; ++k1) {
					byte curRow1 = (byte)(chosen.get(k1 + j) / M);
					byte curCol1 = (byte)(chosen.get(k1 + j) % M);
					if(seqs.get(i).get(k1) != mat.get(curRow1).get(curCol1)) {
						ok = false;
					}
					
				}
				if(ok) {
					curLengOpt = Math.max(curLengOpt, j + seqs.get(i).size());
					allok = true;
					noExist = false;
				}
			}
			if(allok) {
				totalReward += rewards.get(i);
			}
		}
		if(noExist) {
			return;
		}
		bufLeng = Math.max(bufLeng, curLengOpt);
		if(maxReward < totalReward) {
			maxReward = totalReward;
			for(int i = 0; i < chosen.size(); ++i) {
				answer.set(i, chosen.get(i));
			}
		}
	}*/
	
	public static void bruteForce(int cur, boolean ver) {
		if(bs.get(cur) == (byte)0) {
			//System.out.println("FATAL");
		}
		if(chosen.size() == B) {
			//cnt2++;
			int totalReward = 0;
			boolean noExist = true;
			int curLengOpt = 0;
			for(int i = 0; i < Q; ++i) {
				boolean allok = false;
				for(int j = 0; j < B - seqs.get(i).size() + 1 && !allok; ++j) {
					boolean ok = true;
					for(int k = 0; k < seqs.get(i).size() && ok; ++k) {
						byte curRow = (byte)(chosen.get(k + j) / M);
						byte curCol = (byte)(chosen.get(k + j) % M);
						if(seqs.get(i).get(k) != mat.get(curRow).get(curCol)) {
							ok = false;
						}
						
					}
					if(ok) {
						curLengOpt = Math.max(curLengOpt, j + seqs.get(i).size());
						allok = true;
						noExist = false;
					}
				}
				if(allok) {
					totalReward += rewards.get(i);
				}
			}
			if(noExist) {
				return;
			}
			if(maxReward < totalReward || (maxReward == totalReward && curLengOpt < bufLeng)) {
				maxReward = totalReward;
				bufLeng = curLengOpt;
				for(int i = 0; i < chosen.size(); ++i) {
					answer.set(i, chosen.get(i));
				}
			}
			return;
		}
		int k = M;
		if(ver) {
			k = N;
		}
		boolean stuck = true;
		int curRow = (cur / M);
		int curCol = (cur % M);
		for(int i = 0; i < k; ++i) {
			int cell;
			if(ver) {
				cell = i * M + curCol;
			}else {
				cell = curRow * M + i;
			}
			if(bs.get(cell) == (byte)1) {
				continue;
			}
			stuck = false;
			bs.set(cell, (byte)1);
			chosen.push((byte)cell);
			bruteForce(cell, !ver);
			bs.set(cell, (byte)0);
			chosen.pop();
		}
		if(!stuck) {
			return;
		}
		int curLengOpt = 0;
		int totalReward = 0;
		boolean noExist = true;
		for(int i = 0; i < Q; ++i) {
			boolean allok = false;
			for(int j = 0; j < chosen.size() - seqs.get(i).size() + 1 && !allok; ++j) {
				boolean ok = true;
				for(int k1 = 0; k1 < seqs.get(i).size() && ok; ++k1) {
					byte curRow1 = (byte)(chosen.get(k1 + j) / M);
					byte curCol1 = (byte)(chosen.get(k1 + j) % M);
					if(seqs.get(i).get(k1) != mat.get(curRow1).get(curCol1)) {
						ok = false;
					}
					
				}
				if(ok) {
					curLengOpt = Math.max(curLengOpt, j + seqs.get(i).size());
					allok = true;
					noExist = false;
				}
			}
			if(allok) {
				totalReward += rewards.get(i);
			}
		}
		if(noExist) {
			return;
		}
		if(maxReward < totalReward || (maxReward == totalReward && curLengOpt < bufLeng)) {
			maxReward = totalReward;
			bufLeng = curLengOpt;
			for(int i = 0; i < chosen.size(); ++i) {
				answer.set(i, chosen.get(i));
			}
		}
	}
	
	public static void solvemain() {
		/*
		Scanner myObj = new Scanner(System.in);
		System.out.print("Masukkan banyak buffer: ");
		B = myObj.nextInt();*/
		for(int i = 0; i < B; ++i) {
			answer.add((byte)0);
		}
		/*
		System.out.print("Masukkan dimensi NxM: ");
		N = myObj.nextInt();
		M = myObj.nextInt();
		int cnt = 0;*/
		int cnt = 0;
		for(int i = 0; i < N; ++i) {
			mat.add(new ArrayList<Byte>());
		}
		for(int i = 0; i < N; ++i) {
			for(int j = 0; j < M; ++j) {
				mat.get(i).add((byte)0);
			}
		}
		//System.out.println(mat.size());
		//System.out.println(mat.get(0).size());
		for(int i = 0; i < N; ++i) {
			for(int j = 0; j < M; ++j) {
				String s = matrix.get(i).get(j);
				if(!masterMap.containsKey(s)) {
					masterMap.put(s, (byte)cnt);
					cnt++;
				}
				mat.get(i).set(j, masterMap.get(s));
				//System.out.print(mat.get(i).get(j));
				//System.out.print(" ");
				bs.add((byte)0);
			}
			//System.out.println();
		}
		/*System.out.print("Masukkan banyak sekuens: ");
		Q = myObj.nextInt();
		myObj.nextLine();*/
		for(int i = 0; i < Q; ++i) {
			String[] splitted = allline.get(i);
			//System.out.println(allline);
			List<Byte> temp = new ArrayList<Byte>();
			for(int j = 0; j < splitted.length; ++j) {
				if(!masterMap.containsKey(splitted[j])) {
					masterMap.put(splitted[j], (byte)cnt);
					cnt++;
				}
				temp.add((byte)masterMap.get(splitted[j]));
			}
			seqs.add(temp);
		}
		long startTime = System.nanoTime();
		for(int i = 0; i < M; ++i) {
			bs.set(i, (byte)1);
			chosen.push((byte)i);
			bruteForce(i, true);
			chosen.pop();
			bs.set(i, (byte)0);
		}
		long estimatedTime = System.nanoTime() - startTime;
		long timeinMillis = TimeUnit.NANOSECONDS.toMillis(estimatedTime);
		//System.out.println(maxReward);
		//for(int i = 0; i < B; ++i) {
			//int curRow = answer.get(i) / M;
			//int curCol = answer.get(i) % M;
			//String res = String.format("(%d,%d)", curCol + 1, curRow + 1);
			//System.out.println(res);
		//}
		timeinMs = timeinMillis;
		//System.out.println(timeinMillis);
		//System.out.println(cnt2);
	}
	
	public static void main(String[] args) {
		solvemain();
	}
}
