package com.view;

import com.module.OnlinePayment;
import com.module.Payment;
import com.module.chaque;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ObservableValueBase;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class CostumePropValueFactury extends PropertyValueFactory<Payment,String> {
    /**
     * Creates a default PropertyValueFactory to extract the value from a given
     * TableView row item reflectively, using the given property name.
     *
     * @param property The name of the property with which to attempt to
     *                 reflectively extract a corresponding value for in a given object.
     */
    public CostumePropValueFactury(String property) {
        super(property);
    }

    @Override
    public ObservableValue<String> call(TableColumn.CellDataFeatures<Payment, String> param) {
        if(param.getValue().getClass().getName().equals("com.module.Payment")){
            if(getProperty().equals("id") || getProperty().equals("Montant")){
                return super.call(param);
            }else if(getProperty().equals("type")){
                return new Observabletype("espace");
            }else {
                return new Observabletype("-----");
            }
        }
        if(param.getValue().getClass().getName().equals("com.module.chaque")){
            if(getProperty().equals("id") || getProperty().equals("Montant")){
                return  super.call(param);
            }else if(getProperty().equals("NPaiment")){
                return new Observabletype(""+((chaque)(param.getValue())).getnCheque());
            }else if(getProperty().equals("date")){
                return new Observabletype(""+((chaque)(param.getValue())).getDateaf().toString());
            }else if(getProperty().equals("Propriter")){
                return new Observabletype(""+((chaque)(param.getValue())).getNomP());
            }else if(getProperty().equals("type")){
                return new Observabletype("chaque");
            }
        }
        if(param.getValue().getClass().getName().equals("com.module.OnlinePayment")){
            if(getProperty().equals("id") || getProperty().equals("Montant")){
                return  super.call(param);
            }else if(getProperty().equals("NPaiment")){
                return new Observabletype(""+((OnlinePayment)(param.getValue())).getNcompte());
            }else if(getProperty().equals("date")){
                return new Observabletype(""+((OnlinePayment)(param.getValue())).getDatePayment().toString());
            }else if(getProperty().equals("Propriter")){
                return new Observabletype(""+((OnlinePayment)(param.getValue())).getNomP());
            }else if(getProperty().equals("type")){
                return new Observabletype("en Ligne");
            }
        }
        return null;
    }
}

class Observabletype extends ObservableValueBase<String>{

    String returntype;

    public Observabletype(String returntype) {
        this.returntype = returntype;
    }

    @Override
    public String getValue() {
        return returntype;
    }
}