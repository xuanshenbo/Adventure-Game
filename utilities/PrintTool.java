package utilities;

/**
 * This class is used for debugging
 *
 * @author Gareth
 *
 */
public class PrintTool {

	private static boolean debugging = true;

	public static void p(String s) {
		if (debugging) {
			StackTraceElement[] stackTraceElements = Thread.currentThread()
					.getStackTrace();
			System.out.print(stackTraceElements[2].getClassName() + "."
					+ stackTraceElements[2].getMethodName() + "() line:"
					+ stackTraceElements[2].getLineNumber() + ": ");
			outCleanUp();
			System.out.print(s);
			errCleanUp();
			System.out.println();
			outCleanUp();
		}
	}

	public static void p() {
		if (debugging) {
			StackTraceElement[] stackTraceElements = Thread.currentThread()
					.getStackTrace();
			System.out.print(stackTraceElements[2].getClassName() + "."
					+ stackTraceElements[2].getMethodName() + "() line:"
					+ stackTraceElements[2].getLineNumber() + ": ");
			outCleanUp();
			System.out.print("I MADE IT HERE");
			errCleanUp();
			System.out.println();
			outCleanUp();
		}
	}

	public static void p(Object o) {
		if (debugging) {
			StackTraceElement[] stackTraceElements = Thread.currentThread()
					.getStackTrace();
			if (o == null) {
				System.out.println("NULL");
				errCleanUp();
			} else {
				System.out.print(stackTraceElements[2].getClassName() + "."
						+ stackTraceElements[2].getMethodName() + "() line:"
						+ stackTraceElements[2].getLineNumber() + ": ");
				outCleanUp();
				System.out.print(o.toString());
				errCleanUp();
				System.out.println();
				outCleanUp();
			}
		}
	}

	private static void errCleanUp() {
		System.err.flush();
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
		}
	}

	private static void outCleanUp() {
		System.out.flush();
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
		}
	}
}
