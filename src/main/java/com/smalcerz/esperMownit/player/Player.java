package com.smalcerz.esperMownit.player;

import java.util.ArrayList;

import com.smalcerz.esperMownit.handler.util.EventGenerator;

public class Player {
	
	private static Player instance = new Player();
	
	ArrayList<EventGenerator> eventsGenerators = new ArrayList<EventGenerator>();
	
	private Player() {
		
	}
	
	public void addNewEventGenerator(EventGenerator eg) {
		eg.startSendingReadings();
		this.eventsGenerators.add(eg);
	}
	
	public void removeEventGenerator(EventGenerator eg) {
		
		eg.stopSendingReadings();
		this.eventsGenerators.remove(eg);
		
	}
	
	public Player getInstance() {
		return instance;
	}
}
