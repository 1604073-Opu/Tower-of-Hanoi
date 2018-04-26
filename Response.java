package sample;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.*;

public class Response implements Serializable {
    String Name;

    public void Solver(int move, int lavel) {
        Stage frame = new Stage();
        frame.getIcons().add(new Image("icon.png"));
        frame.setResizable(false);
        ImageView imageView = new ImageView(new Image("congrats.jpg"));
        Button ok = new Button("OK");
        ok.setLayoutY(230);
        ok.setLayoutX(330);
        ok.setMaxSize(50, 30);
        ok.setMinSize(50, 20);
        TextField name = new TextField();
        Label label = new Label("Enter Your Name:");
        name.setLayoutX(115);
        name.setLayoutY(200);
        label.setLayoutX(20);
        label.setLayoutY(203);
        Pane pane = new Pane();
        pane.getChildren().addAll(imageView, ok, label, name);
        frame.setScene(new Scene(pane, 400, 250));
        ok.setOnAction(e -> {
            Name = name.getText();
            BufferedWriter writer = null;
            try {
                writer = new BufferedWriter(new FileWriter("C:\\Users\\User\\Solver.txt", true));
                writer.append(Name + "\n");
                if (lavel == 3) Name = "Easy";
                else if (lavel == 5) Name = "Medium";
                else if (lavel == 7) Name = "Hard";
                else if (lavel == 9) Name = "Very Hard";
                writer.append(Name + "\n");
                writer.append((Integer.toString(move)) + "\n");
                writer.close();
            } catch (IOException e1) {
                System.out.println("ERROR");
                e1.printStackTrace();
            }
            frame.close();
            return;
        });
        frame.showAndWait();
    }

    public void showSolver() {
        Sorting s = new Sorting();
        s.sort();
        Stage frame = new Stage();
        frame.getIcons().add(new Image("icon.png"));
        Pane mainPane = new Pane();
        frame.setResizable(false);
        ScrollPane pane = new ScrollPane();
        GridPane grid = new GridPane();
        Label[] names = new Label[1000];
        grid.setVgap(5);
        grid.setHgap(70);
        names[0] = new Label("Player Name");
        names[0].setFont(Font.font("CAC Moose",14));
        grid.add(names[0], 1, 0);
        names[1] = new Label("Difficulty");
        names[1].setFont(Font.font("CAC Moose",14));
        grid.add(names[1], 2, 0);
        names[2] = new Label("Move");
        names[2].setFont(Font.font("CAC Moose",14));
        grid.add(names[2], 3, 0);
        int i = 3;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("C:\\Users\\User\\Solver.txt"));
            while ((Name = reader.readLine()) != null) {
                System.out.println(Name);
                names[i] = new Label(Name);
                grid.add(names[i], 1, i + 2);
                i++;
                Name = reader.readLine();
                names[i] = new Label(Name);
                grid.add(names[i], 2, i + 1);
                i++;
                Name = reader.readLine();
                names[i] = new Label(Name);
                grid.add(names[i], 3, i);
                i++;
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        pane.setContent(grid);
        pane.setPrefSize(415, 350);
        Button ok = new Button("OK");
        ok.setLayoutX(175);
        ok.setLayoutY(370);
        ok.setMaxSize(50, 30);
        ok.setMinSize(50, 30);
        mainPane.getChildren().addAll(ok, pane);
        frame.setScene(new Scene(mainPane, 400, 400));
        ok.setOnAction(e -> {
            frame.close();
            return;
        });
        frame.showAndWait();
    }

    public void finished() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Stage stage=(Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("alert.png"));
        alert.setTitle("Info");
        alert.setHeaderText("Animation Ended");
        alert.setContentText("Thanks for watching");
        alert.show();
    }
    public void  developer()
    {
        Stage frame = new Stage();
        frame.getIcons().add(new Image("icon.png"));
        frame.setTitle("Developer Info");
        frame.setResizable(false);
        Button ok = new Button("OK");
        ok.setLayoutY(150);
        ok.setLayoutX(180);
        ok.setMaxSize(50, 30);
        ok.setMinSize(50, 20);
        Label label = new Label("মোঃ নাহিদুল ইসলাম অপু \n কম্পিউটার সায়েন্স এন্ড ইঞ্জিনিয়ারিং বিভাগ \n চট্টগ্রাম প্রকৌশল ও প্রযুক্তি বিশ্ববিদ্যালয় \n opu.nahidul@gmail.com");
        label.setStyle("-fx-font-size:20px;-fx-text-fill:white");
        label.setLayoutX(20);
        label.setLayoutY(20);
        Pane pane = new Pane();
        pane.getChildren().addAll(new ImageView(new Image("dev.jpg")),ok, label);
        frame.setScene(new Scene(pane,400,200));
        ok.setOnAction(e->{
            frame.close();
        });
        frame.show();
    }
}
