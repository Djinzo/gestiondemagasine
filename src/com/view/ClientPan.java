package com.view;

import com.dataBaseConnection.ClientDAO;
import com.module.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class ClientPan extends GridPane {
	
	private Label labelidclient,labelcinclient,labelnomclient,labelprenomclient,labelteleclient,labelmailclient,labeladresclient;
	private TextField textidclient,textcinclient,textnomclient,textprenomclient,textteleclient,textmailclient,textadresclient;
	private Button buttonaddClient,buttonsupclient,buttonmodclient;
	private TableView<Client> tableClient;
	private ObservableList<Client> obclient;
	public static ArrayList<Client> listeClient;
	
	
	private ClientDAO cldao;

	
	public ClientPan() {
		init();
	}

	private void init() {
		
		labeladresclient=new Label("adresse");
		labelidclient=new Label("id");
		labelcinclient=new Label("cin");
		labelnomclient=new Label("nom");
		labelprenomclient=new Label("prenom");
		labelteleclient=new Label("tele");
		labelmailclient=new Label("mail");
		textadresclient=new TextField();
		textidclient=new TextField();
		textcinclient=new TextField();
		textnomclient=new TextField();
		textprenomclient=new TextField();
		textteleclient=new TextField();
		textmailclient=new TextField();
		buttonaddClient=new Button("ajouter");
		buttonsupclient=new Button("suprimer");
		buttonmodclient=new Button("modifer");
		tableClient=new  TableView<>();
		TableColumn columnidclient=new TableColumn("code");
		columnidclient.setCellValueFactory(new PropertyValueFactory<Client,String>("id"));
		TableColumn columnnomclient=new TableColumn("nom");
		columnnomclient.setCellValueFactory(new PropertyValueFactory<Client,String>("nom"));
		TableColumn columnprenomclient=new TableColumn("prenom");
		columnprenomclient.setCellValueFactory(new PropertyValueFactory<Client,String>("prenom"));
		TableColumn columnmailclient=new TableColumn("mail");
		columnmailclient.setCellValueFactory(new PropertyValueFactory<Client,String>("mail"));
		TableColumn columnadressclient=new TableColumn("adress");
		columnadressclient.setCellValueFactory(new PropertyValueFactory<Client,String>("adress"));
		TableColumn columncinclient=new TableColumn("cin");
		columncinclient.setCellValueFactory(new PropertyValueFactory<Client,String>("cin"));
		TableColumn columnteleclient=new TableColumn("tele");
		columnteleclient.setCellValueFactory(new PropertyValueFactory<Client,String>("tele"));
		cldao=new ClientDAO();
		listeClient=cldao.getAll();
		textidclient.setText(""+(listeClient.size()+1));
		textidclient.setDisable(true);
		//listeClient.add(new Client(10, "10", "", "", "", "", ""));
		obclient=FXCollections.observableArrayList();
		obclient.addAll(listeClient);
		tableClient.setItems(obclient);
		tableClient.getColumns().addAll(columnidclient,columnnomclient,columnprenomclient,columnmailclient,columnteleclient,columnadressclient,columncinclient);
		
		setPosition();
	}

	private void setPosition() {
		this.setHgap(5);
		this.setVgap(5);
		this.setAlignment(Pos.CENTER);
		this.add(labelidclient, 0, 0);this.add(textidclient, 1, 0);
		this.add(labelnomclient, 0, 1);this.add(textnomclient, 1, 1);
		this.add(labelprenomclient, 0, 2);this.add(textprenomclient, 1, 2);
		this.add(labelmailclient, 0, 3);this.add(textmailclient, 1, 3);
		this.add(labeladresclient, 0, 4);this.add(textadresclient, 1, 4);
		this.add(labelcinclient, 0, 5);this.add(textcinclient, 1, 5);
		this.add(labelteleclient, 0, 6);this.add(textteleclient, 1, 6);
		buttonaddClient.setMaxWidth(100);
		buttonmodclient.setMaxWidth(100);
		buttonsupclient.setMaxWidth(100);

		VBox buttonsCleint=new VBox(buttonaddClient,buttonsupclient,buttonmodclient);
		buttonsCleint.setSpacing(5);
		this.add(buttonsCleint, 2, 0,1,7);
		this.add(tableClient,3,0,1,8);
		addEventLisner();
	}

	private void addEventLisner() {
		
		buttonaddClient.setOnAction(v->{
			if(textcinclient.getText().isEmpty()){
				IHM.msg.setContentText("le chompe cin  est vide ");
				IHM.msg.show();
				return;
			}
			if(textnomclient.getText().isEmpty()){
				IHM.msg.setContentText("le chompe nom est vide ");
				IHM.msg.show();
				return;
			}
			if(textprenomclient.getText().isEmpty()){
				IHM.msg.setContentText("le chompe prenom est vide ");
				IHM.msg.show();
				return;
			}
			if(textteleclient.getText().isEmpty()){
				IHM.msg.setContentText("le chompe tele est vide ");
				IHM.msg.show();
				return;
			}
			if(textmailclient.getText().isEmpty()){
				IHM.msg.setContentText("le chompe mail est vide ");
				IHM.msg.show();
				return;
			}
			/*if(!textmailclient.getText().matches("[a-z][A-Z][0-9]{5,30}@[a-z][A-Z][0-9]{3,30}.[a-z][A-Z][0-9]{5,30}")){
				msg.setContentText("le chompe mail est vide ");
				msg.show();
				return;
			}*/
			if(textadresclient.getText().isEmpty()){
				IHM.msg.setContentText("le chompe adress est vide ");
				IHM.msg.show();
				return;
			}
			Client c=new Client(0, textcinclient.getText(), textnomclient.getText(), textprenomclient.getText(), textteleclient.getText(), textmailclient.getText(), textadresclient.getText());
			cldao.create(c);
			listeClient.add(c);
			obclient.clear();
			obclient.addAll(listeClient);
			VentePen.textclientvente.getItems().clear();
			VentePen.textclientvente.getItems().addAll(listeClient);

			textcinclient.clear();
			textnomclient.clear(); 
			textprenomclient.clear(); 
			textteleclient.clear(); 
			textmailclient.clear(); 
			textadresclient.clear();
			textidclient.setText(""+(listeClient.size()+1));
			System.out.println("ckliked");
		});
		buttonsupclient.setOnAction(v->{
			Client c=tableClient.getSelectionModel().getSelectedItem();
			cldao.delete((long) c.getId());
			listeClient.remove(c);
			obclient.remove(c);
			textcinclient.clear();
			textnomclient.clear();
			textprenomclient.clear();
			textteleclient.clear();
			textmailclient.clear();
			textadresclient.clear();
			textidclient.setText(""+(listeClient.size()+1));
			System.out.println("ckliked");
			
		});
		tableClient.setOnMouseClicked(v->{
			if(tableClient.getSelectionModel().getSelectedItem()!=null){
				Client c=tableClient.getSelectionModel().getSelectedItem();
				textcinclient.setText(c.getCin());;
				textnomclient.setText(c.getNom());; 
				textprenomclient.setText(c.getPrenom()); 
				textteleclient.setText(c.getTele()); 
				textmailclient.setText(c.getMail()); 
				textadresclient.setText(c.getAdress());
				textidclient.setText(""+c.getId());
			}
			
		});
		buttonmodclient.setOnAction(v->{
			Client cm=new Client(Integer.parseInt(textidclient.getText()), textcinclient.getText(), textnomclient.getText(), textprenomclient.getText(), textteleclient.getText(), textmailclient.getText(), textadresclient.getText());
			Client c=tableClient.getSelectionModel().getSelectedItem();
			cldao.update(cm);
			listeClient.add(cm);
			listeClient.remove(c);
			obclient.clear();
			obclient.addAll(listeClient);
			textcinclient.clear();
			textnomclient.clear(); 
			textprenomclient.clear(); 
			textteleclient.clear(); 
			textmailclient.clear(); 
			textadresclient.clear();
			textidclient.setText(""+(listeClient.size()+1));
			System.out.println("ckliked");
		});
	}
}
