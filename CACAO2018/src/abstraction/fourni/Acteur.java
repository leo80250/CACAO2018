package abstraction.fourni;

/**
 * Tout acteur doit implementer cette interface
 * 
 * Vous aurez donc a creer une/des implementation(s) de cette classe
 * 
 * @author Romuald Debruyne
 *
 */
public interface Acteur {
	/**
	 * @return Le nom de l'acteur
	 */
	public String getNom();
	
	/**
	 * Methode de l'acteur invoquee suite a l'appui sur le bouton NEXT de la fenetre principale
	 */
	public void next();
}
