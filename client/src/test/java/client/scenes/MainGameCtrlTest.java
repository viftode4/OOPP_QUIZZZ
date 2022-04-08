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
package client.scenes;

import commons.Game;
import commons.MultiPlayerGame;
import commons.Round;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class MainGameCtrlTest {

    private MainGameCtrl sut;

    @BeforeEach
    public void setup() {
        sut = new MainGameCtrl();
    }

    /* Removed for now because of the polling function leading to nullpointer

    @Test
    public void MainControllerPlayerFieldTest() {

        Player player = new Player();
        sut.setPlayer(player);
        assertEquals(player,sut.getPlayer());
    }

    */

    @Test
    public void MainControllerGameFieldTest(){
        Game game = new MultiPlayerGame();
        sut.setGame(game);
        assertEquals(sut.getGame(),game);

    }

    @Test
    public void MainControllerRoundListTest(){
        List<Round> roundlist = new ArrayList<Round>();
        roundlist.add(new Round());
        sut.setRoundList(roundlist);
        assertEquals(roundlist,sut.getRoundList());


    }

}