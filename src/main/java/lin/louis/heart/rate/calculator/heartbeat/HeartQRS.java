package lin.louis.heart.rate.calculator.heartbeat;

public enum HeartQRS {
	SUPRA_VENTRICULAR("A"),
	PREMATURE_VENTRICULAR("V"),
	NORMAL("N"),
	FUSION("F"),
	PACED("P"),
	INVALID("X");

	private final String type;

	HeartQRS(String type) {this.type = type;}

	public String getType() {
		return type;
	}

	public static HeartQRS from(String c) {
		for (HeartQRS heartQRS : HeartQRS.values()) {
			if (heartQRS.type.equals(c)) {
				return heartQRS;
			}
		}
		return INVALID;
	}
}
