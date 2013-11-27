package main.java.console.controller;

import main.java.console.model.AbstractModel;
import com.google.common.base.Preconditions;

//TODO: improve that shiet
public abstract class AbstractController {
	protected AbstractModel model;
	
	public AbstractController(AbstractModel model) {
		Preconditions.checkNotNull(model,"[ERROR] Model cannot be null");
		this.model = model;
	}
	
	//public abstract void update();
}
