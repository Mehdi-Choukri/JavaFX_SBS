package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class RegisterController implements Initializable {
	
	@FXML
	private ComboBox<String> comboGROUPE;
	@FXML 
	private Label lblDATE;
	@FXML 
	private Label lblPROMO;
	@FXML
	private TextField txtCIN;
	@FXML
	private TextField txtNOM;
	@FXML
	private TextField txtPRENOM;
	@FXML
	private TextField txtUSER;
	@FXML
	private PasswordField  txtPASS;
	@FXML
	private TextField txtDATE;
	@FXML
	private TextField txtPROMO;
	//Button
	@FXML
	private Button btnREGISTER;
	@FXML
	private Button btnCANCEL;
	
	// radiobutton
	@FXML
	private RadioButton  radioSTAFF;
	@FXML
	private RadioButton  radioAPP;
	
	
	public Boolean staffUser;
	 
	
	ToggleGroup toggleGroup = new ToggleGroup();
	 
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		 radioAPP.setSelected(true);
		 staffUser = true;
		radioSTAFF.setToggleGroup(toggleGroup);
		radioAPP.setToggleGroup(toggleGroup);
		chargeCombobox();
		checkUser();
		comboGROUPE.setValue("Mary Jackson");
	}
	
	 public void checkUser()
	 {
		
		 if(radioAPP.isSelected())
		 {
			 staffUser = false;
			 System.out.println(staffUser );
			 lblPROMO.setVisible(true);
			 txtPROMO.setVisible(true);
			
			 lblDATE.setVisible(false);
			 txtDATE.setVisible(false);
		 }
		 else if(radioSTAFF.isSelected())
		 {
			 staffUser = true;
			 radioAPP.setSelected(false);
			 System.out.println(staffUser );
			 lblDATE.setVisible(true);
			 txtDATE.setVisible(true);
			 lblPROMO.setVisible(false);
			 txtPROMO.setVisible(false);
		 }
	 }
	
	public void Login(ActionEvent e)
	{
		System.out.println(txtCIN.getText());
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
	
	public void chargeCombobox()
	{
		 
		Connection conn = getConnection();
		String query = "SELECT INTITULE FROM filiere ";
		Statement st;
		ResultSet rs;
		try
		{
			st = conn.createStatement();
			rs = st.executeQuery(query);
			 
			while(rs.next())
			{
				 
				comboGROUPE.getItems().add(rs.getString("INTITULE"));
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
	}
	
	public void addApprenant(ActionEvent e)
	{
	 
		String correctDate ="-01-01";
		if(checkValideUser())
		{
			String query;
			if(!staffUser)
			{
				if(!antiDoublant(txtCIN.getText()))
				{
					
				
					query="INSERT INTO user  VALUES ('"+txtCIN.getText()+"','"+txtNOM.getText()+"','"+txtPRENOM.getText()+"','"+txtUSER.getText()+"','"+txtPASS.getText()+"',"+1+","+groupeID(comboGROUPE.getValue())+","+null+","+txtPROMO.getText()+")";
					
					if(executeQuery(query))
					{
						showAlertWithHeaderText("Message d'inscription ","Vous avez réussi l'inscription ! ","Vous pouvez vous connecter désormais");
	
					}
					else
					{
						showAlertWithHeaderText("Message d'inscription ","Vous n'avez pas réussi l'inscription ! ","Il y a une erreur dans le systéme");
	
					}
				}
				else
				{
					showAlertWithHeaderText("Message d'inscription ","Vous n'avez pas réussi l'inscription ! ","L'utilisateur existe déja");
				}
			}
			else
			{
				if(!antiDoublant(txtCIN.getText()))
				{
					if(checkStaffExist(txtCIN.getText()))
					{
						query="INSERT INTO user  VALUES ('"+txtCIN.getText()+"','"+txtNOM.getText()+"','"+txtPRENOM.getText()+"','"+txtUSER.getText()+"','"+txtPASS.getText()+"',"+2+","+groupeID(comboGROUPE.getValue())+",'"+(txtDATE.getText()+correctDate)+"',"+null+")";
						
							if(executeQuery(query))
							{
								showAlertWithHeaderText("Message d'inscription ","Vous avez réussi l'inscription ! ","Vous pouvez vous connecter désormais");
		
							}
							else
							{
								showAlertWithHeaderText("Message d'inscription ","Vous n'avez pas réussi l'inscription ! ","Il y a une erreur dans le systéme");
		
							}
						
					}
					else
					{
								showAlertWithHeaderText("Message d'inscription ","Vous n'avez pas réussi l'inscription ! ","Vous ne pouvez pas vous s'inscrire entant que Staff");
					}
				
				}
				else
				{
					showAlertWithHeaderText("Message d'inscription ","Vous n'avez pas réussi l'inscription ! ","L'utilisateur existe déja");

				}
			}
			
			
		}
		else
		{
			showAlertWithHeaderText("Tous les champs doivent être remplis","Un champs ou plusieurs sont vides","Remplissez le champs !");

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

	public Boolean checkValideUser()
	{
		Boolean res = true;
		if(!staffUser)
		{
			if(txtCIN.getText() == null || txtCIN.getText().trim().isEmpty() && txtNOM.getText() == null || txtNOM.getText().trim().isEmpty() && txtPRENOM.getText() == null || txtPRENOM.getText().trim().isEmpty() && txtUSER.getText() == null || txtUSER.getText().trim().isEmpty() && txtPASS.getText() == null || txtPASS.getText().trim().isEmpty() && txtPROMO.getText() == null || txtPROMO.getText().trim().isEmpty())
			{
				res=false;
			}
		}
		else
		{
			if(txtCIN.getText() == null || txtCIN.getText().trim().isEmpty() && txtNOM.getText() == null || txtNOM.getText().trim().isEmpty() && txtPRENOM.getText() == null || txtPRENOM.getText().trim().isEmpty() && txtUSER.getText() == null || txtUSER.getText().trim().isEmpty() && txtPASS.getText() == null || txtPASS.getText().trim().isEmpty() && txtDATE.getText() == null || txtDATE.getText().trim().isEmpty())
			{
				res=false;
			}
		}
		
		return res;
	}
	public void btnAnnuler()
	{
		 	txtCIN.setText("");
			txtNOM.setText("");
			txtPRENOM.setText("");
			txtUSER.setText("");
			txtPASS.setText("");
			txtDATE.setText("");
			txtPROMO.setText("");
	}
	private static void showAlertWithHeaderText(String title,String midText,String message) {
		
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(midText);
        alert.setContentText(message);
 
        alert.showAndWait();
    }
	public  int groupeID(String intitule)
	{
		int res = 0;
		 
			 
			Connection conn = getConnection();
			String query = "SELECT ID_FILIERE FROM filiere WHERE INTITULE = '" +intitule+"'";
			Statement st;
			ResultSet rs;
			try
			{
				st = conn.createStatement();
				rs = st.executeQuery(query);
				 
				while(rs.next())
				{
					 
					 res = rs.getInt("ID_FILIERE");
				}
				
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			return res;
		 
		
	}
	public Boolean checkStaffExist(String staffCin)
	{
		Boolean res = false;
		Connection conn = getConnection();
		String query = " SELECT CIN_STAFF FROM liste_staff WHERE CIN_STAFF ='"+staffCin+"'";;
		Statement st;
		ResultSet rs;
		try
		{
			st = conn.createStatement();
			rs = st.executeQuery(query);
			 
			while(rs.next())
			{
				res = true;
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return res;
		
	}
	public Boolean antiDoublant(String cin)
	{
		Boolean res = false;
		Connection conn = getConnection();
		String query = " SELECT * FROM user WHERE CIN   ='"+cin+"'";;
		Statement st;
		ResultSet rs;
		try
		{
			st = conn.createStatement();
			rs = st.executeQuery(query);
			 
			while(rs.next())
			{
				res = true;
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return res;
		
	}
	@FXML 
	public void goToLogin(ActionEvent event ) throws IOException
	{
		Parent Mainlogin = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
		Scene MainScene = new Scene(Mainlogin);
		Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		app_stage.hide();
		app_stage.setScene(MainScene);
		app_stage.show();
	}
	

}
