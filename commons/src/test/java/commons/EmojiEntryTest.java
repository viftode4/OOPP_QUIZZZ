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


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

//CHECKSTYLE:OFF
public class EmojiEntryTest {


	@Test
	public void ConstructorTest()
	{
		EmojiEntry ee = new EmojiEntry("username", "Emoji1");
		assertNotNull(ee);
	}
	@Test
	public void SetGetTest(){
		EmojiEntry ee = new EmojiEntry("username", "Emoji1");
		assertEquals(ee.getEmoji(),"Emoji1");
		assertEquals(ee.getUsername(),"username");
		ee.setEmoji("Emoji2");
		ee.setUsername("username1");
		assertEquals(ee.getUsername(),"username1");
		assertEquals(ee.getEmoji(),"Emoji2");
	}
}