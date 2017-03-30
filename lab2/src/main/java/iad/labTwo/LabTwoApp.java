package iad.labTwo;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

public class LabTwoApp {

	public static void main(String[] args) {

		try {
			String[] names = { "data/Iris-setosa.data", "data/Iris-virginica.data", "data/Iris-versicolor.data" };
			String[] metrics = {"taxicab", "euclidean", "minkowski", "chebyshev", "cosine"};
			List<Double> sds = new ArrayList<Double>();
			
			sds = IrisData.calcSds("data/iris.data", ",");
			IrisData.normalize("data/iris.data", ",", false, sds);
			
			IrisData.initData("data/iris.data");
			List<List<Double>> irisPoints = IrisData.calcPoints("data/iris.data", ",", 3);
			IrisData.writePoints(irisPoints, "results_data/points");
				
			for (String s : names) {
				IrisData.calcResults(s, "\t", irisPoints, Metric::taxicab, metrics[0]);
				IrisData.calcResults(s, "\t", irisPoints, Metric::euclidean, metrics[1]);
				IrisData.calcResults(s, "\t", irisPoints, Metric::minkowski3p, metrics[2]);
				IrisData.calcResults(s, "\t", irisPoints, Metric::chebyshev, metrics[3]);
				IrisData.calcResults(s, "\t", irisPoints, Metric::cosineSimilarity, metrics[4]);
			}
			
			IrisData.normalize("data/iris.data", ",", true, sds);
			for (String s : metrics) { 
				IrisData.normalize("results_data/Iris-setosa.data_"+s, "\t", true, sds);
				IrisData.normalize("results_data/Iris-virginica.data_"+s, "\t", true, sds);
				IrisData.normalize("results_data/Iris-versicolor.data_"+s, "\t", true, sds);
			}
			IrisData.normalize("results_data/points", "\t", true, sds);

			final Runtime rt = Runtime.getRuntime();
			rt.exec("gnuplot " + System.getProperty("user.dir") + "/plot.txt");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		

	}
}
