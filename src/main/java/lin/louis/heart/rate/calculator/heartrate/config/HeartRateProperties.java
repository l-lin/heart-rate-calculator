package lin.louis.heart.rate.calculator.heartrate.config;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import org.springframework.boot.convert.DurationUnit;


public class HeartRateProperties {

	@DurationUnit(ChronoUnit.SECONDS)
	private Duration gapDuration;

	private HriProperties hri;

	private String separator;

	private int nbHeartBeats;

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

	public String getSeparator() {
		return separator;
	}

	public void setSeparator(String separator) {
		this.separator = separator;
	}

	public int getNbHeartBeats() {
		return nbHeartBeats;
	}

	public void setNbHeartBeats(int nbHeartBeats) {
		this.nbHeartBeats = nbHeartBeats;
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
