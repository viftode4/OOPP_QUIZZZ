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
package commons;


import commons.questions.PreciseQuestion;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

//CHECKSTYLE:OFF
public class RoundTest {

	@Test
	public void ConstructorTest() {
		Round r = new Round();
		assertNotNull(r);
	}

	@Test
	public void ConstructorTest2(){

		Round r1 = new Round(1,new PreciseQuestion());
		assertNotNull(r1);
	}
	@Test
	public void SetGetGameTest(){
		Round r = new Round();
		SinglePlayerGame spg = new SinglePlayerGame();
		r.setGame(spg);
		assertEquals(spg,r.getGame());
	}
	@Test
	public void SetGetQTest(){
		PreciseQuestion pq = new PreciseQuestion();
		Round r = new Round();
		r.setQuestion(pq);
		assertEquals(r.getQuestion(),pq);

	}
	@Test
	public void ToStringT(){
		Round r = new Round(1,new PreciseQuestion());
		assertEquals("Round{roundNum=1, question=null, time=8, game=null}",r.toString());
	}
	@Test
	public void getRoundNumberTest(){
		Round r = new Round(1,new PreciseQuestion());
		assertEquals(1,r.getRoundNum());
	}
}