/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myblockchain;

/**
 *
 * @author user
 */
class TransaksionInput {
    public String treansaksionOutputId; //Referon tek TransaksionOutputs -> Id
	public TransaksionOutput UTXO; //Mban outputin e pashpenzuar te transaksionit
	
	public TransaksionInput(String transaksionOutputId) {
		this.treansaksionOutputId = transaksionOutputId;
	}
    
}

//package myblockchain;
//
//import java.security.Security;
//import java.util.ArrayList;
////import java.util.Base64;
//import java.util.HashMap;
////import com.google.gson.GsonBuilder;
//import java.util.Map;
//
//public class MyCoin {
//	
//	public static ArrayList<Bllok> blockchain = new ArrayList<Bllok>();
//	public static HashMap<String,TransaksionOutput> UTXOs = new HashMap<String,TransaksionOutput>();
//	
//	public static int prefix = 3;
//	public static float minTransaksion = 0.1f;
//	public static Portofol protofol1;
//	public static Portofol portofol2;
//	public static Transaksion transaksionGjenez;
//
//	public static void main(String[] args) {	
//		//shtojme blloqet ne ArrayList blockchain:
//		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider()); //Vnedosim ofruesin e sigurise Bouncey Castle
//		
//		//Krijome portofolet:
//		protofol1 = new Portofol();
//		portofol2 = new Portofol();		
//		Portofol portofolBaze = new Portofol();
//		
//		//Krijojme transaksionin e gjenezes,i cili dergon 100 MyCoin tek portofoli i pare:
//		transaksionGjenez = new Transaksion(portofolBaze.celesPublik, protofol1.celesPublik, 100f, null);
//		transaksionGjenez.gjeneroNenshkrimin(portofolBaze.celesPrivat);	 //Behet nenshkrimi manual i transaksionit te gjenezes	
//		transaksionGjenez.transaksionId = "0"; //vendoset id ne menyre manuale
//		transaksionGjenez.outputet.add(new TransaksionOutput(transaksionGjenez.marres, transaksionGjenez.vlera, transaksionGjenez.transaksionId)); //shtojme ne menyre manuale output-in e transaksionit
//		UTXOs.put(transaksionGjenez.outputet.get(0).id, transaksionGjenez.outputet.get(0)); //ruajme transaksionin tone te pare ne listen e UTXOs.
//		
//		System.out.println("Krijimi dhe minimi i bllokut te gjenezes... ");
//		Bllok gjeneza = new Bllok("0");
//		gjeneza.shtoTransaksion(transaksionGjenez);
//		shtoBllok(gjeneza);
//		
//		//testing
//		Bllok blloku1 = new Bllok(gjeneza.hash);
//		System.out.println("\nBalanca e portofolit 1: " + protofol1.getBalance());
//		System.out.println("\nPortofoli 1 po perpiqet te coje fonde tek Portofoli2(40 MyCoin)...");
//		blloku1.shtoTransaksion(protofol1.dergoFonde(portofol2.celesPublik, 40f));
//		shtoBllok(blloku1);
//		System.out.println("\nBalanca e portofolit 1: " + protofol1.getBalance());
//		System.out.println("Balanca e portofolit 2: " + portofol2.getBalance());
//		
//		Bllok blloku2 = new Bllok(blloku1.hash);
//		System.out.println("\nWalletA Attempting to send more funds (1000) than it has...");
//		blloku2.shtoTransaksion(protofol1.dergoFonde(portofol2.celesPublik, 1000f));
//		shtoBllok(blloku2);
//		System.out.println("\nWalletA's balance is: " + protofol1.getBalance());
//		System.out.println("WalletB's balance is: " + portofol2.getBalance());
//		
//		Bllok block3 = new Bllok(blloku2.hash);
//		System.out.println("\nWalletB is Attempting to send funds (20) to WalletA...");
//		block3.shtoTransaksion(portofol2.dergoFonde(protofol1.celesPublik, 20));
//		System.out.println("\nWalletA's balance is: " + protofol1.getBalance());
//		System.out.println("WalletB's balance is: " + portofol2.getBalance());
//		
//		eshteZinxhiriValid();
//		
//	}
//	
//	public static Boolean eshteZinxhiriValid() {
//		Bllok bllokuAktual; 
//		Bllok bllokPerpara;
//		String hashTarget = new String(new char[prefix]).replace('\0', '0');
//		HashMap<String,TransaksionOutput> tempUTXOs = new HashMap<String,TransaksionOutput>(); //nje liste e perkoheshme me transaksione te pashpenzuara ne nje gjendje blloku te dhene.
//		tempUTXOs.put(transaksionGjenez.outputet.get(0).id, transaksionGjenez.outputet.get(0));
//		
//		//cikel pergjat blockchain per te kontrolluar hashet:
//		for(int i=1; i < blockchain.size(); i++) {
//			
//			bllokuAktual = blockchain.get(i);
//			bllokPerpara = blockchain.get(i-1);
//			//krahaso hashin e regjistruar me ate te llogariturin:
//			if(!bllokuAktual.hash.equals(bllokuAktual.llogaritHash()) ){
//				System.out.println("#Hashet Aktuale nuk jane te barabarta");
//				return false;
//			}
//			//krahaso hashin e llogarit te bllokut para me hashin e meperparshem te bllokut aktual  
//			if(!bllokPerpara.hash.equals(bllokuAktual.hashPerpara) ) {
//				System.out.println("#Hashet e meperpareshme nuk jane te barabarta");
//				return false;
//			}
//			//kontrollo nese hashi eshte zgjidhur/minuar
//			if(!bllokuAktual.hash.substring(0, prefix).equals(hashTarget)) {
//				System.out.println("#Hashi nuk eshte minuar");
//				return false;
//			}
//			
//			//cikel pergjate transaksioneve te blockchain:
//			TransaksionOutput tempOutput;
//			for(int t=0; t <bllokuAktual.transaksione.size(); t++) {
//				Transaksion transaksionAktual = bllokuAktual.transaksione.get(t);
//				
//				if(!transaksionAktual.verifikoNenshkrimin()) {
//					System.out.println("#Nenshkrimi ne transaksionin (" + t + ") nuk eshte i vlefshem");
//					return false; 
//				}
//				if(transaksionAktual.getVleraInputeve() != transaksionAktual.getVleraOutputeve()) {
//					System.out.println("#Inputet nuk jane te barabarta me outputet ne transaksionin(" + t + ")");
//					return false; 
//				}
//				
//				for(TransaksionInput input: transaksionAktual.inputet) {	
//					tempOutput = tempUTXOs.get(input.treansaksionOutputId);
//					
//					if(tempOutput == null) {
//						System.out.println("#Inputi i referencuar ne Transaksionin (" + t + ") mungon!");
//						return false;
//					}
//					
//					if(input.UTXO.vlera != tempOutput.vlera) {
//						System.out.println("#Inputi i referencuar ne Transaksionin (" + t + ") ka vlere te pavlefshme!");
//						return false;
//					}
//					
//					tempUTXOs.remove(input.treansaksionOutputId);
//				}
//				
//				for(TransaksionOutput output: transaksionAktual.outputet) {
//					tempUTXOs.put(output.id, output);
//				}
//				
//				if( transaksionAktual.outputet.get(0).marresi != transaksionAktual.marres) {
//					System.out.println("#Marresi i outputit te transaksionit (" + t + ") nuk eshte marresi ne fjale!");
//					return false;
//				}
//				if( transaksionAktual.outputet.get(1).marresi != transaksionAktual.dergues) {
//					System.out.println("#Mbetja e transaksionit (" + t + ") nuk po i kthehet derguesit!");
//					return false;
//				}
//				
//			}
//			
//		}
//		System.out.println("Blockchain eshte i vlefshem");
//		return true;
//	}
//	
//	public static void shtoBllok(Bllok bllokRi) {
//		bllokRi.minoBllokun(prefix);
//		blockchain.add(bllokRi);
//	}
//}

