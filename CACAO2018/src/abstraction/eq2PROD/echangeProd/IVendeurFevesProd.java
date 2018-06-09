package abstraction.eq2PROD.echangeProd;

public interface IVendeurFevesProd {
	
	/* Modelisation par Romain Bernard avec les equipes 3, 4, 5, 7
	 * Code par Alexandre Bigot 
	/* getPrix() donne le prix de vente des feves
	 * Le groupe 3 ne peut nous acheter que des feves de qualite moyenne a notre prix multiplie par 1.06 */
	public double getPrix();
	
	/* acheter(quantite) prend en compte la quantite demandee par le groupe 3 et renvoie une quantite :
	 * ==> soit la quantite demandee, elle est alors vendue au groupe 3 a notre prix de vente*1.06
	 * (il faut mettre a jour stock et solde)
	 * ==> soit une quantite inferieure: elle est quand meme vendue prix*1.06
	 * (il faut mettre a jour stock et solde)
	 * ==> soit 0 alors il n'y a pas d'echange, rien ne se passe
	 */
     public int  acheter(int quantite);
}
