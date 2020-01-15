package com.view;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;


public class IHM extends Application {
    private Scene scene;
    public static Alert msg = new Alert(Alert.AlertType.INFORMATION);


    public void init() {
        msg.setTitle("chompe est incorrect");
        TabPane tp = new TabPane();

        Tab catTab = new Tab("categorie", new CategoriePan());
        catTab.getStyleClass().add("tab");
        Tab prodTab = new Tab("Produit", new ProduitPan());
        Tab clientTap = new Tab("client", new ClientPan());
        Tab ventTab = new Tab("Vente", new VentePen());
        Tab payemntTab = new Tab("Paiment",new PaymentPan());
        payemntTab.setClosable(false);
        prodTab.setClosable(false);
        catTab.setClosable(false);
        clientTap.setClosable(false);
        ventTab.setClosable(false);
        tp.getTabs().addAll(catTab, prodTab, clientTap, ventTab,payemntTab);
        tp.getStyleClass().add("tab-pane");
        scene = new Scene(tp);
        scene.getStylesheets().add("com/test/style.css");
    }


    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("hello Progressbar");
        stage.setWidth(1000);
        stage.setHeight(700);
        init();
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}
