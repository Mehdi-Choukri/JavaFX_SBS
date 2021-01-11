package application;

public class User {
	
	private String cin;
	private String nom;
	private String prenom;
	private String username;
	private String password;
	private String role;
	private int idFiliere;
	
	public User(String c,String n,String p,String u,String pass,int idF,String r)
	{
		
		setCin(c);
		setNom(n);
		setPrenom(p);
		setUsername(u);
		setPassword(pass);
		setIdFiliere(idF);
		setRole(r);
	}
	
	 
	 

	public static User authentifier(String user,String pass)
	{
		return null;
		
	}
	
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}

	public String getCin() {
		return cin;
	}

	public void setCin(String cin) {
		this.cin = cin;
	}

	public int getIdFiliere() {
		return idFiliere;
	}

	public void setIdFiliere(int idFiliere) {
		this.idFiliere = idFiliere;
	}
	
	

}
