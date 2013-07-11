package testing;

import model.Loc;
import java.io.*;

public class Test{
	/* 0 is none
	 * 1 is only false
	 * 2 is full
	 */
	public static void main(String[] args){
		int debug = 0;
		String instr = "";
		if (args.length == 2){
			debug = Integer.parseInt(args[0]);
			instr = args[1];
		}
		else{
			File finstr = new File("config.txt");
			try{
				BufferedReader reader = new BufferedReader(new FileReader(finstr));
				String line;
				while((line=reader.readLine()) != null){
					String[] instrs = line.split(",");
					debug = Integer.parseInt(instrs[0]);
					instr = instrs[1];
				}
				reader.close();
			}
			catch(IOException e){
				System.err.println(e);
			}
		}
		System.out.println(instr);
		if(instr.equals("all")){
			createTest(debug);
			distTest(debug);
		}
		else if(instr.equals("create")){
			createTest(debug);
		}
		else if(instr.equals("dist")){
			distTest(debug);
		}
		else{
			System.out.println(instr + " not a valid command");
		}
	}

	private static void createTest(int debug){
		File fin = new File("data/createTest.txt");
		File fout = new File("data/createTest.log");
		try{
			BufferedReader reader = new BufferedReader(new FileReader(fin));
			BufferedWriter writer = new BufferedWriter(new FileWriter(fout));

			String line;
			boolean worked = true;
			int numWrong = 0;
			int numTest = 0;
			String header = "";
			String metaHeader = "";
			while((line=reader.readLine()) != null){
				if(line.startsWith("#!")){
					header = line.substring(3);
				}
				else if(line.startsWith("#@")){
					metaHeader = line.substring(3);
				}
				else if(!line.startsWith("#")){
				String[] ints = line.split(",");
				int x = Integer.parseInt(ints[0]);
				int y = Integer.parseInt(ints[1]);
				int z = Integer.parseInt(ints[2]);

				int xd = Integer.parseInt(ints[3]);
				int yd = Integer.parseInt(ints[4]);
				int zd = Integer.parseInt(ints[5]);
				boolean shouldSame = Boolean.parseBoolean(ints[6]);

				Loc loc1 = new Loc(x,y,z,true);
				Loc loc2 = new Loc(xd,yd,zd,false);
				boolean same = (loc1.same(loc2) == shouldSame);
				worked = same && worked;
				String result = "" + same;
				String fullResult = "";
				fullResult = "\n" + same + "---------------\n" + metaHeader + "\n" + header + "\n" + loc1.toString() + "\n" + loc2.toString();
				if(!same){
					numWrong ++;
				}
				numTest++;
				if(debug==2){
					writer.write(fullResult);
					writer.newLine();
					System.out.println(fullResult);
				}
				else if(debug==1 && !same){
					writer.write(fullResult);
					writer.newLine();
					System.out.println(fullResult);
				}
				else{
					writer.write(result);
					writer.newLine();
				}
				header = "";
				}
			}
			String outputline = "Results: \n";
			if(worked){
				outputline += "Success!\n";
			}
			else{
				outputline += "Failure\n";
			}
			outputline += ((numTest - numWrong) + "/" + numTest + " succeded\n");
			writer.write(outputline);
			System.out.println(outputline);

			reader.close();
			writer.close();
		}
		catch(IOException e){
			System.err.println(e);
		}
	}

	private static void distTest(int debug){
		File fin = new File("data/distTest.txt");
		File fout = new File("data/distTest.log");

		try{
			BufferedReader reader = new BufferedReader(new FileReader(fin));
			BufferedWriter writer = new BufferedWriter(new FileWriter(fout));

			String line;
			boolean worked = true;
			int numWrong = 0;
			int numTest = 0;
			String header = "";
			String metaHeader = "";
			while((line=reader.readLine()) != null){
				if(line.startsWith("#!")){
					header = line.substring(3);
				}
				else if(line.startsWith("#@")){
					metaHeader = line.substring(3);
				}
				else if(!line.startsWith("#")){
				String[] ints = line.split(",");
				int x = Integer.parseInt(ints[0]);
				int y = Integer.parseInt(ints[1]);
				int z = Integer.parseInt(ints[2]);

				int xd = Integer.parseInt(ints[3]);
				int yd = Integer.parseInt(ints[4]);
				int zd = Integer.parseInt(ints[5]);
				int expected = Integer.parseInt(ints[6]);

				Loc loc1 = new Loc(x,y,z,true);
				Loc loc2 = new Loc(xd,yd,zd,false);
				int dist = loc1.distance(loc2);
				boolean same = (dist == expected);
				worked = same && worked;
				String result = "" + same;
				String fullResult = "";
				fullResult = "\n" + same + "---------------\n" + metaHeader + "\n" + header + "\n" + loc1.toString() + "\n" + loc2.toString() + "\nCalculated : " + dist + "\nexpected : " + expected;
				if(!worked){
					numWrong ++;
				}
				numTest++;
				if(debug==2){
					writer.write(fullResult);
					writer.newLine();
					System.out.println(fullResult);
				}
				else if(debug==1 && !same){
					writer.write(fullResult);
					writer.newLine();
					System.out.println(fullResult);
				}
				else{
					writer.write(result);
					writer.newLine();
				}
				header = "";
				}
			}
			String outputline = "Results: \n";
			if(worked){
				outputline += "Success!\n";
			}
			else{
				outputline += "Failure\n";
			}
			outputline += ((numTest - numWrong) + "/" + numTest + " succeded\n");
			writer.write(outputline);
			System.out.println(outputline);

			reader.close();
			writer.close();
		}
		catch(IOException e){
			System.err.println(e);
		}
	}
}
