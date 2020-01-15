package com.view;


import com.dataBaseConnection.PaymentDAO;
import com.dataBaseConnection.VenteDAO;
import com.module.OnlinePayment;
import com.module.Payment;
import com.module.Vente;
import com.module.chaque;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;


public class PaymentPan extends GridPane {


    private TableView<Vente> tableVente;
    private ObservableList<Vente> obvente;
    private ArrayList<Vente> listeVente;
    private TableView<Payment>  tablePayment;
    private ArrayList<Payment> paymentListe;
    private ObservableList<Payment> obpayment;
    private PaymentDAO pdao;
    private VenteDAO vdao;

    private Label labelid , labelMontant,labeltype,labelNpaymen,labelDate,labelProp,labelRest,labelPayed;
    private TextField textid,textMontant,textNpayment,textProp;
    private DatePicker datePicker = new DatePicker();
    private ComboBox<String> comboType;
    private Button btadd,btsupp,btmod;

    public PaymentPan(){
        initDate();
    }

    public void initDate(){

        pdao=new PaymentDAO();
        vdao=new VenteDAO();
        datePicker=new DatePicker(LocalDate.now());
        labelNpaymen=new Label("------");
        textNpayment=new TextField();
        textNpayment.setDisable(true);
        datePicker.setDisable(true);
        labelDate=new Label("Date");
        btadd=new Button("ajouter");
        btmod=new Button("modifer");
        btsupp=new Button("supprimer");
        labelid =new Label("id");
        labelMontant=new Label("Montant");
        labeltype=new Label("type");
        labelProp=new Label("Propriter");
        textProp=new TextField();
        textProp.setDisable(true);
        textid=new TextField();
        textid.setDisable(true);
        textMontant=new TextField();
        comboType=new ComboBox<String>();
        comboType.setMaxWidth(150);
        comboType.getItems().add("chaque");
        comboType.getItems().add("espace");
        comboType.getItems().add("enligne");
        comboType.setValue("espace");
        tableVente=new TableView<>();
        tableVente=new TableView<>();
        listeVente = VentePen.listeVente;
        obvente= FXCollections.observableArrayList();
        TableColumn columnidvente =new TableColumn("id");
        columnidvente.setCellValueFactory(new PropertyValueFactory<Vente,String>("id"));
        TableColumn columndatevente =new TableColumn("date");
        columndatevente.setCellValueFactory(new  PropertyValueFactory<Vente,String>("date"));
        TableColumn columnClientvente =new TableColumn("client");
        columnClientvente.setCellValueFactory(new  PropertyValueFactory<Vente,String>("client"));
        TableColumn columntotalvente =new TableColumn("total");
        columntotalvente.setCellValueFactory(new  PropertyValueFactory<Vente,String>("totelVente"));
        //obvente.addAll(listeVente);
        tableVente.setItems(VentePen.obvente);
        tableVente.getColumns().addAll(columnidvente,columndatevente,columnClientvente,columntotalvente);


        tablePayment=new TableView<>();
        TableColumn columnidPayment = new TableColumn("id");
        columnidPayment.setCellValueFactory(new CostumePropValueFactury("id"));
        TableColumn columnMontanPayment = new TableColumn("Montant");
        columnMontanPayment.setCellValueFactory(new CostumePropValueFactury("Montant"));
        TableColumn columnNPayment = new TableColumn("NPaiment");
        columnNPayment.setCellValueFactory(new CostumePropValueFactury("NPaiment"));
        TableColumn columnDate=new TableColumn("date");
        columnDate.setCellValueFactory(new CostumePropValueFactury("date"));
        TableColumn columnNomP = new TableColumn("Propriter");
        columnNomP.setCellValueFactory(new CostumePropValueFactury("Propriter"));
        TableColumn columnType = new TableColumn("type");
        columnType.setCellValueFactory(new CostumePropValueFactury("type"));
        paymentListe=new ArrayList<>();
        obpayment=FXCollections.observableArrayList();
        tablePayment.setItems(obpayment);
        tablePayment.getColumns().addAll(columnidPayment,columnMontanPayment,columnType,columnNPayment,columnDate,columnNomP);
        labelRest=new Label("RESRT:---------");
        labelRest.setTextFill(Color.RED);
        labelPayed=new Label("TOTAL PAYE:-----------");
        labelPayed.setTextFill(Color.GREEN);
        setPosition();
    }

    public void setPosition(){
        this.setAlignment(Pos.CENTER);
        this.setHgap(20);
        this.setVgap(10);
        this.add(labelid,0,0);this.add(textid,1,0);
        this.add(labelMontant,0,1);this.add(textMontant,1,1);
        this.add(labeltype,0,2);this.add(comboType,1,2);
        this.add(labelNpaymen,0,3);this.add(textNpayment,1,3);
        this.add(labelDate,0,4);this.add(datePicker,1,4);
        this.add(labelProp,0,5);this.add(textProp,1,5);
        VBox butonBox = new VBox(btadd,btmod,btsupp);
        butonBox.setAlignment(Pos.CENTER);
        butonBox.setSpacing(5);
        this.add(butonBox,2,0,1,3);
        //HBox tableBox = new HBox(tableVente,tablePayment);
        //tableBox.setSpacing(20);
        this.add(tableVente,0,6,3,1);
        this.add(tablePayment,3,0,1,7);
        this.add(labelPayed,0,7);
        this.add(labelRest,2,7);


        addEventLisner();
    }

    private void addEventLisner() {
        btadd.setOnAction(v->{
            Vente vente=tableVente.getSelectionModel().getSelectedItem();
            Payment p=new Payment(0,Double.parseDouble(textMontant.getText()),vente);
            vente.addPayment(p);
            pdao.create(p);

        });
        comboType.setOnAction(v->{
            if(comboType.getValue().equals("espace")) {
                textNpayment.setDisable(true);
                datePicker.setDisable(true);
                textProp.setDisable(true);
                labelNpaymen.setText("-----");
            }
            if(comboType.getValue().equals("chaque")) {
                textNpayment.setDisable(false);
                datePicker.setDisable(false);
                textProp.setDisable(false);
                labelNpaymen.setText("N°chaque");
            }
            if(comboType.getValue().equals("enligne")) {
                textNpayment.setDisable(false);
                datePicker.setDisable(false);
                textProp.setDisable(false);
                labelNpaymen.setText("N° compte");
            }

        });
        tableVente.setOnMouseClicked(v->{
            if(tableVente.getSelectionModel().getSelectedItem()!=null){
                obpayment.clear();
                obpayment.addAll(tableVente.getSelectionModel().getSelectedItem().getPayments());
                Vente t=tableVente.getSelectionModel().getSelectedItem();
                Double Rest=0.0;
                Double payed=0.0;
                for(Payment p:t.getPayments()){
                    payed+=p.getMontant();
                }
                Rest=t.getTotelVente()-payed;

                labelRest.setText("RESRT:"+String.format("%.2f",Rest));
                labelPayed.setText("TOTAL PAYE:"+String.format("%.2f",payed));

                System.out.println("clicked");
            }


            
        });
        btadd.setOnAction(v->{
            Payment p=null;
            Vente t=tableVente.getSelectionModel().getSelectedItem();
            Double payed=0.0;
            for(Payment d:t.getPayments()){
                payed+=d.getMontant();
            }
            if(t.getTotelVente()-payed < Double.parseDouble(textMontant.getText())){
                IHM.msg.setContentText("le montant est superur que le rest");
                IHM.msg.show();

                return;
            }
            if(comboType.getValue().equals("espace")) {
                Vente c=tableVente.getSelectionModel().getSelectedItem();
                p=new Payment(0,Double.parseDouble(textMontant.getText()),c);
                c.addPayment(p);
                pdao.create(p);
                System.out.println(p);
            }
            if(comboType.getValue().equals("chaque")) {
                Vente c=tableVente.getSelectionModel().getSelectedItem();
                System.out.println(datePicker.getValue().toString());
                p=new chaque(0,Double.parseDouble(textMontant.getText()),c,new Date(),Integer.parseInt(textNpayment.getText()),textProp.getText());
                c.addPayment(p);
                pdao.create(p);
            }
            if(comboType.getValue().equals("enligne")) {
                Vente c=tableVente.getSelectionModel().getSelectedItem();
                p=new OnlinePayment(0,Double.parseDouble(textMontant.getText()),c,Integer.parseInt(textNpayment.getText()),new Date(),textProp.getText());
                System.out.println(p.toString());
                try {
                    Socket socket = new Socket("192.168.1.69",5555);
                    BufferedReader bf=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String Smsg=bf.readLine();
                    System.out.println(Smsg);
                    PrintStream ps=new PrintStream(socket.getOutputStream());
                    ps.println(textNpayment.getText()+"-"+textMontant.getText());
                    String result = bf.readLine();
                    if(result.equals("true")){
                        c.addPayment(p);
                        pdao.create(p);
                    }else {
                        IHM.msg.setContentText("solde ins");
                        IHM.msg.show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            obpayment.add(p);
        });
    }
}
