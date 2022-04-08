## What is this all about?

The Quizzzz project is a quiz application that test players' knowledge on the energy consumption of 
a wide variety of activities. One of the most prominent modern world problems is a lack of environmental
awareness. Every kWH of energy spent leaves its carbon footprint on the world, which affects the environment
greatly. Hence, the goal of this project is to raise awareness regarding the energy costs, and show people
how much we can hurt the world sometimes with our over-consumption habits. 

## The Team

| Profile Picture | Name | Email |
|---|---|---|
| ![](https://eu.ui-avatars.com/api/?name=Vlad+George+Iftode&length=4&size=50&color=DDD&background=777&font-size=0.325) | Vlad-George Iftode | V.G.Iftode@student.tudelft.nl |
| ![](https://eu.ui-avatars.com/api/?name=Efe+Unluyurt&length=3&size=50&color=DDD&background=777&font-size=0.325) | Efe Ünlüyurt | E.Unluyurt@student.tudelft.nl |
| ![](https://eu.ui-avatars.com/api/?name=Vladimir+Pavlov&length=4&size=50&color=DDD&background=26FADD&font-size=0.325) | Vladimir Pavlov | V.V.Pavlov-1@student.tudelft.nl |
| ![](https://eu.ui-avatars.com/api/?name=Zanyar+Ogurlu&length=4&size=50&color=DDD&background=26FADD&font-size=0.325) | Zanyar Ogurlu | Z.Ogurlu@student.tudelft.nl |
| ![](https://eu.ui-avatars.com/api/?name=Simon+Kasdorp&length=4&size=50&color=DDD&background=777&font-size=0.325) | Simon Kasdorp | S.A.Kasdorp@student.tudelft.nl |
| ![](https://eu.ui-avatars.com/api/?name=Aszalós+Arpad&length=4&size=50&color=DDD&background=777&font-size=0.325) | Aszalós Árpád | aszalos@student.tudelft.nl |
<!-- Instructions (remove once assignment has been completed -->
<!-- - Add (only!) your own name to the table above (use Markdown formatting) -->
<!-- - Mention your *student* email address -->
<!-- - Preferably add a recognizable photo, otherwise add your GitLab photo -->
<!-- - (please make sure the photos have the same size) --> 

## How to run it

1. Copy our project from gitlab by running the following script on the command prompt


`git clone git@gitlab.ewi.tudelft.nl:cse1105/2021-2022/team-repositories/oopp-group-48/repository-template.git`

2. Open Intellij CE and add the following line to the VM options


`--module-path="%homepath%\JavaFX\javafx-sdk-17.0.2\lib" --add-modules=javafx.controls,javafx.media,javafx.fxml`

3. Also, inside Intellij, add the JavaFX library to the project structure by navigating to 

`File/Project Structure/Libraries`

4. Possible intermediate step: Delete the *quizzzz.lock.db* and *quizzzz.mv.db* files in case they appear in the project

5. Open the server folder, direct to the main class and run this file. The server should start

6. Open a terminal window in Intellij and execute command

`./gradlew run`

7. To run multiple clients at the same time, simply open new terminal windows and execute the command from *step 6*

8. Lead to the game page, the logo for which we designed ourselves. Enjoy our Quizzzz game!

`Note: Server must be run before the client, otherwise exceptions will occur`
