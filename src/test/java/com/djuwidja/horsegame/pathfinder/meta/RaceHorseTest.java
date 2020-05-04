package com.djuwidja.horsegame.pathfinder.meta;

import java.util.Random;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class RaceHorseTest {
	@Test
	public void testInitialization() {		
		Random rand = new Random();
		double fwdSpdMax = rand.nextDouble();
		double angSpdMax = rand.nextDouble();
		double fwdAcc = rand.nextDouble();
		double angAcc = rand.nextDouble();
		
		RaceHorse horse = new RaceHorse(fwdSpdMax, angSpdMax, fwdAcc, angAcc);
		Assert.assertEquals(fwdSpdMax, horse.getFwdSpdMax(), 0.000000001d);
		Assert.assertEquals(angSpdMax, horse.getAngSpdMax(), 0.000000001d);
		Assert.assertEquals(fwdAcc, horse.getFwdAcc(), 0.000000001d);
		Assert.assertEquals(angAcc, horse.getAngAcc(), 0.000000001d);
	}
}
