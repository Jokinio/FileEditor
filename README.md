# ReplaceStrings
Automated String Replaces on files that contains text (including large files) using Regex. Reading from XML the old and new strings(regex) that will be replaced. Creating a new folder with the new content.

1) Needs java 11(java runtime) to run the app.
2) There is an XML file that you have to modify and add all the changes you want to be performed (oldString (regex) -> newString (regex)).
3) The XML and the Editor.exe have to be in the same folder (same place), because the app reads the xml file.
4) XML explanation: 
The xml has keys and changes. Every key has its changes. 
The key data represents the string (propably some ID) that has to be found in a file,
in order to make the changes below. The app searches the keys one by one, if the key is found, then it makes the changes, doesn't search for any other key,
and it continues with the next file. If not, then continues searching the next key in the same file.


How to use it:
- open the app
- choose the folder that contains the files that you want to make changes.
- insert the name of the new folder that will be created after the file changes.
- press the button 'Make changes'.
- after the run finishes, you will see a confirmation modal.

The new folder with the new files inside, will be created in your Desktop.
