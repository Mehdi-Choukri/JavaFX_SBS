package application;

import java.util.Date;

public class CompetenceAquise extends Competence{
	

	private String cin;
	private int niveau1;
	private int niveau2;
	private int niveau3;
	private String intitule;
	

	private Date dateAquisition;
	
	public CompetenceAquise(int idCom, String Cin,int n1,int n2,int n3,Date Da,String inti) {
		super(idCom);
		setCin(Cin);
		setNiveau1(n1);
		setNiveau2(n2);
		setNiveau3(n3);
		setDateAquisition(Da);
		setIntitule(inti);
	}
	public String getCin() {
		return cin;
	}


	public void setCin(String cin) {
		this.cin = cin;
	}

	
	public int getNiveau1() {
		return niveau1;
	}

	public void setNiveau1(int niveau1) {
		this.niveau1 = niveau1;
	}

	public int getNiveau2() {
		return niveau2;
	}

	public void setNiveau2(int niveau2) {
		this.niveau2 = niveau2;
	}

	public int getNiveau3() {
		return niveau3;
	}

	public void setNiveau3(int niveau3) {
		this.niveau3 = niveau3;
	}

	public Date getDateAquisition() {
		return dateAquisition;
	}

	public void setDateAquisition(Date dateAquisition) {
		this.dateAquisition = dateAquisition;
	}
	public String getIntitule() {
		return intitule;
	}
	public void setIntitule(String intitule) {
		this.intitule = intitule;
	}

}
