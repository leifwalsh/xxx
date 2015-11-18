(ns threepape.rs.config
  (:require [selmer.parser :as parser]
            [taoensso.timbre :as timbre]
            [threepape.rs.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (timbre/info "\n-=[threepape.rs started successfully using the development profile]=-"))
   :middleware wrap-dev})
