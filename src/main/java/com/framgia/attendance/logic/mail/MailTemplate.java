package com.framgia.attendance.logic.mail;

import java.io.Serializable;
import java.util.Map;

import org.apache.wicket.util.template.PackageTextTemplate;
import org.apache.wicket.util.template.TextTemplate;

public abstract class MailTemplate implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private TextTemplate template;

	public String getMail(String fileName, String language){
		template = new PackageTextTemplate(MailTemplate.class, 
				getFullName(fileName, language));
		return template.asString(setVariables());
	}
	
	private String getFullName(String fileName, String language){
		return String.format(fileName,language);
	}
	
	abstract protected Map<String, Object>setVariables();
}
