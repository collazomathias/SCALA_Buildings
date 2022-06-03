package functions

import inventory.Inventory
import build.Build
import build.Casa
import build.Edificio
import build.Lago
import build.CanchaDeFutbol
import build.Gimnasio
import citadel.Citadel

trait Errors {
  def getMissingMaterial(inventory : Inventory, build : Build) : Boolean
  def getExistingCoords(citadel : Citadel, x : Integer, y : Integer) : Boolean
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

    def getExistingCoords(citadel: Citadel, x: Integer, y: Integer): Boolean = {
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
    def createRequest(citadel : Citadel, inventory : Inventory, build : Build, x : Integer, y : Integer) : String
    def addInventory(inventory : Inventory, quant : Integer) : Inventory
}

object Functions extends Functions {
    def createRequest(citadel : Citadel, inventory: Inventory, build: Build, x: Integer, y: Integer): String = {
        var message = "You have processed the request successfully."
        if(Errors.getMissingMaterial(inventory, build)) {
            message = "There is not enough material to build."
        } else if(Errors.getExistingCoords(citadel, x, y)) {
            message = "A build already exists in that position."
        }
        message
    }

    def addInventory(inventory: Inventory, quant : Integer): Inventory = {
        inventory.copy(
            cemento = inventory.cemento + quant, 
            grava = inventory.grava + quant, 
            arena = inventory.arena + quant, 
            madera = inventory.madera + quant, 
            adobe = inventory.adobe + quant
        )   
    }
}