package dev.thatcher.daotests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dev.thatcher.daos.BankCrud;
import dev.thatcher.daos.BankCrudImpl;

public class DaoBankTest {
	private static BankCrud bank_crud = new BankCrudImpl();

	@BeforeEach
	public void beforeEach() {
		System.out.println("\n=========================================================\n");
	}

	@Test
	public void createTest() {
		System.out.println("\nCreate test\n");
		System.out.println("\nEnd Create test\n");
	}

	@Test
	public void getByIdTest() {
		System.out.println("\nGet By Id test\n");
		System.out.println("\nEnd Get By Id test\n");
	}

	@Test
	public void updateTest() {
		System.out.println("\nUpdate test\n");
		System.out.println("\nEnd Update test\n");
	}

	@Test
	public void deleteTest() {
		System.out.println("\nDelete test\n");
		System.out.println("\nEnd Delete test\n");
	}

	@Test
	public void getAllTest() {
		System.out.println("\nGet All test\n");
		System.out.println("\nEnd Get All test\n");
	}	
}
