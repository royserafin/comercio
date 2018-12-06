if [%1] NEQ [] goto connumclientes
estresador 5 -jar estresador.jar Client
goto fin

:connumclientes
if [%2] NEQ [] goto connumsolicitudes
estresador %1 -jar estresador.jar Client
gotofin

:connumsolicitudes
if [%3] NEQ [] goto conrmihost
estresador %1 -jar estresador.jar Client %2
gotofin

:conrmihost
estresador %1 -jar estresador.jar Client %2 %3

:fin