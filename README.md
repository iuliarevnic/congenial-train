# congenial-train

This repository encompasses my solution to the DDROIDD Summer Internship 2022 take-home backend coding challenge.
It is a console application which can be started by running the main method in the Main class. This shows a menu allowing the user to do the following:

- see the complete list of available products
- see the complete list of available offers
- add a product to the shopping cart
- proceed to checkout and see a detailed invoice
- exit the application

Each aforementioned option has a number, and the user is asked to type the number of the option they want to select. 

The application is structured into multiple packages:
- model, containing  the classes defining the problem entities
- service, where the computations happen
- controller, which links the service and the UI
- view, which handles the console interface and the user input
- test, which provides a few unit tests targeting the invoice computation for different shopping cart contents

