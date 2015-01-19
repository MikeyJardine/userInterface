#!/bin/bash

if [ "$@" != "app://" ]; then 
    app=$(echo $@ | sed -r 's/app:\/\///g')  
    nohup $app >/dev/null 2>&1 &
else 
    nohup gnome-terminal >/dev/null 2>&1 &
fi

exit
