/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package client;

import client.scenes.*;
import client.scenes.multiplayer.*;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URISyntaxException;

import static com.google.inject.Guice.createInjector;

public class Main extends Application {

    private static final Injector INJECTOR = createInjector(new MyModule());
    private static final MyFXML FXML = new MyFXML(INJECTOR);

    public static void main(String[] args) throws URISyntaxException, IOException {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        var menu = FXML.load(MainMenuWindowCtrl.class, "client", "scenes", "MainMenuWindow.fxml");
        var name_choice = FXML.load(NameChoiceWindowCtrl.class, "client", "scenes", "multiplayer", "NameChoiceWindow.fxml");
        var mainCtrl = INJECTOR.getInstance(MainGameCtrl.class);
        var lobby = FXML.load(LobbyWindowCtrl.class, "client", "scenes","multiplayer", "LobbyWindow.fxml");
        var singlePlayerNameChoice = FXML.load(SinglePlayerNameChoiceWindowCtrl.class,"client", "scenes", "SinglePlayerNameChoiceWindow.fxml");
        var howToPlay = FXML.load(HowToPlayWindowCtrl.class, "client", "scenes", "HowToPlayWindow.fxml");
        var singlePlayerPreciseQWindow = FXML.load(SingleplayerPreciseQGameWindowCtrl.class, "client", "scenes", "SingleplayerPreciseQGameWindow.fxml");
        var singlePlayerGuessQWindow = FXML.load(SingleplayerGuessQGameWindowCtrl.class, "client", "scenes", "SingleplayerGuessQGameWindow.fxml");
        var singlePlayerComparisonQWindow = FXML.load(SingleplayerComparisonQGameWindowCtrl.class, "client", "scenes", "SingleplayerComparisonQGameWindow.fxml");
        var multiplayerEnd = FXML.load(MultiplayerEndWindowCtrl.class, "client", "scenes","multiplayer", "MultiplayerEndWindow.fxml");
        var singlePlayerEnd = FXML.load(SinglePlayerEndWindowCtrl.class, "client", "scenes", "SinglePlayerEndWindow.fxml");
        var pastGames = FXML.load(PastGamesCtrl.class, "client", "scenes", "PastGamesWindow.fxml");
        var lobbyChoice = FXML.load(LobbyChoiceWindowCtrl.class, "client", "scenes","multiplayer", "LobbyChoiceWindow.fxml");
        var adminIntfc = FXML.load(AdminInterfaceCtrl.class,"client","scenes","AdminInterface.fxml");
        var multiPlayerPreciseQWindow = FXML.load(MultiplayerPreciseQGameWindowCtrl.class,"client","scenes", "multiplayer","MultiplayerPreciseQGameWindow.fxml");
        var multiPlayerGuessQWindow = FXML.load(MultiplayerGuessQGameWindowCtrl.class,"client","scenes", "multiplayer","MultiplayerGuessQGameWindow.fxml");
        var multiPlayerComparisonQWindow = FXML.load(MultiplayerComparisonQGameWindowCtrl.class,"client","scenes", "multiplayer","MultiplayerComparisonQGameWindow.fxml");
        var multiplayerLead = FXML.load(MultiplayerLeaderboardWindowCtrl.class, "client", "scenes", "multiplayer","MultiplayerLeaderboardWindow.fxml");

        System.out.println(Thread.currentThread().getName());
        mainCtrl.initialize(primaryStage,
                menu,
                name_choice,
                lobby,
                singlePlayerNameChoice,
                howToPlay,
                singlePlayerPreciseQWindow,
                singlePlayerGuessQWindow,
                singlePlayerComparisonQWindow,
                multiplayerEnd,
                singlePlayerEnd,
                pastGames,
                lobbyChoice,
                adminIntfc,
                multiplayerLead,
                multiPlayerPreciseQWindow,
                multiPlayerComparisonQWindow,
                multiPlayerGuessQWindow);

        primaryStage.setOnCloseRequest(e -> {
            e.consume();
            shutdown(e, lobby, mainCtrl, primaryStage);
        });
    }

    public void shutdown(WindowEvent e, Pair<LobbyWindowCtrl, Parent> lobby, MainGameCtrl mainCtrl, Stage primaryStage) {
        Alert alert = new Alert(Alert.AlertType.NONE, "Are you sure you want to exit?", ButtonType.YES, ButtonType.NO);
        if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
            lobby.getKey().stop();
            mainCtrl.stopGameThread();
            // you may need to close other windows or replace this with Platform.exit();
            primaryStage.close();
            Platform.exit();
        }
    }
}
