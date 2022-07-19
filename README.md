# StarWarsMainDetails

 StarWras App for getting actors and characters list, and films list.

## Overview:
The app developed with Kotlin on Android Studio IDE
App based on the Jetpack libraries, navigation components and Material Design components. Also, can handle configuration changes like rotation and day/night theme.
MVI (MVVM) architecture - each UI event return ViewState wrapped by his state (Loading, Success, Error).


## Tools and 3rd part libraries:
PagingData Adapter with AsyncListDiffer (animate and optimise performance for list operations)
Pagination support
Coroutines and Flow for background operations inside the repository pattern.
Observing LiveData for UI changes.
DataStore - for saving user preferences (such as filter option)
ViewBinding - for safe and fast views reference


## Components:
* MainActivity:
hold main lists fragments with shared ViewModel
* ListFragment:
Listening task is on background thread with Flow that emits people data whenever change occurred.
Option to search people from list.
Listening task is on background thread with Flow that emits films data whenever change occurred.
User can tap on people and open his detailes in new fragment
* DetailsFragment
Present the selcted people details
User can share this screes as PDF file to social media
