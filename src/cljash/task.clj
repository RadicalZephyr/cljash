(ns cljash.task
  (:import msh.Minishell))

(defn main []
  (Minishell/main (into-array String [])))
