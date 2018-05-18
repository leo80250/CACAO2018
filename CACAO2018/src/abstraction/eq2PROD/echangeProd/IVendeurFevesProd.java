package abstraction.eq2PROD.echangeProd;

public interface IVendeurFevesProd {
	/* getPrix donne le prix de vente des fèves, et le grp3 ne peut nous acheter que des feves de
	 * qualité moyenne au prix du marché multiplié par 1.06 */
	public double getPrix();
	/* acheter prend en compte quantite indiqué par le grp3 et renvoie une quantité :
	 * -soit la quantité demandé et elle est alors vendue au grp3 au prix de vente du moment*1.06,
	 * il faut mettre à jour stock et le solde
	 * -soit la quantité est inférieure : elle est quand même vendue prix*1.06, il faut mettre à 
	 * jour stock et solde
	 * -soit 0 alors il n'y a pas d'échange, rien ne se passe
	 */
     public int  acheter(int quantite);
}
