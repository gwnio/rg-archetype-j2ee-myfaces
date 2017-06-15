package org.rg.archetype.web.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "GetNameBean")
@SessionScoped
public class GetNameBean {

	private String name;

	public GetNameBean() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String send() {
		return ("success");
	}
}