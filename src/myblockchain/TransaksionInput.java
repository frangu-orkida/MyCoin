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



