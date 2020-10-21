package myblockchain;

import java.security.*;
import java.util.ArrayList;

public class Transaksion {
	
	public String transaksionId; //Mban kodin hash te transaksionit
	public PublicKey dergues; //Adresa e derguesit/celesi publik.
	public PublicKey marres; //Adresa e marresit/ celesi publik.
	public float vlera; //Vlera qe deshirojme ti dergojme marresit.
	public byte[] nenshkrim; //nenshkrimi dixhitat qe i ndalon te tjeret te perdorin monedhat e portofolit tone.
	
	public ArrayList<TransaksionInput> inputet = new ArrayList<TransaksionInput>();
	public ArrayList<TransaksionOutput> outputet = new ArrayList<TransaksionOutput>();
	
	private static int nrTransaksione = 0; //per te numeruar sa transaksione jane gjeneruar

	public Transaksion(PublicKey dergues, PublicKey marres, float vlera,  ArrayList<TransaksionInput> inputet) {
		this.dergues = dergues;
		this.marres = marres;
		this.vlera = vlera;
		this.inputet = inputet;
	}
	
	public boolean procesoTransaksionin() {
		
		if(verifikoNenshkrimin() == false) {
			System.out.println("#Verifikimi i nenshkrimit dixhital te transaksionit deshtoi...");
			return false;
		}
				
		//Mbledh inputet (sigurohet qe jane te pashpenzuara):
		for(TransaksionInput i : inputet) {
			i.UTXO = MyCoin.UTXOs.get(i.treansaksionOutputId);
		}

		//Kontrollon nese transaksioni eshte i vlefshem:
		if(getVleraInputeve() < MyCoin.minTransaksion) {
			System.out.println("Transaksioni nuk mund te kryhet ne kete vlere te vogel: " + getVleraInputeve());
			System.out.println("Ju lutem vendosni nje vlere me te madhe se: " + MyCoin.minTransaksion);
			return false;
		}
		
		//Gjenero outputet e transaksionit:
		float mbetja = getVleraInputeve() - vlera; 
		transaksionId = calulateHash();
		outputet.add(new TransaksionOutput( this.marres, vlera,transaksionId)); //Dergo sasine e kriptomonedhave tek marresi
		outputet.add(new TransaksionOutput( this.dergues, mbetja,transaksionId)); //Dergo mbetjen/ "kusurin" serisht tek vetje(derguesi)		
				
		//Shto outputet ne listen e pashpenzuar
		for(TransaksionOutput o : outputet) {
			MyCoin.UTXOs.put(o.id , o);
		}
		
		//hiq inputet nga lista e outputeve te pashpenzuara (UTXOs), pasi u shpenzuan:
		for(TransaksionInput i : inputet) {
			if(i.UTXO == null) continue; //bej skip nese nuk gjendet transaksioni 
			MyCoin.UTXOs.remove(i.UTXO.id);
		}
		
		return true;
	}
	
	public float getVleraInputeve() {
		float totali = 0;
		for(TransaksionInput i : inputet) {
			if(i.UTXO == null) continue; 
			totali += i.UTXO.vlera;
		}
		return totali;
	}
	
	public void gjeneroNenshkrimin(PrivateKey celesPrivat) {
		String teDhena = UtiliteteString.getStringNgaCelesi(dergues) + UtiliteteString.getStringNgaCelesi(marres) + Float.toString(vlera)	;
		nenshkrim = UtiliteteString.aplikoECDSA(celesPrivat,teDhena);		
	}
	
	public boolean verifikoNenshkrimin() {
		String teDhena = UtiliteteString.getStringNgaCelesi(dergues) + UtiliteteString.getStringNgaCelesi(marres) + Float.toString(vlera)	;
		return UtiliteteString.verifikoECDSA(dergues, teDhena, nenshkrim);
	}
	
	public float getVleraOutputeve() {
		float totali = 0;
		for(TransaksionOutput o : outputet) {
			totali += o.vlera;
		}
		return totali;
	}
        
	
	private String calulateHash() {
		nrTransaksione++; //rritet numri i transaksioneve per te evituar 2 transaksione identike me te njejtin hash
		return UtiliteteString.aplikoSHA256(UtiliteteString.getStringNgaCelesi(dergues) +
				UtiliteteString.getStringNgaCelesi(marres) + Float.toString(vlera) + nrTransaksione);
	}
}