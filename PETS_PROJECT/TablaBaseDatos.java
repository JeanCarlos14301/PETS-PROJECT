package PETS_PROJECT;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.scene.image.Image;

public class TablaBaseDatos extends Application {

    private TableView<Propietarios> propietarioTable;
    private TableView<Mascotas> mascotasTable;
    private TableView<historiales_medicos> historialTable;
    private TabPane tabPane;
    private Tab propietarioTab;
    private Tab mascotasTab;
    private Tab historialTab;
    private Tab crearPropietariosTab;
    private Tab crearMascotaTab;
    private Tab crearHistorialTab;
    private Tab editarPropietariosTab;
    private Tab editarMascotaTab;
    private Tab editarHistorialTab;
    private ComboBox<Integer> Id_propietarioComboBox;
    private ComboBox<Integer> Id_mascotasComboBox;
    private ComboBox<Integer> Id_historialComboBox;

    @Override
    public void start(Stage secondStage) {
        Image icon = new Image(getClass().getResourceAsStream("icon.png"));
        secondStage.getIcons().add(icon);
        //this.primaryStage = primaryStage;
        secondStage.setTitle("Gestor de Mascostas USTA");

        tabPane = new TabPane();

        //Pestaña de Propietarios
        propietarioTab = new Tab("Propietarios");
        propietarioTable = new TableView<>();
        propietarioTab.setContent(propietarioTable);

        // Pestaña de Mascotas
        mascotasTab = new Tab("Mascotas");
        mascotasTable = new TableView<>();
        mascotasTab.setContent(mascotasTable);

        //Pestaña de historial_medico
        historialTab = new Tab("historiales_medicos");
        historialTable = new TableView<>();
        historialTab.setContent(historialTable);

        //Pestaña de Crear Propietarios
        crearPropietariosTab = new Tab("Crear Propietarios");
        crearPropietariosTab.setContent(createPropietariosPane());

        // Pestaña de Crear Mascotas
        crearMascotaTab = new Tab("Crear Mascotas");
        crearMascotaTab.setContent(createMascotasPane());

        // Pestaña de Crear historial medico
        crearHistorialTab = new Tab("Crear Historial medico");
        crearHistorialTab.setContent(createHistorialPane());

        // Pestaña de Editar Propietarios
        editarPropietariosTab = new Tab("Editar/Eliminar Propietarios");
        editarPropietariosTab.setContent(editPropietariosPane());

        // Pestaña de Editar Mascotas
        editarMascotaTab = new Tab("Editar/Eliminar Mascotas");
        editarMascotaTab.setContent(editMascotaPane());

        // Pestaña de Editar historial medico
        editarHistorialTab = new Tab("Editar/Eliminar historial_medico");
        editarHistorialTab.setContent(editHistorialPane());

        // Configurar las columnas para Propietarios
        TableColumn<Propietarios, Integer> propietarioIdColumn = new TableColumn<>("ID");
        propietarioIdColumn.setCellValueFactory(cellData -> cellData.getValue().id_propietarioProperty().asObject());

        TableColumn<Propietarios, String> propietarioNombreColumn = new TableColumn<>("Nombre");
        propietarioNombreColumn.setCellValueFactory(cellData -> cellData.getValue().Nombre_propietarioProperty());

        TableColumn<Propietarios, String> propietarioTelefonoColumn = new TableColumn<>("Telefono");
        propietarioTelefonoColumn.setCellValueFactory(cellData -> cellData.getValue().telefonoProperty());

        propietarioTable.getColumns().addAll(propietarioIdColumn, propietarioNombreColumn, propietarioTelefonoColumn);

        // Configurar las columnas para MascotasmascotasIdColumn
        TableColumn<Mascotas, Integer> IdMascotaColumn = new TableColumn<>("ID");
        IdMascotaColumn.setCellValueFactory(cellData -> cellData.getValue().Id_mascotasProperty().asObject());

        TableColumn<Mascotas, String> NombreMascotaColumn = new TableColumn<>("Nombre");
        NombreMascotaColumn.setCellValueFactory(cellData -> cellData.getValue().Nombre_mascotasProperty());

        TableColumn<Mascotas, String> RazaColumn = new TableColumn<>("Raza");
        RazaColumn.setCellValueFactory(cellData -> cellData.getValue().RazaProperty());

        TableColumn<Mascotas, String> EspecieColumn = new TableColumn<>("Especie");
        EspecieColumn.setCellValueFactory(cellData -> cellData.getValue().EspecieProperty());

        TableColumn<Mascotas, String> FechaNacimientoColumn = new TableColumn<>("Fecha Nacimiento");
        FechaNacimientoColumn.setCellValueFactory(cellData -> cellData.getValue().Fecha_nacimientoProperty());

        TableColumn<Mascotas, Integer> MascotaIdPropietarioColumn = new TableColumn<>("Propietarios ID");
        MascotaIdPropietarioColumn.setCellValueFactory(cellData -> cellData.getValue().Id_propietarioProperty().asObject());

        mascotasTable.getColumns().addAll(IdMascotaColumn, NombreMascotaColumn, RazaColumn, EspecieColumn, FechaNacimientoColumn, MascotaIdPropietarioColumn);

        // Configurar las columnas para historiales medicos
        TableColumn<historiales_medicos, Integer> historialIdColumn = new TableColumn<>("ID");
        historialIdColumn.setCellValueFactory(cellData -> cellData.getValue().Id_HistorialProperty().asObject());

        TableColumn<historiales_medicos, String> fechaConsultaColumn = new TableColumn<>("Fecha_consulta");
        fechaConsultaColumn.setCellValueFactory(cellData -> cellData.getValue().Fecha_consultaProperty());

        TableColumn<historiales_medicos, String> descripcionColumn = new TableColumn<>("Descripcion");
        descripcionColumn.setCellValueFactory(cellData -> cellData.getValue().DescripcionProperty());

        TableColumn<historiales_medicos, Integer> HistorialIdMascotaColumn = new TableColumn<>("Id_mascota");
        HistorialIdMascotaColumn.setCellValueFactory(cellData -> cellData.getValue().Id_MascotasProperty().asObject());

        historialTable.getColumns().addAll(historialIdColumn, fechaConsultaColumn, descripcionColumn, HistorialIdMascotaColumn);

        // Conectar a la base de datos y cargar datos
        loadData();

        // Crear el menú
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Opciones");

        MenuItem openPropietariosTab = new MenuItem("Abrir Propietarios");
        openPropietariosTab.setOnAction(e -> openTab(propietarioTab));

        MenuItem openMascotaTab = new MenuItem("Abrir Mascotas");
        openMascotaTab.setOnAction(e -> openTab(mascotasTab));

        MenuItem openHistorialTab = new MenuItem("Abrir Historial_Medico");
        openHistorialTab.setOnAction(e -> openTab(historialTab));

        MenuItem openCrearPropietariosTab = new MenuItem("Crear Propietarios");
        openCrearPropietariosTab.setOnAction(e -> openTab(crearPropietariosTab));

        MenuItem openCrearMascotasTab = new MenuItem("Crear Mascotas");
        openCrearMascotasTab.setOnAction(e -> openTab(crearMascotaTab));

        MenuItem openCrearHistorialesTab = new MenuItem("Crear Historiales Medicos");
        openCrearHistorialesTab.setOnAction(e -> openTab(crearHistorialTab));

        MenuItem openEditarPropietariosTab = new MenuItem("Editar/Eliminar Propietarios");
        openEditarPropietariosTab.setOnAction(e -> openTab(editarPropietariosTab));

        MenuItem openEditarMascotaTab = new MenuItem("Editar/Eliminar Mascotas");
        openEditarMascotaTab.setOnAction(e -> openTab(editarMascotaTab));

        MenuItem openEditarHistorialTab = new MenuItem("Editar/Eliminar Historial_medico");
        openEditarHistorialTab.setOnAction(e -> openTab(editarHistorialTab));

        menu.getItems().addAll(openPropietariosTab, openMascotaTab, openHistorialTab, openCrearPropietariosTab, openCrearMascotasTab, openCrearHistorialesTab, openEditarPropietariosTab, openEditarMascotaTab, openEditarHistorialTab);
        menuBar.getMenus().add(menu);

        // Layout principal
        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(tabPane);

        Scene scene = new Scene(root, 800, 600);
        secondStage.setScene(scene);
        secondStage.show();
    }

    private void openTab(Tab tab) {
        if (!tabPane.getTabs().contains(tab)) {
            tabPane.getTabs().add(tab);
        }
        tabPane.getSelectionModel().select(tab);
    }

    private GridPane editPropietariosPane() {
        GridPane pane = new GridPane();
        pane.setHgap(10);
        pane.setVgap(10);

        Label idLabel = new Label("ID:");
        Id_propietarioComboBox = new ComboBox<>();
        Id_propietarioComboBox.setOnAction(e -> loadPropietariosData());

        Label nombreLabel = new Label("Nombre:");
        TextField nombreField = new TextField();

        Label telefonoLabel = new Label("Telefono:");
        TextField telefonoField = new TextField();

        Button updateButton = new Button("Actualizar");
        Button deleteButton = new Button("Eliminar");

        updateButton.setOnAction(e -> {
            // Lógica para actualizar el propietario
            int id_propietario = Id_propietarioComboBox.getValue();
            String Nombre_propietario = nombreField.getText();
            String Telefono = telefonoField.getText();
            updatePropietarios(id_propietario, Nombre_propietario, Telefono);
            loadData();
        });

        deleteButton.setOnAction(e -> {
            // Lógica para eliminar el propietario
            int Id_propietario = Id_propietarioComboBox.getValue();
            deletePropietarios(Id_propietario);
            loadData();
        });

        pane.add(idLabel, 0, 0);
        pane.add(Id_propietarioComboBox, 1, 0);
        pane.add(nombreLabel, 0, 1);
        pane.add(nombreField, 1, 1);
        pane.add(telefonoLabel, 0, 2);
        pane.add(telefonoField, 1, 2);
        pane.add(updateButton, 0, 3);
        pane.add(deleteButton, 1, 3);

        return pane;
    }

    private GridPane editMascotaPane() {
        GridPane pane = new GridPane();
        pane.setHgap(10);
        pane.setVgap(10);

        Label Id_mascotasLabel = new Label("ID:");
        Id_mascotasComboBox = new ComboBox<>();
        Id_mascotasComboBox.setOnAction(e -> loadMascotaData());

        Label Nombre_mascotasLabel = new Label("Nombre:");
        TextField nombreField = new TextField();

        Label EspecieLabel = new Label("Especie:");
        TextField EspecieField = new TextField();

        Label RazaLabel = new Label("Raza:");
        TextField RazaField = new TextField();

        Label Fecha_nacimientoLabel = new Label("Fecha Nacimiento:");
        TextField Fecha_nacimientoField = new TextField();

        Label Id_propietarioLabel = new Label("Propietarios ID:");
        TextField Id_propietarioField = new TextField();

        Button updateButton = new Button("Actualizar");
        Button deleteButton = new Button("Eliminar");

        updateButton.setOnAction(e -> {

            // Lógica para actualizar la mascotas
            int Id_mascotas = Id_mascotasComboBox.getValue();
            String Nombre_mascotas = nombreField.getText();
            String Especie = EspecieField.getText();
            String Raza = RazaField.getText();
            String Fecha_nacimiento = Fecha_nacimientoField.getText();
            int Id_propietario = Integer.parseInt(Id_propietarioField.getText());
            updateMascota(Id_mascotas, Nombre_mascotas, Especie, Raza, Fecha_nacimiento, Id_propietario);
            loadData();
        });
        deleteButton.setOnAction(e -> {
            // Lógica para eliminar la mascotas
            int Id_mascota = Id_mascotasComboBox.getValue();
            deleteMascota(Id_mascota);
            loadData();
        });

        pane.add(Id_mascotasLabel, 0, 0);
        pane.add(Id_mascotasComboBox, 1, 0);
        pane.add(Nombre_mascotasLabel, 0, 1);
        pane.add(nombreField, 1, 1);
        pane.add(EspecieLabel, 0, 2);
        pane.add(EspecieField, 1, 2);
        pane.add(RazaLabel, 0, 3);
        pane.add(RazaField, 1, 3);
        pane.add(Fecha_nacimientoLabel, 0, 4);
        pane.add(Fecha_nacimientoField, 1, 4);
        pane.add(Id_propietarioLabel, 0, 5);
        pane.add(Id_propietarioField, 1, 5);
        pane.add(updateButton, 0, 7);
        pane.add(deleteButton, 1, 7);

        return pane;
    }

    private GridPane editHistorialPane() {
        GridPane pane = new GridPane();
        pane.setHgap(10);
        pane.setVgap(10);

        Label idHistorialLabel = new Label("ID:");
        Id_historialComboBox = new ComboBox<>();
        Id_historialComboBox.setOnAction(e -> loadHistorialesData());

        Label fechaConsultaLabel = new Label("Fecha_consulta:");
        TextField fechaConsultaField = new TextField();

        Label descripcionLabel = new Label("Descripcion:");
        TextField descripcionField = new TextField();

        Label idConsultaLabel = new Label("Id_mascota:");
        TextField idConsultaField = new TextField();

        Button updateButton = new Button("Actualizar");
        Button deleteButton = new Button("Eliminar");

        updateButton.setOnAction(e -> {
            // Lógica para actualizar el historial medico
            int Id_historial = Id_historialComboBox.getValue();
            String Fecha_consulta = fechaConsultaField.getText();
            String Descripcion = descripcionField.getText();
            int id_consulta = Integer.parseInt(idConsultaField.getText());

            updateHistorial(Id_historial, Fecha_consulta, Descripcion, id_consulta);
            loadData();
        });

        deleteButton.setOnAction(e -> {
            // Lógica para eliminar el historial medico
            int Id_historial = Id_historialComboBox.getValue();
            deleteHistorial(Id_historial);
            loadData();
        });

        pane.add(idHistorialLabel, 0, 0);
        pane.add(Id_historialComboBox, 1, 0);
        pane.add(fechaConsultaLabel, 0, 1);
        pane.add(fechaConsultaField, 1, 1);
        pane.add(descripcionLabel, 0, 2);
        pane.add(descripcionField, 1, 2);
        pane.add(idConsultaLabel, 0, 3);
        pane.add(idConsultaField, 1, 3);
        pane.add(updateButton, 0, 4);
        pane.add(deleteButton, 1, 4);

        return pane;

    }

    private void crearPropietario(int id_propietario, String Nombre_propietario, String Telefono) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sistema_mascotas", "root", "");
            Statement statement = connection.createStatement();
            String query = "INSERT INTO Propietarios (Id_propietario, Nombre_propietario, Telefono) VALUES ('" + id_propietario + "', '" + Nombre_propietario + "', '" + Telefono + "')";
            statement.executeUpdate(query);
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void crearMascota(int Id_mascota, String nombreM, String especie, String raza, String fechaN, int id_propietario) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sistema_mascotas", "root", "");
            Statement statement = connection.createStatement();
            String query = "INSERT INTO Mascotas (Id_mascota, Nombre_mascota, Especie, Raza, Fecha_nacimiento, Id_propietario) VALUES ('" + Id_mascota + "', '" + nombreM + "', '" + especie + "', '" + raza + "', '" + fechaN + "', '" + id_propietario + "')";
            statement.executeUpdate(query);
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void crearHistorial(int Id_historial, String Fecha_consulta, String Descripcion, int Id_mascota) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sistema_mascotas", "root", "");
            Statement statement = connection.createStatement();
            String query = "INSERT INTO historiales_medicos (Id_historial, Fecha_consulta, Descripcion, Id_mascota) VALUES ('" + Id_historial + "','" + Fecha_consulta + "', '" + Descripcion + "', '" + Id_mascota + "')";
            statement.executeUpdate(query);
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updatePropietarios(int id_propietario, String Nombre_propietario, String telefono) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sistema_mascotas", "root", "");
            Statement statement = connection.createStatement();
            String query = "UPDATE Propietarios SET Nombre_propietario = '" + Nombre_propietario + "', Telefono = '" + telefono + "' WHERE Id_propietario = " + id_propietario;
            statement.executeUpdate(query);
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deletePropietarios(int id_propietario) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sistema_mascotas", "root", "");
            Statement statement = connection.createStatement();
            String query = "DELETE FROM Propietarios WHERE Id_propietario = " + id_propietario;
            statement.executeUpdate(query);
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateMascota(int Id_mascota, String Nombre_mascota, String Especie, String Raza, String Fecha_nacimiento, int Id_propietario) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sistema_mascotas", "root", "");
            Statement statement = connection.createStatement();
            String query = "UPDATE Mascotas SET Nombre_mascota = '" + Nombre_mascota + "', Especie = '" + Especie + "', Raza = '" + Raza + "', Fecha_nacimiento = '" + Fecha_nacimiento + "', Id_propietario = '" + Id_propietario + "' WHERE Id_mascota = " + Id_mascota;
            statement.executeUpdate(query);
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteMascota(int Id_mascotas) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sistema_mascotas", "root", "");
            Statement statement = connection.createStatement();
            String query = "DELETE FROM Mascotas WHERE Id_mascota = " + Id_mascotas;
            statement.executeUpdate(query);
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateHistorial(int Id_historial, String Fecha_consulta, String Descripcion, int Id_mascota) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sistema_mascotas", "root", "");
            Statement statement = connection.createStatement();
            String query = "UPDATE historiales_medicos SET Fecha_consulta = '" + Fecha_consulta + "', Descripcion = '" + Descripcion + "', Id_mascota = '" + Id_mascota + "' WHERE Id_historial = " + Id_historial;
            statement.executeUpdate(query);
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteHistorial(int Id_historial) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sistema_mascotas", "root", "");
            Statement statement = connection.createStatement();
            String query = "DELETE FROM historiales_medicos WHERE Id_historial = " + Id_historial;
            statement.executeUpdate(query);
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadData() {

        ObservableList<Propietarios> propietarioData = FXCollections.observableArrayList();
        ObservableList<Mascotas> mascotasData = FXCollections.observableArrayList();
        ObservableList<historiales_medicos> historialData = FXCollections.observableArrayList();
        ObservableList<Integer> propietarioIds = FXCollections.observableArrayList();
        ObservableList<Integer> mascotasIds = FXCollections.observableArrayList();
        ObservableList<Integer> historialIds = FXCollections.observableArrayList();

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sistema_mascotas", "root", "");
            Statement statement = connection.createStatement();

            // Cargar datos de propietarios
            ResultSet propietarioResultSet = statement.executeQuery("SELECT * FROM Propietarios");
            while (propietarioResultSet.next()) {
                int id_propietario = propietarioResultSet.getInt("Id_propietario");
                String nombre = propietarioResultSet.getString("Nombre_propietario");
                String telefono = propietarioResultSet.getString("Telefono");
                propietarioData.add(new Propietarios(id_propietario, nombre, telefono));
                propietarioIds.add(id_propietario);
            }

            // Cargar datos de mascotas
            ResultSet mascotasResultSet = statement.executeQuery("SELECT * FROM Mascotas");
            while (mascotasResultSet.next()) {
                int Id_mascotas = mascotasResultSet.getInt("Id_mascota");
                String Nombre_mascotas = mascotasResultSet.getString("Nombre_mascota");
                String Especie = mascotasResultSet.getString("Especie");
                String Raza = mascotasResultSet.getString("raza");
                String Fecha_nacimiento = mascotasResultSet.getString("Fecha_nacimiento");
                int Id_propietario = mascotasResultSet.getInt("Id_propietario");
                mascotasData.add(new Mascotas(Id_mascotas, Nombre_mascotas, Especie, Raza, Fecha_nacimiento, Id_propietario));
                mascotasIds.add(Id_mascotas);
            }

            ResultSet historialResultSet = statement.executeQuery("SELECT * FROM historiales_medicos");
            while (historialResultSet.next()) {
                int Id_historial = historialResultSet.getInt("Id_historial");
                String Fecha_consulta = historialResultSet.getString("Fecha_consulta");
                String Descripcion = historialResultSet.getString("Descripcion");
                int Id_mascota = historialResultSet.getInt("Id_mascota");
                historialData.add(new historiales_medicos(Id_historial, Fecha_consulta, Descripcion, Id_mascota));
                historialIds.add(Id_historial);
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        propietarioTable.setItems(propietarioData);
        // crearMascotaTable.setItems(propietarioData);
        mascotasTable.setItems(mascotasData);
        historialTable.setItems(historialData);
        Id_propietarioComboBox.setItems(propietarioIds);
        Id_mascotasComboBox.setItems(mascotasIds);
        Id_historialComboBox.setItems(historialIds);
    }


    private void loadPropietariosData() {
        Integer id_propietario = Id_propietarioComboBox.getValue();
        if (id_propietario != null) {
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sistema_mascotas", "root", "");
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM Propietarios WHERE Id_propietario = " + id_propietario);
                if (resultSet.next()) {
                    String Nombre_propietario = resultSet.getString("Nombre_propietario");
                    String telefono = resultSet.getString("Telefono");
                    ((TextField) ((GridPane) editarPropietariosTab.getContent()).getChildren().get(3)).setText(Nombre_propietario);
                    ((TextField) ((GridPane) editarPropietariosTab.getContent()).getChildren().get(5)).setText(telefono);
                }
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void loadMascotaData() {
        Integer Id_mascotas = Id_mascotasComboBox.getValue();
        if (Id_mascotas != null) {
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sistema_mascotas", "root", "");
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM Mascotas WHERE Id_mascota = " + Id_mascotas);
                if (resultSet.next()) {
                    String Nombre_mascota = resultSet.getString("Nombre_mascota");
                    String Especie = resultSet.getString("Especie");
                    String Raza = resultSet.getString("Raza");
                    String Fecha_nacimiento = resultSet.getString("Fecha_nacimiento");
                    int Id_propietario = resultSet.getInt("Id_propietario");

                    ((TextField) ((GridPane) editarMascotaTab.getContent()).getChildren().get(3)).setText(Nombre_mascota);
                    ((TextField) ((GridPane) editarMascotaTab.getContent()).getChildren().get(5)).setText(Especie);
                    ((TextField) ((GridPane) editarMascotaTab.getContent()).getChildren().get(7)).setText(Raza);
                    ((TextField) ((GridPane) editarMascotaTab.getContent()).getChildren().get(9)).setText(Fecha_nacimiento);
                    ((TextField) ((GridPane) editarMascotaTab.getContent()).getChildren().get(11)).setText(String.valueOf(Id_propietario));
                }
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void loadHistorialesData() {
        Integer Id_historial = Id_historialComboBox.getValue();
        if (Id_historial != null) {
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sistema_mascotas", "root", "");
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM historiales_medicos WHERE Id_historial = " + Id_historial);
                if (resultSet.next()) {
                    String Fecha_consulta = resultSet.getString("fecha_consulta");
                    String Descripcion = resultSet.getString("descripcion");
                    int Id_mascota = resultSet.getInt("Id_mascota");
                    ((TextField) ((GridPane) editarHistorialTab.getContent()).getChildren().get(3)).setText(Fecha_consulta);
                    ((TextField) ((GridPane) editarHistorialTab.getContent()).getChildren().get(5)).setText(Descripcion);
                    ((TextField) ((GridPane) editarHistorialTab.getContent()).getChildren().get(7)).setText(String.valueOf(Id_mascota));
                }
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private GridPane createPropietariosPane() {
        GridPane pane = new GridPane();
        pane.setHgap(10);
        pane.setVgap(10);

        Label idLabel = new Label("Id propietario:");
        TextField idField = new TextField();

        Label nombreLabel = new Label("Nombre:");
        TextField nombreField = new TextField();

        Label telefonoLabel = new Label("Telefono:");
        TextField telefonoField = new TextField();

        Button createPropietarioButton = new Button("Crear Propietario");

        createPropietarioButton.setOnAction(e -> {
            try {
                String idText = idField.getText();
                if (idText.isEmpty()) {
                    System.out.println("El campo Id propietario no puede estar vacío.");
                    return;
                }
                int Id_propietario = Integer.parseInt(idField.getText());
                String Nombre_propietario = nombreField.getText();
                String Telefono = telefonoField.getText();
                crearPropietario(Id_propietario, Nombre_propietario, Telefono);
                loadData();
            } catch (NumberFormatException ex) {
                System.out.println("El campo Id propietario debe ser un número entero válido.");
            }// Actualizar la vista después de la inserción
        });

        pane.add(idLabel, 0, 0);
        pane.add(idField, 1, 0);
        pane.add(nombreLabel, 0, 1);
        pane.add(nombreField, 1, 1);
        pane.add(telefonoLabel, 0, 2);
        pane.add(telefonoField, 1, 2);
        pane.add(createPropietarioButton, 0, 3);

        return pane;
    }

    //CREAR MASCOTAS
    private GridPane createMascotasPane() {

        GridPane pane = new GridPane();
        pane.setHgap(10);
        pane.setVgap(10);

        //Id_propietarioComboBox = new ComboBox<>();
        //Id_propietarioComboBox.setOnAction(e -> loadPropietariosData());
        Label idMLabel = new Label("Id_mascota:");
        TextField idMField = new TextField();

        Label nombreMLabel = new Label("Nombre_mascota:");
        TextField nombreMField = new TextField();

        Label especieLabel = new Label("Especie:");
        TextField especieField = new TextField();

        Label razaLabel = new Label("Raza:");
        TextField razaField = new TextField();

        Label fechaNLabel = new Label("Fecha_nacimiento:");
        TextField fechaNField = new TextField();

        Label idPropLabel = new Label("Id_propietario");
        TextField idPropField = new TextField();

        Button createMascotaButton = new Button("Crear mascota");

        createMascotaButton.setOnAction(e -> {
            int Id_mascota = Integer.parseInt(idMField.getText());
            String nombreM = nombreMField.getText();
            String especie = especieField.getText();
            String raza = razaField.getText();
            String fechaN = fechaNField.getText();
            //int id_propietario = Id_propietarioComboBox.getValue();
            int id_propietario = Integer.parseInt(idPropField.getText());
            crearMascota(Id_mascota, nombreM, especie, raza, fechaN, id_propietario);
            loadData(); // Actualizar la vista después de la inserción

        });

        pane.add(idMLabel, 0, 0);
        pane.add(idMField, 1, 0);
        pane.add(nombreMLabel, 0, 1);
        pane.add(nombreMField, 1, 1);
        pane.add(especieLabel, 0, 2);
        pane.add(especieField, 1, 2);
        pane.add(razaLabel, 0, 3);
        pane.add(razaField, 1, 3);
        pane.add(fechaNLabel, 0, 4);
        pane.add(fechaNField, 1, 4);
        pane.add(idPropLabel, 0, 5);
        pane.add(idPropField, 1, 5);
        pane.add(createMascotaButton, 0, 6);// Añadir el boton crear Mascota
        //pane.add(Id_propietarioComboBox, 0, 7);// Añadir el boton crear Mascota

        return pane;
    }

    private GridPane createHistorialPane() {
        GridPane pane = new GridPane();
        pane.setHgap(10);
        pane.setVgap(10);

        Label idLabel = new Label("ID de historial:");
        TextField idField = new TextField();

        // Id_mascotasComboBox = new ComboBox<>();
        //Id_mascotasComboBox.setOnAction(e -> loadMascotaData());
        Label fechaLabel = new Label("Fecha_consulta:");
        TextField fechaField = new TextField();

        Label descripcionLabel = new Label("Descripcion:");
        TextField descripcionField = new TextField();

        Label idMLabel = new Label("Id de mascota:");
        TextField idMField = new TextField();

        Button createPropietarioButton = new Button("Crear Historial medico");

        createPropietarioButton.setOnAction(e -> {
            int Id_historial = Integer.parseInt(idMField.getText());
            int Id_mascota = Integer.parseInt(idField.getText());
            String Fecha_consulta = fechaField.getText();
            String Descripcion = descripcionField.getText();
            crearHistorial(Id_historial, Fecha_consulta, Descripcion, Id_mascota);
            loadData(); // Actualizar la vista después de la inserción
        });
        pane.add(idLabel, 0, 0);
        pane.add(idField, 1, 0);
        pane.add(fechaLabel, 0, 1);
        pane.add(fechaField, 1, 1);
        pane.add(descripcionLabel, 0, 2);
        pane.add(descripcionField, 1, 2);
        pane.add(idMLabel, 0, 3);
        pane.add(idMField, 1, 3);
        pane.add(createPropietarioButton, 0, 4);

        return pane;
    }

    public static void main(String[] args) {
        LoginPets.launch(LoginPets.class, args); // Lanzar la vista de la aplicación 

        //TablaBaseDatos.launch(args);
        launch(args);
    }
}
