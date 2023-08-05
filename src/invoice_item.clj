(ns invoice-item)

(defn- discount-factor
  [{:keys [discount-rate]}]
  (if (and discount-rate (number? discount-rate))
    (- 1 (/ discount-rate 100.0))
    1)) ; If discount-rate is nil or not a number, no discount is applied.

(defn subtotal
  [{:keys [precise-quantity precise-price discount-rate]}]
  (let [discount (discount-factor {:discount-rate discount-rate})]
    (* precise-price precise-quantity discount)))
