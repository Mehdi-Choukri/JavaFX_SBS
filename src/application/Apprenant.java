package application;

public class Apprenant extends User implements interListe{

	private int promotion ;
	public Apprenant(String c, String n, String p, String u, String pass, int idF, String r,int promo) {
		super(c, n, p, u, pass, idF, r);
		setPromotion(promo);
	}
	 
	public int getPromotion() {
		return promotion;
	}

	public void setPromotion(int promotion) {
		this.promotion = promotion;
	}

}
