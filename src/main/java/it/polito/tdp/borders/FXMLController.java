
package it.polito.tdp.borders;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtAnno"
    private TextField txtAnno; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader
    
    @FXML
    private ComboBox<Country> boxCountry;

    @FXML
    private Button btnTrova;

    @FXML
    void doCalcolaConfini(ActionEvent event) {
    	txtResult.clear();
    	if(!model.controllaTxt(txtAnno.getText())) {
    		txtResult.setText("NON HAI INSERITO UN VALORE INTERO");
    		return;
    	}
    	int anno = Integer.parseInt(txtAnno.getText());
    	if (anno>2016 || anno <1816) {
    		txtResult.setText("VALORE INSERITO IMPOSSIBILE DA STUDIARE");
    		return;
    	}
    	
    	model.creaGrafo(anno);
    	txtResult.appendText(model.degreesVertexes());
    	txtResult.appendText("Nel grafo esistono "+model.componentiConnessi()+" componenti connesse\n");
    	boxCountry.getItems().addAll(model.getCountries());

    }
    
    @FXML
    void doResearch(ActionEvent event) {
    	/*Map<Country,Country> albero = model.alberoVisita(boxCountry.getValue());
    	
    	for(Country f: albero.keySet()) {
			txtResult.appendText( f + "-->"+albero.get(f)+"\n");
		}
    	*/
    	if(boxCountry.getValue()==null) {
    		txtResult.setText("Selezionare uno Stato");
    		return;
    	}
    	

    	if(model.trovaVicini(boxCountry.getValue()).size()==1) {
    		txtResult.setText("Non ci sono stati raggiungibili.");
    		return;
    	}
    	
    	txtResult.clear();
    	
    	for(Country c: model.trovaVicini(boxCountry.getValue())) {
    		if(c.equals(boxCountry.getValue())) {
    			
    		}else {
    			txtResult.appendText(c+"\n");
    		}
    	}
    	
    	txtResult.appendText("\n\nPUOI RAGGIUNGERE A POCHE ORE:\n");

    	for(Country c : model.trovaViciniAdiacenti(boxCountry.getValue())) {
    		txtResult.appendText(c+"\n");
    	}

    }


    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxCountry != null : "fx:id=\"boxCountry\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnTrova != null : "fx:id=\"btnTrova\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
