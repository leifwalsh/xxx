(ns threepape.rs.routes.home
  (:require [threepape.rs.layout :as layout]
            [compojure.core :refer [defroutes GET]]
            [ring.util.http-response :refer [ok]]
            [clojure.edn :as edn]
            [clojure.java.io :as io]))

(def users
  (memoize
   (fn get-users []
     (-> (io/resource "papers.edn")
         (slurp)
         (edn/read-string)))))

(defn home-page []
  (layout/render "home.html" {:users (->> (users)
                                          (mapv (fn [[id user]]
                                                  {:id id
                                                   :name (:name user)})))}))

(defn user-page [id]
  (if-let [user (get (users) id)]
    (layout/render (format "layout%d.html" (:layout user))
                   user)
    (layout/error-page {:status 404
                        :title "page not found"})))

(defn about-page []
  (layout/render "about.html"))

(defroutes home-routes
  (GET "/" [] (home-page))
  (GET "/about" [] (about-page))
  (GET "/user/:id" [id] (user-page id)))

