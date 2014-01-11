/**
 * ThreadLinkTest.java
 * 
 * The main purpose of this unit test is to confirm that the equals() method
 * is functioning correctly
 */

package nibura.logic;

import java.net.MalformedURLException;

import nibura.logic.BoardListElement.SuiteType;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

public class ThreadLinkTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testEqualsObject() throws MalformedURLException {
		ThreadLink target = new ThreadLink("test","http://www.test.com/",111,SuiteType.NICH_SUITE);
		ThreadLink compareTo = new ThreadLink("test","http://www.test.com/",111,SuiteType.NICH_SUITE);
		ThreadLink diff = new ThreadLink("test", "http://www.diff.com/",123,SuiteType.NICH_SUITE);
		
		//Test
		boolean shouldBeTrue = target.equals(compareTo);
		boolean shouldBeFalse = target.equals(diff);
		
		Assert.assertTrue(shouldBeTrue);
		Assert.assertFalse(shouldBeFalse);
	}

}
