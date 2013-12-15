package nibura.logic;

public class OptionsHandler {
	private static int maxGroupDepth = 10;
	
	/*
	 * Return max number of embedded groups. 
	 * This has an effect on the number of recursive calls to build BoardList HTML
	 */
	public static int getMaxGroupDepth() {
		return maxGroupDepth;
	}
}
