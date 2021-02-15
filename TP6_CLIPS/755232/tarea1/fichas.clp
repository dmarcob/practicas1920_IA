;Autor: Diego Marco, 755232
;Fecha 11/11/2020
;Fichero: fichas.clp
;Coms: Resolucion problema de las fichas. 
;      Se ha utilizado la estrategia A*.
;      Se han empleado los modulos: 
;      MAIN, OPERACIONES, RESTRICCIONES y SOLUCION

; Para estado separar en slots, para camino una lista de strings
; assert, modify, retract, implode, create, +, <, neq, test
; length
; bind $?nuevoestado 

;;;;;;;;;;;;;;;;;;;;;
;; MODULO MAIN     ;;
;;;;;;;;;;;;;;;;;;;;;

(defmodule MAIN (export deftemplate nodo)
                (export deffunction heuristica))

(deftemplate MAIN::nodo
    (multislot estado)
    (multislot camino)
    (slot heuristica) 
    (slot coste)
    (slot clase (default abierto)))

(defglobal MAIN
    ?*estado-inicial* = (create$ B B B H V V V)
    ?*estado-final* = (create$ V V V H B B B))

(deffunction MAIN::heuristica ($?estado)
    (bind ?res 0)
    (loop-for-count (?i 1 7)
        (if (neq (nth ?i $?estado)
                 (nth ?i ?*estado-final*))
            then (bind ?res (+ ?res 1))
        )
    )
    ?res)

;Inserto el hecho inicial a la base de hechos.
;Se obtendría un mismo comportamiento usando deffacts.
(defrule MAIN::inicial
    => 
    (assert (nodo
                (estado ?*estado-inicial*)
                (camino (implode$ ?*estado-inicial*))
                (heuristica (heuristica ?*estado-inicial*))
                (coste 0)
                 )))

;Escoge un nodo abierto tal que no existe otro nodo abierto con un valor de 
;evaluación(heurística + coste)  menor.
;Después cierra ese nodo indicando que es el siguiente a expandir.
(defrule MAIN:pasa-el-mejor-a-cerrado-A*
    ?nodo <- (nodo (clase abierto)
                    (heuristica ?h1) (coste ?c1))
    (not (nodo (clase abierto)
                (heuristica ?h2)(coste ?c2&: (< (+ ?h2 ?c2) (+ ?h1 ?c1))))) 
=>
    (modify ?nodo (clase cerrado))
    (focus OPERADORES))




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


;;;;;;;;;;;;;;;;;;;;;;;
;; MODULO OPERADORES ;;
;;;;;;;;;;;;;;;;;;;;;;;

(defmodule OPERADORES
    (import MAIN deftemplate nodo)
    (import MAIN deffunction heuristica))

(defrule OPERADORES::moverDerecha
    (nodo (estado $?a ?b $?c&:(< (length$ ?c) 3) H $?d)
                   (camino $?movimientos)
                   (coste ?costeAcum)
                   (clase cerrado))
=>
    (bind $?nuevo-estado (create$ $?a H $?c ?b  $?d))
    (bind ?coste 1)
    (assert(nodo
            (estado $?nuevo-estado)
            (camino $?movimientos (implode$ $?nuevo-estado))
            (heuristica (heuristica $?nuevo-estado)) 
            (coste (+ ?costeAcum 1)))))


(defrule OPERADORES::moverIzquierda
    (nodo (estado $?a H $?b&:(< (length$ ?b) 3) ?c $?d)
                   (camino $?movimientos)
                   (coste ?costeAcum)
                   (clase cerrado))
=>
    (bind $?nuevo-estado (create$ $?a ?c $?b H  $?d))
    (bind ?coste 1)
    (assert(nodo
            (estado $?nuevo-estado)
            (camino $?movimientos (implode$ $?nuevo-estado))
            (heuristica (heuristica $?nuevo-estado)) 
            (coste (+ ?costeAcum 1)))))


;;;;;;;;;;;;;;;;;;;;;
;; MODULO SOLUCION ;;
;;;;;;;;;;;;;;;;;;;;;

(defmodule SOLUCION
    (import MAIN deftemplate nodo))

(defrule SOLUCION::reconoce-solucion
    (declare (auto-focus TRUE))
    ?nodo <- (nodo (estado V V V H B B B)
             (camino $?movimientos))
=>
    (retract ?nodo)
    (assert (solucion $?movimientos)))

(defrule SOLUCION::escribe-solucion
    (solucion $?movimientos)
=>
    (bind ?size (length$ ?movimientos))
    (printout t "La solucion tiene " (- ?size 1) " pasos" crlf)
    (loop-for-count(?i 1 ?size)
    (printout t (nth ?i ?movimientos) crlf))
    (halt))
