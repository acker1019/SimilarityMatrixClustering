package SimilarlyMatrixClustering;

public class SimilarlyFunction {
	//difference between two binary data
	public static double func1(Data data1, Data data2) {
		int len = data1.length;	
		int num0 = countNum(0, data1, data2);
		int num1 = countNum(1, data1, data2);
		return 1.0*num1/(len-num0);
	}//func1
	
	private static int countNum(int digit, Data data1, Data data2) {
		int num = 0;
		for(int i = 0 ; i < data1.length ; i++) {
			if(data1.value[i] == data2.value[i]) {
				if(data1.value[i] == digit) num++;
			}//if
		}//for
		return num;
	}//countNum (func1)
	
	//inverse of Euclidean distance
	public static double func2(Data data1, Data data2) {
		int len = data1.length;	
		double sum = 0;
		for(int i = 0 ; i < len ; i++) {
			sum += Math.pow(data1.value[i] - data2.value[i], 2);
		}//for
		return 1/sum;
	}//func2
}//SimilarityFunction
