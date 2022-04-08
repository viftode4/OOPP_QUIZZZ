package client.scenes;


import client.utils.ServerUtils;


import commons.Activity;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import javafx.util.StringConverter;


import javax.inject.Inject;


import javafx.scene.image.ImageView;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;

public class AdminInterfaceCtrl implements Initializable {

    @FXML
    private TableColumn<Activity, String> col_activity;

    @FXML
    private TableColumn<Activity, Number> col_cons;

    @FXML
    private TableColumn<Activity, Number> col_id;

    @FXML
    private TableColumn<Activity, ImageView> col_image;

    @FXML
    private TableView<Activity> databaseview;

    @FXML
    private  Button AddImage;

    @FXML
    private TextField ActivityTitle;

    @FXML
    private TextField Consumption;

    public TableColumn<Activity,Button> col_update;

    public TableColumn<Activity,Button> col_delete;

    @FXML
    private Button AddActivity;

    @FXML
    private Button refresh;

    @FXML
    private Button returntomenu;
    @FXML
    private Label prompterror;
    @FXML
    private Label ImageFileName;

    private ObservableList<Activity> data;
    public final ServerUtils server;
    public final MainGameCtrl Mainctrl;
    public File ChosenImage;

    //constructor
    @Inject
    public AdminInterfaceCtrl(ServerUtils server, MainGameCtrl Mainctrl){
        this.server = server;
        this.Mainctrl = Mainctrl;
        this.col_cons = new TableColumn<>();
        this.col_activity = new TableColumn<>();
        this.col_id  = new TableColumn<>();
        this.databaseview = new TableView<>();
        this.col_update = new TableColumn<>();
        this.col_image = new TableColumn<>();
        this.col_delete = new TableColumn<>();
        this.AddActivity = new Button();
        this.AddImage = new Button();
        this.ActivityTitle = new TextField();
        this.ActivityTitle.setPromptText("Add Title");
        this.Consumption = new TextField("Consumption");
        this.Consumption.setPromptText("Add consumption");
        this.col_update.setText("Update Image");
        this.col_delete.setText("Delete activity");
        this.prompterror = new Label();
    }

    /**
     * Initializes the tableview with the corresponding values of the activity
     * @param location
     * @param resources
     *
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //initialize columns with values from db
        col_id.setCellValueFactory(a -> new SimpleLongProperty(a.getValue().getId()));
        col_activity.setCellValueFactory(a -> new SimpleStringProperty(a.getValue().getActivity()));
        col_cons.setCellValueFactory(a -> new SimpleIntegerProperty(a.getValue().getEnergyConsumption()));
        col_image.setCellValueFactory(a-> {
            ImageView images = new ImageView( new Image( new ByteArrayInputStream(   a.getValue().getImage()  )  ));
            images.setFitHeight(50);
            images.setFitWidth(80);
            SimpleObjectProperty d = new SimpleObjectProperty<>( images);
            return d;
        });


        addButtonToTable();
        loadData();
        editCols();


    }
    /**
     Adds the buttons to the update Tablecolumn, delete TableColumn

     Button functionality
     - Let's the user choose an image file to change the image of the activity
     Delete button
     - Let's the user delete the given activity from database


     */
    public void addButtonToTable(){
        Callback<TableColumn<Activity,Button>,TableCell<Activity,Button>> cellf = new Callback<TableColumn<Activity, Button>, TableCell<Activity, Button>>() {
            @Override
            public TableCell<Activity, Button> call(TableColumn<Activity, Button> param) {
                final TableCell<Activity,Button> cell = new TableCell<>(){
                    private final Button btn = new Button("Upload image");

                    {
                        //do the onaction for the button here
                        btn.setOnAction((ActionEvent) -> {
                            FileChooser fc = new FileChooser();
                            FileChooser.ExtensionFilter extfilt = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
                            fc.getExtensionFilters().add(extfilt);
                            File selected  = fc.showOpenDialog(Mainctrl.getPrimaryStage());
                            byte[] image;
                            try {
                                image = Files.readAllBytes(selected.toPath());
                                this.getTableView().getItems().get(this.getIndex()).setImage(image);

                                col_image.getTableView().getItems().set(this.getIndex(),this.getTableView().getItems().get(this.getIndex()));
                                System.out.println(server.updateActivity(this.getTableView().getItems().get(this.getIndex())));

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            //now image holds the selected image as byte array
                            //now set it the activity's image to this and send it to server to update
                            loadData();
                        });
                    }
                    @Override
                    public void updateItem(Button item, boolean empty){
                        super.updateItem(item,empty);
                        if(empty) {
                            setGraphic(null);
                        }else{
                            setGraphic(btn);
                        }
                    }
                };
                return cell;



            }

        };

        Callback<TableColumn<Activity,Button>,TableCell<Activity,Button>> cellfdelete = new Callback<TableColumn<Activity, Button>, TableCell<Activity, Button>>() {
            @Override
            public TableCell<Activity, Button> call(TableColumn<Activity, Button> param) {
                final TableCell<Activity,Button> cell = new TableCell<>(){
                    private final Button btn = new Button("Delete");

                    {
                        //deletes the activity based on id of the activity
                        btn.setOnAction((ActionEvent) -> {

                            server.deleteActivity(col_delete.getTableView().getItems().get(this.getIndex()).getId());
                            loadData();
                        });
                    }
                    @Override
                    public void updateItem(Button item, boolean empty){
                        super.updateItem(item,empty);
                        if(empty) {
                            setGraphic(null);
                        }else{
                            setGraphic(btn);
                        }
                    }
                };
                return cell;



            }

        };





        col_update.setCellFactory(cellf);
        col_delete.setCellFactory(cellfdelete);
        databaseview.getColumns().add(col_update);
        databaseview.getColumns().add(col_delete);
    }

    /**
     * Makes the columns editable, the activity is updated clientside upon comitting edit (pressing enter)
     * sends updated activity to database through corresponding ServerUtils method
     */
    public void editCols() {

        col_activity.setCellFactory(TextFieldTableCell.forTableColumn());
        col_activity.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setActivity(e.getNewValue());
            System.out.println(server.updateActivity(e.getTableView().getItems().get(e.getTablePosition().getRow())));
        });

        col_cons.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Number>() {
            @Override
            public String toString(Number object) {
                int id = object.intValue();
                return String.valueOf(id);
            }

            @Override
            public Number fromString(String string) {
                return Integer.parseInt(string);
            }
        }));
        col_cons.setOnEditCommit( e -> {
            //send update to database
            //gets the newly inputted data
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setEnergyConsumption(e.getNewValue().intValue());
            System.out.println(server.updateActivity(e.getTableView().getItems().get(e.getTablePosition().getRow())));

        });

        databaseview.setEditable(true);
    }

    /**
     * loads in activities from database
     * Called whenever an update or deletion or addition is made by the user
     */
    public void loadData(){
        this.prompterror.setText("");
        this.prompterror.setStyle("-fx-border-color: white");
        data = FXCollections.observableArrayList(server.getActivities());
        databaseview.setItems(data);
    }

    /**
     * Return the user to the main menu
     * Method through clicking on return button
     */
    public void returnmenu(){
        ChosenImage=null;
        this.ImageFileName.setText("File name");
        this.ActivityTitle.setText("Title");
        this.Consumption.setText("Consumption");
        Mainctrl.showMenu();
    }

    /**
     * This is the function that the Add Activity button does on action
     * This takes the related fields:
     * ActivityTitle
     * Consumption
     * ChosenImage
     * Checks if they are properly inputted with data
     * if they are:
     *      Create new activity and upload to database, then show new tableview with loadData()
     * else
     *      Prompt user through prompterror Label field to try inputting values again.
     */
    public void AddActivity(){


        if(ActivityTitle==null || ActivityTitle.getText().equals("") || ActivityTitle.getText().equals("Title")){
            this.prompterror.setText("Title field was not filled in correctly!");
            this.prompterror.setStyle("-fx-border-color: red");

            return;
        }
        if(Consumption==null || Consumption.getText().equals("") || Consumption.getText().equals("Consumption")){
            this.prompterror.setText("Consumption was filled in incorrectly!");
            this.prompterror.setStyle("-fx-border-color: red");
            return;
        }
        if(ChosenImage==null){
            this.prompterror.setText("Please choose an image file!");
            this.prompterror.setStyle("-fx-border-color: red");
            return;
        }
        try{
            byte[] image = Files.readAllBytes(ChosenImage.toPath());

            try{
                int cons = Integer.parseInt(Consumption.getText());
                Activity newa = new Activity(image,ActivityTitle.getText(),cons);

                server.updateActivity(newa);
            }catch (NumberFormatException e){
                this.prompterror.setText("Consumption was filled in incorrectly!");
                this.prompterror.setStyle("-fx-border-color: red");
                return;
            }

        }catch(Exception e){
            this.prompterror.setText("There was a problem with the image, please try again or choose other image");
            this.prompterror.setStyle("-fx-border-color: red");
            System.out.println("problem with image file");
            e.printStackTrace();
        }
        this.ActivityTitle.setText("Title");
        this.Consumption.setText("Consumption");
        loadData();


    }

    /**
     * Called when Choose Image button is clicked
     * Inititiates a file choosing window with .png .jpg .jpeg filtered image files
     * Upon choosing an image ChosenImage will hold the image.
     */
    public void AddImageForNewActivitiy(){


        FileChooser fc = new FileChooser();
        FileChooser.ExtensionFilter extfilt = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
        fc.getExtensionFilters().add(extfilt);
        File selected  = fc.showOpenDialog(Mainctrl.getPrimaryStage());
        //byte[] image = Files.readAllBytes(selected.toPath());
        ImageFileName.setText(selected.getName());
        this.ChosenImage = selected;

    }

}
