;;;;;;;;;;;;;;;;;;;;;
;; MODULO MAIN     ;;
;;;;;;;;;;;;;;;;;;;;;

;auto-focus TRUE -> Sensibiliza una regla perteneciente a otro módulo.
;                   Tras dispararse, otras reglas de ese módulo se pueden
;                   disparar también


;halt -> detiene el motor de búsqueda
(defmodule MAIN (export deftemplate nodo)
                (export deffunction heuristica))

;default -> valor por defecto si no se especifica
(deftemplate MAIN::nodo
    (multislot estado)
    (multislot camino)
    (multislot heuristica)
    (slot clase (default abierto)))

;create$ -> Lista
;?var -> variable
;$?a -> variable multislot

(defglobal MAIN
    ?*estado-inicial* = (create$ 2 8 3 1 6 4 7 H 5)
    ?*estado-final* = (create$ 1 2 3 8 H 4 7 6 5))

;bind <variable> <valor> -> Inicializa o actualiza variable con valor
;nth <integer> <list> -> Devuelve el elemento en posicion integer
;Heurística de fichas descolocadas
(deffunction MAIN::heuristica ($?estado)
    (bind ?res 0)
    (loop-for-count (?i 1 9)
        (if (neq (nth ?i $?estado)
                 (nth ?i ?*estado-final*))
            then (bind ?res (+ ?res 1))
        )
    )
    ?res)


;clase abierto -> nodos en frontera
;clase cerrado -> nodo más prometedor de la frontera ó nodo ya explorado

;Clase cerrado sensibilidado(solo una vez) para reglas arriba,abajo,etc
;Clase abierto sensibilizado para regla de control que escoge el siguiente
;nodo a expandir según su valor de heurística
(defrule MAIN::inicial
    => 
    (assert (nodo
                (estado ?*estado-inicial*)
                (camino)
                (heuristica (heuristica ?*estado-inicial*))
                 )))


;=================
; IMPORTANTE
; Para NO usar prioridades se utiliza la siguiente estrategia:
; En el módulo MAIN defino pasa-el-mejor-a-cerrado que cambiará 
; a clase cerrado el nodo con mejor heurística.
; Después cambiará el focus a OPERADORES. La agenda OPERADORES se 
; apilará sobre MAIN y se ejecutarán las reglas de OPERADORES que estén
; sensibilizadas. Cuando no se pueda ejecutar ninguna más, se desapilará
; la agenda OPERADORES y se ejecutarán las reglas de MAIN otra vez.


; De esta forma conseguimos, elegir el mejor nodo(MAIN), después generar sus
; sucesores(OPERADORES), elegir el mejor sucesor (MAIN),....
;================== 

;Escoge un nodo tal que no existe otro nodo con un valor heurístico
;menor que el suyo
(defrule MAIN:pasa-el-mejor-a-cerrado
    ?nodo <- (nodo (clase abierto)
                    (heuristica ?h1))
    (not (nodo (clase abierto)
                (heuristica ?h2&: (< ?h2 ?h1)))) 
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
                    (camino $?movimientos-1 ? $?))
=>
    (retract ?nodo))


;;;;;;;;;;;;;;;;;;;;;;;
;; MODULO OPERADORES ;;
;;;;;;;;;;;;;;;;;;;;;;;


(defmodule OPERADORES
    (import MAIN deftemplate nodo)
    (import MAIN deffunction heuristica))

(defrule OPERADORES::arriba
    (nodo (estado $?a ?b ?c ?d H $?e)
            (camino $?movimientos)
            (clase cerrado))
=>
    (bind $?nuevo-estado (create$ $?a H ?c ?d ?b $?e))
    (assert (nodo
            (estado $?nuevo-estado)
            (camino $?movimientos ^)
            (heuristica (heuristica $?nuevo-estado)))))

(defrule OPERADORES::abajo
    (nodo (estado $?a H ?b ?c ?d $?e)
            (camino $?movimientos)
            (clase cerrado))
=>
    (bind $?nuevo-estado (create$ $?a ?d ?b ?c H $?e))
    (assert (nodo
            (estado $?nuevo-estado)
            (camino $?movimientos v)
            (heuristica (heuristica $?nuevo-estado)))))



(defrule OPERADORES::izquierda
    (nodo (estado $?a&: (neq (mod (length $?a) 3) 2)
                      ?b H $?c)
            (camino $?movimientos)
            (clase cerrado))
=>
    (bind $?nuevo-estado (create$ $?a H ?b $?c))
    (assert (nodo
            (estado $?nuevo-estado)
            (camino $?movimientos <)
            (heuristica (heuristica $?nuevo-estado)))))



(defrule OPERADORES::derecha
    (nodo (estado $?a H ?b $?c&: (neq (mod (length $?c) 3) 2))
            (camino $?movimientos)
            (clase cerrado))
=>
    (bind $?nuevo-estado (create$ $?a ?b H $?c))
    (assert (nodo
            (estado $?nuevo-estado)
            (camino $?movimientos >)
            (heuristica (heuristica $?nuevo-estado)))))



;;;;;;;;;;;;;;;;;;;;;
;; MODULO SOLUCION ;;
;;;;;;;;;;;;;;;;;;;;;

(defmodule SOLUCION
    (import MAIN deftemplate nodo))

(defrule SOLUCION::reconoce-solucion
    (declare (auto-focus TRUE))
    ?nodo <- (nodo (heuristica 0)
                (camino $?movimientos))
=>
    (retract ?nodo)
    (assert (solucion $?movimientos)))

(defrule SOLUCION::escribe-solucion
    (solucion $?movimientos)
=>
    (printout t "Solucion:" $?movimientos crlf)
    (halt))

