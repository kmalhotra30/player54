# Player 54 EC Assignment

This repository consists of the modules which were developed by Practical Assignment Group 54 for Evolutionary Computing Assignment (MSc Artificial Intelligence Sept-Oct 2018).

The modules have been prepared with 2 different data structure representations. The first module uses ArrayList and Pairs as Data Structures but failed to run online on http://mac360.few.vu.nl:8080/EC_BB_ASSIGNMENT/. We are under the assumption that the compatibility of javafx Pair was the reason behind the failiure. However this module has been successfully executed on local machine for testing and results.

The second module uses classical data structures and has executed online (http://mac360.few.vu.nl:8080/EC_BB_ASSIGNMENT/) successfully.

# Executing the code :

### Module 1
- The preqrequiste to run this module is javafx. On Ubuntu 16.04 , openjfx was installed to fulfill the requirement. The command to install openjfx is as follows :  
  
    ```sh
    $ sudo apt-get install openjfx
    ```
 - This module consists of 3 submodules / sub folders :
    -   Naive - This contains the code for Naive Evolutionary Algorithm.
    -   DE - This contains the code for Differential Evolution.
    -   IM - This contains the code for Island Model over Naive EA. This sub module has been used only for Katsuura and hence the parameters are hardcoded for Katsuura only.  Parameters were varied during tuning and testing.

### Module 2

This module consists of 2 submodules / sub folders :
-   Naive - This contains the code for Naive Evolutionary Algorithm.
-   DE - This contains the code for Differential Evolution.
    

### Notes

All subfolders/sub modules contain a text file `commands.txt` which comprises of commands to compile the java files and to build jars.

The java specifications used for programming are as follows :-

-   Java 8
-   OpenJdk version : 1.8.0_181
-   Openjfx (For module 1)
-   OS : Ubuntu 16.04

All subfolders contain compiled submission.jar.


