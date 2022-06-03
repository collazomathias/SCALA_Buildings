package request

import build.Build

case class Request(
    build : Build,
    x_coord : Int,
    y_coord : Int
)
