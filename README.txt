First, run the program, and the program will check if you have a file named "text.txt", if there's no such file the program will create one and terminate.
After the first run, enter your requird text preceding your e and n and make sure you leave space between (e) and (n).
Then run the program again and the menu will show you three options, 1- is for encryption, 2- is for decryption(but you will need to have a file named "text.rsa"), and 3- to terminate the program.
Running the program and starting with option 1, will start encryption, then will create a file("text.rsa") and write the encrypted message in it, and then take you back to main menu.
Option 2 will start decryption then write it to "text.dec" file and then terminate immediatly without going back to the main menu.


Decryption1 - Brute-force with known (n):
For the first encrypted message, (n) was known so by that instead of factoring n and getting two prime numbers (p)&(q) then we subtract 1 from them (p-1)&(q-1) and then choosing a number (e) that is relatively prime to them, and after that gettong the inverse of e modulo (p-1)(q-1), I made my program to try every (d) from 2 up to 361.
First run of program i got an exception that terminated the execution of the program, so I made the program handel the exception by try catching an ArrayIndexOutOfBound exception because some values of (d) cause a problem with the mod function.
the program produced many values of (d) other than the one in the"p1.dec" file that decrypt the message successfully and those values are: 132961, 265561, 398161, 530761, 663361, 795961.

Decryption2 - Brute-force with (d) and (n) unkown: 
The seconed encrypted message (n) was unkown but it won't exceed 4 digits i.e. not upove 10000. So with this knowledge I made my pogram loop through every n from 7878 up to 999 and with everytime n is incrimented the program loops through all values of (d) from 2 up to (n), basically brute forcing, instead of trying many primes (p)(q) and getting (e) then getting (d) which is the inverse of e mod (p-1)(q-1). With the use of the decryption part of my project program only, but I encounterd the same error as the first decryption program (ArrayIndexOutOfBound), and just handeled it the same way.
When the program reached n = 9179 I got two other values of d: 161, 9137.
