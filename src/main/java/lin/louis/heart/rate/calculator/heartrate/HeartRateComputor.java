package lin.louis.heart.rate.calculator.heartrate;

import java.util.Arrays;


public class HeartRateComputor {
	public double compute(int... values) {
		if (values == null || values.length == 0) {
			return 0d;
		}
		Arrays.sort(values);
		if (values.length % 2 == 0) {
			return ((double) values[values.length / 2] + (double) values[values.length / 2 - 1]) / 2;
		}
		return values[values.length / 2];
	}
}
