package SimilarlyMatrixClustering;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.function.BiFunction;

public class SimilarlyMatrixClustering {
	Data[] dataset;
	boolean[] usedList;
	ArrayList<ArrayList<Data>> Groups;
	
	public SimilarlyMatrixClustering(Data[] dataset, double t, int seed, BiFunction<Data, Data, Double> similarlyFunc) {
		//init
		Groups = new ArrayList<ArrayList<Data>>();;
		this.dataset = dataset;
		int len = dataset.length;
		usedList = new boolean[len];
		for(int i = 0 ; i < len ; i++) {
			usedList[i] = false;
		}//for

		//1. seeding
		Data tmp = dataset[seed];
		dataset[seed] = dataset[0];
		dataset[0] = tmp;
		
		//1-2. build similarly matrix
		double[][] matrix = new double[len][len];
		for(int i = 0 ; i < len ; i++) {
			for(int j = 0 ; j < len ; j++) {
				matrix[i][j] = similarlyFunc.apply(dataset[i], dataset[j]);
			}//for j
		}//for i
		
		
		int dataID;
		while((dataID = getUnused(0)) != -1) {
			Groups.add(new ArrayList<Data>());
			Groups.get(Groups.size()-1).add(dataset[dataID]);
			usedList[dataID] = true;
			
			//2. Add Phase
			while(true) {
				double max = Double.MIN_VALUE;
				int max_idx = -1;
				int idx = 0;
				while((idx = getUnused(++idx)) != -1) {
					double simiVal = matrix[dataID][idx];
					if(simiVal > max) {
						max = simiVal;
						max_idx = idx;
					}//if
				}//while
				if(max_idx == -1) {
					break;
				}//if
				double sum = 0;
				for(Data compare_data : Groups.get(Groups.size()-1)) {
					sum += matrix[max_idx][compare_data.order];
				}//for
				int C = Groups.get(Groups.size()-1).size();
				if (sum >= t*C) {
					Groups.get(Groups.size()-1).add(dataset[max_idx]);
					usedList[max_idx] = true;
				} else {
					break;
				}//if
			}//while true
			
			//3. Remove Phase
			for(ArrayList<Data> g : Groups) {
				double min = Double.MAX_VALUE;
				int min_idx = -1;
				for(int i = 0 ; i < g.size() ; i++) {
					double sum = 0;
					for(int j = 0 ; j < g.size() ; j++) {
						double simiVal = matrix[i][j];
						sum += simiVal;
					}//for j
					if(sum < min) {
						min = sum;
						min_idx = i;
					}//if
				}//for i
				if(min_idx == -1) {
					continue;
				}//if
				int C = g.size();
				if(min <= t*C) {
					g.remove(min_idx);
					usedList[min_idx] = false;
				} else {
					break;
				}//if
			}//for Groups - Remove Phase
			
		}//while getUnused
	}//cons.
	
	private int getUnused(int start) {
		for(int i = start ; i < usedList.length ; i++) {
			if(usedList[i] == false) return i;
		}//for
		return -1;
	}//getUnused
	
	public String analyzeClusteredInstances() {
		DecimalFormat nf = new DecimalFormat("#.0");
		String out = "-------- ClusteredInstances\n";
		for(int i = 0 ; i < Groups.size() ; i++) {
			ArrayList<Data> group = Groups.get(i);
			out += "G" + i + ": " + group.size() + "( " + nf.format(100.0*group.size() / dataset.length) + " %)\n";
		}//for group
		return out;
	}//clusteredInstances
	
	public String analyzeDatasetDistribution() {
		String out = "-------- ClustersDistribution\n";
		double[] distri = new double[dataset[0].length];
		for(int j = 0 ; j < distri.length ; j++) {
			distri[j] = 0;
		}//for
		for(int j = 0 ; j < dataset.length ; j++) {
			Data data = dataset[j];
			for(int k = 0 ; k < data.length ; k++) {
				distri[k] += data.value[k];
			}//for
		}//for data
		for(int k = 0 ; k < dataset[0].length ; k++) {
			distri[k] /= dataset.length;
		}//for
		out += "Dataset: { ";
		for(int j = 0 ; j < distri.length ; j++) {
			out += String.format("%-6.2f", distri[j]);
			if(j != distri.length-1) {
				out += ", ";
			}//if
		}//for data
		return out + " }\n";
	}//analyzeDatasetDistribution
	
	public String analyzeClustersDistribution() {
		String out = "-------- ClustersDistribution\n";
		for(int i = 0 ; i < Groups.size() ; i++) {
			ArrayList<Data> group = Groups.get(i);
			double[] distri = new double[group.get(0).length];
			for(int j = 0 ; j < distri.length ; j++) {
				distri[j] = 0;
			}//for
			for(int j = 0 ; j < Groups.get(i).size() ; j++) {
				Data data = group.get(j);
				for(int k = 0 ; k < data.length ; k++) {
					distri[k] += data.value[k];
				}//for
			}//for data
			for(int k = 0 ; k < group.get(0).length ; k++) {
				distri[k] /= group.size();
			}//for
			out += "G" + i + ": { ";
			for(int j = 0 ; j < distri.length ; j++) {
				out += String.format("%-6.2f", distri[j]);
				if(j != distri.length-1) {
					out += ", ";
				}//if
			}//for data
			out += " }\n";
		}//for group
		return out;
	}//clusteredInstances
	
	@Override
	public String toString() {
		String out = "-------- Clusters\n";
		for(int i = 0 ; i < Groups.size() ; i++) {
			ArrayList<Data> group = Groups.get(i);
			out += "G" + i + ": { ";
			for(int j = 0 ; j < Groups.get(i).size() ; j++) {
				Data data = group.get(j);
				out += data.ID;
				if(j != group.size()-1) {
					out += ", ";
				}//if
			}//for data
			out += " }\n";
		}//for group
		return out;
	}//toString
}//SimilarityMatrixClustering
