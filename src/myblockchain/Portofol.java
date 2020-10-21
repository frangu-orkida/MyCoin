package myblockchain;
import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Portofol {
	
	public PrivateKey celesPrivat;
	public PublicKey celesPublik;
	
        public HashMap<String,TransaksionOutput> UTXOs = new HashMap<String,TransaksionOutput>();
	
	public Portofol() {
		gjeneroCiftCelesash();
	}
		
	public void gjeneroCiftCelesash() {
		try {
			KeyPairGenerator gjenCeles = KeyPairGenerator.getInstance("ECDSA","BC");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");
			// inicializo gjeneruesin e celesit dhe gjenero nje cift celesash
			gjenCeles.initialize(ecSpec, random); //256 
	        KeyPair ciftCeles = gjenCeles.generateKeyPair();
	        // dhnja e vlerave celesit publik dhe privat nga objekti ciftCeles
	        celesPrivat = ciftCeles.getPrivate();
	        celesPublik = ciftCeles.getPublic();
	        
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public float getBalance() {
		float total = 0;	
        for (Map.Entry<String, TransaksionOutput> item: MyCoin.UTXOs.entrySet()){
        	TransaksionOutput UTXO = item.getValue();
            if(UTXO.eshteImja(celesPublik)) { //nese outputi me perket mua (nese monedhat jane te mijat)
            	UTXOs.put(UTXO.id,UTXO); //shtoji ne listen tone te outputeve te pashpenzuara.
            	total += UTXO.vlera ; 
            }
        }  
		return total;
	}
	
	public Transaksion dergoFonde(PublicKey marresi,float vlera ) {
		if(getBalance() < vlera) {
			System.out.println("#Nuk ke fonde mjaftueshem per transaksionin.");
			return null;
		}
		ArrayList<TransaksionInput> inputet = new ArrayList<TransaksionInput>();
		
		float total = 0;
		for (Map.Entry<String, TransaksionOutput> item: UTXOs.entrySet()){
			TransaksionOutput UTXO = item.getValue();
			total += UTXO.vlera;
			inputet.add(new TransaksionInput(UTXO.id));
			if(total > vlera) break;
		}
		
		Transaksion transaksionRi = new Transaksion(celesPublik, marresi , vlera, inputet);
		transaksionRi.gjeneroNenshkrimin(celesPrivat);
		
		for(TransaksionInput input: inputet){
			UTXOs.remove(input.treansaksionOutputId);
		}
		
		return transaksionRi;
	}

    public PublicKey getCelesPublik() {
        return celesPublik;
    }

    
	
}
