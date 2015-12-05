(ns threepape.rs.routes.home
  (:require [threepape.rs.layout :as layout]
            [compojure.core :refer [defroutes GET]]
            [ring.util.http-response :refer [ok]]
            [clojure.edn :as edn]
            [clojure.java.io :as io]))

(def users
  (fn get-users []
    (-> (io/resource "papers.edn")
        (slurp)
        (edn/read-string))))

(def about
  (fn get-about []
    (-> (io/resource "docs/about.md")
        (slurp))))

(def id->previous-id
  (fn previous-id []
    (->> (users)
         (keys)
         (partition 2 1)
         (map reverse)
         (map vec)
         (into {(ffirst (users)) ((comp first last) (users))}))))

(def id->next-id
  (fn []
    (->> (id->previous-id)
         (map reverse)
         (map vec)
         (into {}))))

(defn home-page []
  (layout/render "home.html" {:users (->> (users)
                                          (mapv (fn [[id user]]
                                                  (merge user {:id id}))))}))

(defn user-page [id]
  (let [id->user-info (users)]
    (if-let [user (get id->user-info id) ]
      (layout/render (str (:layout user) ".html")
                     (assoc user
                            :next ((id->next-id) id)
                            :prev ((id->previous-id) id)))
      (layout/error-page {:status 404
                          :title "page not found"}))))

(defn about-page []
  (layout/render "about.html" {:about (about)}))

(defroutes home-routes
  (GET "/" [] (home-page))
  (GET "/about" [] (about-page))
  (GET "/user/:id" [id] (user-page id)))

