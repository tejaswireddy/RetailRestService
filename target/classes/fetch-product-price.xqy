xquery version "1.0-ml";

declare variable $id as xs:string external;

let $uri := "/data/canonical/product/"||$id||"/price.xml"
let $price := fn:doc($uri)/product/price/fn:string()
let $currency := fn:doc($uri)/product/currency/fn:string()
return
if(fn:exists(fn:doc($uri))) then
($price,$currency)
else
()