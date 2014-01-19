package nibura.logic;

public class RUNTIME_STATUS {
	public enum STATUS {
		ANDROID,
		DEBUG,
		PHP;
	}
	
	private static STATUS status = null;
	
	public RUNTIME_STATUS() {
	}
	
	public static void setStatus(STATUS status) {
		RUNTIME_STATUS.status = status;
	}
	
	public static STATUS getStatus() {
		if(status == null) {
			throw new AssertionError("RUNTIME_STATUS requested but not properly set prior to action.", null);
		}
		return status;
	}
}
