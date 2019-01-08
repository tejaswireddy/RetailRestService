xquery version "1.0-ml";

declare variable $id as xs:string external;
declare variable $price as xs:string external;
declare variable $currency as xs:string external;

let $uri := "/data/canonical/product/"||$id||"/price.xml"

let $new-price-node := <price>{$price}</price>
let $new-currency-node := <currency>{$currency}</currency>
let $_ as empty-sequence() := xdmp:node-replace(doc($uri)/product/price,$new-price-node)
let $_ as empty-sequence() := xdmp:node-replace(doc($uri)/product/currency,$new-currency-node)

return
if(fn:exists(fn:doc($uri))) then
fn:true()
else fn:false()