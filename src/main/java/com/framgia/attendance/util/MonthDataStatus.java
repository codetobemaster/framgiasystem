package com.framgia.attendance.util;

public enum MonthDataStatus {

	UNAPPLIED(0, "app.MonthDataStatus.unapplied"), 
	APPLIED(1,"app.MonthDataStatus.applied"), 
	APPROVED(2,"app.MonthDataStatus.approved"),
	FINALIZED(3,"app.MonthDataStatus.finalized");

	private int value;
	private String messageKey;

	MonthDataStatus(int value, String messageKey) {
		this.value = value;
		this.messageKey = messageKey;
	}

	public static String getMessageKey(Integer value) {
		if (null == value) {
			return null;
		}
		String ret = "";
		for (MonthDataStatus type : MonthDataStatus.values()) {
			if (type.getValue() == value) {
				return type.getMessageKey();
			}
		}
		return ret;
	}

	public int getValue() {
		return value;
	}

	public int value() {
		return value;
	}

	public String getMessageKey() {
		return messageKey;
	}
}
