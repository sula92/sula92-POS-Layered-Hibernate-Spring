package com.sula;

import com.sula.business.custom.CustomerBO;
import com.sula.business.custom.ItemBO;
import com.sula.business.custom.OrderBO;
import com.sula.business.custom.impl.CustomerBOImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
//import com.sula.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

public class Appinitializer extends Application {

    public static AnnotationConfigApplicationContext ctx;

    public static CustomerBO customerBo;
    public static OrderBO orderBO;
    public static ItemBO itemBO;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        try {

            ctx = new AnnotationConfigApplicationContext();
            ctx.register(AppConfig.class);
            ctx.refresh();

            customerBo= ctx.getBean(CustomerBOImpl.class);
            orderBO = ctx.getBean(OrderBO.class);
            itemBO = ctx.getBean(ItemBO.class);


            Parent root = FXMLLoader.load(this.getClass().getResource("/com/sula/view/main.fxml"));
            Scene mainScene = new Scene(root);
            primaryStage.setScene(mainScene);
            primaryStage.setTitle("In-memory POS");
            primaryStage.centerOnScreen();
            primaryStage.show();

        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR, "Something went wrong, please contact SULA").show();
        }

    }
}
