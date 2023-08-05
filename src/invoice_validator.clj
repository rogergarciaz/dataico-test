(ns invoice-validator
  (:require [clojure.data.json :as json]
            [clojure.spec.alpha :as s]
            [clojure.java.io :as io]
            [invoice-spec :as inv-spec])) ; Require the invoice-spec namespace

;; Helper function to read JSON from file
(defn read-json-file [filename]
  (with-open [reader (io/reader filename)]
    (json/read reader :key-fn keyword))) ; Convert keys to keywords

(defn generate-invoice [filename]
  (let [json-data (read-json-file filename)
        issue-date-str (get-in json-data ["invoice" "issue_date"])
        customer-data (get-in json-data ["invoice" "customer"])
        item-data (get-in json-data ["invoice" "items"])]
    (let [issue-date (s/conform :invoice/issue-date issue-date-str)
          customer (s/conform :invoice/customer customer-data)
          items (s/conform :invoice/items item-data)]
      {:invoice/issue-date issue-date
       :invoice/customer customer
       :invoice/items items})))

;; Test the generated invoice using the provided JSON file
(defn main [& args]
  (let [filename "../invoice.json"]
    (let [generated-invoice (generate-invoice filename)]
      (println "Generated invoice:")
      (println generated-invoice)
      (println "Is valid? " (s/valid? :invoice-spec/invoice generated-invoice))
      (println "Non-conforming values:")
      (println (s/explain-data :invoice-spec/invoice generated-invoice)))))

;; Run the validation using the relative file location
(main)