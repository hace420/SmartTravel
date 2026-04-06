 /$$$$$$  /$$      /$$  /$$$$$$  /$$$$$$$  /$$$$$$$$       /$$$$$$$$ /$$$$$$$   /$$$$$$  /$$    /$$ /$$$$$$$$ /$$              /$$$$$$  /$$$$$$$$ /$$$$$$$  /$$    /$$ /$$$$$$  /$$$$$$  /$$$$$$$$
 /$$__  $$| $$$    /$$$ /$$__  $$| $$__  $$|__  $$__/      |__  $$__/| $$__  $$ /$$__  $$| $$   | $$| $$_____/| $$             /$$__  $$| $$_____/| $$__  $$| $$   | $$|_  $$_/ /$$__  $$| $$_____/
| $$  \__/| $$$$  /$$$$| $$  \ $$| $$  \ $$   | $$            | $$   | $$  \ $$| $$  \ $$| $$   | $$| $$      | $$            | $$  \__/| $$      | $$  \ $$| $$   | $$  | $$  | $$  \__/| $$      
|  $$$$$$ | $$ $$/$$ $$| $$$$$$$$| $$$$$$$/   | $$            | $$   | $$$$$$$/| $$$$$$$$|  $$ / $$/| $$$$$   | $$            |  $$$$$$ | $$$$$   | $$$$$$$/|  $$ / $$/  | $$  | $$      | $$$$$   
 \____  $$| $$  $$$| $$| $$__  $$| $$__  $$   | $$            | $$   | $$__  $$| $$__  $$ \  $$ $$/ | $$__/   | $$             \____  $$| $$__/   | $$__  $$ \  $$ $$/   | $$  | $$      | $$__/   
 /$$  \ $$| $$\  $ | $$| $$  | $$| $$  \ $$   | $$            | $$   | $$  \ $$| $$  | $$  \  $$$/  | $$      | $$             /$$  \ $$| $$      | $$  \ $$  \  $$$/    | $$  | $$    $$| $$      
|  $$$$$$/| $$ \/  | $$| $$  | $$| $$  | $$   | $$            | $$   | $$  | $$| $$  | $$   \  $/   | $$$$$$$$| $$$$$$$$      |  $$$$$$/| $$$$$$$$| $$  | $$   \  $/    /$$$$$$|  $$$$$$/| $$$$$$$$
 \______/ |__/     |__/|__/  |__/|__/  |__/   |__/            |__/   |__/  |__/|__/  |__/    \_/    |________/|________/       \______/ |________/|__/  |__/    \_/    |______/ \______/ |________/




 RecentList<T> Generic Wrapper : 
- used linked list to track list of most recently viewed trips as it allows the usage of  list.removeLast() and list.addFirst which are the most common usages for recent list and are not available for arrayList
- Without it the code would be more complex and would require tracking index and shifting positions of trips inside arrayList and would make code less optimized.

NOTE: 
- all previous A2 operations untouched menu 5 options all still work.
- original file manager files are still present and fucntionality remains 
- dashboard and chart generator files have been modified to work with arrayList
- Predefined test scenerio still works