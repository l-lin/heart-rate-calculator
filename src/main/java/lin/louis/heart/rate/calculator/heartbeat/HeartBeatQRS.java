package lin.louis.heart.rate.calculator.heartbeat;

import org.springframework.lang.Nullable;


/**
 * Event describing activity of the heart
 */
public enum HeartBeatQRS {
	SUPRA_VENTRICULAR("A"),
	PREMATURE_VENTRICULAR("V"),
	NORMAL("N"),
	FUSION("F"),
	PACED("P"),
	INVALID("X");

	private final String type;

	HeartBeatQRS(String type) {this.type = type;}

	@Nullable
	public static HeartBeatQRS from(String c) {
		for (HeartBeatQRS heartBeatQRS : HeartBeatQRS.values()) {
			if (heartBeatQRS.type.equalsIgnoreCase(c)) {
				return heartBeatQRS;
			}
		}
		return null;
	}
}
