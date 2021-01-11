package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class MainController {
	
	public int role;
	
	@FXML 
	private TextField txtUSER ;
	@FXML 
	private PasswordField txtPASS ;
	public void generateRandom(ActionEvent e)
	{
	 
		  System.out.println(txtUSER.getText() + " " + txtPASS.getText());
		  
		 
	}
	@FXML 
	public void goToRegister(ActionEvent event) throws IOException
	{
		Parent MainRegister = FXMLLoader.load(getClass().getResource("RegisterScene.fxml"));
		Scene registerScene = new Scene(MainRegister);
		Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		app_stage.hide();
		app_stage.setScene(registerScene);
		app_stage.show();
	}
	public Boolean checkFieldNotEmpty()
	{
		 
		if(txtUSER.getText() == null || txtUSER.getText().trim().isEmpty() && txtPASS.getText() == null || txtPASS.getText().trim().isEmpty())
		{
			return true;
		}
		else
		{
			return false;
		}
		
	}
	public void Login(ActionEvent e) throws IOException
	{
		if(!checkFieldNotEmpty())
		{
			if(CheckCorrectUserAndPass())
			{
				if(role == 2)
				{
					goToCompetence(e);
				}
				else if (role == 1)
				{
					goToCompetenceApp(e);
				}
				
			}
			else
			{
				showAlertWithHeaderText("Message d'authentification","Vous n'avez pas réussi l'authentification ! ","L'utilisateur ou le mot de passe est incorrect");
			}
		}
	}
	public Boolean CheckCorrectUserAndPass()
	{
		Boolean res = false;
		Connection conn = getConnection();
		String query = "SELECT * FROM user WHERE USERNAME ='"+txtUSER.getText()+"' AND PASSWORD = '"+txtPASS.getText()+"'";
		
		Statement st;
		ResultSet rs;
		try
		{
			st = conn.createStatement();
			rs = st.executeQuery(query);
			 
			while(rs.next())
			{
				 role = rs.getInt("ROLE");
				 SessionID SI = new SessionID(rs.getString("CIN"));
				 res = true;
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return res;
		
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
	@FXML 
	public void goToCompetence(ActionEvent event) throws IOException
	{
		Parent Maincomp = FXMLLoader.load(getClass().getResource("CompetenceScene.fxml"));
		Scene MainScene = new Scene(Maincomp);
		Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		app_stage.hide();
		app_stage.setScene(MainScene);
		app_stage.show();
	}
	private static void showAlertWithHeaderText(String title,String midText,String message) {
		
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(midText);
        alert.setContentText(message);
 
        alert.showAndWait();
    }
	public void goToCompetenceApp(ActionEvent event) throws IOException
	{
		Parent Maincomp = FXMLLoader.load(getClass().getResource("CompetenceSceneApp.fxml"));
		Scene MainScene = new Scene(Maincomp);
		Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		app_stage.hide();
		app_stage.setScene(MainScene);
		app_stage.show();
	}
	 
}
