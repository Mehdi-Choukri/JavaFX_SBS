package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

import javafx.scene.control.ComboBox;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

public class CompetenceSceneAppController implements Initializable{
	@FXML 
	private TableView<CompetenceAquise> tableID;
	@FXML
	private Label labUserNom;
	@FXML
	private Label lblNIVEAU;
	@FXML
	private TableColumn<CompetenceAquise, Integer> colID;
	@FXML
	private TableColumn<CompetenceAquise, String> colINTITULE;
	@FXML
	private TableColumn<CompetenceAquise, Integer>  colNV1;
	@FXML
	private TableColumn<CompetenceAquise, Integer> colNV2;
	@FXML
	private TableColumn<CompetenceAquise, Integer> colNV3;
	@FXML
	private TableColumn<CompetenceAquise, Date> colDATE;
	@FXML
	private Button btnMODIF;
	@FXML
	private Button btnSUPP;
	@FXML
	private Button btnAJOUT;
	@FXML
	private ComboBox<String> comboACTION;
	@FXML
	private ComboBox<String> comboCOMP;
	@FXML
	private ComboBox <String>comboNIVEAU;
	public String idUser;
	public void chargerCmbAction()
	{
		comboACTION.getItems().add("Ajouter une compétence");
		comboACTION.getItems().add("Modifier une compétence");
		comboACTION.getItems().add("Supprimer une compétence");
		comboACTION.getSelectionModel().select(0);;
		showAndHideBtn();
		
		
	}
	public void chargerCmbNiveau()
	{
		comboNIVEAU.getItems().add("Niveau 1");
		comboNIVEAU.getItems().add("Niveau 2");
		comboNIVEAU.getItems().add("Niveau 3");
		comboNIVEAU.getSelectionModel().select(0);;
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		 
		chargerCmbAction();
		chargerDataUser();
		showCompetenceAquise();
		chargeCmbCompetenceByQuery();
		chargerCmbNiveau();
	}
	public void chargerDataUser()
	{
		String nom = "";
		String prenom = "";
		 SessionID si = new SessionID();
		  idUser = si.getIdUser();
		  Connection conn = getConnection();
			String query = "SELECT * FROM user WHERE CIN ='"+idUser+"'";
			
			Statement st;
			ResultSet rs;
			try
			{
				st = conn.createStatement();
				rs = st.executeQuery(query);
				 
				while(rs.next())
				{
					 
					nom = rs.getString("NOM");
					prenom = rs.getString("PRENOM");
				 
				}
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		 labUserNom.setText(nom + " " + prenom);
	}
	public Connection getConnection()
	{
		 
		Connection c ;
		try
		{
 
			c = DriverManager.getConnection("jdbc:mysql://localhost:3306/DB_STEP?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root","");
			 
			return c;
		}catch(Exception e)
		{
		 
			System.out.println("Erreur : " + e.getMessage());
			return null;
		}
	}
	public void showAndHideBtn()
	{
		if(comboACTION.getValue() == "Ajouter une compétence")
		{
			btnAJOUT.setVisible(true);
			btnMODIF.setVisible(false);
			btnSUPP.setVisible(false);
			lblNIVEAU.setVisible(true);
			comboNIVEAU.setVisible(true);
		}
		else if(comboACTION.getValue() == "Modifier une compétence")
		{
			btnAJOUT.setVisible(false);
			btnMODIF.setVisible(true);
			btnSUPP.setVisible(false);
			lblNIVEAU.setVisible(true);
			comboNIVEAU.setVisible(true);
		}
		else if(comboACTION.getValue() == "Supprimer une compétence")
		{
			btnAJOUT.setVisible(false);
			btnMODIF.setVisible(false);
			btnSUPP.setVisible(true);
			lblNIVEAU.setVisible(false);
			comboNIVEAU.setVisible(false);
		}
		chargeCmbCompetenceByQuery();
	}
	
	public ObservableList<CompetenceAquise> getCompetenceAquise()
	{
		ObservableList<CompetenceAquise> ListcompetencesAquise = FXCollections.observableArrayList();
		Connection conn = getConnection();
		String query = "SELECT * FROM competence_aquise WHERE CIN ='"+idUser+"'";
		Statement st;
		ResultSet rs;
		try
		{
			st = conn.createStatement();
			rs = st.executeQuery(query);
			CompetenceAquise CA ;
			while(rs.next())
			{
				CA = new CompetenceAquise(rs.getInt("ID_COMPETENCE"),rs.getString("CIN"),rs.getInt("NIVEAU1"),rs.getInt("NIVEAU2"),rs.getInt("NIVEAU3"),rs.getDate("DATE_AQUISITION"),CompetenceName(rs.getInt("ID_COMPETENCE")));
				
				ListcompetencesAquise.add(CA);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return ListcompetencesAquise;
		
	}
	public String CompetenceName(int id_competence)
	{
		String res="";
		Connection conn = getConnection();
		String query = "SELECT INTITULE FROM competence where ID_COMPETENCE = "+id_competence;
		Statement st;
		ResultSet rs;
		try
		{
			st = conn.createStatement();
			rs = st.executeQuery(query);
			
			while(rs.next())
			{
				 
				res = rs.getString("INTITULE");
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		return res;
	}
	public void showCompetenceAquise()
	{
		ObservableList<CompetenceAquise> ListcompetencesAquise  = getCompetenceAquise();
		colID.setCellValueFactory(new PropertyValueFactory<CompetenceAquise,Integer>("idCompetence"));
		colINTITULE.setCellValueFactory(new PropertyValueFactory<CompetenceAquise,String>("intitule"));
		colNV1.setCellValueFactory(new PropertyValueFactory<CompetenceAquise,Integer>("niveau1"));
		colNV2.setCellValueFactory(new PropertyValueFactory<CompetenceAquise,Integer>("niveau2"));
		colNV3.setCellValueFactory(new PropertyValueFactory<CompetenceAquise,Integer>("niveau3"));
		colDATE.setCellValueFactory(new PropertyValueFactory<CompetenceAquise,Date>("dateAquisition"));
		
		tableID.setItems(ListcompetencesAquise);

	}
	
	public void chargeCmbCompetenceByQuery()
	{
		String req = "";
		if(comboACTION.getValue() == "Ajouter une compétence")
		{
			req = "SELECT competence.ID_COMPETENCE FROM competence WHERE   ID_COMPETENCE NOT IN (SELECT ID_COMPETENCE FROM competence_aquise  WHERE CIN ='"+idUser+"')";
			
		}
		else
		{
			req ="SELECT ID_COMPETENCE FROM competence_aquise WHERE  CIN = '"+idUser+"'";
		}
		chargerCmbCompetence(req);
		
	}
	public void chargerCmbCompetence(String query)
	{
		comboCOMP.getItems().clear();
		Connection conn = getConnection();
		 
		Statement st;
		ResultSet rs;
		try
		{
			st = conn.createStatement();
			rs = st.executeQuery(query);
			
			while(rs.next())
			{
				 
				comboCOMP.getItems().add(CompetenceName(rs.getInt("ID_COMPETENCE")));
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		 
		comboCOMP.getSelectionModel().select(0);
	}
	public int CompetenceID(String intitule_competence)
	{
		int res=0;
		Connection conn = getConnection();
		String query = "SELECT ID_COMPETENCE  FROM competence where  INTITULE = '"+intitule_competence+"'";
		Statement st;
		ResultSet rs;
		try
		{
			st = conn.createStatement();
			rs = st.executeQuery(query);
			
			while(rs.next())
			{
				 
				res = rs.getInt("ID_COMPETENCE");
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		return res;
	}
	public void addCompetence()
	{
		String query = "";
		if(comboNIVEAU.getValue() == "Niveau 1")
		{
			query = "INSERT INTO competence_aquise  VALUES ("+CompetenceID(comboCOMP.getValue())+",'"+idUser+"',"+1+","+0+","+0+","+null+")";
		 
		}
		else if( comboNIVEAU.getValue() == "Niveau 2")
		{
			query = "INSERT INTO competence_aquise  VALUES ("+CompetenceID(comboCOMP.getValue())+",'"+idUser+"',"+1+","+1+","+0+","+null+")";
		}
		else if(comboNIVEAU.getValue() == "Niveau 3")
		{
			query = "INSERT INTO competence_aquise  VALUES ("+CompetenceID(comboCOMP.getValue())+",'"+idUser+"',"+1+","+1+","+1+",CURDATE())";
		}
		
		if(executeQuery(query))
		{
			showAlertWithHeaderText("Message d'insertion ","Vous avez ajouté la compétence avec succès ! ","Bravo !");
			showCompetenceAquise();
			chargeCmbCompetenceByQuery();

		}
		else
		{
			showAlertWithHeaderText("Message d'insertion","Vous n'avez pas réussi l'ajout ! ","Il y a une erreur dans le systéme");

		}
		
	}
	private Boolean executeQuery(String query) {
		Boolean res = false;
		Connection conn = getConnection();
		Statement st;
		
		try
		{
			st = conn.createStatement();
			st.executeUpdate(query);
			res = true;
			 
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return res;
		
	}
	private static void showAlertWithHeaderText(String title,String midText,String message) {
		
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(midText);
        alert.setContentText(message);
 
        alert.showAndWait();
    }
	public void deleteCompetence()
	{
		if(displayModalPopup("Avertissement","Oui pour continuer","Non pour annuler"))
		{
			String query = "DELETE FROM competence_aquise WHERE CIN='"+idUser+"' and ID_COMPETENCE="+CompetenceID(comboCOMP.getValue());
			
			if(executeQuery(query))
			{
				showAlertWithHeaderText("Message de suppression ","Vous avez Supprimé la compétence avec succès ! ","à la prochaine !");
				showCompetenceAquise();
				chargeCmbCompetenceByQuery();

			}
			else
			{
				showAlertWithHeaderText("Message de suppression","Vous n'avez pas réussi la suppression ! ","Il y a une erreur dans le systéme");

			}
		}
		 
		
	}
	public boolean displayModalPopup(String message, String yesmessage, String nomessage) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Perte éventuelle de données");
		alert.setContentText("Est-ce que vous voulez continuer ?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK)
			return true;
		return false;
	}
	public void updateCompetence()
	{
		String query = "";
		if(comboNIVEAU.getValue() == "Niveau 1")
		{
			query = "UPDATE competence_aquise SET NIVEAU1="+1+",NIVEAU2="+0+",NIVEAU3="+0+",DATE_AQUISITION="+null+" WHERE ID_COMPETENCE ="+CompetenceID(comboCOMP.getValue())+" AND CIN='"+idUser+"'";
			System.out.println(query);
		 
		}
		else if( comboNIVEAU.getValue() == "Niveau 2")
		{
			query = "UPDATE competence_aquise SET NIVEAU1="+1+",NIVEAU2="+1+",NIVEAU3="+0+",DATE_AQUISITION="+null+" WHERE ID_COMPETENCE ="+CompetenceID(comboCOMP.getValue())+" AND CIN='"+idUser+"'";
			 
		}
		else if(comboNIVEAU.getValue() == "Niveau 3")
		{
			query = "UPDATE competence_aquise SET NIVEAU1="+1+",NIVEAU2="+1+",NIVEAU3="+1+",DATE_AQUISITION = CURDATE()  WHERE ID_COMPETENCE ="+CompetenceID(comboCOMP.getValue())+" AND CIN='"+idUser+"'";
 
		}
		
		 
		if(executeQuery(query))
		{
			showAlertWithHeaderText("Message de modification ","Vous avez modifié la compétence avec succès ! ","Bravo !");
			showCompetenceAquise();
			chargeCmbCompetenceByQuery();

		}
		else
		{
			showAlertWithHeaderText("Message de modification","Vous n'avez pas réussi la modification ! ","Il y a une erreur dans le systéme");

		}
		 
		
	}
	
	

}
