package ibannummer_2;

import java.util.Scanner;

public class Iban_main {
	
	public static String laekennung, laenderkennung;
	public static String blz;
	public static String nummer;
	public static Long intteilerg;
	public static int mod, i;
	private final static int con = 98;
		
	public static String ibannr, bban;
	public static String bbanerw , stringtemp;
	private static String pruefz, zcode;

	
	
	public static void main(String[] args) {
		
	Scanner sc = new Scanner(System.in);
	
	System.out.println("Bitte geben sie die Länderkennung ein: ");
	laenderkennung = sc.next();

	
	System.out.println("Bitte geben sie die Bankleitzahl ein: ");
	blz = sc.next();
	
	
	System.out.println("Bitte geben sie die Kontonummer ein: ");
	nummer = sc.next();
		
	erzeugeIban(laenderkennung, blz, nummer);	
	System.out.println("IBAN-Nummer der Kontodaten: " + ibannr);
		
	}
	
	
	
	public static String erzeugeIban(String laenderkennung, String blz, String nummer)  {
			
		laekennung = normalisiereLaendercode(laenderkennung);
		System.out.println(laekennung);
		
		nummer = normalisiereKontonr(nummer);
		System.out.println("normalisierte Kto-Nr: " + nummer);		
		
		bban = erzeugeBban(blz, nummer);
		System.out.println("generierte BBAN: " + bban);

		zcode = erstelleZahlencode(laekennung); 
		System.out.println("Länderkennung als Code: " + zcode);		
		
		ergebnisAufg4(bban);
//		System.out.println(bbanerw);
		
		erzeugeModulo();
		System.out.println("letzter Mod-Wert: " + mod);
		
		erstellePruefzahl(mod);
		
		erstelleIban();
		System.out.println("Prüfziffer: " + pruefz);
		
		return(ibannr);
			
	}
	
	private static String erstelleIban() {
	
		ibannr = laekennung + pruefz + bban;
		return ibannr;
		
	}

	// Erstellen der Prüfzahl aus dem letzten Modulo-Wert
	private static void erstellePruefzahl(int mod) {
		
		if((con - mod) > 9)  {
			pruefz = Integer.toString(con - mod);
		}  else  { 
			pruefz = '0' + Integer.toString(con - mod);
		}
		
	}


	private static int erzeugeModulo() {
	
		i = 9;
		stringtemp = bbanerw;
			
		// Auslesen der ersten 9 Stellen in eine int-Variable
		intteilerg = (long) Integer.parseInt(stringtemp.substring(0, 9));
		mod = (int) (intteilerg % 97);

		// schreiben des Modulo als erste Ziffern in die Variable
		stringtemp = Integer.toString(mod);
		
//		System.out.println("erster modulo-Wert: " + stringtemp);
			
		// die temporäre Variable wird aufgefüllt bis auf 9 Stellen					
		while (stringtemp.length() < 9 && i < bbanerw.length())  {
				stringtemp = stringtemp + (bbanerw.substring(i,i+1));
				i++;
				}
		
//		System.out.println("zweiter Teilstring: " + stringtemp);
	
		mod = Integer.parseInt(stringtemp) % 97;
		stringtemp = Integer.toString(mod);
		
//		System.out.println("zweiter modulo-Wert: " + stringtemp);
			
		while (stringtemp.length() < 9 && i < bbanerw.length())  {
			
			stringtemp = stringtemp + (bbanerw.substring(i,i+1));
			i++;
			}
//		System.out.println("dritter Teilstring: " + stringtemp);
						
		mod = Integer.parseInt(stringtemp) % 97;
//		System.out.println("dritter Modulowert: " + mod);
		
	
		stringtemp = Integer.toString(mod);	
		
		while (stringtemp.length() < 9 && i < bbanerw.length())  {
			
			stringtemp = stringtemp + (bbanerw.substring(i,i+1));
			i++;
			}
		System.out.println("vierter Teilstring: " + stringtemp);
					
		mod = Integer.parseInt(stringtemp) % 97;
		System.out.println("Letzter Modulo-Wert: " + mod);
		return mod;
	}
	
	
	
	private static String ergebnisAufg4(String bban) {
		bbanerw = bban + zcode + "00";
		return bbanerw;
		
	}

	private static String erstelleZahlencode(String laekennung) {
		zcode = Integer.toString(laekennung.charAt(0) - 64 + 9) + Integer.toString(laekennung.charAt(1) - 64 + 9);
		return zcode;
	}


	private static String erzeugeBban(String blz, String kto) {
		bban = blz + kto;
		return bban;
	}


	// Normalisieren durch Auffüllen mit Nullen
	private static String normalisiereKontonr(String kto) {
		
		while (kto.length() < 10)  {
			kto = '0' + kto;
			}	
		return kto;
	}

	// Normalisieren der Länderkennung
	private static String normalisiereLaendercode(String laekennung) {
		return laekennung.toUpperCase(); 
	}

}
