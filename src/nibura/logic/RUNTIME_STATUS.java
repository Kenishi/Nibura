package nibura.logic;

public class RUNTIME_STATUS {
	public enum STATUS {
		ANDROID,
		DEBUG,
		PHP;
	}
	
	private static STATUS status = STATUS.DEBUG;
	
	public RUNTIME_STATUS() {
	}
	
	public static void setStatus(STATUS status) {
		RUNTIME_STATUS.status = status;
	}
	
	public static STATUS getStatus() {
		return status;
	}
}
