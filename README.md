JavaCom
=======

JavaCom is a two peace of libraries for Windows Java users to communicate to 
serial COM. As I could not find alternative, I wrote new. (There was RX TX, 
but it is outdated and no sources are not available at this moment.) 
JavaCom is in-house written in Ministry Of Internal Affairs of Georgia 
(Europian Country :)) by Java enterprise developer, so don't judge me 
(the developer) for bad/not easy to understand/non-standard/poorly 
documented code. It just works and does its job very well. 
This software is released under GPLv3+ license, hoping it helps other people too.

My name is Rezo Imnadze and you can contact me at rezo@imnadze.ge. Feedback is 
highly appreciated.

Thank you for using this library.



To Build Dll
============

I used "MinGW" and "MinGW-w64" to build 32 and 64 bit binaries. It also needs
Java JDK to use JNI headers. You must have environment variables ready.

32 bit:
gcc -Wl,--add-stdcall-alias -I"%JAVA_HOME%\include" -I"%JAVA_HOME%\include\win32" -shared -o build\JavaCom32.dll JavaCom.c

64 bit:
x86_64-w64-mingw32-gcc -Wl,--add-stdcall-alias -I"%JAVA_HOME%\include" -I"%JAVA_HOME%\include\win32" -shared -o build\JavaCom64.dll JavaCom.c

Ready dlls are located in "build" directory.


To Build Jar
============

JavaComApi is Maven application, so "mvn package" is enough to build jar.
It has no dependencies.



To Setup Machine
================

You need to rename JavaCom32.dll or JavaCom64.dll (depending on java runtime version)
to JavaCom.dll in your Java application's working directory or in JRE's bin 
directory. Anywhere application can directly access.


To Use Api From Java
====================

Little example is like this:

ComPort comPort=new ComPort(3, 115200, 8, ComPort.ONESTOPBIT, ComPort.NOPARITY);
comPort.open();
InputStream is = comPort.getInputStream();
...
comPort.close();


----
And I think that's it. :) Enjoy!