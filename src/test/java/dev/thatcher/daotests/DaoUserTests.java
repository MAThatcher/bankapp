package dev.thatcher.daotests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dev.thatcher.daos.UserCrud;
import dev.thatcher.daos.UserCrudImpl;

class DaoUserTests {

	private static UserCrud user_crud = new UserCrudImpl();
	
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
	
	@Test
	public void deleteAllTest() {
		System.out.println("\nDelete All test\n");
		System.out.println("\nEnd Delete All test\n");
	}
	
	@Test
	public void getByUserNameTest() {
		System.out.println("\nGet By Username test\n");
		System.out.println("\nEnd Get By Username test\n");
	}
	
	

	
}
