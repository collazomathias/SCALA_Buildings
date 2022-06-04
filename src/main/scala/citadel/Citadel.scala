package citadel

import build.Build
import buildorder.BuildOrder
import request.Request
import inventory.Inventory

case class Citadel(
    finishedBuilds : List[(Build, (Integer, Integer))],
    ordersInProgress : List[BuildOrder],
    requests : List[Request],
    inventory : Inventory,
    day : Integer,
    month : Integer,
    year : Integer,
)
