package com.framgia.attendance.util;


public enum SegmentType {

	EMPLOYEE(1, "app.segment.type.employee"),
	PART_TIME(2, "app.segment.type.parttime"),
	INTERN(3, "app.segment.type.intern"),
	OUTSOURCING(4, "app.segment.type.outsourcing"),
	RESIGN(5, "app.segment.type.resign");

	private int key;
	private String value;

	SegmentType(int key, String messageKey) {
		this.key = key;
		this.value = messageKey;
	}

	public static String getMessageKey(Integer key) {
		if (null == key) {
			return null;
		}
		String ret = "";
		for (SegmentType type : SegmentType.values()) {
			if (type.getKey() == key) {
				return type.getValue();
			}
		}
		return ret;
	}

	public int getKey() {
		return key;
	}
	
	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return value;
	}

}
