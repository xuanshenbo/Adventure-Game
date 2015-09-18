package utilities;

/**
 * This class is used for debugging
 * @author Gareth
 *
 */
public class PrintTool {

	private static boolean debugging = true;



	public static void p(String s){
		if(debugging){
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			System.out.println(stackTraceElements[2].getClassName()+"."+
					stackTraceElements[2].getMethodName()+"() line:"+stackTraceElements[2].getLineNumber()+": "+s);
		}
	}

	public static void p(){
		if(debugging){
		StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
		System.out.println(stackTraceElements[2].getClassName()+"."+
				stackTraceElements[2].getMethodName()+" line:"+stackTraceElements[2].getLineNumber()+" I MADE IT HERE");
		}
	}

		public static void p(Object o){
		if(debugging){
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			if(o == null){
				System.out.println("NULL");
			}else{
				System.out.println(stackTraceElements[2].getClassName()+"."+
					stackTraceElements[2].getMethodName()+"() line:"+stackTraceElements[2].getLineNumber()+": "+o.toString());
			}
		}
	}
}
