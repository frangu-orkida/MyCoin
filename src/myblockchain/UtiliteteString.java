package myblockchain;

import java.security.*;
import java.util.ArrayList;
import java.util.Base64;
import com.google.gson.GsonBuilder;
import java.util.List;

public class UtiliteteString {
	
	//Aplikon algoritmin SHA256 ne nje streg dhe kthen vleren perseri 
            //si string me 32 karaktere. 
	public static String aplikoSHA256(String str){
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");	        
			//Aplikon sha256 tek parametri str, 
			byte[] hash = md.digest(str.getBytes("UTF-8"));
	        
			StringBuffer hexadecimalSB = new StringBuffer(); 
                        // do te mbaje Hash-in si hexadecimal
			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if(hex.length() == 1) hexadecimalSB.append('0');
				hexadecimalSB.append(hex);
			}
			return hexadecimalSB.toString();
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	//Aplikon nenshkrimin ECDSA dhe kthen rezultatin ( ne byte ).
	public static byte[] aplikoECDSA(PrivateKey celesPrivat, String input) {
		Signature dsa;
		byte[] output = new byte[0];
		try {
			dsa = Signature.getInstance("ECDSA", "BC");
			dsa.initSign(celesPrivat);
			byte[] strByte = input.getBytes();
			dsa.update(strByte);
			byte[] nenshkrimi = dsa.sign();
			output = nenshkrimi;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return output;
	}
	
	//Verifikon nje nenshkrim String 
	public static boolean verifikoECDSA(PublicKey celesPublik, 
                                            String teDhena, byte[] nenshkrimi) {
		try {
			Signature ecdsa = Signature.getInstance("ECDSA", "BC");
			ecdsa.initVerify(celesPublik);
			ecdsa.update(teDhena.getBytes());
			return ecdsa.verify(nenshkrimi);
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	//Kthen objektet e klases Object ne json string
	public static String getJson(Object o) {
		return new GsonBuilder().setPrettyPrinting().create().toJson(o);
	}
	
	//Kthen nje String me aq 0 sa prefiksi  
	public static String getPrefixString(int prefix) {
		return new String(new char[prefix]).replace('\0', '0');
	}	
	public static String getStringNgaCelesi(Key celes) {
		return Base64.getEncoder().encodeToString(celes.getEncoded());
	}	
	public static String getRrenjeMerkle(ArrayList<Transaksion> transaksione){
		int count = transaksione.size();
		
		List<String> TreeLayerPerpara = new ArrayList<String>();
		for(Transaksion transaksion : transaksione) {
			TreeLayerPerpara.add(transaksion.transaksionId);
		}
		List<String> treeLayer = TreeLayerPerpara;		
		while(count > 1) {
			treeLayer = new ArrayList<String>();
			for(int i=1; i < TreeLayerPerpara.size(); i+=2) {
				treeLayer.add(aplikoSHA256(TreeLayerPerpara.get(i-1)
                                        + TreeLayerPerpara.get(i)));
			}
			count = treeLayer.size();
			TreeLayerPerpara = treeLayer;
		}		
		String rrenjaMerkle = (treeLayer.size() == 1) ? treeLayer.get(0):"";
		return rrenjaMerkle;
	}
}
