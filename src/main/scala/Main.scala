import inventory.Inventory
import functions._
import build._
import citadel.Citadel
import buildorder.BuildOrder
import java.time.LocalDate
import request.Request

object Main extends App {
    val finishedBuilds : List[(Build, (Integer, Integer))] = List()
    val ordersInProgress : List[BuildOrder] = List()
    val requests : List[Request] = List()
    val inventory = Inventory(1000, 1000, 1000, 500, 1000)
    val day = LocalDate.now().getDayOfMonth()
    val month = LocalDate.now().getMonthValue()
    val year = LocalDate.now().getYear()
    val citadel = Citadel(finishedBuilds, ordersInProgress, requests, inventory, day, month, year)
    val citadel2 = Functions.createRequest(citadel, Edificio, 5, 5)
    val citadel3 = Functions.createRequest(citadel2, Casa, 6, 6)
    val citadel4 = Functions.createRequest(citadel3, Lago, 7, 7)
    val citadel5 = Functions.createRequest(citadel4, Gimnasio, 8, 8)
    val citadel6 = Functions.updateOrders(citadel5)
    val citadel7 = Functions.updateOrders(citadel6)
    val citadel8 = Functions.updateOrders(citadel7)
    Utils.showInformation(citadel8)
}