package functions

import inventory.Inventory
import build._
import citadel.Citadel
import request.Request
import buildorder.BuildOrder
import java.util.Calendar
import java.time.LocalDate
import java.time.LocalDateTime

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
  def showInformation(citadel : Citadel) : Citadel
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

    def showInformation(citadel: Citadel): Citadel = {
        println("|--------------------------------|")
        println("|     Information of Citadel     |")
        println("|--------------------------------|")
        println(s"Inventory: \n\tArena: ${citadel.inventory.arena} " +
            s"\n\tGrava: ${citadel.inventory.grava} " +
            s"\n\tCemento: ${citadel.inventory.cemento} " +
            s"\n\tMadera: ${citadel.inventory.madera} " +
            s"\n\tAdobe: ${citadel.inventory.adobe}")
        if(citadel.finishedBuilds.isEmpty) println("Finished builds: Finished builds not found.")
        else {
            println("Finished builds:")
            citadel.finishedBuilds.foreach(build => {
                build._1 match {
                    case Casa => println(s"\tBuild type: Casa\t" +
                        s"Coordinates: (${build._2._1}, ${build._2._2})")
                    case Edificio => println(s"\tBuild type: Edificio" +
                        s"Coordinates: (${build._2._1}, ${build._2._2})")
                    case Lago => println(s"\tBuild type: Lago" +
                        s"Coordinates: (${build._2._1}, ${build._2._2})")
                    case CanchaDeFutbol => println(s"\tBuild type: Cancha de fútbol" +
                        s"Coordinates: (${build._2._1}, ${build._2._2})")
                    case Gimnasio => println(s"\tBuild type: Gimnasio" +
                        s"Coordinates: (${build._2._1}, ${build._2._2})")
                    case _ => println("\tBuild type not found")
                }
            })
        }
        if(citadel.ordersInProgress.isEmpty) println("Build orders: Build orders not found.")
        else {
            println(s"Build orders:")
            citadel.ordersInProgress.foreach(order => {
                order.build match {
                    case Casa => println(s"\tBuild type: Casa" +
                        s"\tCoordinates: (${order.x_coord},${order.y_coord})" +
                        s"\tStart day: ${order.startDay}/${order.startMonth}/${order.startYear}" +
                        s"\tEnd day: ${order.endDay}/${order.endMonth}/${order.endYear}" +
                        s"\tStatus: ${order.status}")
                    case Edificio => println(s"\tBuild type: Edificio" +
                        s"\tCoordinates: (${order.x_coord},${order.y_coord})" +
                        s"\tStart day: ${order.startDay}/${order.startMonth}/${order.startYear}" +
                        s"\tEnd day: ${order.endDay}/${order.endMonth}/${order.endYear}" +
                        s"\tStatus: ${order.status}")
                    case Lago => println(s"\tBuild type: Lago" +
                        s"\tCoordinates: (${order.x_coord},${order.y_coord})" +
                        s"\tStart day: ${order.startDay}/${order.startMonth}/${order.startYear}" +
                        s"\tEnd day: ${order.endDay}/${order.endMonth}/${order.endYear}" +
                        s"\tStatus: ${order.status}")
                    case CanchaDeFutbol => println(s"\tBuild type: Cancha de fútbol" +
                        s"\tCoordinates: (${order.x_coord},${order.y_coord})" +
                        s"\tStart day: ${order.startDay}/${order.startMonth}/${order.startYear}" +
                        s"\tEnd day: ${order.endDay}/${order.endMonth}/${order.endYear}" +
                        s"\tStatus: ${order.status}")
                    case Gimnasio => println(s"\tBuild type: Gimnasio" +
                        s"\tCoordinates: (${order.x_coord},${order.y_coord})" +
                        s"\tStart day: ${order.startDay}/${order.startMonth}/${order.startYear}" +
                        s"\tEnd day: ${order.endDay}/${order.endMonth}/${order.endYear}" +
                        s"\tStatus: ${order.status}")
                    case _ => println(s"\tBuild type not found")
                }
            })
        }
        println(s"Building end date: ${citadel.day}/${citadel.month}/${citadel.year}.")
        citadel
    }
}

trait Functions {
    type Coordinate = Integer
    def createRequest(citadel : Citadel, build : Build, x : Coordinate, y : Coordinate) : Citadel
    def addInventory(citadel : Citadel, quant : Integer, material : String) : Citadel
    def updateOrders(citadel : Citadel) : Citadel
}

object Functions extends Functions {
    def createRequest(citadel : Citadel, build: Build, x: Coordinate, y: Coordinate) : Citadel = {
        if(Errors.getMissingMaterial(citadel.inventory, build)) {
            println("There is not enough material to build.")
            return citadel
        } else if(Errors.getExistingCoords(citadel, x, y)) {
            println("A build already exists in that position, or is in a 'Pending/In-progress' state.")
            return citadel
        }
        val request = Request(build, x, y)
        if(LocalDate.of(citadel.year, citadel.month, citadel.day).plusDays(1).isBefore(LocalDate.now())) {
            val buildOrderDate = LocalDate.now().plusDays(1)
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
        } else {
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
        
    }

    def addInventory(citadel : Citadel, quant : Integer, material : String): Citadel = {

        material match {
            case "Cemento" => citadel.copy(inventory = citadel.inventory.copy(cemento = citadel.inventory.cemento + quant))
            case "Grava" => citadel.copy(inventory = citadel.inventory.copy(grava = citadel.inventory.grava + quant))
            case "Arena" => citadel.copy(inventory = citadel.inventory.copy(arena = citadel.inventory.arena + quant))
            case "Madera" => citadel.copy(inventory = citadel.inventory.copy(madera = citadel.inventory.madera + quant))
            case "Adobe" => citadel.copy(inventory = citadel.inventory.copy(adobe = citadel.inventory.adobe + quant))
            case _ => citadel
        }
    }

    def updateOrders(citadel: Citadel): Citadel = {
        val trunced = citadel.ordersInProgress.take(1).head
        val hour = LocalDateTime.now().getHour()
        val day = LocalDate.now().getDayOfMonth()
        val month = LocalDate.now().getMonthValue()
        val year = LocalDate.now().getYear()
        if(trunced.status == "Pending" && //Para probar funcionamiento hay que quitar las condiciones de fecha y hora.
            day == trunced.startDay &&
            month == trunced.startMonth &&
            year == trunced.startYear &&
            hour >= 6 &&
            hour <= 12) {
            val newCitadel = citadel.copy(ordersInProgress = citadel.ordersInProgress.drop(1))
            println("The order has been put in 'In-progress' status.")
            return newCitadel.copy(ordersInProgress = List(trunced.copy(status = "In-progress")) ++ newCitadel.ordersInProgress)
        } else if(trunced.status == "In-progress" && //Para probar funcionamiento hay que quitar las condiciones de fecha y hora.
            day == trunced.endDay &&
            month == trunced.endMonth &&
            year == trunced.endYear &&
            hour >= 17 &&
            hour <= 23) {
            println("The order has been completed.")
            return citadel.copy(ordersInProgress = citadel.ordersInProgress.drop(1), finishedBuilds = citadel.finishedBuilds.appended((trunced.build, (trunced.x_coord, trunced.y_coord))))
        }
        citadel
    }
}