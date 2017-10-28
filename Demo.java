import java.util.function.BiFunction;

import SimilarlyMatrixClustering.Data;
import SimilarlyMatrixClustering.SimilarlyFunction;
import SimilarlyMatrixClustering.SimilarlyMatrixClustering;

public class Demo {
	public static void main(String[] args) {
		Data[] dataset = Dataset1();
		double threshold = 0.6;
		int seedIdx = 0;
		SimilarlyMatrixClustering cluster = new SimilarlyMatrixClustering(
				dataset, threshold, seedIdx,
				new BiFunction<Data, Data, Double>() {
					@Override
					public Double apply(Data t, Data u) {
						return SimilarlyFunction.func1(t, u);
					}//apply
				});
		System.out.println(cluster.toString());
		System.out.println(cluster.analyzeClusteredInstances());
		System.out.println(cluster.analyzeDatasetDistribution());
		System.out.println(cluster.analyzeClustersDistribution());
	}//main
	
	private static Data[] Dataset1() {
		Data[] dataset = {new Data(1, 1, 0, 0, 0, 0, 0, 0, 0),
							 		    new Data(2, 1, 1, 1, 0, 0, 0, 1, 0),
								 	    new Data(3, 1, 0, 0, 0, 0, 0, 0 ,0),
									    new Data(4, 1, 1, 1, 1, 0, 0, 1, 1), 
									    new Data(5, 1, 0, 1, 1, 1, 0, 1, 1),
									    new Data(6, 1, 1, 1, 0, 1, 0, 0, 0),
									    new Data(7, 1, 0, 1, 0, 0, 0, 1, 1),
									    new Data(8, 1, 0, 1, 0, 1, 0, 0, 0),
									    new Data(9, 1, 1, 1, 0, 1, 0, 1, 0),
									    new Data(10, 1, 0, 1, 1, 1, 1, 1, 1)};
		return dataset;
	}//Dataset1
}//Main
