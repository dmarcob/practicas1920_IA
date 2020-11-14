;Autor: Diego Marco, 755232
;Fecha 12/11/2020
;Fichero: figura.clp


;;;;;;;;;;;;;;;;;;;;;
;; MODULO MAIN     ;;
;;;;;;;;;;;;;;;;;;;;;

(defmodule MAIN (export deftemplate nodo))

;El estado es una tupla (S, Nºsaltos, Nºandadas)
;Donde S es la casilla actual, rango [1,8]
;Donde NºSaltos representa el número de acciones saltar tomadas.
;DONDE Nºandadas representa el número de accionas andas tomadas.
(deftemplate MAIN::nodo
    (multislot estado)
    (multislot camino)
    (slot coste)
    (slot clase (default abierto)))

(deffacts MAIN::estado-inicial
    (nodo
         (estado 1 0 0)
         (camino (implode$ (create$ 1 0 0)))
         (coste 0)
    )
)
    ;El estado final es aquel estado en el que la posición
    ;sobre la figura sea la número 8

(defrule MAIN:pasa-el-mejor-a-cerrado-CU
    ?nodo <- (nodo (clase abierto)
                    (coste ?c1))
    (not (nodo (clase abierto)
               (coste ?c2&: (< ?c2 ?c1)))) 
=>
    (modify ?nodo (clase cerrado))
    (focus OPERADORES))



;;;;;;;;;;;;;;;;;;;;;;;
;; MODULO OPERADORES ;;
;;;;;;;;;;;;;;;;;;;;;;;

(defmodule OPERADORES
    (import MAIN deftemplate nodo))

(defrule OPERADORES::andar
    (nodo (estado ?S ?saltos ?andadas)
                   (camino $?movimientos)
                   (coste ?costeAcum)
                   (clase cerrado))
    (test (< ?S 8))
=>
    (bind $?nuevo-estado (create$ (+ ?S 1) ?saltos (+ ?andadas 1)))
    (bind ?coste 1)
    (assert(nodo
            (estado $?nuevo-estado)
            (camino $?movimientos (implode$ $?nuevo-estado))
            (coste (+ ?costeAcum 1)))))


(defrule OPERADORES::saltar
    (nodo (estado ?S ?saltos ?andadas)
                   (camino $?movimientos)
                   (coste ?costeAcum)
                   (clase cerrado))
    (test (<= ?S 4))
    (test (< ?saltos ?andadas))
=>
    (bind $?nuevo-estado (create$ (* ?S 2) (+ ?saltos 1) ?andadas))
    (bind ?coste 2)
    (assert(nodo
            (estado $?nuevo-estado)
            (camino $?movimientos (implode$ $?nuevo-estado))
            (coste (+ ?costeAcum 1)))))



;;;;;;;;;;;;;;;;;;;;;;;;;;
;; MODULO RESTRICCIONES ;;
;;;;;;;;;;;;;;;;;;;;;;;;;;

(defmodule RESTRICCIONES
    (import MAIN deftemplate nodo))

(defrule RESTRICCIONES::repeticiones-de-nodo
    (declare (auto-focus TRUE))
    (nodo (estado $?actual)
            (camino $?movimientos-1))
    ?nodo <- (nodo (estado $?actual)
                    (camino $?movimientos-1 ?a $?))
=>
    (retract ?nodo))


;;;;;;;;;;;;;;;;;;;;;
;; MODULO SOLUCION ;;
;;;;;;;;;;;;;;;;;;;;;

(defmodule SOLUCION
    (import MAIN deftemplate nodo))

(defrule SOLUCION::reconoce-solucion
    (declare (auto-focus TRUE))
    ?nodo1 <- (nodo (estado $?actual)
              (camino $?movimientos)
              (clase cerrado))
    (test (eq (nth$ 1 ?actual) 8))
=>
    (retract ?nodo1)
    (assert (solucion $?movimientos)))

(defrule SOLUCION::escribe-solucion
    (solucion $?movimientos)
=>
    (bind ?size (length$ ?movimientos))
    (printout t "La solucion tiene " (- ?size 1) " pasos" crlf)
    (loop-for-count(?i 1 ?size)
    (printout t (nth$ ?i ?movimientos) crlf))
    (halt))

