package com.sula.controller;

import com.sula.Appinitializer;
import com.sula.business.BOFactory;
import com.sula.business.BOTypes;
import com.sula.business.custom.CustomerBO;
import com.jfoenix.controls.JFXTextField;
import com.sula.db.DBConnection;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import com.sula.dto.CustomerDTO;
import com.sula.entity.Customer;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import com.sula.dao.custom.impl.CustomerDAOImpl;
import com.sula.util.CustomerTM;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerViewController implements Initializable {


    public FontAwesomeIconView iconHome;
    public AnchorPane root;
    @FXML
    private JFXTextField txId;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtAddress;

   @FXML
    private Button btnSave;

    @FXML
    private Button btnCus;

    @FXML
    private TableView<CustomerTM> tblCustomer;

    @FXML
    private TableColumn colCusId;

    @FXML
    private TableColumn colCusName;

    @FXML
    private TableColumn colCusAdd;

    @FXML
    private TableColumn colDel;

    @FXML
    private JFXTextField txtSearch;

    private PreparedStatement preparedStatement;
    private Connection connection=DBConnection.getInstance().getConnection();
    private CustomerDAOImpl customerDAOImpl =new CustomerDAOImpl();

    private CustomerDTO customerDTO;
    @Autowired
    private CustomerBO customerBO= Appinitializer.customerBo;//= BOFactory.getInstance().getBO(BOTypes.CUSTOMER);


    ArrayList<Customer> customers=new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        colCusId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCusName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCusAdd.setCellValueFactory(new PropertyValueFactory<>("address"));
        colDel.setCellValueFactory(new PropertyValueFactory<>("button"));

        tblCustomer.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            btnSave.setText("UPDATE");
            txId.setText(newValue.getId());
            txtName.setText(newValue.getName());
            txtAddress.setText(newValue.getAddress());
            btnCus.setDisable(true);



        });

        try {
            loadCustomer();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        txtSearch.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                ObservableList<CustomerTM> customerTMS=tblCustomer.getItems();
                customerTMS.clear();

                for (CustomerTM customer:tblCustomer.getItems()) {
                    if(customer.getId().contains(newValue)||customer.getName().contains(newValue)||customer.getAddress().contains(newValue)) {
                        Button button=new Button("DELETE");
                        button.setStyle("-fx-background-color: red");
                        customerTMS.add(new CustomerTM(customer.getId(), customer.getName(), customer.getAddress(), button));
                        button.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                customerTMS.remove(customer);
                                try {
                                    deleteCustomer(customer.getId());
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                txtSearch.clear();
                                tblCustomer.refresh();
                            }

                        });
                    }

                }
            }
        });

    }


    @FXML
    void btnCusOnAction(ActionEvent event) throws Exception {

        txtAddress.setText("");
        txtName.setText("");
        btnSave.setText("SAVE");

        int i=1;


        String x= customerBO.getLastCustomerId();



        int id= Integer.valueOf(x.substring(1));
        int maxid=maxid=id+1;
        String newid;
        System.out.println(maxid);

        if(x.startsWith("C00")){

            newid="C00"+maxid;
            System.out.println(newid);
            txId.setText(newid);


        }
        else if(x.startsWith("C0")){

            newid="C0"+maxid;
            System.out.println(newid);
            txId.setText(newid);
        }
        else {

            newid="C"+maxid;
            System.out.println(newid);
            txId.setText(newid);
        }


    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws Exception {

        String id = txId.getText();
        String nm = txtName.getText();
        String add = txtAddress.getText();

        if(btnSave.getText().equalsIgnoreCase("save")) {


             customerBO.saveCustomer(new CustomerDTO(id,nm,add));

                new Alert(Alert.AlertType.INFORMATION,"Customer Added Successfully ").show();
                tblCustomer.getItems().clear();
                loadCustomer();
                tblCustomer.refresh();
                btnCusOnAction(event);


        }
        else {

            try {
                customerDTO=new CustomerDTO(id,nm,add);
                customerBO.updateCustomer(customerDTO);
                new Alert(Alert.AlertType.INFORMATION, "Updated").show();

            }
            catch (Exception e){
                new Alert(Alert.AlertType.ERROR, "An Error!").show();
            }
            finally {
                tblCustomer.getItems().clear();
                loadCustomer();
                tblCustomer.refresh();

            }

            btnCusOnAction(event);
        }

       // loadCustomer();
        btnCus.setDisable(false);



    }





    public void loadCustomer() throws Exception {
        List<CustomerDTO> customers= customerBO.findAllCustomers();
        ObservableList<CustomerDTO> customerObservableList= FXCollections.observableList(customers);
        customerObservableList.stream().forEach(customer -> {
            Button button=new Button("DELETE");
            button.setStyle("-fx-background-color: red");
            CustomerTM customerTM=new CustomerTM(customer.getId(),customer.getName(),customer.getAddress(),button);
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    try {
                        deleteCustomer(customer.getId());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    tblCustomer.getItems().clear();
                    try {
                        loadCustomer();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    ;

                }
            });
            tblCustomer.getItems().add(customerTM);
        });
    }

    private void deleteCustomer(String id) throws SQLException {
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION,"Are You Sure You Wantto Delete This Customer?", ButtonType.YES,ButtonType.CANCEL);
        Optional<ButtonType> buttonType=alert.showAndWait();
        if (buttonType.get()==ButtonType.YES){
            try {
                 customerBO.deleteCustomer(id);


                    new Alert(Alert.AlertType.INFORMATION,"Customer Has Been Deleted Successfully").show();
                    tblCustomer.getItems().clear();
                    loadCustomer();
                    tblCustomer.refresh();


            } catch (SQLException e) {
                new Alert(Alert.AlertType.INFORMATION,"Failed To Delete the Customer!").show();
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void navigate(MouseEvent mouseEvent) throws IOException {

        if (mouseEvent.getSource() instanceof FontAwesomeIconView){
            FontAwesomeIconView icon = (FontAwesomeIconView) mouseEvent.getSource();

            Parent root = null;

            switch(icon.getId()){
                case "iconHome":
                    root = FXMLLoader.load(this.getClass().getResource("/com/sula/view/main.fxml"));
                    break;
                case "iconItem":
                    root = FXMLLoader.load(this.getClass().getResource("/com/sula/view/itemView.fxml"));
                    break;
                case "iconPlaceOrder":
                    root = FXMLLoader.load(this.getClass().getResource("/com/sula/view/PlaceOrderView.fxml"));
                    break;
                case "iconSearch":
                    root = FXMLLoader.load(this.getClass().getResource("/com/sula/view/SearchOrderView.fxml"));
                    break;
            }

            if (root != null){
                Scene subScene = new Scene(root);
                Stage primaryStage = (Stage) this.root.getScene().getWindow();
                primaryStage.setScene(subScene);
                primaryStage.centerOnScreen();

                TranslateTransition tt = new TranslateTransition(Duration.millis(350), subScene.getRoot());
                tt.setFromX(-subScene.getWidth());
                tt.setToX(0);
                tt.play();

            }
        }
    }


}
