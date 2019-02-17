package com.upgrad.electionresult;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.upgrad.electionresult.cmd.Loader;
import com.upgrad.electionresult.utils.BinSort;
import com.upgrad.electionresult.utils.BloomFilter;

public class BootStrap {

	private static Loader cmd = new Loader();
	private static BloomFilter bloomFilter;
	private static BinSort voter = new BinSort(899999);
	private static BinSort candidCount = new BinSort(8999999);
	private static String evmFile = "/Users/singaravelannandakumar/Desktop/NoonAutomation/election-result-processor/data/votersCandList.txt";
	private static String voterFile = "/Users/singaravelannandakumar/Desktop/NoonAutomation/election-result-processor/data/validVotersList.txt";
	static BufferedReader br = null;
	static FileReader fr = null;
	static CSVReader cr = null;

	public static void main(String[] args) throws IOException {

		Scanner scn = new Scanner(System.in);
		int caseScan = 0;
		while (caseScan <= 5) {
			caseScan = scn.nextInt();
			switch (caseScan) {
			case 1:
				BootStrap.loadBloom(voterFile);
				break;
			case 2:
				BootStrap.checkWithBloomFilter(evmFile);
				break;
			case 3:
				BootStrap.checkWithHashTable(evmFile);
				break;
			case 4:
				int candidID = scn.nextInt();
				BootStrap.getCandidCount(candidID);
				break;
			}

		}
	}

	public static void loadBloom(String voterFile) throws IOException {
		bloomFilter = cmd.loadDataToBloomFilter(voterFile);
	}

	public static void checkWithBloomFilter(String evmFile) {
		try {
			String fileLocation = evmFile;
			fr = new FileReader(fileLocation);
			cr = new CSVReaderBuilder(fr).withSkipLines(1).build();
			String[] nextRecord;
			while ((nextRecord = cr.readNext()) != null) {
				String[] voterCandidateID = nextRecord[0].split(" ");
				System.out.println(voterCandidateID[0]+" "+voterCandidateID[1]);
				boolean isPresent = bloomFilter.isPresent(voterCandidateID[0]);
				if (isPresent) {
					voter.add(Integer.parseInt(voterCandidateID[0]), Integer.parseInt(voterCandidateID[1]));
					candidCount.count(Integer.parseInt(voterCandidateID[1]), Integer.parseInt(voterCandidateID[0]));
				}else {
					System.out.println("Not Present");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void checkWithHashTable(String evmFile) {
		try {

			String fileLocation = evmFile;
			fr = new FileReader(fileLocation);
			cr = new CSVReaderBuilder(fr).withSkipLines(1).build();
			String[] nextRecord;
			while ((nextRecord = cr.readNext()) != null) {
				boolean isPresent = bloomFilter.isPresent(nextRecord[0]);
				if (isPresent) {
					voter.add(200000, 21);
					long isUserPresent = voter.find(200000);
					if (isUserPresent != 0) {
						candidCount.count(21, 200000);
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void getCandidCount(int candidID) {
		System.out.println("Finding the candidID");
		long find = candidCount.find(candidID);
		System.out.println(find);
		
	}

}
