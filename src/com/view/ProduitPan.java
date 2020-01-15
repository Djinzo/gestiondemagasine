package com.view;

import com.dataBaseConnection.ProduitDAO;
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

public class ProduitPan extends GridPane {

	private Label labelcodeProduit, labelDisProduit, labelPrixProduit, labelCatProuit, labelidproduit;
	private TextField textcodeProduit, textDisProdut, textPrixProduit, textidproduit;
	public static ComboBox<Categorie> comboCategorie;
	private Button btaddProd, btsupProd, btmodProd;
	private TableView<Produit> tableProduit;
	private ObservableList<Produit> obProd;
	private static ArrayList<Produit> listProd;
	private ProduitDAO pdao;

	public ProduitPan() {
		init();
	}

	public void init() {

		labelcodeProduit = new Label("code");
		labelDisProduit = new Label("Disegniation");
		labelPrixProduit = new Label("Produit");
		labelCatProuit = new Label("Categorie");
		labelidproduit = new Label("id");

		comboCategorie = new ComboBox<>();
		comboCategorie.setMaxWidth(150);
		comboCategorie.getItems().addAll(CategoriePan.listecat);
		textcodeProduit = new TextField();
		textDisProdut = new TextField();
		textPrixProduit = new TextField();
		textidproduit = new TextField();

		btaddProd = new Button("ajouter");
		btmodProd = new Button("Modifer");
		btsupProd = new Button("suprimer");

		tableProduit = new TableView<>();
		obProd = FXCollections.observableArrayList();
		pdao = new ProduitDAO();
		listProd = pdao.getAll();
		// obProd.addAll(listProd);
		// for(Produit p:listProd) System.out.println(p.getId());
		TableColumn columnCodeProduit = new TableColumn("code");
		columnCodeProduit.setCellValueFactory(new PropertyValueFactory<Produit, String>("code"));
		TableColumn columnDisProduit = new TableColumn("Designation");
		columnDisProduit.setCellValueFactory(new PropertyValueFactory<Produit, String>("disegniation"));
		TableColumn columnPrixProduit = new TableColumn("prix");
		columnPrixProduit.setCellValueFactory(new PropertyValueFactory<Produit, Double>("prix"));
		TableColumn columncatProduit = new TableColumn("categorie");
		columncatProduit.setCellValueFactory(new PropertyValueFactory<Produit, String>("categorie"));
		obProd.addAll(listProd);
		tableProduit.setItems(obProd);
		tableProduit.getColumns().addAll(columnCodeProduit, columnDisProduit, columnPrixProduit, columncatProduit);
		textidproduit.setText("" + (listProd.size() + 1));
		textidproduit.setDisable(true);
		setPosition();
	}

	public void setPosition() {
		this.setHgap(5);
		this.setVgap(5);
		this.setAlignment(Pos.CENTER);
		this.add(labelidproduit, 0, 0);
		this.add(textidproduit, 1, 0);
		this.add(labelcodeProduit, 0, 1);
		this.add(textcodeProduit, 1, 1);
		this.add(labelDisProduit, 0, 2);
		this.add(textDisProdut, 1, 2);
		this.add(labelPrixProduit, 0, 3);
		this.add(textPrixProduit, 1, 3);
		this.add(labelCatProuit, 0, 4);
		this.add(comboCategorie, 1, 4);
		btaddProd.setMaxWidth(100);
		btmodProd.setMaxWidth(100);
		btsupProd.setMaxWidth(100);
		VBox buttonsProd = new VBox(btaddProd, btmodProd, btsupProd);
		buttonsProd.setAlignment(Pos.CENTER);
		buttonsProd.setSpacing(5);
		this.add(buttonsProd, 2, 0, 1, 4);
		this.add(tableProduit, 3, 0, 1, 6);
		addEventLisner();
	}

	public void addEventLisner() {
		
		btaddProd.setOnAction(v -> {

			if (textcodeProduit.getText().isEmpty()) {
				IHM.msg.setContentText("le chompe code de produit est vide ");
				IHM.msg.show();
				return;
			}
			if (textDisProdut.getText().isEmpty()) {
				IHM.msg.setContentText("le chompe disegniation de produit est vide ");
				IHM.msg.show();
				return;
			}
			if (textPrixProduit.getText().isEmpty()) {
				IHM.msg.setContentText("le chompe Prix de produit est vide ");
				IHM.msg.show();
				return;
			}
			if (!textPrixProduit.getText().matches("[0-9]{1,20}.[0-9]{0,20}")) {
				IHM.msg.setContentText("le chompe Prix de produit ne pas valide  ");
				IHM.msg.show();
				return;
			}
			if (comboCategorie.getValue() == null) {
				IHM.msg.setContentText("le chompe categorie de produit est vide ");
				IHM.msg.show();
				return;
			}
			Produit p = new Produit(textcodeProduit.getText(), textDisProdut.getText(),
					Double.parseDouble(textPrixProduit.getText()), comboCategorie.getValue());
			pdao.create(p);
			listProd.add(p);
			obProd.add(p);
			textcodeProduit.clear();
			textDisProdut.clear();
			textPrixProduit.clear();
			textidproduit.setText("" + (listProd.size() + 1));

		});
		tableProduit.setOnMouseClicked(v -> {
			Produit p = tableProduit.getSelectionModel().getSelectedItem();
			textidproduit.setText("" + p.getId());
			textcodeProduit.setText("" + p.getCode());
			textPrixProduit.setText("" + p.getPrix());
			textDisProdut.setText("" + p.getDisegniation());
			comboCategorie.setValue(p.getCategorie());
		});
		btsupProd.setOnAction(v -> {
			Produit p = tableProduit.getSelectionModel().getSelectedItem();
			Alert b = new Alert(Alert.AlertType.CONFIRMATION);
			b.setTitle("warning");
			b.setContentText("le prdouit va etre suprimer !!!");

			if (b.showAndWait().get() == ButtonType.OK) {
				pdao.delete((long) p.getId());
				listProd.remove(p);
				obProd.remove(p);
				textcodeProduit.clear();
				textDisProdut.clear();
				textPrixProduit.clear();
				textidproduit.setText("" + (listProd.size() + 1));
			} else {
				textcodeProduit.clear();
				textDisProdut.clear();
				textPrixProduit.clear();
				textidproduit.setText("" + (listProd.size() + 1));
			}

		});
		btmodProd.setOnAction(v -> {
			if (textcodeProduit.getText().isEmpty()) {
				IHM.msg.setContentText("le chompe code de produit est vide ");
				IHM.msg.show();
				return;
			}
			if (textDisProdut.getText().isEmpty()) {
				IHM.msg.setContentText("le chompe disegniation de produit est vide ");
				IHM.msg.show();
				return;
			}
			if (textPrixProduit.getText().isEmpty()) {
				IHM.msg.setContentText("le chompe Prix de produit est vide ");
				IHM.msg.show();
				return;
			}
			if (!textPrixProduit.getText().matches("[0-9]{1,20}.[0-9]{0,20}")) {
				IHM.msg.setContentText("le chompe Prix de produit ne pas valide  ");
				IHM.msg.show();
				return;
			}
			if (comboCategorie.getValue() == null) {
				IHM.msg.setContentText("le chompe categorie de produit est vide ");
				IHM.msg.show();
				return;
			}
			Produit p = tableProduit.getSelectionModel().getSelectedItem();
			Produit pm = new Produit(Integer.parseInt(textidproduit.getText()), textcodeProduit.getText(),
					textDisProdut.getText(), Double.parseDouble(textPrixProduit.getText()), comboCategorie.getValue());
			pdao.update(pm);
			listProd.remove(p);
			listProd.add(pm);
			obProd.clear();
			obProd.addAll(listProd);
		});
	}
}
