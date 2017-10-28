package SimilarlyMatrixClustering;

public class Data {
	private static int counter = 0;
	public final int order;
	public final int ID;
	public final int length;
	public final int[] value;

	public Data(int ID, int... value) {
		this.order = counter++;
		this.ID = ID;
		this.length = value.length;
		this.value = new int[value.length];
		for(int i = 0 ; i < value.length ; i++) {
			this.value[i] = value[i];
		}//for
	}//cons.
}//BinaryData
