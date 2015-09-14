# Adventure-Game

1. [shell scripts usage] (#autopush)
2. [Print Tool] (#print)

#1. autopush.sh usage: <a id="autopush"></a>

What this script does is rather than having to type:
```
% git pull https://github.com/xuanshenbo/Adventure-Game
% git add .
% git commit -m "comment message"
% git push
```

All you need to do is type:
```
% ./gitpull.sh
```
which pulls the repo and then:
```
% ./autopush.sh
```
where ```./``` means current directory and autopush.sh is an excutable

and then type your comment message, it pushes your commit for you automatically.

Sweet!

#2. PRINT TOOL: <a id="print"></a>

This is a simple class that I made that makes it easier to put print statements into your code and allows a lot more information to be recived by them

You have to import

```
import static utilities.PrintTool.p;
```

from here you now have access to three methods

```p(String message)``` will print out the message that you send

```p()``` will print out "I MADE IT HERE" plus the extra information

```p(Object o)``` will print out the objects toString method.

this information will be printed out in the format:

state.Area.generateWorld() line:43: new line in generate world

where this represents:

package.Class.method() line: lineNumber: "message"

If you know how to change the color of text in the console then come speak to me!!!
