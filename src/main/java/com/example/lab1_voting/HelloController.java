package com.example.lab1_voting;

import com.example.lab1_voting.DatabaseConnection.DatabaseConnection;
import com.example.lab1_voting.Windows.AlertWindow;
import com.example.lab1_voting.Windows.ConfirmationWindow;
import com.example.lab1_voting.Windows.ErrorWindow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private Button loginButton;

    @FXML
    private Button exitButton;

    @FXML
    private TextField loginField;

    @FXML
    private Label loginLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private PasswordField passwordField;

    @FXML
    void onExitButton(ActionEvent event) {
        Optional<ButtonType> result = new ConfirmationWindow().showConfirmationWindow("Закрытие приложения", "Вы уверены, что ходите закрыть приложение?");
        if (result.get() == ButtonType.OK){
            System.exit(1);
        }
    }

    @FXML
    void onLoginConnectButtonClick(ActionEvent event) {
        DatabaseConnection dbConnection = new DatabaseConnection();
        Connection dbLink = dbConnection.getDatabaseLink();
        String login = loginField.getText();
        String password = passwordField.getText();

        if (login.isEmpty()) {
            new AlertWindow().showWarningWindow("Вход в систему", "Вы не ввели логин");
            return;
        }

        if (password.isEmpty()) {
            new AlertWindow().showWarningWindow("Вход в систему", "Вы не ввели пароль");
            return;
        }

        try {
            Statement statement = dbLink.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Users");

            while (resultSet.next()) {
                String actualLogin = resultSet.getString("login");
                String actualPassword = resultSet.getString("password");

                if (actualLogin != null && actualPassword != null) {
                    if (actualLogin.equals(login) && actualPassword.equals(password)) {
                        new AlertWindow().showWarningWindow("Вход в систему", "Добро пожаловать");
                        return;
                    }
                }
            }
            new ErrorWindow().showWindow("Вход в систему", "Вы ввели неправильный логин или пароль. Проверьте ввод и попробуйте еще раз");
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

}