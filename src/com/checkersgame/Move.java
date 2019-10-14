package com.checkersgame;



public class Move{
	public int start;
	public int end;
	public boolean isJump;
	public int[] multiJump;
	public Move(int ... jump ) {//throws Exception{
		for(int i : jump) {
//			if(start<0||start>31||end<0||end>31) {
//				throw new Exception("Out Of Board ");
//			}
		}
		start = jump[0];
		end = jump[jump.length-1];
		multiJump = jump;
	}
	
	public Move(int start, int end) {//throws Exception {
//		if(start<0||start>31||end<0||end>31) {
//			throw new Exception("Out Of Board ");
//		}
		this.start=start;
		this.end=end;
		this.isJump=false;
	}
	public Move(int start, int end,boolean isJump) {//throws Exception {
//		if(start<0||start>31||end<0||end>31) {
//			throw new Exception("Out Of Board ");
//		}
		this.start=start;
		this.end=end;
		this.isJump = isJump;//uhh
	}
	
	//hide indexes
	@Override
	public String toString() {
		return "Move [ start="+start+", end="+end+" ] " + (isJump ?"Jump Move":"");
//		if(multiJump == null) {
//			return "Move [start=" + (start+1) + ", end=" + (end+1) + "]";
//		} else {
//			String m = "";
//			for(int i = 0 ; i < multiJump.length; i++) {
//				m += i;
//				if(i != (multiJump.length-1) ) {
//					m+="-";
//				}
//			}
//			return "Move [ "+m+" ] " + (isJump ?"Jump Move":"");
//		}
	}
}