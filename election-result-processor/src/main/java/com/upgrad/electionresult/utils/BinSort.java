package com.upgrad.electionresult.utils;

public class BinSort {

	long[] data;
	int candidVoteCount = 1;

	public BinSort(int size) {
		data = new long[size];
	}

	public void add(int key, long value) {
		data[key] = value;
	}

	public long find(int key) {
		return data[key];
	}

	public void count(int key, long value) {
		int valuee = (int) data[key];
		if (valuee != 0) {
			candidVoteCount = valuee+1;
			data[key] = Math.abs(candidVoteCount);
		}else {
			data[key] =  Math.abs(candidVoteCount);
		}
	}

}
