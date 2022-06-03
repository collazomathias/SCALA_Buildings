package citadel

import build.Build
import buildorder.BuildOrder

case class Citadel(
    finishedBuilds : List[(Build, (Double, Double))],
    ordersInProgress : List[BuildOrder],
    day : Integer,
    month : Integer,
    year : Integer,
)
