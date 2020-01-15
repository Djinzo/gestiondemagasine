package com.view;

import com.dataBaseConnection.VenteDAO;
import com.module.Client;
import com.module.LignedeCommande;
import com.module.Produit;
import com.module.Vente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class VentePen extends GridPane {

	private VenteDAO vdao;
	private Label labelidvente,labeldatevente,labelClientvente,labeltotalvente;
	private TextField textidvente,texttotlavente;
	public static ComboBox<Client> textclientvente;
	private DatePicker textdatevente;
	private Button btaddvent,btsupvent,btmodvent;
	private TableView<Vente> tablevente;
	public static ObservableList<Vente> obvente;
	public static ArrayList<Vente> listeVente;
	
	private TableView<LignedeCommande> Tablecomnde;
	private ObservableList<LignedeCommande> obcomande;
	private ArrayList<LignedeCommande> listeComande;
	
	public VentePen() {
		init();
	}

	private void init() {

		labelidvente=new Label("id");
		labeldatevente=new Label("date");
		labeltotalvente=new Label("total");
		labelClientvente=new Label("client");
		
		textidvente=new TextField();
		textdatevente=new DatePicker();
		textdatevente.setValue(LocalDate.now());
		texttotlavente=new TextField();
		textclientvente=new ComboBox<>();
		textclientvente.getItems().addAll(ClientPan.listeClient);
		
		btaddvent=new Button("ajouter");
		btsupvent=new Button("suprimer");
		btmodvent=new Button("modifer");
		vdao=new VenteDAO();
		listeVente=vdao.getAll();
		textidvente.setText(""+(1+listeVente.size()));
		textidvente.setDisable(true);
		tablevente=new TableView<>();
		obvente=FXCollections.observableArrayList();
		TableColumn columnidvente =new TableColumn("id");
		columnidvente.setCellValueFactory(new  PropertyValueFactory<Vente,String>("id"));
		TableColumn columndatevente =new TableColumn("date");
		columndatevente.setCellValueFactory(new  PropertyValueFactory<Vente,String>("date"));
		TableColumn columnClientvente =new TableColumn("client");
		columnClientvente.setCellValueFactory(new  PropertyValueFactory<Vente,String>("client"));
		TableColumn columntotalvente =new TableColumn("total");
		columntotalvente.setCellValueFactory(new  PropertyValueFactory<Vente,String>("totelVente"));
		obvente.addAll(listeVente);
		tablevente.setItems(obvente);
		tablevente.getColumns().addAll(columnidvente,columndatevente,columnClientvente,columntotalvente);
		
		Tablecomnde=new TableView<>();
		TableColumn clumid=new TableColumn("id");
		clumid.setMaxWidth(40);
		clumid.setCellValueFactory(new  PropertyValueFactory<LignedeCommande,Integer>("id"));
		TableColumn clumProduit=new TableColumn("Produit");
		clumProduit.setCellValueFactory(new  PropertyValueFactory<LignedeCommande,Produit>("p"));
		TableColumn clumqte=new TableColumn("qte");
		clumqte.setMaxWidth(40);
		clumqte.setCellValueFactory(new  PropertyValueFactory<LignedeCommande,Integer>("qte"));
		TableColumn clumtotal=new TableColumn("total");
		clumtotal.setCellValueFactory(new  PropertyValueFactory<LignedeCommande,Double>("total"));
		Tablecomnde.getColumns().addAll(clumid,clumProduit,clumqte,clumtotal);
		obcomande=FXCollections.observableArrayList();
		listeComande=new ArrayList<>();
		obcomande.addAll(listeComande);
		Tablecomnde.setItems(obcomande);
		
		
		setPosition();
		
	}

	private void setPosition() {
		this.setHgap(5);
		this.setVgap(5);
		this.setAlignment(Pos.CENTER);
		this.add(labelidvente, 0, 0);this.add(textidvente, 1, 0);
		this.add(labeldatevente, 0, 1);this.add(textdatevente, 1, 1);
		//vgp.add(labeltotalvente, 0, 2);vgp.add(texttotlavente, 1, 2);
		this.add(labelClientvente, 0, 3);this.add(textclientvente, 1, 3);
		HBox buttonvente=new HBox(btaddvent,btsupvent,btmodvent);
		this.add(buttonvente, 0,4,2,1);
		this.add(tablevente, 0, 5,4,1);
		Tablecomnde.setMaxWidth(250);
		Tablecomnde.setMinHeight(100);
		this.add(Tablecomnde, 3, 0,1,4);
		
		AddEventLisner();
		
	}

	private void AddEventLisner() {
		
		btaddvent.setOnAction(v->{
			//GregorianCalendar gc=new GregorianCalendar();
			System.out.println(textdatevente.toString());
			LocalDate local=textdatevente.getValue();
			Instant instant=Instant.from(local.atStartOfDay(ZoneId.systemDefault()));
			Date d=Date.from(instant);
			//gc.setTime(d);
			Vente ve=new Vente(0,d, 0.0, textclientvente.getValue());
			ComandeLine c=new ComandeLine(ve);
			if(c.showAndWait()!=null) {
				obcomande.clear();
				vdao.create(ve);
				listeVente.add(ve);
				obvente.add(ve);
				
			}
			
			
			
			
		});
		tablevente.setOnMouseClicked(v->{
			if(tablevente.getSelectionModel().getSelectedItem()!=null){
				Vente ve=tablevente.getSelectionModel().getSelectedItem();
				listeComande=ve.getComandeliste();
				//for(LignedeCommande z:listeComande) System.out.println(" m"+z.getId());
				obcomande.clear();
				obcomande.addAll(listeComande);
				textidvente.setText(""+ve.getId());
				textdatevente.setValue(LocalDate.of(ve.getDate().getYear(), ve.getDate().getMonth(), ve.getDate().getDay()));
				textclientvente.setValue(ve.getClient());
			}
		});
		btsupvent.setOnAction(v->{
			if(tablevente.getSelectionModel().getSelectedItem()!=null){
				Vente ve=tablevente.getSelectionModel().getSelectedItem();
				vdao.delete((long) ve.getId());
				listeVente.remove(ve);
				obvente.clear();
				obvente.addAll(listeVente);
				obcomande.clear();
				textdatevente.setValue(LocalDate.now());
				textidvente.setText(""+(listeVente.size()+1));
				textclientvente.setValue(null);
				
			}
		});
		btmodvent.setOnAction(v->{
			LocalDate local=textdatevente.getValue();
			Instant instant=Instant.from(local.atStartOfDay(ZoneId.systemDefault()));
			Date d=Date.from(instant);
			Vente va=new Vente(Integer.parseInt(textidvente.getText()), d, 0, textclientvente.getValue());
			va.setComandeliste(tablevente.getSelectionModel().getSelectedItem().getComandeliste());
			ComandeLine c=new ComandeLine(va);
			c.showAndWait();
			vdao.update(va);
		});
		
	}
}
