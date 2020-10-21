package myblockchain;
import java.util.ArrayList;
import java.util.Date;

public class Bllok {	
	public String hash;
	public String hashPerpara; 
	public String rrenjaMerkle;
	public ArrayList<Transaksion> transaksione=new ArrayList<Transaksion>(); 
	public long vulaKohore; //numri i milisekondave qe nga 1/1/1970.
	public int nonce;
	 
	public Bllok(String hashPerpara ) {
		this.hashPerpara = hashPerpara;
		this.vulaKohore = new Date().getTime();		
		this.hash = llogaritHash(); //pasi kemi marre vlerat e mesiperme.
	}
	//Llogaritja e hashit ne baze te permbajtjes se blloqeve
	public String llogaritHash() {
		String hashStr = UtiliteteString.aplikoSHA256(hashPerpara +
				Long.toString(vulaKohore) +
				Integer.toString(nonce) + 
				rrenjaMerkle
				);
		return hashStr;
	}
	//Rrit nounce dhe llogarit hash deri sa te arrihet targeti 
        //(aq 0 ne fillim sa tregon prefiksi).
	public void minoBllokun(int prefix) {
		rrenjaMerkle = UtiliteteString.getRrenjeMerkle(transaksione);
                //krijo nje string me prefix * "0"
		String target = UtiliteteString.getPrefixString(prefix);  
		while(!hash.substring(0, prefix).equals(target)) {
			nonce ++;
			hash = llogaritHash();
		}
		System.out.println("Blloku u krijua : \n{\n Hash paraardhes: "  + hashPerpara +"\nHash: " 
                        + hash + "\n}");
	}	
	//Shtojme transaksionin ne bllok
	public boolean shtoTransaksion(Transaksion transaksion) {
		//behet procesimi i transaksionit dhe verifikohet validiteti 
                //nese nuk este lloku gjeneze.
		if(transaksion == null) return false;		
		if((!"0".equals(hashPerpara))) {
                    if((transaksion.procesoTransaksionin() != true)) {
                        System.out.println("Transaksioni deshtoi ne procesim.");
                        return false;
                    }
		}
		transaksione.add(transaksion);
		System.out.println("Transaksioni u shtua me sukses ne bllok");
		return true;
	}
	
}