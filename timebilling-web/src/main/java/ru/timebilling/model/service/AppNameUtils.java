package ru.timebilling.model.service;

public class AppNameUtils {
	
	public static final String reservedWords = 
			"(?iu)\\b((АО)|(ОАО)|(ЗАО)|(ООО)|(ОДО)|(ПК)|(УП)|(ХП)|(НО)|(ОО)|(ФИРМА)|(КОМПАНИЯ)|(ОБЩЕСТВО)|(ФОНД)|(ОБЪЕДИНЕНИЕ))\\b";
	
	public static int MAX_LENGTH = 30;
	
	public static String removeReservedWords(String name){
		return name.replaceAll(reservedWords, "");
	}

	public static String removeQuotes(String name){
		name = name.replace("\"", "");
		name = name.replace("\'", "");
		return name;
	}

	public static String transliterate(String name){
		return Transliterator.transliterate(name);
	}
	
	public static String removeSpecialSymbsForURL(String name){
		return name.trim().replaceAll("[^a-zA-Z0-9/]" , "");
	}
	
	public static String cut(String name, int length){
		return name.substring(0, name.length() < MAX_LENGTH ? name.length() : MAX_LENGTH);
	}

	
	public static String prepareAppName(String name){
		return cut(removeSpecialSymbsForURL(
				transliterate(removeQuotes(removeReservedWords(name)))), MAX_LENGTH);
	}

	
	public static void main(String[] args){
		System.out.println(removeReservedWords("ООО РомОООашка"));
		System.out.println(removeQuotes(removeReservedWords("фирма \"РомОООашка\"\'1\'")));
		System.out.println(transliterate(removeQuotes(removeReservedWords("ФИРМА \"РомОООашка\"\'1\'"))));
		
		System.out.println(cut(removeSpecialSymbsForURL(
				transliterate(removeQuotes(removeReservedWords("ФИРМА \"РомОООашка\"\' и сыновья\'")))), MAX_LENGTH));
		
		System.out.println(prepareAppName("Компания Юков, Хренов и Партнеры"));
		System.out.println(prepareAppName("MGAP"));

	}

}
