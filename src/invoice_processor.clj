(ns invoice_processor
  (:require [clojure.edn :as edn]))

(defn load-invoice []
  (edn/read-string (slurp "../invoice.edn")))

(defn satisfy-conditions? [item]
  (let [has-iva? (some #(= (:tax/category %) :iva) (:taxable/taxes item))
        has-ret-fuente? (some #(= (:retention/category %) :ret_fuente) (:retentionable/retentions item))]
    (not (and has-iva? has-ret-fuente?))))

(defn filter-invoice-items [invoice]
  (->> invoice
       :invoice/items
       (filter satisfy-conditions?)))

(defn main []
  (let [invoice (load-invoice)]
    (println "Items satisfying the conditions:")
    (doseq [item (filter-invoice-items invoice)]
      (println item))))

(main)
