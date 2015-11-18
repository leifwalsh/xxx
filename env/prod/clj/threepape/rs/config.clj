(ns threepape.rs.config
  (:require [taoensso.timbre :as timbre]))

(def defaults
  {:init
   (fn []
     (timbre/info "\n-=[threepape.rs started successfully]=-"))
   :middleware identity})
