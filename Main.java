package sample;

import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polyline;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Optional;

import static java.lang.Math.abs;


public class Main extends Application {

    Stage frame;
    Button[] disc = new Button[10];
    Tower tower1, tower2, tower3;
    Response response = new Response();
    Label label = new Label();
    double x = 0, y = 0, temp1, temp2;
    int movesCounter = 0, duration = 5, disk = 3, count = 0, cv = 0;
    int[] solve = new int[1200];
    Boolean animation = false, remark = false;
    PathTransition transition = null;


    public void playFrame() {
        Pane pane = new Pane();
        ImageView back = new ImageView(new Image("Background.jpg"));
        back.relocate(0, 0);
        Pane discPane = setDisc();
        if (animation == false) {
            if (disk == 3) moveDisc1();
            else if (disk == 5) moveDisc2();
            else if (disk == 7) moveDisc3();
            else moveDisc4();
        }
        label.setText("Moves\n    " + Integer.toString(movesCounter));
        label.setLayoutX(775);
        label.setLayoutY(10);
        label.setFont(Font.font("Woodcut", 40));
        label.setStyle("-fx-text-fill:white");
        Button menu = new Button();
        menu.setStyle("-fx-background-color:transparent");
        menu.setGraphic(new ImageView(new Image("home.png")));
        menu.setLayoutX(25);
        menu.setLayoutY(25);
        menu.setMinSize(25, 25);
        menu.setMaxSize(60, 60);
        menu.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            Stage stage=(Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("alert.png"));
            alert.setHeaderText("Return to Main Menu?");
            alert.setContentText("All progress will be lost");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    transition.stop();
                    start(frame);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            } else {
                alert.close();
            }
        });
        Button restart = new Button();
        restart.setStyle("-fx-background-color:transparent");
        restart.setGraphic(new ImageView(new Image("restart.png")));
        restart.setLayoutX(25);
        restart.setLayoutY(110);
        restart.setMinSize(25, 25);
        restart.setMaxSize(60, 60);
        restart.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            Stage stage=(Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("alert.png"));
            alert.setHeaderText("Are you sure to restart?");
            alert.setContentText("All progress will be lost");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    transition.stop();
                    init();
                    playFrame();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            } else {
                alert.close();
            }
        });
        pane.getChildren().addAll(back, discPane, label, menu, restart);
        if (animation == true) {
            Button pause = new Button();
            pause.setStyle("-fx-background-color:transparent");
            pause.setGraphic(new ImageView(new Image("play.png")));
            Button fast = new Button();
            fast.setStyle("-fx-background-color:transparent");
            fast.setGraphic(new ImageView(new Image("fast.png")));
            Button slow = new Button();
            slow.setStyle("-fx-background-color:transparent");
            slow.setGraphic(new ImageView(new Image("slow.png")));
            pause.setLayoutX(450);
            pause.setLayoutY(525);
            fast.setLayoutX(750);
            fast.setLayoutY(525);
            slow.setLayoutX(186);
            slow.setLayoutY(525);
            pane.getChildren().addAll(pause, fast, slow);
            fast.setOnAction(e -> {

                duration--;
                if (duration <= 1) duration = 1;
            });
            slow.setOnAction(e -> {
                duration++;
                if (duration >= 10) duration = 10;
            });
            playAnimation(solve[cv++], solve[cv++]);
            pause.setOnAction(e -> {
                if (remark) {
                    transition.pause();
                    pause.setStyle("-fx-background-color:transparent");
                    pause.setGraphic(new ImageView(new Image("play.png")));
                    remark = false;
                } else {
                    transition.play();
                    pause.setStyle("-fx-background-color:transparent");
                    pause.setGraphic(new ImageView(new Image("pause.png")));
                    remark = true;
                }
            });

        }
        frame.setScene(new Scene(pane, 1000, 600));
        frame.setResizable(false);
        frame.show();
    }

    public Pane setDisc() {
        Pane pane = new Pane();
        for (int i = 1; i <= disk; i++) {
            disc[i] = new Button();
            tower1.add(i);
            disc[i].setLayoutX(tower1.X());
            disc[i].setLayoutY(tower1.Y());
            disc[i].setMinSize(180 - i * 14, 18);
            disc[i].setMaxSize(180 - i * 14, 18);
            disc[i].setStyle("-fx-background-color:gray");
            pane.getChildren().add(disc[i]);
        }
        return pane;
    }

    public Boolean checkMovable(int discNo) {
        if (tower1.lastDisc() == discNo) {
            return true;
        } else if (tower2.lastDisc() == discNo) {
            return true;
        } else if (tower3.lastDisc() == discNo) {
            return true;
        } else return false;
    }

    public void moveDisc1() {
        disc[1].setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                temp1 = disc[1].getLayoutX();
                temp2 = disc[1].getLayoutY();
                disc[1].setCursor(Cursor.MOVE);
            }
        });
        disc[1].setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (checkMovable(1) == true) {
                    set(mouseEvent, 1);
                    disc[1].setLayoutX(x);
                    disc[1].setLayoutY(y);
                }
                disc[1].setCursor(Cursor.HAND);
            }
        });
        disc[1].setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (checkMovable(1) == true) {
                    disc[1].setLayoutX(mouseEvent.getSceneX());
                    disc[1].setLayoutY(mouseEvent.getSceneY());
                }
            }
        });
        disc[2].setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                temp1 = disc[2].getLayoutX();
                temp2 = disc[2].getLayoutY();
                disc[2].setCursor(Cursor.MOVE);
            }
        });
        disc[2].setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (checkMovable(2) == true) {
                    set(mouseEvent, 2);
                    disc[2].setLayoutX(x);
                    disc[2].setLayoutY(y);
                }

                disc[2].setCursor(Cursor.HAND);
            }
        });
        disc[2].setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (checkMovable(2) == true) {
                    disc[2].setLayoutX(mouseEvent.getSceneX());
                    disc[2].setLayoutY(mouseEvent.getSceneY());
                }
            }
        });
        disc[3].setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                temp1 = disc[3].getLayoutX();
                temp2 = disc[3].getLayoutY();
                disc[3].setCursor(Cursor.MOVE);
            }
        });
        disc[3].setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (checkMovable(3) == true) {
                    set(mouseEvent, 3);
                    disc[3].setLayoutX(x);
                    disc[3].setLayoutY(y);
                }
                disc[3].setCursor(Cursor.HAND);
                if (disk == 3 && (tower3.finished(disk) == true || tower2.finished(disk) == true)) {
                    response.Solver(movesCounter, disk);
                    try {
                        start(frame);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        disc[3].setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (checkMovable(3) == true) {
                    disc[3].setLayoutX(mouseEvent.getSceneX());
                    disc[3].setLayoutY(mouseEvent.getSceneY());
                }
            }
        });
    }

    public void moveDisc2() {
        moveDisc1();
        disc[4].setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                temp1 = disc[4].getLayoutX();
                temp2 = disc[4].getLayoutY();
                disc[4].setCursor(Cursor.MOVE);
            }
        });
        disc[4].setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (checkMovable(4) == true) {
                    set(mouseEvent, 4);
                    disc[4].setLayoutX(x);
                    disc[4].setLayoutY(y);
                }

                disc[4].setCursor(Cursor.HAND);
            }
        });
        disc[4].setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (checkMovable(4) == true) {
                    disc[4].setLayoutX(mouseEvent.getSceneX());
                    disc[4].setLayoutY(mouseEvent.getSceneY());
                }
            }
        });
        disc[5].setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                temp1 = disc[5].getLayoutX();
                temp2 = disc[5].getLayoutY();
                disc[5].setCursor(Cursor.MOVE);
            }
        });
        disc[5].setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (checkMovable(5) == true) {
                    set(mouseEvent, 5);
                    disc[5].setLayoutX(x);
                    disc[5].setLayoutY(y);
                }

                disc[5].setCursor(Cursor.HAND);
                if (disk == 5 && (tower3.finished(disk) == true || tower2.finished(disk) == true)) {
                    response.Solver(movesCounter, disk);
                    try {
                        start(frame);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        disc[5].setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (checkMovable(5) == true) {
                    disc[5].setLayoutX(mouseEvent.getSceneX());
                    disc[5].setLayoutY(mouseEvent.getSceneY());
                }
            }
        });
    }

    public void moveDisc3() {
        moveDisc2();
        disc[6].setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                temp1 = disc[6].getLayoutX();
                temp2 = disc[6].getLayoutY();
                disc[6].setCursor(Cursor.MOVE);
            }
        });
        disc[6].setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (checkMovable(6) == true) {
                    set(mouseEvent, 6);
                    disc[6].setLayoutX(x);
                    disc[6].setLayoutY(y);
                }

                disc[6].setCursor(Cursor.HAND);
            }
        });
        disc[6].setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (checkMovable(6) == true) {
                    disc[6].setLayoutX(mouseEvent.getSceneX());
                    disc[6].setLayoutY(mouseEvent.getSceneY());
                }
            }
        });
        disc[7].setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                temp1 = disc[7].getLayoutX();
                temp2 = disc[7].getLayoutY();
                disc[7].setCursor(Cursor.MOVE);
            }
        });
        disc[7].setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (checkMovable(7) == true) {
                    set(mouseEvent, 7);
                    disc[7].setLayoutX(x);
                    disc[7].setLayoutY(y);
                }
                disc[7].setCursor(Cursor.HAND);
                if (disk == 7 && (tower3.finished(disk) == true || tower2.finished(disk) == true)) {
                    response.Solver(movesCounter, disk);
                    try {
                        start(frame);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        disc[7].setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (checkMovable(7) == true) {
                    disc[7].setLayoutX(mouseEvent.getSceneX());
                    disc[7].setLayoutY(mouseEvent.getSceneY());
                }
            }
        });
    }

    public void moveDisc4() {
        moveDisc3();
        disc[8].setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                temp1 = disc[8].getLayoutX();
                temp2 = disc[8].getLayoutY();
                disc[8].setCursor(Cursor.MOVE);
            }
        });
        disc[8].setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (checkMovable(8) == true) {
                    set(mouseEvent, 8);
                    disc[8].setLayoutX(x);
                    disc[8].setLayoutY(y);
                }

                disc[8].setCursor(Cursor.HAND);
            }
        });
        disc[8].setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (checkMovable(8) == true) {
                    disc[8].setLayoutX(mouseEvent.getSceneX());
                    disc[8].setLayoutY(mouseEvent.getSceneY());
                }
            }
        });
        disc[9].setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                temp1 = disc[9].getLayoutX();
                temp2 = disc[9].getLayoutY();
                disc[9].setCursor(Cursor.MOVE);
            }
        });
        disc[9].setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (checkMovable(9) == true) {
                    set(mouseEvent, 9);
                    disc[9].setLayoutX(x);
                    disc[9].setLayoutY(y);
                }

                disc[9].setCursor(Cursor.HAND);
                if (disk == 9 && (tower3.finished(disk) == true || tower2.finished(disk) == true)) {
                    response.Solver(movesCounter, disk);
                    try {
                        start(frame);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        disc[9].setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (checkMovable(9) == true) {
                    disc[9].setLayoutX(mouseEvent.getSceneX());
                    disc[9].setLayoutY(mouseEvent.getSceneY());
                }
            }
        });
    }

    public void set(MouseEvent mouseEvent, int discNo) {
        if ((abs(tower1.X() - mouseEvent.getSceneX()) < abs(tower2.X() - mouseEvent.getSceneX())) && ((abs(tower1.X() - mouseEvent.getSceneX()) < abs(tower3.X() - mouseEvent.getSceneX())))) {
            {
                if (discNo > tower1.lastDisc() || tower1.lastDisc() == 0) {
                    tower1.remove(discNo);
                    tower2.remove(discNo);
                    tower3.remove(discNo);
                    tower1.add(discNo);
                    x = tower1.X();
                    y = tower1.Y();
                    movesCounter++;
                    label.setText("Moves\n    " + Integer.toString(movesCounter));
                } else {
                    x = temp1;
                    y = temp2;
                }
            }
        } else if ((abs(tower2.X() - mouseEvent.getSceneX()) < abs(tower1.X() - mouseEvent.getSceneX())) && ((abs(tower2.X() - mouseEvent.getSceneX()) < abs(tower3.X() - mouseEvent.getSceneX())))) {
            {
                if (discNo > tower2.lastDisc() || tower2.lastDisc() == 0) {
                    tower1.remove(discNo);
                    tower2.remove(discNo);
                    tower3.remove(discNo);
                    tower2.add(discNo);
                    x = tower2.X();
                    y = tower2.Y();
                    movesCounter++;
                    label.setText("Moves\n    " + Integer.toString(movesCounter));
                } else {
                    x = temp1;
                    y = temp2;
                }
            }
        } else {
            if (discNo > tower3.lastDisc() || tower3.lastDisc() == 0) {
                tower1.remove(discNo);
                tower2.remove(discNo);
                tower3.remove(discNo);
                tower3.add(discNo);
                x = tower3.X();
                y = tower3.Y();
                movesCounter++;
                label.setText("Moves\n    " + Integer.toString(movesCounter));
            } else {
                x = temp1;
                y = temp2;
            }
        }
    }

    public void solveTower(int discNo, int start, int auxiliary, int end) {
        if (discNo == 1) {
            solve[count++] = start;
            solve[count++] = end;
        } else {
            solveTower(discNo - 1, start, end, auxiliary);
            solve[count++] = start;
            solve[count++] = end;
            solveTower(discNo - 1, auxiliary, start, end);
        }
    }

    public void playAnimation(int start, int end) {
        int d = 0;
        Double x = null, y = null, x1 = null, y1 = null;
        if (start == 1) {
            d = tower1.lastDisc();
            x1 = tower1.X() - (178 - d * 14) / 2;
            y1 = tower1.Y() - 7;
            tower1.remove(d);

        } else if (start == 2) {
            d = tower2.lastDisc();
            x1 = tower2.X() - (178 - d * 14) / 2;
            y1 = tower2.Y() - 7;
            tower2.remove(d);

        } else if (start == 3) {
            d = tower3.lastDisc();
            x1 = tower3.X() - (178 - d * 14) / 2;
            y1 = tower3.Y() - 7;
            tower3.remove(d);
        }
        if (end == 1) {
            tower1.add(d);
            x = tower1.X();
            y = tower1.Y();
        } else if (end == 2) {
            tower2.add(d);
            x = tower2.X();
            y = tower2.Y();

        } else if (end == 3) {
            tower3.add(d);
            x = tower3.X();
            y = tower3.Y();
        }
        disc[d].setLayoutY(y1 + 7);
        disc[d].setLayoutX(x1 + (178.0 - d * 14) / 2);
        transition.setNode(disc[d]);
        transition.setDuration(Duration.seconds(duration));
        Polyline path = new Polyline();
        path.getPoints().addAll(new Double[]{
                (178.0 - d * 14) / 2, 7.0,
                (178.0 - d * 14) / 2, -(y1 - 150.0),
                x - x1, -(y1 - 150.0),
                x - x1, y - y1
        });
        transition.setPath(path);
        transition.play();
        if (remark == false) transition.pause();
        transition.setOnFinished(e -> {
            movesCounter++;
            label.setText("Moves\n    " + Integer.toString(movesCounter));
            if (cv >= count) {
                response.finished();
            } else playAnimation(solve[cv++], solve[cv++]);

        });
    }

    public void init() {
        movesCounter = 0;
        duration = 5;
        cv = 0;
        remark = false;
        transition = new PathTransition();
        tower1 = new Tower(1);
        tower2 = new Tower(2);
        tower3 = new Tower(3);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        frame = primaryStage;
        primaryStage.setOnCloseRequest(e -> {
            e.consume();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            Stage stage=(Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("alert.png"));
            alert.setTitle("Confirm Exit");
            alert.setHeaderText("Exiting to Desktop");
            alert.setContentText("Are you sure?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                alert.close();
                primaryStage.close();
            } else {
                alert.close();
            }
        });
        init();
        primaryStage.getIcons().add(new Image("icon.png"));
        count = 0;
        animation = false;
        Pane pane = new Pane();
        ImageView back = new ImageView(new Image("welcomeBackground.jpg"));
        pane.getChildren().add(back);
        Button play = new Button("Play");
        Button tutorial = new Button("Tutorial");
        ChoiceBox<String> difficulty = new ChoiceBox<String>();
        difficulty.getItems().addAll("Difficulty: Easy", "Difficulty: Medium", "Difficulty: Hard", "Difficulty: Very Hard");
        if (disk == 3) difficulty.setValue("Difficulty: Easy");
        else if (disk == 5) difficulty.setValue("Difficulty: Medium");
        else if (disk == 7) difficulty.setValue("Difficulty: Hard");
        else if (disk == 9) difficulty.setValue("Difficulty: Very Hard");
        Button score = new Button("Scores");
        Button Exit = new Button("Exit");
        Button info = new Button();
        info.setGraphic(new ImageView(new Image("info.png")));
        info.setStyle("-fx-background-color:transparent");
        GridPane g_pane = new GridPane();
        g_pane.setVgap(10);
        g_pane.setHgap(10);
        play.setFont(Font.font("Cartoon", 18));
        tutorial.setFont(Font.font("Cartoon", 18));
        difficulty.setStyle("-fx-font-family:Cartoon;-fx-font-size:18px");
        score.setFont(Font.font("Cartoon", 18));
        Exit.setFont(Font.font("Cartoon", 18));
        g_pane.add(play, 40, 15);
        g_pane.add(tutorial, 40, 20);
        g_pane.add(difficulty, 40, 25);
        g_pane.add(score, 40, 30);
        g_pane.add(Exit, 40, 35);
        info.setLayoutX(950);
        info.setLayoutY(550);
        pane.getChildren().addAll(g_pane, info);
        primaryStage.setScene(new Scene(pane, 1000, 600));
        primaryStage.setResizable(false);
        primaryStage.show();
        play.setOnAction(e -> {
            playFrame();
        });
        tutorial.setOnAction(e -> {
            animation = true;
            solveTower(disk, 1, 2, 3);
            playFrame();
        });
        difficulty.setOnAction(e -> {
            String choice = difficulty.getValue().toString();
            if (choice == "Difficulty: Easy") {
                disk = 3;
            } else if (choice == "Difficulty: Medium") {
                disk = 5;
            } else if (choice == "Difficulty: Hard") {
                disk = 7;
            } else if (choice == "Difficulty: Very Hard") {
                disk = 9;
            }
        });
        score.setOnAction(e -> {
            response.showSolver();
        });
        Exit.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            Stage stage=(Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("alert.png"));
            alert.setTitle("Exit");
            alert.setHeaderText("EXIT TO WINDOWS??");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                alert.close();
                primaryStage.close();
            } else {
                alert.close();
            }
        });
        info.setOnAction(e -> {
            response.developer();
        });
    }

    public static void main(String args) {
        launch(args);
    }
}
