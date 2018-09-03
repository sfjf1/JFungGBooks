# Design of this App
1. This is a command line application using Google Book API to search for books online.
2. This application uses maven as the build framework. All dependencies can be updated in the pom.xml.
3. Unit tests are written to test the basic functionalities.
# To Build:
```sh
$ cd <download directory>
$ mvn package
```
# Quickstart:
There is 1 mandatory argument to run this application which is '-f' for the file path of the bookshelf json.
```sh
$ java -cp target/JFungGBooks-1.0-SNAPSHOT-jar-with-dependencies.jar com.jfung.App -f /tmp/bookshelf.json
===================================
               Menu
===================================
1 : Search books
2 : Add book to bookshelf
3 : Load book from bookshelf
4 : Display books from bookshelf
5 : Save bookshelf to disk
6 : Quit
===================================
Please enter an option to proceed
```

# Use Case 1 (First time running)
1. Enter *1* at the prompt to "Search books"
2. Enter keywords (e.g. Digital Photography) at the second prompt
3. Enter *2* to "Add book to bookshelf"
4. Enter the bookID of one book to the prompt (bookID is the first left column)
5. Enter *4* to "Display books from bookshelf"
6. Enter *5* to "Save bookshelf to disk"
7. Repeat steps *3* to *6* to add another book to bookshelf

# Use Case 2 (Working with existing bookshelf)
1. Enter *3* to "Load book from bookshelf"
2. Repeat steps *1* to *6* in Use Case 1 to find books and add to bookshelf.

