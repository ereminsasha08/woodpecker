import {User} from "../user.model";

export interface Order {
  id: Number
  dateCreate: Date
  completed: Boolean
  marketPlace: Boolean
  isPaid: Boolean
  manager: User





  isColorPlywood: Boolean
  laser: String
  // plywoodList: List<String>
  namePainter: String
  // stage: Stage
  isAvailability: Boolean
  // geographyMap: GeographyMap
// time
  cutBegin: Date
  cutEnd: Date
  paintingBegin: Date
  paintingEnd: Date
  gluingEnd: Date
  packedEnd: Date
  postEnd: Date
}

