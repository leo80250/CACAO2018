package abstraction.eq4TRAN;

import abstraction.eq3PROD.echangesProdTransfo.ContratFeve;
import abstraction.eq4TRAN.VendeurChoco.Vendeur;
import abstraction.eq7TRAN.echangeTRANTRAN.ContratPoudre;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Journal;

public class SousActeur {
	private Indicateur stockTabBQ ;
	private Indicateur stockTabMQ ;
	private Indicateur stockTabHQ ;
	private Indicateur stockChocMQ ;
	private Indicateur stockChocHQ ;
	private Indicateur prodTabBQ ;
	private Indicateur prodTabMQ ;
	private Indicateur prodTabHQ ;
	private Indicateur prodChocMQ ;
	private Indicateur prodChocHQ ;
	private Indicateur solde ; 
	private Journal JournalEq4 = new Journal("JournalEq4") ;
	private Vendeur vendeur;
	private ContratFeve[] contratFeveEnCours ; 
	private ContratPoudre[] contratPoudreEnCoursEq7TRAN ;
	private ContratPoudre[] contratPoudreEnCoursEq5TRAN;
	
	public SousActeur(Indicateur stockTabBQ, Indicateur stockTabMQ, Indicateur stockTabHQ, Indicateur stockChocMQ, Indicateur stockChocHQ, Indicateur prodTabBQ, Indicateur prodTabMQ, Indicateur prodTabHQ,  Indicateur prodChocMQ , Indicateur prodChocHQ, ContratFeve[] contratFeveEnCours, ContratPoudre[] contratPoudreEnCoursEq7TRAN, ContratPoudre[] contratPoudreEnCoursEq5TRAN) {
	this.JournalEq4 = JournalEq4;
	this.prodChocHQ = prodChocHQ;
	this.prodChocMQ = prodChocMQ;
	this.prodTabBQ = prodTabBQ;
	this.prodTabHQ = prodTabHQ;
	this.prodTabMQ = prodTabMQ;
	this.solde = solde;
	this.stockChocHQ = stockChocHQ;
	this.stockChocMQ = stockChocMQ;
	this.stockTabBQ = stockTabBQ;
	this.stockTabHQ = stockTabHQ;
	this.stockTabMQ = stockTabMQ;
	this.vendeur = vendeur;
	this.contratFeveEnCours = contratFeveEnCours;
	this.contratPoudreEnCoursEq5TRAN = contratPoudreEnCoursEq5TRAN;
	this.contratPoudreEnCoursEq7TRAN = contratPoudreEnCoursEq7TRAN;
	}
	public Indicateur getStockTabBQ() {
		return stockTabBQ;
	}
	public void setStockTabBQ(Indicateur stockTabBQ) {
		this.stockTabBQ = stockTabBQ;
	}
	public Indicateur getStockTabMQ() {
		return stockTabMQ;
	}
	public void setStockTabMQ(Indicateur stockTabMQ) {
		this.stockTabMQ = stockTabMQ;
	}
	public Indicateur getStockTabHQ() {
		return stockTabHQ;
	}
	public void setStockTabHQ(Indicateur stockTabHQ) {
		this.stockTabHQ = stockTabHQ;
	}
	public Indicateur getStockChocMQ() {
		return stockChocMQ;
	}
	public void setStockChocMQ(Indicateur stockChocMQ) {
		this.stockChocMQ = stockChocMQ;
	}
	public Indicateur getStockChocHQ() {
		return stockChocHQ;
	}
	public void setStockChocHQ(Indicateur stockChocHQ) {
		this.stockChocHQ = stockChocHQ;
	}
	public Indicateur getProdTabBQ() {
		return prodTabBQ;
	}
	public void setProdTabBQ(Indicateur prodTabBQ) {
		this.prodTabBQ = prodTabBQ;
	}
	public Indicateur getProdTabMQ() {
		return prodTabMQ;
	}
	public void setProdTabMQ(Indicateur prodTabMQ) {
		this.prodTabMQ = prodTabMQ;
	}
	public Indicateur getProdTabHQ() {
		return prodTabHQ;
	}
	public void setProdTabHQ(Indicateur prodTabHQ) {
		this.prodTabHQ = prodTabHQ;
	}
	public Indicateur getProdChocMQ() {
		return prodChocMQ;
	}
	public void setProdChocMQ(Indicateur prodChocMQ) {
		this.prodChocMQ = prodChocMQ;
	}
	public Indicateur getProdChocHQ() {
		return prodChocHQ;
	}
	public void setProdChocHQ(Indicateur prodChocHQ) {
		this.prodChocHQ = prodChocHQ;
	}
	public Indicateur getSolde() {
		return solde;
	}
	public void setSolde(Indicateur solde) {
		this.solde = solde;
	}
	public Journal getJournalEq4() {
		return JournalEq4;
	}
	public void setJournalEq4(Journal journalEq4) {
		JournalEq4 = journalEq4;
	}
	public Vendeur getVendeur() {
		return vendeur;
	}
	public void setVendeur(Vendeur vendeur) {
		this.vendeur = vendeur;
	}
	public ContratFeve[] getContratFeveEnCours() {
		return contratFeveEnCours;
	}
	public void setContratFeveEnCours(ContratFeve[] contratFeveEnCours) {
		this.contratFeveEnCours = contratFeveEnCours;
	}
	public ContratPoudre[] getContratPoudreEnCoursEq7TRAN() {
		return contratPoudreEnCoursEq7TRAN;
	}
	public void setContratPoudreEnCoursEq7TRAN(ContratPoudre[] contratPoudreEnCoursEq7TRAN) {
		this.contratPoudreEnCoursEq7TRAN = contratPoudreEnCoursEq7TRAN;
	}
	public ContratPoudre[] getContratPoudreEnCoursEq5TRAN() {
		return contratPoudreEnCoursEq5TRAN;
	}
	public void setContratPoudreEnCoursEq5TRAN(ContratPoudre[] contratPoudreEnCoursEq5TRAN) {
		this.contratPoudreEnCoursEq5TRAN = contratPoudreEnCoursEq5TRAN;
	}

}
