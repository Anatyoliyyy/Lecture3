package Reflection;

import java.lang.reflect.*;

public class Reflection {

	static private final class Test {
		public String p = "Test string";
		private int a = 7;
		protected long b = 8;
		
		public Test() {}
		public Test(int a) { this.a = a; }
		public Test(int a, long b) { this.a = a; this.b = b; }
		public int getA() { return a; }
		public long getB() { return b; }
		public void setA(int a) { this.a = a; }
	}
	
	public static void main(String[] args) {
		final Class<?> cls = Test.class;
		
		System.out.println("Class name: " + cls.getName());
		
		System.out.print("Modifiers: ");
		int mods = cls.getModifiers(); 
		if (Modifier.isPrivate(mods))
		    System.out.print("private "); 
		if (Modifier.isAbstract(mods))
		    System.out.print("abstract "); 
		if (Modifier.isFinal(mods))
		    System.out.print("final ");

		System.out.println("All fields:");
		Field[] fields = cls.getDeclaredFields();
		for (Field field : fields) { 
		     Class<?> fieldType = field.getType(); 
		     System.out.println("\tName: " + field.getName()); 
		     System.out.println("\tType: " + fieldType.getName()); 
		} 
		
		System.out.println("Constructors:");
		Constructor<?>[] constrs = cls.getConstructors(); 
		for (Constructor<?> ctr : constrs) { 
		    Class<?>[] paramTypes = ctr.getParameterTypes(); 
		    for (Class<?> paramType : paramTypes) { 
		        System.out.print(paramType.getName() + " "); 
		    } 
		    System.out.println();
		} 
		
		try {
			Class<?>[] paramTypes = new Class<?>[] { int.class }; 
			Constructor<?> ctr = cls.getConstructor(paramTypes); 
			Test t = (Test)ctr.newInstance(1);
			System.out.println("Fields: " + t.getA() + ", " + t.getB());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		Method[] methods = cls.getMethods(); 
		for (Method method : methods) { 
		    System.out.println("Name: " + method.getName()); 
		    System.out.println("\tReturn type: " + method.getReturnType().getName()); 
		 
		    Class<?>[] paramTypes = method.getParameterTypes(); 
		    System.out.print("\tParam types:"); 
		    for (Class<?> paramType : paramTypes) { 
		        System.out.print(" " + paramType.getName()); 
		    } 
		    System.out.println(); 
		} 
		
		try {
			Test obj = new Test();
			Class<?>[] paramTypes = new Class<?>[] { int.class }; 
			Method method = cls.getMethod("setA", paramTypes);
			method.invoke(obj, 55);
			
			System.out.println("A: " + obj.getA());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		try {
			Test obj = new Test();
			Class<?>[] paramTypes = new Class<?>[] { int.class }; 
			Method method = cls.getMethod("someUnknownMethod", paramTypes);
			method.invoke(obj, 55);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// CyberVision
		try {
			Test obj = new Test();
			Field field = cls.getDeclaredField("a");
			field.setAccessible(true);
			System.out.println("Private field value: " + field.getInt(obj));
			field.setInt(obj, 100);
			System.out.println("New private field value: " + field.getInt(obj));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}


//Class name: Reflection.Reflection$Test
//Modifiers: private final All fields:
//	Name: p
//	Type: java.lang.String
//	Name: a
//	Type: int
//	Name: b
//	Type: long
//Constructors:
//int long
//int
//
//Fields: 1, 8
//Name: getA
//	Return type: int
//	Param types:
//Name: getB
//	Return type: long
//	Param types:
//Name: setA
//	Return type: void
//	Param types: int
//Name: wait
//	Return type: void
//	Param types:
//Name: wait
//	Return type: void
//	Param types: long int
//Name: wait
//	Return type: void
//	Param types: long
//Name: equals
//	Return type: boolean
//	Param types: java.lang.Object
//Name: toString
//	Return type: java.lang.String
//	Param types:
//Name: hashCode
//	Return type: int
//	Param types:
//Name: getClass
//	Return type: java.lang.Class
//	Param types:
//Name: notify
//	Return type: void
//	Param types:
//Name: notifyAll
//	Return type: void
//	Param types:
//A: 55
//java.lang.NoSuchMethodException: Reflection.Reflection$Test.someUnknownMethod(int)
//	at java.lang.Class.getMethod(Class.java:1786)
//	at Reflection.Reflection.main(Reflection.java:88)
//Private field value: 7
//New private field value: 100
//
//Process finished with exit code 0