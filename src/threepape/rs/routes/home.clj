(ns threepape.rs.routes.home
  (:require [threepape.rs.layout :as layout]
            [compojure.core :refer [defroutes GET]]
            [ring.util.http-response :refer [ok]]
            [clojure.java.io :as io]))

(defn home-page []
  (layout/render
   "home.html"
   {:name "leif walsh"
    :userpic "/img/leif.jpg"
    :userinfo "peon at two sigma"
    :papers [{:title "On the Zone Theorem for Hyperplane Arrangements"
              :authors "Herbert Edelsbrunner, Raimund Seidel, and Micha Sharir"
              :link "http://pub.ist.ac.at/~edels/Papers/1993-J-02-OnZoneTheorem.pdf"
              :story "Sweeeeet."}
             {:title "On Khovanov's categorification of the Jones polynomial"
              :authors "Dror Bar-Natan"
              :link "http://arxiv.org/abs/math/0201043"
              :story "I like this paper.

It's pretty swell."}
             {:title "Reflections on Trusting Trust"
              :authors "Ken Thompson"
              :link "https://www.ece.cmu.edu/~ganger/712.fall02/papers/p761-thompson.pdf"
              :story "Also p. dope."}]}))

(defn about-page []
  (layout/render "about.html"))

(defroutes home-routes
  (GET "/" [] (home-page))
  (GET "/about" [] (about-page)))

