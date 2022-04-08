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
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Scopes;

public class MyModule implements Module {

    @Override
    public void configure(Binder binder) {
        binder.bind(MainGameCtrl.class).in(Scopes.SINGLETON);
        binder.bind(NameChoiceWindowCtrl.class).in(Scopes.SINGLETON);
        binder.bind(MainMenuWindowCtrl.class).in(Scopes.SINGLETON);
        binder.bind(MultiplayerGameWindowCtrl.class).in(Scopes.SINGLETON);
        binder.bind(LobbyWindowCtrl.class).in(Scopes.SINGLETON);
        binder.bind(PastGamesCtrl.class).in(Scopes.SINGLETON);
        binder.bind(MultiplayerEndWindowCtrl.class).in(Scopes.SINGLETON);
        binder.bind(SinglePlayerEndWindowCtrl.class).in(Scopes.SINGLETON);
        binder.bind(SingleplayerPreciseQGameWindowCtrl.class).in(Scopes.SINGLETON);
        binder.bind(SingleplayerGuessQGameWindowCtrl.class).in(Scopes.SINGLETON);
        binder.bind(SingleplayerComparisonQGameWindowCtrl.class).in(Scopes.SINGLETON);
        binder.bind(SinglePlayerNameChoiceWindowCtrl.class).in(Scopes.SINGLETON);
        binder.bind(AdminInterfaceCtrl.class).in(Scopes.SINGLETON);
        binder.bind(MultiplayerLeaderboardWindowCtrl.class).in(Scopes.SINGLETON);
    }
}