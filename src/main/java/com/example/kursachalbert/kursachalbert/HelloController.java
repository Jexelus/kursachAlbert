package com.example.kursachalbert.kursachalbert;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;


import java.io.File;
import java.io.IOException;
import java.util.Objects;

import com.healthmarketscience.jackcess.ColumnBuilder;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;
import com.healthmarketscience.jackcess.Table;
import com.healthmarketscience.jackcess.TableBuilder;
import com.healthmarketscience.jackcess.DataType;
import com.healthmarketscience.jackcess.Row;


public class HelloController {
    @FXML
    private Label ConnectHeader;


    @FXML
    private TextField DataBaseName;


    @FXML
    private Button connect;

    @FXML
    private TextField IDField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField menuField;

    @FXML
    private TextField barField;

    @FXML
    private TextField checkField;

    @FXML
    private Button addRow;

    @FXML
    private GridPane root;

    @FXML
    private Button update;

    @FXML
    private Button baseDelete;

    @FXML
    protected void Update() throws IOException{
        File file = new File(DataBaseName.getText()+".mdb");
        Database db = new DatabaseBuilder(file)
                .setReadOnly(false)
                .open();
        Table table = db.getTable("Table");
        root.getChildren().clear();
        root.setGridLinesVisible(true);
        int row_g = 0;
        for (Row row :table) {
            int col_g = 0;
            for (Object value : row.values().toArray()){
                Label chil = new Label();
                chil.setPadding(new Insets(20.0, 20.0, 20.0, 20.0));
                chil.setText(value.toString());
                root.setGridLinesVisible(true);
                root.add(chil, col_g, row_g);
                col_g += 1;
            }
            Button delete_btn = new Button();
            delete_btn.setText("Удалить");
            delete_btn.setOnAction(e -> {
                try {
                    table.deleteRow(row);
                    delete_btn.setText("Удаленно");
                    delete_btn.setDisable(true);

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
            root.add(delete_btn, col_g+1, row_g);
            row_g += 1;
        }
    }

    @FXML
    protected void addRow(ActionEvent actionEvent) throws IOException{
        File file = new File(DataBaseName.getText()+".mdb");
        Database db = new DatabaseBuilder(file)
                .setReadOnly(false)
                .open();
        Table table = db.getTable("Table");
        if ((IDField.getText() != null) &&
                (nameField.getText() != null) &&
                (menuField.getText() != null) &&
                (barField.getText() != null) &&
                (checkField.getText() != null)) {
            table.addRow(Integer.parseInt(IDField.getText()),
                    nameField.getText(),
                    menuField.getText(),
                    barField.getText(),
                    Integer.parseInt(checkField.getText()));
        }
        IDField.setText("");
        nameField.setText("");
        menuField.setText("");
        barField.setText("");
        checkField.setText("");
        CreateBase();
    }


    @FXML
    protected void CreateBase() throws IOException {
        root.getChildren().clear();
        root.setGridLinesVisible(true);
        if (new File(DataBaseName.getText()+".mdb").isFile()) {
            File file = new File(DataBaseName.getText()+".mdb");
            Database db = new DatabaseBuilder(file)
                    .open();
        }
        else {
            File file = new File(DataBaseName.getText()+".mdb");
            Database db = new DatabaseBuilder(file)
                    .setFileFormat(Database.FileFormat.V2000)
                    .create();
            Table table = new TableBuilder("Table")
                    .addColumn(new ColumnBuilder("ID", DataType.LONG))
                    .addColumn(new ColumnBuilder("name", DataType.TEXT))
                    .addColumn(new ColumnBuilder("menu", DataType.TEXT))
                    .addColumn(new ColumnBuilder("bar", DataType.TEXT))
                    .addColumn(new ColumnBuilder("check", DataType.TEXT))
                    .toTable(db);
        }
        File file = new File(DataBaseName.getText()+".mdb");
        Database db = new DatabaseBuilder(file)
                .setReadOnly(false)
                .open();
        Table table = db.getTable("Table");

        ConnectHeader.setVisible(false);
        DataBaseName.setVisible(false);
        connect.setVisible(false);

        baseDelete.setVisible(true);
        baseDelete.setText("Удалить базу");
        IDField.setVisible(true);
        nameField.setVisible(true);
        menuField.setVisible(true);
        barField.setVisible(true);
        checkField.setVisible(true);
        addRow.setVisible(true);
        update.setVisible(true);

        int row_g = 0;
        for (Row row :table) {
            int col_g = 0;
            for (Object value : row.values().toArray()){
                Label chil = new Label();
                chil.setPadding(new Insets(20.0, 20.0, 20.0, 20.0));
                chil.setText(value.toString());
                root.setGridLinesVisible(true);
                root.add(chil, col_g, row_g);
                col_g += 1;
            }
            Button delete_btn = new Button();
            delete_btn.setText("Удалить");
            delete_btn.setOnAction(e -> {
                try {
                    table.deleteRow(row);
                    delete_btn.setText("Удаленно");
                    delete_btn.setDisable(true);

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
            root.add(delete_btn, col_g+1, row_g);
            row_g += 1;
        }
    }



    public void deleteDatabase(ActionEvent actionEvent) {
        File file = new File(DataBaseName.getText()+".mdb");
        if(file.delete()){
            System.out.println(DataBaseName.getText()+".mdb" + "файл был удален с корневой папки проекта");
        }else System.out.println(DataBaseName.getText()+".mdb" + "не был найден в корневой папке проекта");
        Platform.exit();
    }
}