package abstraction.eq4TRAN;

import java.util.ArrayList;

import abstraction.eq4TRAN.VendeurChoco.GPrix2;

/**
 * 
 * @author Etienne
 *
 */

// Interface décrivant les échanges entre Transformateurs et Distributeurs, en se plaçant du côté Transformateur
// (vendeur de chocolats)


public interface IVendeurChocoBis {
	
	/* Méthode fournissant aux distributeurs les stocks disponibles par produit, sous forme d'une ArrayList<Integer>
	Stocks, dans laquelle Stocks.get(IDProduit-1) = quantité (en nombre d'unités) du produit IDProduit en stock.
	Les IDProduit sont définis publiquement dans la classe GPrix2 du package eq4TRAN/VendeurChoco */
	
	public ArrayList<Integer> getStock();
	
	/* On utilise un tableau de prix spécial (GPrix2) défini dans Eq4TRAN/VendeurChoco, permettant de s'affranchir de prix
	 * constants, en définissant des prix différents en fonction de la quantité de produit achetée.
	 * Il est à noter que la classe GPrix2 est munie d'une méthode getPrixProduit(int quantite, int IDProduit) permettant 
	 * aux différents acteurs d'accéder facilement à un prix voulu pour un certain produit et une quantité donnée, sans 
	 * avoir à savoir manipuler/définir un tableau GPrix2.
	 * 
	 * Enfin, la méthode getPrix() doit fournir aux distributeurs les prix choisis pour chaque produit proposé (0 si le 
	 * produit n'est pas disponible à la vente)
	 */
	
	public GPrix2 getPrix();
	
	/* getLivraison() est une méthode qui prend en paramètre la commande des distributeurs (au premier indice
	 * l'équipe Eq1DIST, en deuxième Eq6DIST puis en troisième DISTFictif).
	 * A partir de ces commandes, le transformateur décide de ce qu'il livre effectivement à chaque distributeur.
	 * Chacune des 3 livraisons devra respecter le format décrit dans l'en-tête de la méthode getStock(), puis le 
	 * transformateur renvoie les 3 livraisons concaténées dans une liste, en suivant le format décrit ci-dessus.
	 */
	
	public ArrayList<ArrayList<Integer>> getLivraison(ArrayList<ArrayList<Integer>> commandes);
}
