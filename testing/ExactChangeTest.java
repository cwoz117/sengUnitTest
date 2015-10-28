package testing;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.matchers.JUnitMatchers;
import org.junit.Assert;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;

import ca.ucalgary.seng301.myvendingmachine.VendingMachineLogic;
import ca.ucalgary.seng301.vendingmachine.Coin;
import ca.ucalgary.seng301.vendingmachine.PopCan;
import ca.ucalgary.seng301.vendingmachine.hardware.CapacityExceededException;
import ca.ucalgary.seng301.vendingmachine.hardware.CoinRack;
import ca.ucalgary.seng301.vendingmachine.hardware.DisabledException;
import ca.ucalgary.seng301.vendingmachine.hardware.PopCanRack;
import ca.ucalgary.seng301.vendingmachine.hardware.VendingMachine;

public class ExactChangeTest {

	private VendingMachine vm;
	private VendingMachineLogic vml;
	
	@Before
	public void setup() {
		int[] lst = {5, 10, 25, 100};
		vm = new VendingMachine(lst, 3, 10, 10, 10);
		vml = new VendingMachineLogic(vm);

		String[] popNa = {"Coke", "water", "stuff"};
		List<String> popNames = new ArrayList<String>();
		popNames.addAll(Arrays.asList(popNa));
		Integer[] popCo = {250, 250, 205};
		List<Integer> popCosts = new ArrayList<Integer>();	
		popCosts.addAll(Arrays.asList(popCo));
		
		vm.configure(popNames, popCosts);
		
		
		List<Integer> coinCounts = new ArrayList<Integer>();
		Integer[] coinC = {1,1,2,0};
		coinCounts.addAll(Arrays.asList(coinC));
		int i = 0;
		for(Integer coinCount : coinCounts) {
			CoinRack cr = vm.getCoinRack(i);
			for(int count = 0; count < coinCount; count++)
				cr.loadWithoutEvents(new Coin(vm.getCoinKindForRack(i)));
			i++;
		}
		
		List<Integer> popCanCounts = new ArrayList<Integer>();
		Integer[] popCanCou = {1,1,1};
		popCanCounts.addAll(Arrays.asList(popCanCou));
		i = 0;
		for(Integer popCanCount : popCanCounts) {
			PopCanRack pcr = vm.getPopCanRack(i);
			for(int count = 0; count < popCanCount; count++)
				pcr.loadWithoutEvents(new PopCan(vm.getPopKindName(i)));
			i++;
		}
		
	}
	
	@Test
	public void insertTest() {
		try {
			vm.getCoinReceptacle().acceptCoin(new Coin(100));
		} catch (CapacityExceededException e) {
			e.printStackTrace();
		} catch (DisabledException e) {
			e.printStackTrace();
		}
		
		List<Object> lst = new ArrayList<Object>();
		lst.add("Coke");
		assertThis(lst, CoreMatchers.hasItem("Coke"));
		
		Object[] m = vm.getDeliveryChute().removeItems();
		fail("Not yet implemented");
		
		
	}

}