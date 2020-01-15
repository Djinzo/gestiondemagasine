package com.view;

import com.dataBaseConnection.ProduitDAO;
import com.module.LignedeCommande;
import com.module.Produit;
import com.module.Vente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.lang.reflect.InvocationHandler;
import java.util.ArrayList;

public class ComandeLine extends Dialog<Vente> {

    private Scene scene;
    private Vente v;
    private Label labelid, labelproduit, labelqte, labeltotal;
    private TextField textid, textqte, texttotal;
    private ComboBox<Produit> combpProduit;
    private ArrayList<Produit> produitListe;
    private TableView<LignedeCommande> tabelcommande;
    private ObservableList<LignedeCommande> obcomande;
    private ArrayList<LignedeCommande> listcomande;
    private ProduitDAO pdao;
    private Button btadd, btsup, btmod;
    private ButtonType btok;

    public ComandeLine(Vente ve) {
        v = ve;
        produitListe = new ArrayList<>();
        pdao = new ProduitDAO();
        //setHeight(500);
        initdata();
        getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
    }

    public void initdata() {
        labelid = new Label("id");
        labelproduit = new Label("produit");
        labelqte = new Label("qte");
        labeltotal = new Label("total");
        textid = new TextField();
        textid.setText("1");
        textid.setDisable(true);
        textqte = new TextField();
        texttotal = new TextField();
        combpProduit = new ComboBox<>();
        produitListe = pdao.getAll();
        combpProduit.getItems().addAll(produitListe);
        produitListe.addAll(produitListe);
        btadd = new Button("ajouter");
        btsup = new Button("suprimer");
        btmod = new Button("modifer");
        listcomande = v.getComandeliste();
        tabelcommande = new TableView<>();
        TableColumn clumid = new TableColumn("id");
        clumid.setCellValueFactory(new PropertyValueFactory<LignedeCommande, Integer>("id"));
        TableColumn clumProduit = new TableColumn("Produit");
        clumProduit.setCellValueFactory(new PropertyValueFactory<LignedeCommande, Produit>("p"));
        TableColumn clumqte = new TableColumn("qte");
        clumqte.setCellValueFactory(new PropertyValueFactory<LignedeCommande, Integer>("qte"));
        TableColumn clumtotal = new TableColumn("total");
        clumtotal.setCellValueFactory(new PropertyValueFactory<LignedeCommande, Double>("total"));

        obcomande = FXCollections.observableArrayList();
        obcomande.addAll(listcomande);
        tabelcommande.setItems(obcomande);
        tabelcommande.getColumns().addAll(clumid, clumProduit, clumqte, clumtotal);

        posdata();
    }

    public void posdata() {
        GridPane gp = new GridPane();
        gp.setHgap(5);
        gp.setVgap(5);
        gp.setAlignment(Pos.CENTER);
        gp.add(labelid, 0, 0);
        gp.add(textid, 1, 0);
        gp.add(labelproduit, 0, 1);
        gp.add(combpProduit, 1, 1);
        gp.add(labelqte, 0, 2);
        gp.add(textqte, 1, 2);
        //gp.add(labeltotal, 0, 3);gp.add(texttotal, 1, 3);
        HBox butons = new HBox(btadd, btsup, btmod);
        gp.add(butons, 0, 4, 2, 1);
        gp.add(tabelcommande, 0, 5, 2, 1);
        scene = new Scene(gp);
        btok = new ButtonType("ajouter vente", ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().add(btok);
        getDialogPane().setContent(gp);
        setResultConverter(new Callback<ButtonType, Vente>() {
            @Override
            public Vente call(ButtonType param) {
                if (param == btok && listcomande.size() > 0) {
                    return v;
                } else {

                    return null;


                }

            }
        });
        addEventHnderl();
    }


    public void addEventHnderl() {
        btadd.setOnAction(v -> {

            LignedeCommande l = new LignedeCommande(Integer.parseInt(textid.getText()), combpProduit.getValue(), Integer.parseInt(textqte.getText()), this.v);
            LignedeCommande s = null;
            for (LignedeCommande t : this.v.getComandeliste()) {
                if (combpProduit.getValue().getId() == t.getP().getId()) {
                    s = t;
                    break;
                }
            }
            if (s == null) {
                this.v.addComandeLine(l);
                //listcomande.add(l);

                obcomande.add(l);
                textid.setText("" + (Integer.parseInt(textid.getText()) + 1));
                combpProduit.setValue(null);
                textqte.clear();
            } else {
                s.setQte(s.getQte() + Integer.parseInt(textqte.getText()));
                //System.out.println("test");
                obcomande.clear();
                System.out.println("hello2");
                //listcomande.add(s);
                s.setTotal(s.getQte() * s.getP().getPrix());
                obcomande.addAll(listcomande);
                //textid.setText(""+(Integer.parseInt(textid.getText())));
                combpProduit.setValue(null);
                textqte.clear();
            }
        });
        tabelcommande.setOnMouseClicked(V -> {
            LignedeCommande c = tabelcommande.getSelectionModel().getSelectedItem();
            for (LignedeCommande d : this.v.getComandeliste()) System.out.println(d.getP());
            textid.setText("" + c.getId());
            textqte.setText("" + c.getQte());
            combpProduit.setValue(c.getP());
        });
        btsup.setOnAction(v -> {
            LignedeCommande c = tabelcommande.getSelectionModel().getSelectedItem();
            listcomande.remove(c);
            obcomande.remove(c);
        });
    }



	/*@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("hello Progressbar");
		stage.setWidth(600);
		stage.setHeight(600);
		initdata();
		stage.setScene(scene);
		stage.show();
		
	}*/

}
