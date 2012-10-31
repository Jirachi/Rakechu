package com.miage.jirachi.rakechu;



public class ServerController {	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BitStream test1 = new BitStream();
		test1.write((int)489450145);
		test1.write((char)'a');
		test1.write("Salut");
		test1.write(15.405645f);
		
		Byte[] bytes = test1.getBytes();
		
		BitStream test2 = new BitStream(bytes);
		int value1 = test2.readInt();
		char value2 = test2.readChar();
		String value3 = test2.readString();
		float value4 = test2.readFloat();
		
		String serialized = "" + value1 + ";" + value2 + ";" + value3 + ";" + value4;
		
		System.out.println("Integer value: " + value1);
		System.out.println("Char value: " + value2);
		System.out.println("String value: " + value3);
		System.out.println("Float value: " + value4);
		System.out.println("Packet size: " + bytes.length + " bytes");
		System.out.println("Packet size if we'd String everything using ';' as separator: " + serialized.getBytes().length + " bytes");
	}

}
