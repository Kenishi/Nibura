package com.android.nibura.logic;

public class RootGroup extends BoardGroup implements BoardListElement {
	private static RootGroup rootGroup = null;
	
	private RootGroup(String name) {
		super(name);
	}
	
	//=== Public Methods
	public static RootGroup createRootGroup() {
		if (rootGroup == null) {
			rootGroup = new RootGroup("**root**");
		}
		return rootGroup;
	}
	
	//=== Protected Methods
	//=== Private Methods
}
