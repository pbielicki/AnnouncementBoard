package com.bielu.util.junit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import junit.framework.Assert;
import junit.framework.AssertionFailedError;

/**
 * <code>TestUtil</code> utility class for unit tests.
 */
public final class TestUtil {

	private TestUtil() {
	}

	/**
	 * Wraps given action in <code>try/catch</code> block and asserts caught exception.
	 * 
	 * @param exceptionClass
	 *            exception class that has to be caught.
	 * @param action
	 *            action that has to be invoked.
	 */
	public static void assertThrows(Class<? extends Throwable> exceptionClass, Action action) {
		try {
			action.run();
		} catch (Throwable e) {
			if (!exceptionClass.isAssignableFrom(e.getClass())) {
				fail("[" + exceptionClass.getName() + "] should have been thrown instead of [" + e.getClass().getName()
						+ "]" + "\n" + e.toString(), e);
			}
			return;
		}
		Assert.fail("[" + exceptionClass.getName() + "] should have been thrown");
	}

	private static void fail(String msg, Throwable e) throws AssertionFailedError {
		// simulating exception thrown from a different place
		AssertionFailedError error = new AssertionFailedError(msg);
		error.setStackTrace(e.getStackTrace());
		throw error;

	}

	public static void assertThrowsAndMsgContainsRe(Class<? extends Throwable> exceptionClass,
			String expectedMsgMatchesRe, Action action) {

		assertThrowsAndMsgMatchesRe(exceptionClass, ".*" + expectedMsgMatchesRe + ".*", action);
	}

	public static void assertThrowsAndMsgMatchesRe(Class<? extends Throwable> exceptionClass,
			String expectedMsgMatchesRe, Action action) {

		try {
			action.run();
		} catch (Throwable e) {
			if (!exceptionClass.isAssignableFrom(e.getClass())) {
				fail("[" + exceptionClass.getName() + "] should have been thrown instead of [" + e.getClass().getName()
						+ "]" + "\n" + e.toString(), e);
			}
			if (e.getMessage().matches(expectedMsgMatchesRe) == false) {
				fail("Exception message [" + e.getMessage() + "] does not match expected reg exp ["
						+ expectedMsgMatchesRe + "]", e);
			}
			return;
		}
		Assert.fail("[" + exceptionClass.getName() + "] with message matching [" + expectedMsgMatchesRe
				+ "] should have been thrown");

	}

	public static <E> void assertHasOnlyElements(E[] array, E... elements) {
		Assert.assertNotNull(array);
		Assert.assertEquals("size is different:", elements.length, array.length);
		List<E> tmp = Arrays.asList(array);
		for (E e : elements) {
			Assert.assertTrue("Given list does not contain element [" + e + "]", tmp.contains(e));
		}
	}

	public static <E> void assertHasOnlyElements(Collection<E> list, E... elements) {
		Assert.assertEquals(elements.length, list.size());
		for (E e : elements) {
			Assert.assertTrue("Given list does not contain element [" + e + "]", list.contains(e));
		}
	}

	public static <E> void assertEquals(Collection<E> a, Collection<E> b) {
		Assert.assertEquals(a.size(), b.size());
		Assert.assertTrue(a.containsAll(b));
		Assert.assertTrue(b.containsAll(a));
	}

	public static <E> void assertEqualsSymmetrical(E a, E b) {
		Assert.assertEquals(a, b);
		Assert.assertEquals(b, a);
	}

	public static <E> void assertEqualsSymmetrical(Collection<E> a, Collection<E> b) {
		assertEquals(a, b);
		assertEquals(b, a);
	}

	public static <E> void assertNotEquals(E a, E b) {
		if (a.equals(b) == true || b.equals(a) == true) {
			Assert.fail("[" + a + "] equals [" + b + "]");
		}
	}

	public static <E> void assertEquals(E[] expected, E[] actual) {
		if (expected.length != actual.length) {
			Assert.fail("Actual array has different number of elements than actual one [" + expected.length + "] vs. ["
					+ actual.length + "]");
		}

		for (int i = 0; i < expected.length; i++) {
			Assert.assertEquals("[" + i + "] element differes in arrays. Expected [" + expected[i] + "] actual ["
					+ actual[i] + "]", expected[i], actual[i]);
		}
	}

	public static <T> List<T> asList(Class<T> clazz, T... elements) {
		ArrayList<T> res = new ArrayList<T>(elements.length);
		for (T e : elements) {
			res.add(e);
		}
		return res;
	}

	public static <T> void assertNullOrEmpty(T[] array) {
		Assert.assertTrue(array == null || array.length == 0);
	}
}
