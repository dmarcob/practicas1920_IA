(deftemplate estado (slot garrafa (type INTEGER)))

(defrule agnade-un-litro
?estado <- (estado (garrafa ?cantidad&: (< ?cantidad 3)))
=>
(modify ?estado (garrafa (+ ?cantidad 1)))
(printout t "garrafa :" ?cantidad crlf))

(deffacts hechos
(estado (garrafa 1))
)
