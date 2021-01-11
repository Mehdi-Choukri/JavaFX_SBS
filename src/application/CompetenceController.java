package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class CompetenceController implements Initializable{
	
	public String idUser;
	public int idGroupe;
	@FXML
	private Label labUserNom;
	@FXML
	private Label lblGroupe;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		chargerDataUser();
		showApprenant();
		chargeCombobox();
		
		lblGroupe.setText(groupeName(idGroupe)); 
	}
	@FXML 
	private Label titleID ;
	 
	//Table des apprenants
	@FXML 
	private ComboBox<String> comboUSER ;
	@FXML 
	private TableView<Apprenant> tableAPP;
	@FXML 
	private TableColumn<Apprenant, String> colCIN;
	@FXML 
	private TableColumn<Apprenant, String> colNOM;
	@FXML 
	private TableColumn<Apprenant, String> colPRENOM;
	@FXML 
	private TableColumn<Apprenant, String> colUSER;
	@FXML 
	private TableColumn<Apprenant, Integer> colPROMO;
	
	//Table des competences
	 
		@FXML 
		private TableView<CompetenceAquise> tableCOMP;
		 
		@FXML 
		private TableColumn<CompetenceAquise, Integer> colCOMP;
		@FXML 
		private TableColumn<CompetenceAquise, String> colINTI;
		@FXML 
		private TableColumn<CompetenceAquise, Integer> colNV1;
		@FXML 
		private TableColumn<CompetenceAquise, Integer> colNV2;
		@FXML 
		private TableColumn<CompetenceAquise, Integer> colNV3;
		@FXML 
		private TableColumn<CompetenceAquise, Date> colDATE;
		
		public void Actions(ActionEvent e)
		{
			showCompetenceAquise();
		}
		
		
		public ObservableList<CompetenceAquise> getCompetenceAquise()
		{
			ObservableList<CompetenceAquise> ListcompetencesAquise = FXCollections.observableArrayList();
			Connection conn = getConnection();
			String query = "SELECT * FROM competence_aquise WHERE CIN ='"+comboUSER.getValue()+"'";
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
		public void showCompetenceAquise()
		{
			ObservableList<CompetenceAquise> ListcompetencesAquise  = getCompetenceAquise();
			colCOMP.setCellValueFactory(new PropertyValueFactory<CompetenceAquise,Integer>("idCompetence"));
			colINTI.setCellValueFactory(new PropertyValueFactory<CompetenceAquise,String>("intitule"));
			colNV1.setCellValueFactory(new PropertyValueFactory<CompetenceAquise,Integer>("niveau1"));
			colNV2.setCellValueFactory(new PropertyValueFactory<CompetenceAquise,Integer>("niveau2"));
			colNV3.setCellValueFactory(new PropertyValueFactory<CompetenceAquise,Integer>("niveau3"));
			colDATE.setCellValueFactory(new PropertyValueFactory<CompetenceAquise,Date>("dateAquisition"));
			
			tableCOMP.setItems(ListcompetencesAquise);

		}
		
		public ObservableList<Apprenant> getApprenant()
		{
			ObservableList<Apprenant> ListApprenant = FXCollections.observableArrayList();
			Connection conn = getConnection();
			String query = "SELECT * FROM user where ROLE = 1 AND   ID_FILIERE = "+idGroupe;//+comboUSER.getValue();
			Statement st;
			ResultSet rs;
			try
			{
				st = conn.createStatement();
				rs = st.executeQuery(query);
				Apprenant AP ;
				while(rs.next())
				{
					AP = new Apprenant(rs.getString("CIN"),rs.getString("NOM"),rs.getString("PRENOM"),rs.getString("USERNAME"),rs.getString("PASSWORD"),rs.getInt("ID_FILIERE"),rs.getString("ROLE"),rs.getInt("PROMOTION"));
					
					ListApprenant.add(AP);
				}
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			return ListApprenant;
			
		}
		
		public void showApprenant()
		{
			ObservableList<Apprenant> ListApprenant   = getApprenant();
			colNOM.setCellValueFactory(new PropertyValueFactory<Apprenant,String>("nom"));
			colCIN.setCellValueFactory(new PropertyValueFactory<Apprenant,String>("cin"));
			colPRENOM.setCellValueFactory(new PropertyValueFactory<Apprenant,String>("prenom"));
			colUSER.setCellValueFactory(new PropertyValueFactory<Apprenant,String>("username"));
			colPROMO.setCellValueFactory(new PropertyValueFactory<Apprenant,Integer>("promotion"));
			 
			
			tableAPP.setItems(ListApprenant);

		}
		public void chargeCombobox()
		{
			 
			Connection conn = getConnection();
			String query = "SELECT CIN FROM user where ROLE = 1 AND   ID_FILIERE = "+idGroupe; 
			Statement st;
			ResultSet rs;
			try
			{
				st = conn.createStatement();
				rs = st.executeQuery(query);
				// comboUSER.setItems(rs.getString("CIN"));
				while(rs.next())
				{
					 
					comboUSER.getItems().add(rs.getString("CIN"));
				}
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			
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
				// comboUSER.setItems(rs.getString("CIN"));
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
						idGroupe = rs.getInt("ID_FILIERE");
					 
					}
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
			 labUserNom.setText(nom + " " + prenom);
		}
		public String groupeName(int idFiliere)
		{
			String res = "";
			 Connection conn = getConnection();
				String query = "SELECT INTITULE FROM filiere WHERE ID_FILIERE = "+idFiliere+"";
				
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
		

}
