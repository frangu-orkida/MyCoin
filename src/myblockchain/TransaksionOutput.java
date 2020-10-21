/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myblockchain;

import java.security.PublicKey;

public class TransaksionOutput {
	public String id;
	public PublicKey marresi; //i njohur si zoteruesi i ri i monedhave.
	public float vlera; //sasia e monedhave qe zoterojne
	public String transaksionPrindId; //id e transaksionit ku ky output u krijua
	
	
	public TransaksionOutput(PublicKey marresi, float vlera, String transaksionPrindId) {
		this.marresi = marresi;
		this.vlera = vlera;
		this.transaksionPrindId = transaksionPrindId;
		this.id = UtiliteteString.aplikoSHA256(UtiliteteString.getStringNgaCelesi(marresi)+Float.toString(vlera)+transaksionPrindId);
	}
	
	//kontrollon nese moedha eshte imja
	public boolean eshteImja(PublicKey celesPublik) {
		return (celesPublik == marresi);
	}
	
}