package me.kamkor.wishlists.domain

case class WishListItem(
  product: String,
  amount: Int = 1,
  note: Option[String],
  createdAt: Option[String]
)



//"properties":
//{
//"product":
//{
//"type":"string",
//"pattern":"^.+"
//},
//"amount":
//{
//"type":"integer",
//"minimum":1,
//"default":1
//},
//"note":
//{
//"type":"string"
//},
//"createdAt":
//{
//"type":"string",
//"format":"date-time"
//}
//},
//"required":["product", "amount"]