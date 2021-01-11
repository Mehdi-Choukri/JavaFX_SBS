package application;

public class Competence extends Filiere{
	
	private int idCompetence ;
	
	public Competence(int idFiliere, String intitule,int idCom) {
		super(idFiliere, intitule);
		setIdCompetence(idCom);
		 
	}
	public Competence(int idCom) {
		 super();
		setIdCompetence(idCom);
		 
	}

	
	
	
	 
	public int getIdCompetence() {
		return idCompetence;
	}
	public void setIdCompetence(int idCompetence) {
		this.idCompetence = idCompetence;
	}
	 
	
	

}
