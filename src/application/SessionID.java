package application;

public class SessionID {
	
	private static String idUser;
	
	public SessionID(String id)
	{
		setIdUser(id);
	}
	public SessionID()
	{
		
	}

	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		SessionID.idUser = idUser;
	}

	 

}
