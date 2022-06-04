package functions

import inventory.Inventory
import build._
import citadel.Citadel
import request.Request
import buildorder.BuildOrder
import java.util.Calendar
import java.time.LocalDate

trait Errors {
    type Coordinate = Integer
    def getMissingMaterial(inventory : Inventory, build : Build) : Boolean
    def getExistingCoords(citadel : Citadel, x : Coordinate, y : Coordinate) : Boolean
}

object Errors extends Errors {
    def getMissingMaterial(inventory : Inventory, build : Build) : Boolean = {
        var missing = false
        if(inventory.cemento < build.necessaryCemento ||
            inventory.grava < build.necessaryGrava ||
            inventory.arena < build.necessaryArena ||
            inventory.madera < build.necessaryMadera ||
            inventory.adobe < build.necessaryAdobe) missing = true
        missing
    }

    def getExistingCoords(citadel: Citadel, x: Coordinate, y: Coordinate) : Boolean = {
        var existing = false
        citadel.ordersInProgress.foreach(build => {
            if(build.x_coord == x && build.y_coord == y) existing = true 
        })
        citadel.finishedBuilds.foreach(build => {
            if(build._2._1 == x && build._2._2 == y) existing = true
        })
        existing
    }
}

trait Utils {
  def getBuildType(build : Build) : String 
  def getFinishDate(citadel : Citadel) : String
}

object Utils extends Utils {
    def getBuildType(build: Build) : String = {
        build match {
            case Casa => "House"
            case Edificio => "Building"
            case Lago => "Lake"
            case CanchaDeFutbol => "Soccer field"
            case Gimnasio => "Gym"
            case _ => "Type not found"
        }
    }

    def getFinishDate(citadel : Citadel): String = {
        val date = s"End date: ${citadel.day}/${citadel.month}/${citadel.year}.\n"
        date
    }

}

trait Functions {
    type Coordinate = Integer
    def createRequest(citadel : Citadel, build : Build, x : Coordinate, y : Coordinate) : Citadel
    def addInventory(citadel : Citadel, quant : Integer) : Citadel
    def updateOrders(citadel : Citadel) : Citadel
}

object Functions extends Functions {
    def createRequest(citadel : Citadel, build: Build, x: Coordinate, y: Coordinate): Citadel = {
        if(Errors.getMissingMaterial(citadel.inventory, build)) {
            println("There is not enough material to build.")
            return citadel
        } else if(Errors.getExistingCoords(citadel, x, y)) {
            println("A build already exists in that position.")
            return citadel
        }
        val request = Request(build, x, y)
        val buildOrderDate = LocalDate.of(citadel.year, citadel.month, citadel.day).plusDays(1)
        val buildOrderDay = buildOrderDate.getDayOfMonth()
        val buildOrderMonth = buildOrderDate.getMonthValue()
        val buildOrderYear = buildOrderDate.getYear()
        val buildOrderFinishDate = LocalDate.of(buildOrderYear, buildOrderMonth, buildOrderDay).plusDays(build.buildingTime.toLong)
        val buildOrderFinishDay = buildOrderFinishDate.getDayOfMonth()
        val buildOrderFinishMonth = buildOrderFinishDate.getMonthValue()
        val buildOrderFinishYear = buildOrderFinishDate.getYear()
        val buildOrder = BuildOrder(build, x, y, buildOrderDay, buildOrderMonth, buildOrderYear, buildOrderFinishDay, buildOrderFinishMonth, buildOrderFinishYear, "Pending")
        println("You have processed the request successfully.")
        citadel.copy(ordersInProgress = citadel.ordersInProgress.appended(buildOrder), 
                    requests = citadel.requests.appended(request),
                    day = buildOrderFinishDay,
                    month = buildOrderFinishMonth,
                    year = buildOrderFinishYear,
                    inventory = citadel.inventory.copy(cemento = citadel.inventory.cemento - build.necessaryCemento,
                                                        grava = citadel.inventory.grava - build.necessaryGrava,
                                                        arena = citadel.inventory.arena - build.necessaryArena,
                                                        madera = citadel.inventory.madera - build.necessaryMadera,
                                                        adobe = citadel.inventory.adobe - build.necessaryAdobe))
    }

    def addInventory(citadel : Citadel, quant : Integer): Citadel = {
        citadel.copy(inventory = citadel.inventory.copy(cemento = citadel.inventory.cemento + quant,
                                                        grava = citadel.inventory.grava + quant,
                                                        arena = citadel.inventory.arena + quant,
                                                        madera = citadel.inventory.madera + quant,
                                                        adobe = citadel.inventory.adobe + quant))
    }

    def updateOrders(citadel: Citadel): Citadel = {
        val trunced = citadel.ordersInProgress.take(1).head
        if(trunced.status == "Pending" &&
            LocalDate.now().getDayOfMonth() == trunced.startDay &&
            LocalDate.now().getMonthValue() == trunced.startMonth &&
            LocalDate.now().getYear() == trunced.startYear) {
            val newCitadel = citadel.copy(ordersInProgress = citadel.ordersInProgress.drop(1))
            return newCitadel.copy(ordersInProgress = List(trunced.copy(status = "In progress")) ++ newCitadel.ordersInProgress)
        } else if(trunced.status == "In progress" &&
            LocalDate.now().getDayOfMonth() == trunced.endDay &&
            LocalDate.now().getMonthValue() == trunced.endMonth &&
            LocalDate.now().getYear() == trunced.endYear) {
            return citadel.copy(ordersInProgress = citadel.ordersInProgress.drop(1), finishedBuilds = citadel.finishedBuilds.appended((trunced.build, (trunced.x_coord, trunced.y_coord))))
        }
        citadel
    }
}