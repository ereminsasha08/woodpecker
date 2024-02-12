export interface Good {
  id: number
  name: String
  typeGoods: TypeGoods
  size: Size
  price: number
  description: String
  dateCreate: Date
  userCreate: Date
  isView: Boolean
}

interface Size {
  height: number
  length: number
  weight: number
}

interface TypeGoods {
}
