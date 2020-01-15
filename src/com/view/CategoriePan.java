package com.view;

import com.dataBaseConnection.CategorieDAO;
import com.module.Categorie;
import com.module.Produit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Properties;

public class CategoriePan extends GridPane {
	
	private Label labelid,labelnom;
	private TextField textid,textnom;
	private Button btaddcat,btsuppcat,btmodcat;
	private TableView<Categorie> tableCat;
	private ObservableList<Categorie> obcat;
	public static ArrayList<Categorie> listecat;
	private CategorieDAO cdao;
	
	public CategoriePan() {
		init();
	}
	
	public void init(){
		labelid=new Label("id");
		labelnom=new Label("nom");
		textid=new TextField();
		textnom=new TextField();
		btaddcat=new Button("ajouter");
		btsuppcat=new Button("supprimer");
		btmodcat=new Button("modifer");
		tableCat=new TableView<>();
		cdao=new CategorieDAO();
		listecat=cdao.getAll();
		textid.setText(""+(listecat.size()+1));
		textid.setDisable(true);;
		TableColumn columnIdcategorie=new TableColumn("id");
		columnIdcategorie.setCellValueFactory(new PropertyValueFactory<Categorie, String>("id"));
		TableColumn columnnom=new TableColumn("Disgniation");
		columnnom.setCellValueFactory(new PropertyValueFactory<Categorie,String>("nom"));
		obcat=FXCollections.observableArrayList();
		obcat.addAll(listecat);
		tableCat.setItems(obcat);
		tableCat.getColumns().addAll(columnIdcategorie,columnnom);
		
		setPosition();
	}
	
	public void setPosition(){
		this.setHgap(5);
		this.setVgap(5);
		this.setAlignment(Pos.CENTER);
		this.add(labelid, 0, 0);this.add(textid, 1, 0);
		this.add(labelnom, 0, 1);this.add(textnom, 1, 1);
		btaddcat.setMaxWidth(100);
		btmodcat.setMaxWidth(100);
		btsuppcat.setMaxWidth(100);
		VBox buttonscatego=new VBox(btaddcat,btmodcat,btsuppcat);
		buttonscatego.setSpacing(5);
		buttonscatego.setAlignment(Pos.CENTER);
		this.add(buttonscatego,2, 0,1,2);
		this.add(tableCat, 3, 0,1,3);
		
		addEventLisner();
	}
	
	public void addEventLisner(){
		
		btaddcat.setOnAction(v->{
			if(textnom.getText().isEmpty()){
				IHM.msg.setContentText("le chompe nom categorie est vide ");
				IHM.msg.show();
				return;
			}
			Categorie c=new Categorie(0, textnom.getText());
			cdao.create(c);
			listecat.add(c);
			obcat.add(c);
			textnom.clear();
			textid.setText(""+(listecat.size()+1));
			ProduitPan.comboCategorie.getItems().add(c);
		});
		btsuppcat.setOnAction(v->{
			if(textnom.getText().isEmpty()){
				IHM.msg.setContentText("le chompe nom categorie est vide ");
				IHM.msg.show();

				return;
			}
			IHM.msg.setContentText("la categorie va etre suprimer !!!");
			Optional<ButtonType> result=IHM.msg.showAndWait();
			if(result.get()==ButtonType.OK){
				Categorie c=tableCat.getSelectionModel().getSelectedItem();
				cdao.delete((long) c.getId());
				obcat.remove(c);
				listecat.remove(c);
				textnom.clear();
				textid.setText(""+(listecat.size()+1));
				ProduitPan.comboCategorie.getItems().remove(c);
			}

		});
		tableCat.setOnMouseClicked(v->{
			Categorie c=tableCat.getSelectionModel().getSelectedItem();
			if(c!=null){
				textid.setText(""+c.getId());
				textnom.setText(c.getNom());
			}

		});
		btmodcat.setOnAction(v->{
			if(textnom.getText().isEmpty()){
				IHM.msg.setContentText("le chompe nom categorie est vide ");
				IHM.msg.show();
				return;
			}
			Categorie c=tableCat.getSelectionModel().getSelectedItem();
			if(c!=null){
				c.setNom(textnom.getText());
				cdao.update(c);
				obcat.removeAll(listecat);
				//for(Categorie p:listecat) System.out.println(p.getNom());
				obcat.addAll(listecat);
				ProduitPan.comboCategorie.getItems().removeAll();
				ProduitPan.comboCategorie.setItems(obcat);
			}
		});
	}
	
}
