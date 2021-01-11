package application;

import java.util.Date;

public class Staff extends User{

	public Staff(String c, String n, String p, String u, String pass, int idF, String r,Date dE) {
		super(c, n, p, u, pass, idF, r);
		 setDateEmbauche(dE);
	}
	 
	private Date dateEmbauche;
	 
	
	  
	public Date getDateEmbauche() {
		return dateEmbauche;
	}
	public void setDateEmbauche(Date dateEmbauche) {
		this.dateEmbauche = dateEmbauche;
	}

}
