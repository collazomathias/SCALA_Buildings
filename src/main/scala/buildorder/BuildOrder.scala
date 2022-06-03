package buildorder

import build.Build

case class BuildOrder(
    build : Build,
    x_coord : Int,
    y_coord : Int,
    startDay : Int,
    startMonth : Int,
    startYear : Int,
    endDay : Int,
    endMonth : Int,
    endYear : Int,
    status : String
)
