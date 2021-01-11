package application;

public class Filiere {
	
	private int idFiliere ;
	private String intitule ;
	
	public Filiere()
	{}

	public Filiere(int idFiliere, String intitule) {
		 
		setIdFiliere (idFiliere);
		setIntitule (intitule);
	}
	public int getIdFiliere() {
		return idFiliere;
	}
	public void setIdFiliere(int idFiliere) {
		this.idFiliere = idFiliere;
	}
	public String getIntitule() {
		return intitule;
	}
	public void setIntitule(String intitule) {
		this.intitule = intitule;
	}
	
}
