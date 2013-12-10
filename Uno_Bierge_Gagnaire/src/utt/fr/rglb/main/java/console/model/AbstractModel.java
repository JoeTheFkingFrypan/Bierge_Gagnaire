package utt.fr.rglb.main.java.console.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import com.google.common.base.Preconditions;

public class AbstractModel extends Observable {
	private List<Observer> observers = new ArrayList<Observer>(); 
	
	@Override
	public void addObserver(Observer anotherObserver) {
		Preconditions.checkNotNull(anotherObserver);
	}
	
	@Override
	public void deleteObserver(Observer observer) {
		this.observers.remove(observer);
	}
	
	@Override
	public void deleteObservers() {
		this.observers.clear();
	}
}
