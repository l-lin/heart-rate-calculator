package lin.louis.heart.rate.calculator.heartrate;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import org.springframework.boot.convert.DurationUnit;


public class HeartRateProperties {

	@DurationUnit(ChronoUnit.SECONDS)
	private Duration gapDuration;

	private HriProperties hri;

	public Duration getGapDuration() {
		return gapDuration;
	}

	public void setGapDuration(Duration gapDuration) {
		this.gapDuration = gapDuration;
	}

	public HriProperties getHri() {
		return hri;
	}

	public void setHri(HriProperties hri) {
		this.hri = hri;
	}

	public class HriProperties {

		private int min;

		private int max;

		public int getMin() {
			return min;
		}

		public void setMin(int min) {
			this.min = min;
		}

		public int getMax() {
			return max;
		}

		public void setMax(int max) {
			this.max = max;
		}
	}
}
