(ns routing.main
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.util.codec :refer [form-decode]]
            [clojure.pprint :as pp]))

(def not-found
  "Not found")

;; (def routes
;;   {"get /lists" "Lists page"
;;    "get /lists/:id" "Single list page"
;;    "get /info" "Info page"})

(defn f
  [route rt]
  (re-matches (re-pattern (clojure.string/replace
                           rt
                           #"/:\w+"
                           "/\\\\d+")) route))

(defn find-route
  [route routes]
  (second (first (filter #(f route (first %)) routes))))





(defn lists
  [request]
  (println (form-decode (:query-string request)))
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "Multiple lists"})

(defn list
  [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "Single list"})

(defn list-rename
  [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "Rename list"})

(defn info
  [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "Info"})

(defn not-found
  []
  {:status 404
   :headers {"Content-Type" "text/html"}
   :body "Not found"})

(defn handler
  [request]
  (pp/pprint request)
  (let [route (str (name (:request-method request)) " " (:uri request))
        routes {"get /lists" (lists request)
                "get /lists/:id/rename" (list-rename request)
                "get /lists/:id" (list request)
                "get /info" (info request)}]
    (or (find-route route routes) (not-found))))

(def app
  (wrap-reload handler))

(defn -main
  [& args]
  (jetty/run-jetty app {:port 3000}))

(comment
  (re-pattern (clojure.string/replace
               "get /lists/:id"
               #"/:\w+"
               "/\\\\d+"))
  (re-matches (re-pattern (clojure.string/replace
                           "get /lists/:id"
                           #"/:\w+"
                           "/\\\\d+")) "get /lists/10")
  ;; "get /lists/:id" -> "get /lists/\d+", i.e. ":smth" -> "\d+"
  "\fds"
  "\\d+"
  (find-route "get /lists/1")
  (re-matches #"get /lists/(\d+)" "get /lists/10"))
