# Java-Minesweeper
This project features a version of Minesweeper inplemented in Java. The game is built using the JGameGrid package.

The project features multithreading in order to improve performance. This is achieved using the wait method and calling the notify method to wake waiting threads. An example of this is in the runGame method in the Minesweeper class, where this method is stopped using the wait method, and woken each time the mouse is clicked, meaning the win condition is only checked when a mouse event occurs instead of busy-waiting. Similarly, the StatusBar operates on its own thread and refreshes twice per second or when a flag is placed, instead of constantly calculating the game duaration. The Driver is also operated as a Thread, meaning a new game can be started by stopping the old thread and starting a new Driver thread.

JSON files are used to store best scores. Using JSON as opposed to a 2D data storage format such as a csv means that top scores can be stored for each possible configuration of board height, width, and number of mines, and the file is only expanded when a new board configuration is used. The Google Gson package was used to read the JSON file into a Java object and vice versa.

Awt was used to create the in-game menu, as well as swing being used to create the main menu. This includes buttons and text fields to choose the board configuration and event listeners for each of these.

The JGameGrid library allowed the game to be developed relatively quickly, however as the library is primarily intended for education it has certain performance drawbacks, most notably the poor responsiveness of mouse inputs. As such, a potential extension of this project would be to migrate it to a different engine/GUI.

## Dependencies
- Aegidius Pluess JGameGrid
- Google Gson

## Authors
- Joshua Linehan
