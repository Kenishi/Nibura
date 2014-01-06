package nibura.logic;

public abstract class BoardListElement {
	public static enum SuiteType {
		NICH_SUITE
	};
	
	public static String NO_BOARD_LINK = "NO BOARD LINK";
	
	public abstract String getName();
	public abstract String getLink();
	public abstract String getId();
	public abstract SuiteType getSuiteType();
	public abstract String toString();	
}
